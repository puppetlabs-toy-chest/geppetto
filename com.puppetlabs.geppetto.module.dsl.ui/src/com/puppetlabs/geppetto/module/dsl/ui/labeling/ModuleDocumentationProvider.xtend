package com.puppetlabs.geppetto.module.dsl.ui.labeling

import org.eclipse.xtext.documentation.IEObjectDocumentationProvider
import org.eclipse.emf.ecore.EObject

class ModuleDocumentationProvider implements IEObjectDocumentationProvider {
	override getDocumentation(EObject o) {
		if(o instanceof com.puppetlabs.geppetto.module.dsl.metadata.Pair)
			'Hello pair of class ' + o.class.name
	}
}