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

import org.jrubyparser.ast.Node;

public class RubyFinder {

	/**
	 * An evaluator of constant ruby expressions
	 */
	final ConstEvaluator constEvaluator;

	final Node root;

	public RubyFinder(Node root) {
		this.constEvaluator = new ConstEvaluator(root);
		this.root = root;
	}

	public ConstEvaluator getConstEvaluator() {
		return constEvaluator;
	}
}
