/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   itemis AG - initial API and implementation
 *   Puppet Labs
 *
 */
package com.puppetlabs.geppetto.pp.dsl.ui.editor.findrefs;

import static java.util.Collections.singleton;

import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.IReferenceDescription;

import com.google.common.base.Predicate;
import com.puppetlabs.geppetto.pp.dsl.ui.editor.findrefs.PPReferenceFinder.IPPQueryData;

public class PPReferenceQueryData implements IPPQueryData {

	private URI localContextResourceURI;

	private URI leadElementURI;

	private Set<URI> targetURIs;

	private Predicate<IReferenceDescription> resultFilter;

	private String label;

	public PPReferenceQueryData(URI targetURI) {
		this(targetURI, singleton(targetURI), targetURI.trimFragment(), null, "");
	}

	public PPReferenceQueryData(URI leadElementURI, Set<URI> targetURIs, URI localContextResourceURI,
			Predicate<IReferenceDescription> resultFilter, String label) {
		this.leadElementURI = leadElementURI;
		this.targetURIs = targetURIs;
		this.localContextResourceURI = localContextResourceURI;
		this.resultFilter = resultFilter;
		this.label = label;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public URI getLeadElementURI() {
		return leadElementURI;
	}

	@Override
	public URI getLocalContextResourceURI() {
		return localContextResourceURI.trimFragment();
	}

	@Override
	public Predicate<IReferenceDescription> getResultFilter() {
		return resultFilter;
	}

	@Override
	public Set<URI> getTargetURIs() {
		return targetURIs;
	}
}
