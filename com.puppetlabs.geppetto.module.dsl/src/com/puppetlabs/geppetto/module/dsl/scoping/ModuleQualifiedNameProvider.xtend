package com.puppetlabs.geppetto.module.dsl.scoping;

import com.google.inject.Inject
import com.puppetlabs.geppetto.module.dsl.ModuleUtil
import com.puppetlabs.geppetto.module.dsl.metadata.JsonMetadata
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.naming.QualifiedName

public class ModuleQualifiedNameProvider extends IQualifiedNameProvider.AbstractImpl {

 	@Inject
	extension ModuleUtil

	override getFullyQualifiedName(EObject obj) {
		if (obj instanceof JsonMetadata) {
			var moduleName = obj.name
			if(moduleName != null)
				return QualifiedName.create(moduleName.owner, moduleName.name)
		}
		null
	}
}
