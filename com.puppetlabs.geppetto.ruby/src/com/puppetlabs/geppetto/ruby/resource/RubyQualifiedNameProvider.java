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
package com.puppetlabs.geppetto.ruby.resource;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

import com.puppetlabs.geppetto.pp.pptp.INamed;
import com.puppetlabs.geppetto.pp.pptp.Provider;
import com.puppetlabs.geppetto.pp.pptp.TargetElement;
import com.puppetlabs.geppetto.pp.pptp.Type;

/**
 * @author thhal
 */
public class RubyQualifiedNameProvider extends IQualifiedNameProvider.AbstractImpl {
	public QualifiedName getFullyQualifiedName(final EObject obj) {
		if(obj instanceof TargetElement) {
			TargetElement te = (TargetElement) obj;
			EObject container = te.eContainer();
			if(container instanceof Type)
				return QualifiedName.create(((Type) container).getName(), te.getName());

			if(obj instanceof Provider) {
				Provider p = (Provider) obj;
				return QualifiedName.create(p.getTypeName(), p.getName());
			}
		}

		if(obj instanceof INamed)
			return QualifiedName.create(((INamed) obj).getName());
		return null;
	}
}
