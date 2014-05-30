package com.puppetlabs.geppetto.module.dsl.ui;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.inject.Singleton;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.eclipse.xtext.ui.XtextProjectHelper;
import org.eclipse.xtext.ui.containers.AbstractAllContainersState;
import org.eclipse.xtext.ui.resource.IStorage2UriMapper;
import org.eclipse.xtext.xbase.lib.Exceptions;

/**
 * An {@link IAllContainersState} that considers all projects with nature {@link #PUPPET_NATURE}
 * as candidates
 */
@Singleton
@SuppressWarnings("all")
public class ModuleProjectsState extends AbstractAllContainersState {
  public final static String PUPPET_NATURE = "com.puppetlabs.geppetto.pp.dsl.ui.puppetNature";
  
  private final static Logger log = Logger.getLogger(ModuleProjectsState.class);
  
  public static boolean isAccessiblePuppetProject(final IProject p) {
    try {
      boolean _and = false;
      boolean _and_1 = false;
      boolean _notEquals = (!Objects.equal(p, null));
      if (!_notEquals) {
        _and_1 = false;
      } else {
        boolean _hasNature = XtextProjectHelper.hasNature(p);
        _and_1 = _hasNature;
      }
      if (!_and_1) {
        _and = false;
      } else {
        boolean _hasNature_1 = p.hasNature(ModuleProjectsState.PUPPET_NATURE);
        _and = _hasNature_1;
      }
      return _and;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public Collection<URI> doInitContainedURIs(final String containerHandle) {
    List<URI> _xblockexpression = null;
    {
      try {
        Path _path = new Path(null, containerHandle);
        final IPath projectPath = _path.makeAbsolute();
        int _segmentCount = projectPath.segmentCount();
        boolean _equals = (_segmentCount == 1);
        if (_equals) {
          IWorkspaceRoot _workspaceRoot = this.getWorkspaceRoot();
          final IProject project = _workspaceRoot.getProject(containerHandle);
          boolean _isAccessiblePuppetProject = ModuleProjectsState.isAccessiblePuppetProject(project);
          if (_isAccessiblePuppetProject) {
            IStorage2UriMapper _mapper = this.getMapper();
            Map<URI, IStorage> _allEntries = _mapper.getAllEntries(project);
            return _allEntries.keySet();
          }
        }
      } catch (final Throwable _t) {
        if (_t instanceof IllegalArgumentException) {
          final IllegalArgumentException e = (IllegalArgumentException)_t;
          ModuleProjectsState.log.warn((("Cannot init contained URIs for containerHandle \'" + containerHandle) + "\'"), e);
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
      _xblockexpression = Collections.<URI>emptyList();
    }
    return _xblockexpression;
  }
  
  protected String doInitHandle(final URI uri) {
    String _xblockexpression = null;
    {
      boolean _isPlatform = uri.isPlatform();
      if (_isPlatform) {
        IWorkspaceRoot _workspaceRoot = this.getWorkspaceRoot();
        String _platformString = uri.toPlatformString(true);
        Path _path = new Path(_platformString);
        final IFile file = _workspaceRoot.getFile(_path);
        boolean _notEquals = (!Objects.equal(file, null));
        if (_notEquals) {
          IProject _project = file.getProject();
          return _project.getName();
        }
      }
      _xblockexpression = null;
    }
    return _xblockexpression;
  }
  
  protected List<String> doInitVisibleHandles(final String handle) {
    List<String> _xblockexpression = null;
    {
      try {
        IWorkspaceRoot _workspaceRoot = this.getWorkspaceRoot();
        final IProject project = _workspaceRoot.getProject(handle);
        boolean _isAccessiblePuppetProject = ModuleProjectsState.isAccessiblePuppetProject(project);
        if (_isAccessiblePuppetProject) {
          final ArrayList<String> result = Lists.<String>newArrayList();
          result.add(handle);
          IWorkspaceRoot _workspaceRoot_1 = this.getWorkspaceRoot();
          IProject[] _projects = _workspaceRoot_1.getProjects(0);
          for (final IProject referencedProject : _projects) {
            boolean _isAccessiblePuppetProject_1 = ModuleProjectsState.isAccessiblePuppetProject(referencedProject);
            if (_isAccessiblePuppetProject_1) {
              String _name = referencedProject.getName();
              result.add(_name);
            }
          }
          return result;
        }
      } catch (final Throwable _t) {
        if (_t instanceof IllegalArgumentException) {
          final IllegalArgumentException e = (IllegalArgumentException)_t;
          ModuleProjectsState.log.warn((("Cannot init visible handles for containerHandle \'" + handle) + "\'"), e);
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
      _xblockexpression = Collections.<String>emptyList();
    }
    return _xblockexpression;
  }
}
