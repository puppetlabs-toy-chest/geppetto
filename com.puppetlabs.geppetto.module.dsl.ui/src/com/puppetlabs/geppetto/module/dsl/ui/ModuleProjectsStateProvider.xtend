package com.puppetlabs.geppetto.module.dsl.ui

import com.google.inject.Provider
import org.eclipse.xtext.resource.containers.IAllContainersState
import com.google.inject.Inject

class ModuleProjectsStateProvider implements Provider<IAllContainersState> {
	@Inject
	Provider<ModuleProjectsState> stateProvider;

	override get() {
		return stateProvider.get();
	}

}
