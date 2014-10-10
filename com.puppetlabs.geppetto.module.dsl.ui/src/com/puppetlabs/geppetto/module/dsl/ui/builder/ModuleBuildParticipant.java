package com.puppetlabs.geppetto.module.dsl.ui.builder;

import static com.puppetlabs.geppetto.pp.dsl.validation.ValidationPreference.IGNORE;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.builder.BuilderParticipant;
import org.eclipse.xtext.builder.IXtextBuilderParticipant;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;

import com.google.common.collect.Sets;
import com.google.inject.Singleton;
import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.diagnostic.ExceptionDiagnostic;
import com.puppetlabs.geppetto.diagnostic.FileDiagnostic;
import com.puppetlabs.geppetto.forge.Forge;
import com.puppetlabs.geppetto.forge.model.Metadata;
import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.module.dsl.ModuleUtil;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonMetadata;
import com.puppetlabs.geppetto.module.dsl.metadata.MetadataPackage.Literals;
import com.puppetlabs.geppetto.module.dsl.ui.ModuleProjectsState;
import com.puppetlabs.geppetto.module.dsl.validation.IModuleValidationAdvisor;
import com.puppetlabs.geppetto.pp.dsl.ui.preferences.RebuildChecker;
import com.puppetlabs.geppetto.pp.dsl.validation.ValidationPreference;

@Singleton
public class ModuleBuildParticipant extends BuilderParticipant {
	public static File toLocalFile(IFile file, IProgressMonitor monitor) throws CoreException {
		return EFS.getStore(file.getLocationURI()).toLocalFile(EFS.NONE, monitor);
	}

	public static String PUPPET_MODULE_PROBLEM_MARKER_TYPE = "com.puppetlabs.geppetto.module.dsl.ui.puppetModuleProblem";

	public static String PUPPET_MODULE_PROBLEM_MARKER_CHECK_FAST = "com.puppetlabs.geppetto.module.dsl.ui.module.check.fast";

	public static String PUPPET_MODULE_PROBLEM_MARKER_CHECK_NORMAL = "com.puppetlabs.geppetto.module.dsl.ui.module.check.normal";

	public static String PUPPET_MODULE_PROBLEM_MARKER_CHECK_EXPENSIVE = "com.puppetlabs.geppetto.module.dsl.ui.module.check.expensive";

	private final ModuleUtil moduleUtil;

	private final RebuildChecker rebuildChecker;

	private final Forge forge;

	private final IWorkspaceRoot workspaceRoot;

	private final IModuleValidationAdvisor validationAdvisor;

	@Inject
	public ModuleBuildParticipant(ModuleUtil moduleUtil, Forge forge, IWorkspaceRoot workspaceRoot,
			IModuleValidationAdvisor validationAdvisor, RebuildChecker rebuildChecker) {
		this.moduleUtil = moduleUtil;
		this.forge = forge;
		this.workspaceRoot = workspaceRoot;
		this.validationAdvisor = validationAdvisor;
		this.rebuildChecker = rebuildChecker;
	}

	private void addProjectReferences(JsonMetadata metadata, Set<IProject> wanted) {
		for(JsonMetadata refMd : moduleUtil.getResolvedDependencies(metadata)) {
			IProject proj = getAccessibleProject(refMd);
			if(proj != null)
				wanted.add(proj);
		}
	}

	@Override
	public void build(IXtextBuilderParticipant.IBuildContext context, IProgressMonitor monitor) throws CoreException {
		SubMonitor subMon = SubMonitor.convert(monitor, 10);
		IProject proj = context.getBuiltProject();
		if(context.getBuildType() == BuildType.CLEAN)
			removeErrorMarkers(proj);

		super.build(context, subMon.newChild(7));
		if(ModuleProjectsState.isAccessiblePuppetProject(proj)) {
			Set<IProject> referencedProjects = Sets.newHashSet(workspaceRoot.getProject(".com_puppetlabs_geppetto_pptp_target"));
			for(IResourceDescription.Delta delta : context.getDeltas()) {
				IResourceDescription dsc = delta.getNew();
				if(dsc == null)
					dsc = delta.getOld();
				for(IEObjectDescription objDesc : dsc.getExportedObjectsByType(Literals.JSON_METADATA)) {
					EObject obj = objDesc.getEObjectOrProxy();
					if(obj.eIsProxy())
						obj = EcoreUtil.resolve(obj, context.getResourceSet());
					if(!obj.eIsProxy())
						addProjectReferences((JsonMetadata) obj, referencedProjects);
				}
			}
			syncProjectReferences(proj, referencedProjects, subMon.newChild(1));
		}
		legacyCheck(proj, subMon.newChild(2));
		monitor.done();
	}

	/**
	 * Creates module problem markers from the given diagnostic
	 */
	private void createResourceMarkers(IResource r, Diagnostic diagnostic) throws CoreException {
		if(!r.isAccessible())
			return;

		for(Diagnostic child : diagnostic)
			createResourceMarkers(r, child);

		String msg = diagnostic.getMessage();
		if(msg == null)
			return;

		IMarker m = r.createMarker(ModuleBuildParticipant.PUPPET_MODULE_PROBLEM_MARKER_TYPE);
		m.setAttribute(IMarker.MESSAGE, msg);
		m.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
		m.setAttribute(IMarker.SEVERITY, getMarkerSeverity(diagnostic));
		m.setAttribute(IMarker.LOCATION, r.getName());
		if((diagnostic instanceof FileDiagnostic))
			m.setAttribute(IMarker.LINE_NUMBER, ((FileDiagnostic) diagnostic).getLineNumber());
	}

	private IProject getAccessibleProject(JsonMetadata metadata) {
		try {
			ModuleName name = moduleUtil.getName(metadata);
			if(name != null) {
				IProject proj = workspaceRoot.getProject(name.getName());
				if(ModuleProjectsState.isAccessiblePuppetProject(proj))
					return proj;
			}
		}
		catch(IllegalArgumentException e) {
		}
		return null;
	}

	private int getDiagnosticSeverity(ValidationPreference pref) {
		int severity;
		switch(pref) {
			case WARNING:
				severity = Diagnostic.WARNING;
				break;
			case ERROR:
				severity = Diagnostic.ERROR;
				break;
			default:
				severity = Diagnostic.OK;
		}
		return severity;
	}

	private int getMarkerSeverity(Diagnostic diagnostic) {
		int severity;
		switch(diagnostic.getSeverity()) {
			case Diagnostic.FATAL:
			case Diagnostic.ERROR:
				severity = IMarker.SEVERITY_ERROR;
				break;
			case Diagnostic.WARNING:
				severity = IMarker.SEVERITY_WARNING;
				break;
			default:
				severity = IMarker.SEVERITY_INFO;
		}
		return severity;
	}

	/**
	 * This check takes care of the following:
	 * <ol>
	 * <li>Emits warnings on existing Modulefile</li>
	 * <li>Removes derived status from existing metadata.json file</li>
	 * <li>Generates a metadata.json file if it's missing and the Modulefile is present</li>
	 * </ol>
	 */
	private void legacyCheck(IProject project, SubMonitor subMon) throws CoreException {
		subMon.setWorkRemaining(2);
		IFile moduleFile = project.getFile(Forge.MODULEFILE_NAME);
		if(moduleFile.isAccessible())
			onModulefileDetected(moduleFile, subMon.newChild(1));
		else
			subMon.worked(1);

		IFile mdJsonFile = project.getFile(Forge.METADATA_JSON_NAME);
		if(mdJsonFile.isDerived())
			mdJsonFile.setDerived(false, subMon.newChild(1));
		else
			subMon.worked(1);
	}

	private void onModulefileDetected(IFile moduleFile, SubMonitor monitor) throws CoreException {
		IContainer parentDir = moduleFile.getParent();
		IFile metadataJSON = parentDir.getFile(Path.fromPortableString(Forge.METADATA_JSON_NAME));
		Diagnostic diag = new Diagnostic();
		if(metadataJSON.isAccessible()) {
			if(validationAdvisor.getModulefileExists() != IGNORE)
				diag.addChild(new Diagnostic(
					getDiagnosticSeverity(validationAdvisor.getModulefileExists()), Forge.FORGE,
						"Modulefile is deprecated. Using metadata.json"));
		}
		else {
			if(validationAdvisor.getModulefileExistsAndIsUsed() != IGNORE)
				diag.addChild(new Diagnostic(
					getDiagnosticSeverity(validationAdvisor.getModulefileExistsAndIsUsed()), Forge.FORGE,
						"Modulefile is deprecated. Building metadata.json from modulefile"));

			try {
				Metadata md = forge.loadModulefile(ModuleBuildParticipant.toLocalFile(moduleFile, monitor.newChild(1)), diag);
				if(md != null && diag.getSeverity() < Diagnostic.ERROR) {
					forge.saveJSONMetadata(md, ModuleBuildParticipant.toLocalFile(metadataJSON, monitor.newChild(1)));
					parentDir.refreshLocal(IResource.DEPTH_ONE, monitor.newChild(1));
				}
			}
			catch(IOException e) {
				diag.addChild(new ExceptionDiagnostic(Diagnostic.ERROR, Forge.FORGE, "Unable to create metadata.json from Modulefile", e));
			}
		}
		createResourceMarkers(moduleFile, diag);
	}

	/**
	 * Deletes all puppet module problem markers set by this builder.
	 */
	private void removeErrorMarkers(IProject project) throws CoreException {
		project.deleteMarkers(PUPPET_MODULE_PROBLEM_MARKER_TYPE, true, IResource.DEPTH_INFINITE);
		project.deleteMarkers(PUPPET_MODULE_PROBLEM_MARKER_CHECK_FAST, true, IResource.DEPTH_INFINITE);
		project.deleteMarkers(PUPPET_MODULE_PROBLEM_MARKER_CHECK_NORMAL, true, IResource.DEPTH_INFINITE);
		project.deleteMarkers(PUPPET_MODULE_PROBLEM_MARKER_CHECK_EXPENSIVE, true, IResource.DEPTH_INFINITE);
	}

	private void syncProjectReferences(IProject project, Set<IProject> wanted, SubMonitor subMon) throws CoreException {
		subMon.setWorkRemaining(2);
		wanted.remove(project); // Avoid self references
		project.refreshLocal(IResource.DEPTH_INFINITE, subMon.newChild(1));
		IProjectDescription description = project.getDescription();
		HashSet<IProject> current = Sets.newHashSet(description.getDynamicReferences());
		if(!(current.size() == wanted.size() && current.containsAll(wanted))) {

			// not in sync, set them
			description.setDynamicReferences(wanted.toArray(new IProject[wanted.size()]));
			project.setDescription(description, IResource.FORCE | IResource.KEEP_HISTORY, subMon.newChild(1));

			// We need a full build when dependencies change
			//
			rebuildChecker.triggerBuild();
		}
	}
}
