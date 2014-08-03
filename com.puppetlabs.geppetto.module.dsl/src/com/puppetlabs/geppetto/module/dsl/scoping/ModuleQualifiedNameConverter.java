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
	public QualifiedName toQualifiedName(final String qualifiedNameAsString) {
		try {
			ModuleName mn = ModuleName.create(qualifiedNameAsString, false);
			if(mn != null)
				return QualifiedName.create(mn.getOwner(), mn.getName());
		}
		catch(IllegalArgumentException e) {
		}
		return null;
	}
}
