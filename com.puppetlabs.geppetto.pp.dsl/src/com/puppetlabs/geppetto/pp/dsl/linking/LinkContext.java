/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Puppet Labs
 *
 */
package com.puppetlabs.geppetto.pp.dsl.linking;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.nodemodel.INode;

import com.puppetlabs.geppetto.pp.dsl.adapters.PPImportedNamesAdapter;

/**
 * @author thhal
 */
public class LinkContext extends AbstractMessageAcceptor {
	private final PPImportedNamesAdapter importedNames;

	private final IMessageAcceptor acceptor;

	private final PPSearchPath searchPath;

	private final Resource resource;

	public LinkContext(LinkContext ctx, IMessageAcceptor acceptor) {
		this.importedNames = ctx.getImportedNames();
		this.acceptor = acceptor;
		this.searchPath = ctx.getSearchPath();
		this.resource = ctx.getResource();
	}

	public LinkContext(PPImportedNamesAdapter importedNames, IMessageAcceptor acceptor, PPSearchPath searchPath, Resource resource) {
		this.importedNames = importedNames;
		this.searchPath = searchPath;
		this.resource = resource;
		this.acceptor = acceptor;
	}

	@Override
	public void accept(Severity severity, String message, EObject source, EStructuralFeature feature, int index, String issueCode,
			String... issueData) {
		acceptor.accept(severity, message, source, feature, index, issueCode, issueData);
	}

	@Override
	public void accept(Severity severity, String message, EObject source, int textOffset, int textLength, String issueCode,
			String[] issueData) {
		acceptor.accept(severity, message, source, textOffset, textLength, issueCode, issueData);
	}

	@Override
	public void accept(Severity severity, String message, INode node, String issueCode, String... issueData) {
		acceptor.accept(severity, message, node, issueCode, issueData);
	}

	/**
	 * @return the importedNames
	 */
	public PPImportedNamesAdapter getImportedNames() {
		return importedNames;
	}

	/**
	 * @return the resource
	 */
	public Resource getResource() {
		return resource;
	}

	/**
	 * @return the searchPath
	 */
	public PPSearchPath getSearchPath() {
		return searchPath;
	}
}
