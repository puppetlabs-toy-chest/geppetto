package com.puppetlabs.geppetto.module.dsl.ui;

import static com.puppetlabs.geppetto.module.dsl.ui.internal.ModuleActivator.*

class ModuleInjectorProvider {
	def getInjector() {
		instance.getInjector(COM_PUPPETLABS_GEPPETTO_MODULE_DSL_MODULE)
	}
}
