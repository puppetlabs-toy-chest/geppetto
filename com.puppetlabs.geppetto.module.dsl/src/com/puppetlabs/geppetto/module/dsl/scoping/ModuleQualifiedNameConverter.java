package com.puppetlabs.geppetto.module.dsl.scoping;

import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;

import com.google.inject.Singleton;
import com.puppetlabs.geppetto.forge.model.ModuleName;

/**
 * Qualified names conforming to the puppet module name syntax. Accepts both
 * '-' and '/' as separators
 */
@Singleton
public class ModuleQualifiedNameConverter extends IQualifiedNameConverter.DefaultImpl {
	@Override
	public String getDelimiter() {
		return "-";
	}

	@Override
	public QualifiedName toQualifiedName(final String fullName) {
		if(fullName == null)
			return null;
		String[] split = ModuleName.splitName(fullName);
		return split[0] == null
			? QualifiedName.create(split[1])
			: QualifiedName.create(split);
	}
}
