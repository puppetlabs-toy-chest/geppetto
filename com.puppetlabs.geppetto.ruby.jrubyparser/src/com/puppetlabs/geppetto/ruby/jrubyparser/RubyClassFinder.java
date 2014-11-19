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
package com.puppetlabs.geppetto.ruby.jrubyparser;

import java.util.List;

import org.jrubyparser.ast.ClassNode;
import org.jrubyparser.ast.NewlineNode;
import org.jrubyparser.ast.Node;
import org.jrubyparser.ast.NodeType;

import com.google.common.collect.Lists;

/**
 * Finds a class
 */
public class RubyClassFinder extends RubyFinder {

	/**
	 * Returned when a visited node detect it is not meaningful to visit its
	 * children.
	 */
	public static final Object DO_NOT_VISIT_CHILDREN = new Object();

	/**
	 * @param root
	 */
	RubyClassFinder(Node root) {
		super(root);
	}

	public ClassNode findClass(String... qualifiedName) {
		return internalFindClass(root, Lists.newArrayList(qualifiedName));
	}

	public ClassNode internalFindClass(Node node, List<String> wantedName) {

		for(Node n : node.childNodes()) {
			if(n.getNodeType() == NodeType.NEWLINENODE)
				n = ((NewlineNode) n).getNextNode();
			switch(n.getNodeType()) {
				case ROOTNODE: // fall through
				case BLOCKNODE:
					return internalFindClass(n, wantedName);
				case CLASSNODE:
					ClassNode classNode = (ClassNode) n;
					if(wantedName.equals(constEvaluator.eval(classNode.getCPath())))
						return (ClassNode) n;
				default:
					break;
			}
		}
		return null;
	}
}
