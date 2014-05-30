package com.puppetlabs.geppetto.module.dsl.scoping

import org.eclipse.xtext.naming.IQualifiedNameConverter
import com.puppetlabs.geppetto.forge.model.ModuleName
import org.eclipse.xtext.naming.QualifiedName

/**
 * Qualified names conforming to the puppet module name syntax. Accepts both
 * '-' and '/' as separators
 */
class ModuleQualifiedNameConverter extends IQualifiedNameConverter.DefaultImpl {
	override getDelimiter() {
		return '-'
	}

	override toQualifiedName(String qualifiedNameAsString) {
		try {
			val mn = ModuleName.create(qualifiedNameAsString, false)
			if(mn !== null)
				return QualifiedName.create(mn.owner, mn.name)
		} catch (IllegalArgumentException e) {
		}
		null
	}
}
