package com.puppetlabs.geppetto.module.dsl.ui.builder

import com.google.inject.Singleton
import com.puppetlabs.geppetto.diagnostic.Diagnostic
import com.puppetlabs.geppetto.diagnostic.ExceptionDiagnostic
import com.puppetlabs.geppetto.diagnostic.FileDiagnostic
import com.puppetlabs.geppetto.forge.Forge
import com.puppetlabs.geppetto.module.dsl.ModuleUtil
import com.puppetlabs.geppetto.module.dsl.metadata.JsonMetadata
import com.puppetlabs.geppetto.module.dsl.metadata.MetadataPackage
import com.puppetlabs.geppetto.module.dsl.ui.ModuleProjectsState
import com.puppetlabs.geppetto.module.dsl.validation.IModuleValidationAdvisor
import com.puppetlabs.geppetto.pp.dsl.ui.preferences.RebuildChecker
import com.puppetlabs.geppetto.pp.dsl.validation.ValidationPreference
import java.io.IOException
import java.util.List
import javax.inject.Inject
import org.apache.log4j.Logger
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IMarker
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IResource
import org.eclipse.core.resources.IWorkspaceRoot
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.Path
import org.eclipse.core.runtime.SubMonitor
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.builder.BuilderParticipant

@Singleton
class ModuleBuildParticipant extends BuilderParticipant {

	public static val PUPPET_MODULE_PROBLEM_MARKER_TYPE = "com.puppetlabs.geppetto.module.dsl.ui.puppetModuleProblem"

	static val log = Logger.getLogger(ModuleBuildParticipant)

	@Inject
	extension ModuleUtil

	val RebuildChecker rebuildChecker

	val Forge forge

	val IWorkspaceRoot workspaceRoot

	val IModuleValidationAdvisor validationAdvisor

	@Inject
	new(Forge forge, IWorkspaceRoot workspaceRoot, IModuleValidationAdvisor validationAdvisor,
		RebuildChecker rebuildChecker) {
		this.forge = forge
		this.workspaceRoot = workspaceRoot
		this.validationAdvisor = validationAdvisor
		this.rebuildChecker = rebuildChecker
	}

	override build(IBuildContext context, IProgressMonitor monitor) throws CoreException {
		val subMon = SubMonitor.convert(monitor, 10)
		super.build(context, subMon.newChild(7))
		val proj = context.builtProject
		proj.removeErrorMarkers
		if (ModuleProjectsState.isAccessiblePuppetProject(proj)) {
			for (delta : context.deltas) {
				var dsc = delta.^new
				if (dsc == null)
					dsc = delta.old
				for (objDesc : dsc.getExportedObjectsByType(MetadataPackage.Literals.JSON_METADATA)) {
					var obj = objDesc.EObjectOrProxy
					if (obj.eIsProxy)
						obj = EcoreUtil2.resolve(obj, context.resourceSet)
					if (!obj.eIsProxy)
						proj.syncProjectReferences((obj as JsonMetadata).projectReferences, subMon.newChild(1))
				}
			}
			proj.legacyCheck(subMon.newChild(2))
		}
		monitor.done()
	}

	/**
	 * This check takes care of the following:<ol>
	 * <li>Emits warnings on existing Modulefile</li>
	 * <li>Removes derived status from existing metadata.json file</li>
	 * <li>Generates a metadata.json file if it's missing and the Modulefile is present</li>
	 * </ol> 
	 */
	def private legacyCheck(IProject project, SubMonitor subMon) {
		subMon.workRemaining = 2
		if (validationAdvisor.modulefileExists !== ValidationPreference.IGNORE) {
			val moduleFile = project.getFile(Forge.MODULEFILE_NAME)
			if (moduleFile.accessible)
				moduleFile.onModulefileDetected(subMon.newChild(1))
			else
				subMon.worked(1)
		}

		val mdJsonFile = project.getFile(Forge.METADATA_JSON_NAME)
		if (mdJsonFile.derived)
			mdJsonFile.setDerived(false, subMon.newChild(1))
		else
			subMon.worked(1)
	}

	/**
	 * Creates module problem markers from the given diagnostic
	 */
	def private void createResourceMarkers(IResource r, Diagnostic diagnostic) throws CoreException {
		if (!r.accessible)
			return;

		for (child : diagnostic)
			r.createResourceMarkers(child)

		val msg = diagnostic.message
		if (msg == null)
			return

		val m = r.createMarker(PUPPET_MODULE_PROBLEM_MARKER_TYPE)
		m.setAttribute(IMarker.MESSAGE, msg)
		m.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH)
		m.setAttribute(IMarker.SEVERITY, diagnostic.markerSeverity)
		m.setAttribute(IMarker.LOCATION, r.name)
		if (diagnostic instanceof FileDiagnostic)
			m.setAttribute(IMarker.LINE_NUMBER, diagnostic.lineNumber)
	}

	def private getMarkerSeverity(Diagnostic diagnostic) {
		var int severity
		switch diagnostic.getSeverity() {
			case Diagnostic.FATAL, case Diagnostic.ERROR:
				severity = IMarker.SEVERITY_ERROR
			case Diagnostic.WARNING:
				severity = IMarker.SEVERITY_WARNING
			default:
				severity = IMarker.SEVERITY_INFO
		}
		severity
	}

	def private getDiagnosticSeverity(ValidationPreference pref) {
		var int severity
		switch (pref) {
			case WARNING:
				severity = Diagnostic.WARNING
			case ERROR:
				severity = Diagnostic.ERROR
			default:
				severity = Diagnostic.OK
		}
		severity
	}

	def private void onModulefileDetected(IFile moduleFile, SubMonitor monitor) throws CoreException {
		val parentDir = moduleFile.parent
		val metadataJSON = parentDir.getFile(Path.fromPortableString(Forge.METADATA_JSON_NAME))
		val diag = new Diagnostic()

		if (metadataJSON.accessible)
			diag.addChild(
				new Diagnostic(validationAdvisor.modulefileExists.diagnosticSeverity, Forge.FORGE,
					"Modulefile is deprecated. Using metadata.json"))
		else {
			diag.addChild(
				new Diagnostic(validationAdvisor.modulefileExistsAndIsUsed.diagnosticSeverity, Forge.FORGE,
					"Modulefile is deprecated. Building metadata.json from modulefile"))
			try {
				val md = forge.loadModulefile(moduleFile.fullPath.toFile, diag)
				if (md != null && diag.severity < Diagnostic.ERROR) {
					forge.saveJSONMetadata(md, metadataJSON.fullPath.toFile)
					parentDir.refreshLocal(IResource.DEPTH_ONE, monitor)
				}
			} catch (IOException e) {
				diag.addChild(
					new ExceptionDiagnostic(Diagnostic.ERROR, Forge.FORGE,
						"Unable to create metadata.json from Modulefile", e))
			}
		}
		moduleFile.createResourceMarkers(diag)
	}

	/**
	 * Deletes all puppet module problem markers set by this builder.
	 */
	def private void removeErrorMarkers(IProject project) throws CoreException {
		project.deleteMarkers(PUPPET_MODULE_PROBLEM_MARKER_TYPE, true, IResource.DEPTH_INFINITE)
	}

	def private getAccessibleProject(JsonMetadata metadata) {
		try {
			val name = metadata.name
			if (name !== null) {
				val proj = workspaceRoot.getProject(name.name)
				if (ModuleProjectsState.isAccessiblePuppetProject(proj))
					return proj
			}
		} catch (IllegalArgumentException e) {
		}
		null
	}

	def private getProjectReferences(JsonMetadata metadata) {
		val wanted = newArrayList(workspaceRoot.getProject('.com_puppetlabs_geppetto_pptp_target'))
		for (refMd : metadata.resolvedDependencies) {
			val proj = refMd.accessibleProject
			if (proj !== null)
				wanted.add(proj)
		}
		return wanted
	}

	def private void syncProjectReferences(IProject project, List<IProject> wanted, SubMonitor subMon) {
		try {
			subMon.workRemaining = 2
			project.refreshLocal(IResource.DEPTH_INFINITE, subMon.newChild(1))
			val description = project.description
			val current = newHashSet(description.dynamicReferences)
			if (!(current.size() === wanted.size() && current.containsAll(wanted))) {

				// not in sync, set them
				description.dynamicReferences = wanted.toArray(newArrayOfSize(wanted.size()))
				project.setDescription(description, subMon.newChild(1))

				// We need a full build when dependencies change
				//
				rebuildChecker.triggerBuild
			}
		} catch (CoreException e) {
			log.error("Can not sync project's dynamic dependencies", e)
		}
	}
}
