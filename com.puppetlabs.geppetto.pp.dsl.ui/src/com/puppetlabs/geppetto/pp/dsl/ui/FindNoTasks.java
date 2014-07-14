/**
 * Copyright (c) 2014 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Puppet Labs
 *
 */
package com.puppetlabs.geppetto.pp.dsl.ui;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.tasks.ITaskFinder;
import org.eclipse.xtext.tasks.ITaskParser;
import org.eclipse.xtext.tasks.Task;

/**
 * A ITaskFinder implementation that finds no tasks. Geppetto has its own way of detecting
 * tasks.
 *
 * TODO: Implement a proper {@link ITaskParser}
 *
 * @See PPTask
 * @See PPDocumentationParser
 */
public class FindNoTasks implements ITaskFinder {
	@Override
	public List<Task> findTasks(Resource resource) {
		return Collections.emptyList();
	}
}
