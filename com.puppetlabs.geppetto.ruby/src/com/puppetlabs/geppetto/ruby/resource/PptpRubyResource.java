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

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;

import com.puppetlabs.geppetto.common.os.UnicodeReaderFactory;
import com.puppetlabs.geppetto.pp.pptp.Function;
import com.puppetlabs.geppetto.pp.pptp.MetaType;
import com.puppetlabs.geppetto.pp.pptp.PPTPFactory;
import com.puppetlabs.geppetto.pp.pptp.Parameter;
import com.puppetlabs.geppetto.pp.pptp.Property;
import com.puppetlabs.geppetto.pp.pptp.Type;
import com.puppetlabs.geppetto.pp.pptp.TypeFragment;
import com.puppetlabs.geppetto.ruby.PPFunctionInfo;
import com.puppetlabs.geppetto.ruby.PPProviderInfo;
import com.puppetlabs.geppetto.ruby.PPTypeInfo;
import com.puppetlabs.geppetto.ruby.RubyHelper;
import com.puppetlabs.geppetto.ruby.RubySyntaxException;
import com.puppetlabs.geppetto.ruby.spi.IRubyIssue;
import com.puppetlabs.geppetto.ruby.spi.IRubyParseResult;

/**
 * A Resource that loads .rb files containing Puppet "target platform"
 * information. Ruby source on particular paths are transformed into PPTP model
 * contents.
 */
public class PptpRubyResource extends ResourceImpl {

	public enum LoadType {
		TYPE, TYPEFRAGMENT, META, PROVIDER, FUNCTION, IGNORED;

	}

	public static class RubyIssueDiagnostic implements Diagnostic {
		private final IRubyIssue issue;

		public RubyIssueDiagnostic(IRubyIssue issue) {
			this.issue = issue;
		}

		/**
		 * @throws UnsupportedOperationException
		 *             - column is not available.
		 */
		@Override
		public int getColumn() {
			throw new UnsupportedOperationException();
		}

		public IRubyIssue getIssue() {
			return issue;
		}

		@Override
		public int getLine() {
			return issue.getLine();
		}

		@Override
		public String getLocation() {
			return issue.getFileName();
		}

		@Override
		public String getMessage() {
			return issue.getMessage();
		}
	}

	/**
	 * (SOMEROOT/lib/puppet/) parser/functions/F.rb (SOMEROOT/lib/puppet/)
	 * type/T.rb (SOMEROOT/lib/puppet/) type/FRAGMENTDIR/TypeFragment.rb
	 * (SOMEROOT/lib/puppet/) type.rb - META TYPE (typically in a distro)
	 *
	 * @return
	 */
	public static LoadType detectLoadType(URI uri) {
		List<String> segments = uri.segmentsList();
		final int lastPuppet = segments.lastIndexOf("puppet");
		final int segmentCount = segments.size();
		int idx = lastPuppet + 1;
		if(lastPuppet < 0 || idx >= segmentCount)
			return LoadType.IGNORED;

		String typeDir = segments.get(idx++);
		if(idx == segmentCount)
			return "type.rb".equals(typeDir)
				? LoadType.META
				: LoadType.IGNORED;

		String segment = segments.get(idx++);
		switch(typeDir) {
			case "parser":
				if("functions".equals(segment) && idx == segmentCount - 1 && segments.get(idx).endsWith(".rb"))
					return LoadType.FUNCTION;
				break;
			case "type":
				// a .rb file under type
				if(segment.endsWith(".rb") && idx == segmentCount)
					return LoadType.TYPE;

				// typefragment must be in a subdir of type, e.g.
				// type/file/X.rb
				segment = segments.get(idx++);
				if(idx == segmentCount && segment.endsWith(".rb"))
					return LoadType.TYPEFRAGMENT;
				break;
			case "provider":
				if(segment.endsWith(".rb") && idx == segmentCount)
					return LoadType.PROVIDER;

				segment = segments.get(idx++);
				if(idx == segmentCount && segment.endsWith(".rb"))
					return LoadType.PROVIDER;
				break;
		}
		return LoadType.IGNORED;
	}

	private final RubyHelper rubyHelper;

	/**
	 * Create an instance with a reference to a resource in Ruby text format.
	 *
	 * @param uri
	 */
	public PptpRubyResource(URI uri, RubyHelper rubyHelper) {
		super(uri);
		this.rubyHelper = rubyHelper;
	}

	protected LoadType detectLoadType() {
		return detectLoadType(getURI());
	}

	@Override
	public void doLoad(InputStream in, Map<?, ?> options) throws IOException {
		internalLoadRuby(in);
	}

	/**
	 * Loads one (or more) PPTP Type, PPTP Function, PPTP Meta, or PPTP Fragment
	 * depending on the type of load (determined by looking at the path to the
	 * parsed .rb file).
	 *
	 * @throws IOException
	 */
	protected void internalLoadRuby(InputStream inputStream) throws IOException {
		LoadType loadType = detectLoadType();
		if(loadType == LoadType.IGNORED) {
			this.getContents().clear();
			return;
		}
		List<IRubyIssue> warnings = null;
		URI uri = getURI();
		try {
			switch(loadType) {
				case TYPE: {
					IRubyParseResult<List<PPTypeInfo>> typeInfo = rubyHelper.getTypeInfo(
						uri.path(), UnicodeReaderFactory.createReader(inputStream, "UTF-8"));
					warnings = typeInfo.getIssues();

					for(PPTypeInfo info : typeInfo.getResult()) {
						Type type = PPTPFactory.eINSTANCE.createType();
						type.setName(info.getTypeName());
						type.setDocumentation(info.getDocumentation());
						for(Map.Entry<String, PPTypeInfo.Entry> entry : info.getParameters().entrySet()) {
							Parameter parameter = PPTPFactory.eINSTANCE.createParameter();
							parameter.setName(entry.getKey());
							parameter.setDocumentation(entry.getValue().documentation);
							parameter.setRequired(entry.getValue().isRequired());
							type.getParameters().add(parameter);
						}
						for(Map.Entry<String, PPTypeInfo.Entry> entry : info.getProperties().entrySet()) {
							Property property = PPTPFactory.eINSTANCE.createProperty();
							property.setName(entry.getKey());
							property.setDocumentation(entry.getValue().documentation);
							property.setRequired(entry.getValue().isRequired());
							type.getProperties().add(property);
						}
						getContents().add(type);
					}
					break;
				}

				case FUNCTION: {
					IRubyParseResult<List<PPFunctionInfo>> functions = rubyHelper.getFunctionInfo(
						uri.path(), UnicodeReaderFactory.createReader(inputStream, "UTF-8"));
					warnings = functions.getIssues();

					for(PPFunctionInfo info : functions.getResult()) {
						Function pptpFunc = PPTPFactory.eINSTANCE.createFunction();
						pptpFunc.setName(info.getFunctionName());
						pptpFunc.setRValue(info.isRValue());
						pptpFunc.setDocumentation(info.getDocumentation());
						getContents().add(pptpFunc);
					}
				}
					break;

				case META: {
					IRubyParseResult<PPTypeInfo> rr = rubyHelper.getMetaTypeInfo(
						uri.path(), UnicodeReaderFactory.createReader(inputStream, "UTF-8"));
					warnings = rr.getIssues();

					PPTypeInfo info = rr.getResult();
					MetaType type = PPTPFactory.eINSTANCE.createMetaType();
					type.setName(info.getTypeName());
					type.setDocumentation(info.getDocumentation());
					for(Map.Entry<String, PPTypeInfo.Entry> entry : info.getParameters().entrySet()) {
						Parameter parameter = PPTPFactory.eINSTANCE.createParameter();
						parameter.setName(entry.getKey());
						parameter.setDocumentation(entry.getValue().documentation);
						parameter.setRequired(entry.getValue().isRequired());
						type.getParameters().add(parameter);
					}
					// TODO: Scan the puppet source for providers for the type
					// This is a CHEAT -
					// https://github.com/puppetlabs/geppetto/issues/37
					Parameter p = PPTPFactory.eINSTANCE.createParameter();
					p.setName("provider");
					p.setDocumentation("");
					p.setRequired(false);
					type.getParameters().add(p);

					getContents().add(type);
					break;
				}

				case TYPEFRAGMENT: {
					IRubyParseResult<List<PPTypeInfo>> rr = rubyHelper.getTypeFragments(
						uri.path(), UnicodeReaderFactory.createReader(inputStream, "UTF-8"));
					warnings = rr.getIssues();

					for(PPTypeInfo type : rr.getResult()) {
						TypeFragment fragment = PPTPFactory.eINSTANCE.createTypeFragment();
						fragment.setName(type.getTypeName());

						// add the properties (will typically load just one).
						for(Map.Entry<String, PPTypeInfo.Entry> entry : type.getProperties().entrySet()) {
							Property property = PPTPFactory.eINSTANCE.createProperty();
							property.setName(entry.getKey());
							property.setDocumentation(entry.getValue().documentation);
							property.setRequired(entry.getValue().isRequired());
							fragment.getProperties().add(property);
						}

						// add the parameters (will typically load just one).
						for(Map.Entry<String, PPTypeInfo.Entry> entry : type.getParameters().entrySet()) {
							Parameter parameter = PPTPFactory.eINSTANCE.createParameter();
							parameter.setName(entry.getKey());
							parameter.setDocumentation(entry.getValue().documentation);
							parameter.setRequired(entry.getValue().isRequired());
							fragment.getParameters().add(parameter);
						}
						getContents().add(fragment);
					}
					break;
				}

				case PROVIDER: {
					IRubyParseResult<List<PPProviderInfo>> rr = rubyHelper.getProviderInfo(
						uri.path(), UnicodeReaderFactory.createReader(inputStream, "UTF-8"));
					warnings = rr.getIssues();

					for(PPProviderInfo info : rr.getResult())
						getContents().add(info.toProvider());
				}
					break;
				case IGNORED:
					break;
			}
		}
		catch(RubySyntaxException syntaxException) {
			getErrors().add(new RubyIssueDiagnostic(syntaxException));
			for(IRubyIssue warning : syntaxException.getIssues())
				getWarnings().add(new RubyIssueDiagnostic(warning));
		}
		if(warnings != null)
			for(IRubyIssue warning : warnings)
				getWarnings().add(new RubyIssueDiagnostic(warning));
	}

	@Override
	public void load(Map<?, ?> options) throws IOException {
		if(!super.isLoaded) {
			super.isLoading = true;

			internalLoadRuby(getURIConverter().createInputStream(uri));

			super.isLoading = false;
			super.isLoaded = true;
		}
	}

	@Override
	public void save(Map<?, ?> options) throws IOException {
		throw new UnsupportedOperationException("Save of PPTP parsed from a ruby file is not possible.");
	}
}
