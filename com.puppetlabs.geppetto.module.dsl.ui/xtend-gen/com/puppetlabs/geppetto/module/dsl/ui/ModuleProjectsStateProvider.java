package com.puppetlabs.geppetto.module.dsl.ui;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.puppetlabs.geppetto.module.dsl.ui.ModuleProjectsState;
import org.eclipse.xtext.resource.containers.IAllContainersState;

@SuppressWarnings("all")
public class ModuleProjectsStateProvider implements Provider<IAllContainersState> {
  @Inject
  private Provider<ModuleProjectsState> stateProvider;
  
  public IAllContainersState get() {
    return this.stateProvider.get();
  }
}
