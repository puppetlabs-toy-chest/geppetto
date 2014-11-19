package com.puppetlabs.geppetto.module.dsl;

import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.generator.IGenerator;
import org.eclipse.xtext.linking.ILinkingDiagnosticMessageProvider;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.containers.StateBasedContainerManager;

import com.google.inject.Binder;
import com.google.inject.Provider;
import com.puppetlabs.geppetto.common.os.FileExcluderProvider;
import com.puppetlabs.geppetto.common.os.IFileExcluder;
import com.puppetlabs.geppetto.forge.client.GsonModule;
import com.puppetlabs.geppetto.forge.impl.ForgeModule;
import com.puppetlabs.geppetto.injectable.CommonModuleProvider;
import com.puppetlabs.geppetto.module.dsl.generator.ModuleGenerator;
import com.puppetlabs.geppetto.module.dsl.linking.ModuleLinkingDiagnosticMessageProvider;
import com.puppetlabs.geppetto.module.dsl.scoping.ModuleQualifiedNameConverter;
import com.puppetlabs.geppetto.module.dsl.scoping.ModuleQualifiedNameProvider;
import com.puppetlabs.geppetto.module.dsl.validation.DefaultModuleValidationAdvisor;
import com.puppetlabs.geppetto.module.dsl.validation.IModuleValidationAdvisor;
import com.puppetlabs.geppetto.module.dsl.validation.ModuleValidationAdvisorWrapper;

public class ModuleRuntimeModule extends AbstractModuleRuntimeModule {

	private final FileExcluderProvider fileExcluderProvider = new FileExcluderProvider();

	private final ModuleValidationAdvisorWrapper advisorWrapper = new ModuleValidationAdvisorWrapper(
		DefaultModuleValidationAdvisor.INSTANCE);

	@Override
	public Class<? extends IContainer.Manager> bindIContainer$Manager() {
		return StateBasedContainerManager.class;
	}

	public Provider<IFileExcluder> provideIFileExcluder() {
		return fileExcluderProvider;
	}

	/**
	 * Need separate binding for the configurable provider so that it can be reached as
	 * a provider instance.
	 *
	 * @return The configurable FileExcluderProvider instance
	 */
	public FileExcluderProvider bindIFileExcluderProvider() {
		return fileExcluderProvider;
	}

	public Class<? extends IGenerator> bindIGenerator() {
		return ModuleGenerator.class;
	}

	public Class<? extends ILinkingDiagnosticMessageProvider> bindILinkingDiagnosticMessageProvider() {
		return ModuleLinkingDiagnosticMessageProvider.class;
	}

	/**
	 * Metadata names use the '-' as the separator
	 */
	public Class<? extends IQualifiedNameConverter> bindIQualifiedNameConverter() {
		return ModuleQualifiedNameConverter.class;
	}

	/**
	 * Name must be provided from a specific JSON entry
	 */
	@Override
	public Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
		return ModuleQualifiedNameProvider.class;
	}

	public Class<? extends IResourceDescription.Manager> bindIResourceDescriptionManager() {
		return ModuleResourceDescriptionManager.class;
	}

	public Provider<IModuleValidationAdvisor> provideIModuleValidationAdvisor() {
		return new Provider<IModuleValidationAdvisor>() {
			public IModuleValidationAdvisor get() {
				return advisorWrapper;
			}
		};
	}

	public ModuleValidationAdvisorWrapper bindModuleValidationAdvisorWrapper() {
		return advisorWrapper;
	}

	/**
	 * Binds the resource provide that reacts to "metadata.json" files rather than
	 * just the filename extension
	 */
	public Class<? extends IResourceServiceProvider> bindIResourceServiceProvider() {
		return ModuleResourceServiceProvider.class;
	}

	@Override
	public Class<? extends IValueConverterService> bindIValueConverterService() {
		return ModuleConverters.class;
	}

	public void configureForge(Binder binder) {
		binder.install(CommonModuleProvider.getCommonModule());
		binder.install(GsonModule.INSTANCE);
		binder.install(new ForgeModule());
	}
}
