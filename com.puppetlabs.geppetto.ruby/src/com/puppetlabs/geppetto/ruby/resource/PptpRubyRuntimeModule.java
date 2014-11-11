/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Puppet Labs
 */
package com.puppetlabs.geppetto.ruby.resource;

import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.parser.IEncodingProvider;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.generic.AbstractGenericResourceRuntimeModule;

import com.google.inject.Binder;
import com.google.inject.name.Names;
import com.puppetlabs.geppetto.common.os.FileExcluder;
import com.puppetlabs.geppetto.common.os.IFileExcluder;
import com.puppetlabs.geppetto.injectable.CommonModuleProvider;
import com.puppetlabs.geppetto.ruby.MockRubyServices;
import com.puppetlabs.geppetto.ruby.spi.IRubyServices;
import com.puppetlabs.geppetto.ruby.spi.RubyParserConfiguration;
import com.puppetlabs.geppetto.ruby.spi.RubyParserConfiguration.RubyVersion;

/**
 * A runtime module for PPTP RUBY model.
 */
public class PptpRubyRuntimeModule extends AbstractGenericResourceRuntimeModule {

	public static final String PPTP_RUBY_LANGUAGE_NAME = "com.puppetlabs.geppetto.pp.dsl.PPTP.RB";

	public static final String PPTP_RUBY_EXTENSION = "rb";

	/**
	 * Note: Ruby < 1.9 does not handle encoding at all (it expects single byte UsAscii). Ruby 1.9 has
	 * a comment line e.g. '# encoding : utf-8'. This declaration is not really used as the ruby parser is
	 * simply given an input stream without any encoding, but an encoding provider must still be provided (to prevent
	 * the
	 * default XMLEncodingProvider from opening the file and not finding any XML (and hence no encoding specification)).
	 * A provider returning the default charset for the platform is used here.
	 */
	@Override
	public Class<? extends IEncodingProvider> bindIEncodingProvider() {
		return IEncodingProvider.Runtime.class;
	}

	public Class<? extends IFileExcluder> bindIFileExcluder() {
		return FileExcluder.class;
	}

	/**
	 * Handles creation of QualifiedNames for referenceable PP model elements.
	 *
	 * @see com.puppetlabs.geppetto.pp.dsl.AbstractPPRuntimeModule#bindIQualifiedNameProvider()
	 */
	@Override
	public Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
		return RubyQualifiedNameProvider.class;
	}

	/**
	 * Binds a provider that skips .rb files that are on "uninteresting" paths.
	 */
	@Override
	public Class<? extends IResourceServiceProvider> bindIResourceServiceProvider() {
		return PptpRubyResourceServiceProvider.class;
	}

	public Class<? extends IRubyServices> bindIRubyServices() {
		return MockRubyServices.class;
	}

	public Class<? extends RubyParserConfiguration> bindRubyParserConfiguration() {
		return RubyParserConfiguration.class;
	}

	public void configureCommon(Binder binder) {
		binder.install(CommonModuleProvider.getCommonModule());
	}

	public void configureRubyCollectWarnings(Binder binder) {
		binder.bind(RubyVersion.class).toInstance(RubyVersion.V2_0);
	}

	public void configureRubyVersion(Binder binder) {
		binder.bind(Boolean.class).annotatedWith(Names.named(RubyParserConfiguration.COLLECT_WARNINGS)).toInstance(Boolean.TRUE);
	}

	@Override
	protected String getFileExtensions() {
		return PPTP_RUBY_EXTENSION;
	}

	@Override
	protected String getLanguageName() {
		return PPTP_RUBY_LANGUAGE_NAME;
	}
}
