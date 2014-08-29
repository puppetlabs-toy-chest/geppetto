package com.puppetlabs.geppetto.module.dsl.ui.labeling;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider;
import org.eclipse.xtext.util.Strings;

import com.google.inject.Inject;
import com.puppetlabs.geppetto.module.dsl.ModuleUtil;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonArray;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonDependency;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonMetadata;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonObject;

public class ModuleLabelProvider extends DefaultEObjectLabelProvider {
	@Inject
	private ModuleUtil moduleUtil;

	@Inject
	public ModuleLabelProvider(final AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}

	public String image(final JsonDependency dependency) {
		return "obj16/puppet-16.png";
	}

	public String image(final JsonMetadata metadata) {
		return "obj16/puppet-16.png";
	}

	public String text(final JsonArray array) {
		return "[]";
	}

	public Object text(final JsonDependency dependency) {
		return moduleUtil.isResolved(dependency)
			? text(moduleUtil.getReferencedModule(dependency))
			: moduleUtil.getName(moduleUtil.getReferencedModule(dependency));
	}

	public String text(final JsonMetadata metadata) {
		String desc = moduleUtil.getString(metadata, "summary");
		if(Strings.isEmpty(desc))
			desc = moduleUtil.getString(metadata, "name");
		return desc;
	}

	public String text(final JsonObject obj) {
		return "{}";
	}
}
