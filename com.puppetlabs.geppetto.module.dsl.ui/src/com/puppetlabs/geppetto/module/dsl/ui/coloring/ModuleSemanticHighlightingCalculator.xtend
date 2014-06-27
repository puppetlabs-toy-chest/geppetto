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

import static com.puppetlabs.geppetto.module.dsl.ui.coloring.ModuleHighlightingConfiguration.*

import com.google.inject.Inject
import com.puppetlabs.geppetto.module.dsl.ModuleUtil
import com.puppetlabs.geppetto.module.dsl.metadata.JsonObject
import com.puppetlabs.geppetto.module.dsl.metadata.UnrecognizedPair
import org.eclipse.xtext.nodemodel.ICompositeNode
import org.eclipse.xtext.nodemodel.ILeafNode
import org.eclipse.xtext.nodemodel.INode
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultSemanticHighlightingCalculator
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightedPositionAcceptor

/**
 * Highlighting for metadata.json.
 */
class ModuleSemanticHighlightingCalculator extends DefaultSemanticHighlightingCalculator {
	@Inject
	extension ModuleUtil

	def ILeafNode getFirstVisibleChildLeaf(ICompositeNode node) {
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

	override provideHighlightingFor(XtextResource resource, IHighlightedPositionAcceptor acceptor) {
		val nodes = resource?.parseResult?.rootNode?.asTreeIterable
		if (nodes !== null)
			for (INode node : nodes)
				if (node instanceof ICompositeNode && node.hasDirectSemanticElement) {
					val elem = node.semanticElement
					if (elem instanceof UnrecognizedPair) {
						val container = node.semanticElement.eContainer as JsonObject
						if (!container.isKnownKey(elem.name))
							(node as ICompositeNode).highlightKey(
								acceptor,
								if(container.isDeprecatedKey(elem.name)) DEPRECATED_KEY_ID else UNRECOGNIZED_KEY_ID
							)
					}
				}
	}
}
