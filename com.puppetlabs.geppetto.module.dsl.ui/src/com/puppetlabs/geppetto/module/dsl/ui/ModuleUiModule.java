package com.puppetlabs.geppetto.module.dsl.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.builder.IXtextBuilderParticipant;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.eclipse.xtext.ui.editor.hover.IEObjectHover;
import org.eclipse.xtext.ui.editor.hover.IEObjectHoverProvider;
import org.eclipse.xtext.ui.editor.model.IResourceForEditorInputFactory;
import org.eclipse.xtext.ui.editor.model.ResourceForIEditorInputFactory;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.ISemanticHighlightingCalculator;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;
import org.eclipse.xtext.ui.resource.SimpleResourceSetProvider;

import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.puppetlabs.geppetto.common.os.IFileExcluder;
import com.puppetlabs.geppetto.module.dsl.ui.builder.ModuleBuildParticipant;
import com.puppetlabs.geppetto.module.dsl.ui.coloring.ModuleHighlightingConfiguration;
import com.puppetlabs.geppetto.module.dsl.ui.coloring.ModuleSemanticHighlightingCalculator;
import com.puppetlabs.geppetto.module.dsl.ui.labeling.ModuleHover;
import com.puppetlabs.geppetto.module.dsl.ui.labeling.ModuleHoverProvider;
import com.puppetlabs.geppetto.module.dsl.ui.preferences.PreferencedBasedValidationAdvisor;
import com.puppetlabs.geppetto.module.dsl.validation.IModuleValidationAdvisor;
import com.puppetlabs.geppetto.pp.dsl.ui.internal.PPActivator;
import com.puppetlabs.geppetto.pp.dsl.ui.internal.PPDSLActivator;
import com.puppetlabs.geppetto.pp.dsl.ui.jdt_ersatz.ImagesOnFileSystemRegistry;
import com.puppetlabs.geppetto.pp.dsl.ui.preferences.RebuildChecker;
import com.puppetlabs.geppetto.pp.dsl.ui.preferences.editors.PPPreferenceStoreAccess;

/**
 * Use this class to register components to be used within the IDE.
 */
public class ModuleUiModule extends AbstractModuleUiModule {
	public ModuleUiModule(final AbstractUIPlugin plugin) {
		super(plugin);
	}

	@Override
	public Class<? extends IEObjectHover> bindIEObjectHover() {
		return ModuleHover.class;
	}

	public Class<? extends IEObjectHoverProvider> bindIEObjectHoverProvider() {
		return ModuleHoverProvider.class;
	}

	public Class<? extends IHighlightingConfiguration> bindIHighlightingConfiguration() {
		return ModuleHighlightingConfiguration.class;
	}

	public Class<? extends IPreferenceStoreAccess> bindIPreferenceStoreAccess() {
		return PPPreferenceStoreAccess.class;
	}

	@Override
	public Class<? extends IResourceForEditorInputFactory> bindIResourceForEditorInputFactory() {
		return ResourceForIEditorInputFactory.class;
	}

	@Override
	public Class<? extends IResourceSetProvider> bindIResourceSetProvider() {
		return SimpleResourceSetProvider.class;
	}

	public Class<? extends ISemanticHighlightingCalculator> bindISemanticHighlightingCalculator() {
		return ModuleSemanticHighlightingCalculator.class;
	}

	public Class<? extends IModuleValidationAdvisor> bindIValidationAdvisor() {
		return PreferencedBasedValidationAdvisor.class;
	}

	public Class<? extends IXtextBuilderParticipant> bindIXtextBuilderParticipant() {
		return ModuleBuildParticipant.class;
	}

	@Provides
	public IFileExcluder getIFolderDiscriminator() {
		return getPPInjector().getInstance(IFileExcluder.class);
	}

	@Provides
	public ImagesOnFileSystemRegistry getImagesOnFileSystemRegistry() {
		return getPPInjector().getInstance(ImagesOnFileSystemRegistry.class);
	}

	private Injector getPPInjector() {
		return ((PPDSLActivator) PPActivator.getInstance()).getPPInjector();
	}

	@Provides
	public RebuildChecker getRebuldChecker() {
		return getPPInjector().getInstance(RebuildChecker.class);
	}

	@Override
	public Provider<IAllContainersState> provideIAllContainersState() {
		return new ModuleProjectsStateProvider();
	}
}
