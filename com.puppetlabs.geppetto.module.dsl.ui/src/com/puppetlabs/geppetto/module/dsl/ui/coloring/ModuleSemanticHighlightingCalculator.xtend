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

import com.google.inject.Inject
import com.puppetlabs.geppetto.module.dsl.ModuleUtil
import com.puppetlabs.geppetto.module.dsl.metadata.JsonObject
import com.puppetlabs.geppetto.module.dsl.metadata.Pair
import com.puppetlabs.geppetto.module.dsl.metadata.UnrecognizedPair
import org.eclipse.xtext.nodemodel.ICompositeNode
import org.eclipse.xtext.nodemodel.ILeafNode
import org.eclipse.xtext.nodemodel.INode
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultSemanticHighlightingCalculator
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightedPositionAcceptor

import static com.puppetlabs.geppetto.module.dsl.ui.coloring.ModuleHighlightingConfiguration.*
import com.puppetlabs.geppetto.module.dsl.metadata.JsonValue

/**
 * Highlighting for metadata.json.
 */
class ModuleSemanticHighlightingCalculator extends DefaultSemanticHighlightingCalculator {
	@Inject
	extension ModuleUtil

	private def ILeafNode getFirstVisibleChildLeaf(ICompositeNode node) {
		for (var child = node.firstChild; child != null; child = child.nextSibling) {
			if (child instanceof ICompositeNode)
				return child.firstVisibleChildLeaf
			if (child instanceof ILeafNode)
				if (!child.hidden)
					return child
		}
	}

	def void highlightKey(ICompositeNode node, IHighlightedPositionAcceptor acceptor, String id) {
		val keyNode = node.firstVisibleChildLeaf
		if (keyNode != null)
			acceptor.addPosition(keyNode.offset, keyNode.length, id);
	}

	/**
	 * Calculates the correct highligh id to use when highlighting the key of the given
	 * pair.
	 *
	 * @return the correct highligh id for the key of the given pair
	 */
	def getKeywordHighlightId(Pair pair) {
		val container = pair.eContainer as JsonObject
		val name = pair.name
		if(container.isKnownKey(name))
			KEYWORD_ID
		else if(container.isDeprecatedKey(name))
			DEPRECATED_KEY_ID
		else if(pair instanceof UnrecognizedPair)
			UNRECOGNIZED_KEY_ID
		else
			STRING_ID
	}

	def highlightSemanticNode(ICompositeNode node, IHighlightedPositionAcceptor acceptor) {
		val elem = node.semanticElement
		if (elem instanceof Pair) {
			node.highlightKey(acceptor, elem.keywordHighlightId)
		} else if (elem instanceof JsonValue) {
			// Prevent that keywords are highlighted when used as values
			if(elem.value instanceof String)
				node.highlightKey(acceptor, STRING_ID)
		}
	}

	override provideHighlightingFor(XtextResource resource, IHighlightedPositionAcceptor acceptor) {
		val nodes = resource?.parseResult?.rootNode?.asTreeIterable
		if (nodes !== null)
			for (INode node : nodes)
				if (node instanceof ICompositeNode && node.hasDirectSemanticElement)
					(node as ICompositeNode).highlightSemanticNode(acceptor)
	}
}
