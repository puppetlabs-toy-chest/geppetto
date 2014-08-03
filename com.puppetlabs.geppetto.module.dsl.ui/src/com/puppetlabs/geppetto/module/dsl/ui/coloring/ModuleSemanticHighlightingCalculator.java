/**
 * Copyright (c) 2014 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Puppet Labs
 */
package com.puppetlabs.geppetto.module.dsl.ui.coloring;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.BidiTreeIterable;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultSemanticHighlightingCalculator;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightedPositionAcceptor;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.puppetlabs.geppetto.module.dsl.ModuleUtil;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonObject;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonValue;
import com.puppetlabs.geppetto.module.dsl.metadata.Pair;
import com.puppetlabs.geppetto.module.dsl.metadata.UnrecognizedPair;

/**
 * Highlighting for metadata.json.
 */
@Singleton
public class ModuleSemanticHighlightingCalculator extends DefaultSemanticHighlightingCalculator {
	@Inject
	private ModuleUtil moduleUtil;

	private ILeafNode getFirstVisibleChildLeaf(ICompositeNode node) {
		for(INode child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
			if(child instanceof ICompositeNode)
				return getFirstVisibleChildLeaf((ICompositeNode) child);

			if(child instanceof ILeafNode) {
				ILeafNode leaf = (ILeafNode) child;
				if(!leaf.isHidden())
					return leaf;
			}
		}
		return null;
	}

	/**
	 * Calculates the correct highligh id to use when highlighting the key of the given
	 * pair.
	 *
	 * @return the correct highligh id for the key of the given pair
	 */
	public String getKeywordHighlightId(Pair pair) {

		JsonObject container = ((JsonObject) pair.eContainer());
		String name = pair.getName();
		if(moduleUtil.isKnownKey(container, name))
			return DefaultHighlightingConfiguration.KEYWORD_ID;

		if(moduleUtil.isDeprecatedKey(container, name))
			return ModuleHighlightingConfiguration.DEPRECATED_KEY_ID;

		if(pair instanceof UnrecognizedPair)
			return ModuleHighlightingConfiguration.UNRECOGNIZED_KEY_ID;

		return DefaultHighlightingConfiguration.STRING_ID;
	}

	public void highlightKey(ICompositeNode node, IHighlightedPositionAcceptor acceptor, String id) {
		ILeafNode keyNode = getFirstVisibleChildLeaf(node);
		if(keyNode != null)
			acceptor.addPosition(keyNode.getOffset(), keyNode.getLength(), id);
	}

	public void highlightSemanticNode(ICompositeNode node, IHighlightedPositionAcceptor acceptor) {
		EObject elem = node.getSemanticElement();
		if((elem instanceof Pair))
			highlightKey(node, acceptor, getKeywordHighlightId(((Pair) elem)));
		else if(elem instanceof JsonValue && ((JsonValue) elem).getValue() instanceof String)
			highlightKey(node, acceptor, DefaultHighlightingConfiguration.STRING_ID);
	}

	@Override
	public void provideHighlightingFor(XtextResource resource, IHighlightedPositionAcceptor acceptor) {
		if(resource != null) {
			IParseResult parseResult = resource.getParseResult();
			if(parseResult != null) {
				ICompositeNode rootNode = parseResult.getRootNode();
				if(rootNode != null) {
					BidiTreeIterable<INode> nodes = rootNode.getAsTreeIterable();
					if(nodes != null) {
						for(INode node : nodes)
							if(node instanceof ICompositeNode && node.hasDirectSemanticElement())
								highlightSemanticNode(((ICompositeNode) node), acceptor);
					}
				}
			}
		}
	}
}
