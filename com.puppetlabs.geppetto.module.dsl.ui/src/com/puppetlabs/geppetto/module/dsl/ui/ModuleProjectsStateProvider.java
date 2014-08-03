package com.puppetlabs.geppetto.module.dsl.ui;

import org.eclipse.xtext.resource.containers.IAllContainersState;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class ModuleProjectsStateProvider implements Provider<IAllContainersState> {
	@Inject
	private Provider<ModuleProjectsState> stateProvider;

	@Override
	public IAllContainersState get() {
		return this.stateProvider.get();
	}
}
