package com.puppetlabs.geppetto.module.dsl.ui.builder;

import com.google.common.base.Objects;
import com.google.inject.Singleton;
import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.diagnostic.ExceptionDiagnostic;
import com.puppetlabs.geppetto.diagnostic.FileDiagnostic;
import com.puppetlabs.geppetto.forge.Forge;
import com.puppetlabs.geppetto.forge.model.Metadata;
import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.module.dsl.ModuleUtil;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonMetadata;
import com.puppetlabs.geppetto.module.dsl.metadata.MetadataPackage;
import com.puppetlabs.geppetto.module.dsl.ui.ModuleProjectsState;
import com.puppetlabs.geppetto.module.dsl.validation.IModuleValidationAdvisor;
import com.puppetlabs.geppetto.pp.dsl.ui.preferences.RebuildChecker;
import com.puppetlabs.geppetto.pp.dsl.validation.ValidationPreference;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.inject.Inject;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.builder.BuilderParticipant;
import org.eclipse.xtext.builder.IXtextBuilderParticipant;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;

@Singleton
@SuppressWarnings("all")
public class ModuleBuildParticipant extends BuilderParticipant {
  public final static String PUPPET_MODULE_PROBLEM_MARKER_TYPE = "com.puppetlabs.geppetto.module.dsl.ui.puppetModuleProblem";
  
  public final static String PUPPET_MODULE_PROBLEM_MARKER_CHECK_FAST = "com.puppetlabs.geppetto.module.dsl.ui.module.check.fast";
  
  public final static String PUPPET_MODULE_PROBLEM_MARKER_CHECK_NORMAL = "com.puppetlabs.geppetto.module.dsl.ui.module.check.normal";
  
  public final static String PUPPET_MODULE_PROBLEM_MARKER_CHECK_EXPENSIVE = "com.puppetlabs.geppetto.module.dsl.ui.module.check.expensive";
  
  @Inject
  @Extension
  private ModuleUtil _moduleUtil;
  
  private final RebuildChecker rebuildChecker;
  
  private final Forge forge;
  
  private final IWorkspaceRoot workspaceRoot;
  
  private final IModuleValidationAdvisor validationAdvisor;
  
  @Inject
  public ModuleBuildParticipant(final Forge forge, final IWorkspaceRoot workspaceRoot, final IModuleValidationAdvisor validationAdvisor, final RebuildChecker rebuildChecker) {
    this.forge = forge;
    this.workspaceRoot = workspaceRoot;
    this.validationAdvisor = validationAdvisor;
    this.rebuildChecker = rebuildChecker;
  }
  
  public void build(final IXtextBuilderParticipant.IBuildContext context, final IProgressMonitor monitor) throws CoreException {
    final SubMonitor subMon = SubMonitor.convert(monitor, 10);
    final IProject proj = context.getBuiltProject();
    IXtextBuilderParticipant.BuildType _buildType = context.getBuildType();
    boolean _tripleEquals = (_buildType == IXtextBuilderParticipant.BuildType.CLEAN);
    if (_tripleEquals) {
      this.removeErrorMarkers(proj);
    }
    SubMonitor _newChild = subMon.newChild(7);
    super.build(context, _newChild);
    boolean _isAccessiblePuppetProject = ModuleProjectsState.isAccessiblePuppetProject(proj);
    if (_isAccessiblePuppetProject) {
      List<IResourceDescription.Delta> _deltas = context.getDeltas();
      for (final IResourceDescription.Delta delta : _deltas) {
        {
          IResourceDescription dsc = delta.getNew();
          boolean _equals = Objects.equal(dsc, null);
          if (_equals) {
            IResourceDescription _old = delta.getOld();
            dsc = _old;
          }
          Iterable<IEObjectDescription> _exportedObjectsByType = dsc.getExportedObjectsByType(MetadataPackage.Literals.JSON_METADATA);
          for (final IEObjectDescription objDesc : _exportedObjectsByType) {
            {
              EObject obj = objDesc.getEObjectOrProxy();
              boolean _eIsProxy = obj.eIsProxy();
              if (_eIsProxy) {
                ResourceSet _resourceSet = context.getResourceSet();
                EObject _resolve = EcoreUtil2.resolve(obj, _resourceSet);
                obj = _resolve;
              }
              boolean _eIsProxy_1 = obj.eIsProxy();
              boolean _not = (!_eIsProxy_1);
              if (_not) {
                ArrayList<IProject> _projectReferences = this.getProjectReferences(((JsonMetadata) obj));
                SubMonitor _newChild_1 = subMon.newChild(1);
                this.syncProjectReferences(proj, _projectReferences, _newChild_1);
              }
            }
          }
        }
      }
      SubMonitor _newChild_1 = subMon.newChild(2);
      this.legacyCheck(proj, _newChild_1);
    }
    monitor.done();
  }
  
  /**
   * This check takes care of the following:<ol>
   * <li>Emits warnings on existing Modulefile</li>
   * <li>Removes derived status from existing metadata.json file</li>
   * <li>Generates a metadata.json file if it's missing and the Modulefile is present</li>
   * </ol>
   */
  private void legacyCheck(final IProject project, final SubMonitor subMon) throws CoreException {
    subMon.setWorkRemaining(2);
    final IFile moduleFile = project.getFile(Forge.MODULEFILE_NAME);
    boolean _isAccessible = moduleFile.isAccessible();
    if (_isAccessible) {
      SubMonitor _newChild = subMon.newChild(1);
      this.onModulefileDetected(moduleFile, _newChild);
    } else {
      subMon.worked(1);
    }
    final IFile mdJsonFile = project.getFile(Forge.METADATA_JSON_NAME);
    boolean _isDerived = mdJsonFile.isDerived();
    if (_isDerived) {
      SubMonitor _newChild_1 = subMon.newChild(1);
      mdJsonFile.setDerived(false, _newChild_1);
    } else {
      subMon.worked(1);
    }
  }
  
  /**
   * Creates module problem markers from the given diagnostic
   */
  private void createResourceMarkers(final IResource r, final Diagnostic diagnostic) throws CoreException {
    boolean _isAccessible = r.isAccessible();
    boolean _not = (!_isAccessible);
    if (_not) {
      return;
    }
    for (final Diagnostic child : diagnostic) {
      this.createResourceMarkers(r, child);
    }
    final String msg = diagnostic.getMessage();
    boolean _equals = Objects.equal(msg, null);
    if (_equals) {
      return;
    }
    final IMarker m = r.createMarker(ModuleBuildParticipant.PUPPET_MODULE_PROBLEM_MARKER_TYPE);
    m.setAttribute(IMarker.MESSAGE, msg);
    m.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
    int _markerSeverity = this.getMarkerSeverity(diagnostic);
    m.setAttribute(IMarker.SEVERITY, _markerSeverity);
    String _name = r.getName();
    m.setAttribute(IMarker.LOCATION, _name);
    if ((diagnostic instanceof FileDiagnostic)) {
      int _lineNumber = ((FileDiagnostic)diagnostic).getLineNumber();
      m.setAttribute(IMarker.LINE_NUMBER, _lineNumber);
    }
  }
  
  private int getMarkerSeverity(final Diagnostic diagnostic) {
    int _xblockexpression = (int) 0;
    {
      int severity = 0;
      int _severity = diagnostic.getSeverity();
      switch (_severity) {
        case Diagnostic.FATAL:
        case Diagnostic.ERROR:
          severity = IMarker.SEVERITY_ERROR;
          break;
        case Diagnostic.WARNING:
          severity = IMarker.SEVERITY_WARNING;
          break;
        default:
          severity = IMarker.SEVERITY_INFO;
          break;
      }
      _xblockexpression = severity;
    }
    return _xblockexpression;
  }
  
  private int getDiagnosticSeverity(final ValidationPreference pref) {
    int _xblockexpression = (int) 0;
    {
      int severity = 0;
      if (pref != null) {
        switch (pref) {
          case WARNING:
            severity = Diagnostic.WARNING;
            break;
          case ERROR:
            severity = Diagnostic.ERROR;
            break;
          default:
            severity = Diagnostic.OK;
            break;
        }
      } else {
        severity = Diagnostic.OK;
      }
      _xblockexpression = severity;
    }
    return _xblockexpression;
  }
  
  public static File toLocalFile(final IFile file, final IProgressMonitor monitor) throws CoreException {
    URI _locationURI = file.getLocationURI();
    IFileStore _store = EFS.getStore(_locationURI);
    return _store.toLocalFile(EFS.NONE, monitor);
  }
  
  private void onModulefileDetected(final IFile moduleFile, final SubMonitor monitor) throws CoreException {
    final IContainer parentDir = moduleFile.getParent();
    IPath _fromPortableString = Path.fromPortableString(Forge.METADATA_JSON_NAME);
    final IFile metadataJSON = parentDir.getFile(_fromPortableString);
    final Diagnostic diag = new Diagnostic();
    boolean _isAccessible = metadataJSON.isAccessible();
    if (_isAccessible) {
      ValidationPreference _modulefileExists = this.validationAdvisor.getModulefileExists();
      boolean _tripleNotEquals = (_modulefileExists != ValidationPreference.IGNORE);
      if (_tripleNotEquals) {
        ValidationPreference _modulefileExists_1 = this.validationAdvisor.getModulefileExists();
        int _diagnosticSeverity = this.getDiagnosticSeverity(_modulefileExists_1);
        Diagnostic _diagnostic = new Diagnostic(_diagnosticSeverity, Forge.FORGE, 
          "Modulefile is deprecated. Using metadata.json");
        diag.addChild(_diagnostic);
      }
    } else {
      ValidationPreference _modulefileExistsAndIsUsed = this.validationAdvisor.getModulefileExistsAndIsUsed();
      boolean _tripleNotEquals_1 = (_modulefileExistsAndIsUsed != ValidationPreference.IGNORE);
      if (_tripleNotEquals_1) {
        ValidationPreference _modulefileExistsAndIsUsed_1 = this.validationAdvisor.getModulefileExistsAndIsUsed();
        int _diagnosticSeverity_1 = this.getDiagnosticSeverity(_modulefileExistsAndIsUsed_1);
        Diagnostic _diagnostic_1 = new Diagnostic(_diagnosticSeverity_1, Forge.FORGE, 
          "Modulefile is deprecated. Building metadata.json from modulefile");
        diag.addChild(_diagnostic_1);
      }
      try {
        SubMonitor _newChild = monitor.newChild(1);
        File _localFile = ModuleBuildParticipant.toLocalFile(moduleFile, _newChild);
        final Metadata md = this.forge.loadModulefile(_localFile, diag);
        boolean _and = false;
        boolean _notEquals = (!Objects.equal(md, null));
        if (!_notEquals) {
          _and = false;
        } else {
          int _severity = diag.getSeverity();
          boolean _lessThan = (_severity < Diagnostic.ERROR);
          _and = _lessThan;
        }
        if (_and) {
          SubMonitor _newChild_1 = monitor.newChild(1);
          File _localFile_1 = ModuleBuildParticipant.toLocalFile(metadataJSON, _newChild_1);
          this.forge.saveJSONMetadata(md, _localFile_1);
          SubMonitor _newChild_2 = monitor.newChild(1);
          parentDir.refreshLocal(IResource.DEPTH_ONE, _newChild_2);
        }
      } catch (final Throwable _t) {
        if (_t instanceof IOException) {
          final IOException e = (IOException)_t;
          String _message = e.getMessage();
          String _plus = ("Unable to create metadata.json from Modulefile: " + _message);
          ExceptionDiagnostic _exceptionDiagnostic = new ExceptionDiagnostic(Diagnostic.ERROR, Forge.FORGE, _plus, e);
          diag.addChild(_exceptionDiagnostic);
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    }
    this.createResourceMarkers(moduleFile, diag);
  }
  
  /**
   * Deletes all puppet module problem markers set by this builder.
   */
  private void removeErrorMarkers(final IProject project) throws CoreException {
    project.deleteMarkers(ModuleBuildParticipant.PUPPET_MODULE_PROBLEM_MARKER_TYPE, true, IResource.DEPTH_INFINITE);
    project.deleteMarkers(ModuleBuildParticipant.PUPPET_MODULE_PROBLEM_MARKER_CHECK_FAST, true, IResource.DEPTH_INFINITE);
    project.deleteMarkers(ModuleBuildParticipant.PUPPET_MODULE_PROBLEM_MARKER_CHECK_NORMAL, true, IResource.DEPTH_INFINITE);
    project.deleteMarkers(ModuleBuildParticipant.PUPPET_MODULE_PROBLEM_MARKER_CHECK_EXPENSIVE, true, IResource.DEPTH_INFINITE);
  }
  
  private IProject getAccessibleProject(final JsonMetadata metadata) {
    IProject _xblockexpression = null;
    {
      try {
        final ModuleName name = this._moduleUtil.getName(metadata);
        boolean _tripleNotEquals = (name != null);
        if (_tripleNotEquals) {
          String _name = name.getName();
          final IProject proj = this.workspaceRoot.getProject(_name);
          boolean _isAccessiblePuppetProject = ModuleProjectsState.isAccessiblePuppetProject(proj);
          if (_isAccessiblePuppetProject) {
            return proj;
          }
        }
      } catch (final Throwable _t) {
        if (_t instanceof IllegalArgumentException) {
          final IllegalArgumentException e = (IllegalArgumentException)_t;
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
      _xblockexpression = null;
    }
    return _xblockexpression;
  }
  
  private ArrayList<IProject> getProjectReferences(final JsonMetadata metadata) {
    IProject _project = this.workspaceRoot.getProject(".com_puppetlabs_geppetto_pptp_target");
    final ArrayList<IProject> wanted = CollectionLiterals.<IProject>newArrayList(_project);
    List<JsonMetadata> _resolvedDependencies = this._moduleUtil.getResolvedDependencies(metadata);
    for (final JsonMetadata refMd : _resolvedDependencies) {
      {
        final IProject proj = this.getAccessibleProject(refMd);
        boolean _tripleNotEquals = (proj != null);
        if (_tripleNotEquals) {
          wanted.add(proj);
        }
      }
    }
    return wanted;
  }
  
  private void syncProjectReferences(final IProject project, final List<IProject> wanted, final SubMonitor subMon) throws CoreException {
    subMon.setWorkRemaining(2);
    SubMonitor _newChild = subMon.newChild(1);
    project.refreshLocal(IResource.DEPTH_INFINITE, _newChild);
    final IProjectDescription description = project.getDescription();
    IProject[] _dynamicReferences = description.getDynamicReferences();
    final HashSet<IProject> current = CollectionLiterals.<IProject>newHashSet(_dynamicReferences);
    boolean _and = false;
    int _size = current.size();
    int _size_1 = wanted.size();
    boolean _tripleEquals = (_size == _size_1);
    if (!_tripleEquals) {
      _and = false;
    } else {
      boolean _containsAll = current.containsAll(wanted);
      _and = _containsAll;
    }
    boolean _not = (!_and);
    if (_not) {
      int _size_2 = wanted.size();
      IProject[] _newArrayOfSize = new IProject[_size_2];
      IProject[] _array = wanted.<IProject>toArray(_newArrayOfSize);
      description.setDynamicReferences(_array);
      int _bitwiseOr = (IResource.FORCE | IResource.KEEP_HISTORY);
      SubMonitor _newChild_1 = subMon.newChild(1);
      project.setDescription(description, _bitwiseOr, _newChild_1);
      this.rebuildChecker.triggerBuild();
    }
  }
}
