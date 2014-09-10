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

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.nodemodel.INode;

import com.google.common.collect.Lists;

/**
 * A message acceptor to use in situations where optional constructs are validated and
 * messaged should be skipped when the chosen construct is omitted.
 */
public class RecordingMessageAcceptor extends AbstractMessageAcceptor {

	private static class FeatureMessage extends Message {
		final EStructuralFeature feature;

		final int index;

		final EObject source;

		public FeatureMessage(Severity severity, String message, EObject source, EStructuralFeature feature, int index, String issueCode,
				String[] issueData) {
			super(severity, message, issueCode, issueData);
			this.source = source;
			this.feature = feature;
			this.index = index;
		}

		@Override
		void play(IMessageAcceptor acceptor) {
			acceptor.accept(severity, message, source, feature, index, issueCode, issueData);
		}
	}

	private static abstract class Message {
		final String message;

		final Severity severity;

		final String issueCode;

		final String[] issueData;

		public Message(Severity severity, String message, String issueCode, String[] issueData) {
			this.severity = severity;
			this.message = message;
			this.issueCode = issueCode;
			this.issueData = issueData;
		}

		abstract void play(IMessageAcceptor acceptor);
	}

	private static class NodeMessage extends Message {
		final INode node;

		public NodeMessage(Severity severity, String message, INode node, String issueCode, String[] issueData) {
			super(severity, message, issueCode, issueData);
			this.node = node;
		}

		@Override
		void play(IMessageAcceptor acceptor) {
			acceptor.accept(severity, message, node, issueCode, issueData);
		}
	}

	private static class TextMessage extends Message {
		final EObject source;

		final int textLength;

		final int textOffset;

		public TextMessage(Severity severity, String message, EObject source, int textOffset, int textLength, String issueCode,
				String[] issueData) {
			super(severity, message, issueCode, issueData);
			this.source = source;
			this.textOffset = textOffset;
			this.textLength = textLength;
		}

		@Override
		void play(IMessageAcceptor acceptor) {
			acceptor.accept(severity, message, source, textOffset, textLength, issueCode, issueData);
		}
	}

	private final List<Message> messages = Lists.newArrayList();

	@Override
	public void accept(Severity severity, String message, EObject source, EStructuralFeature feature, int index, String issueCode,
			String... issueData) {
		messages.add(new FeatureMessage(severity, message, source, feature, index, issueCode, issueData));
	}

	@Override
	public void accept(Severity severity, String message, EObject source, int textOffset, int textLength, String issueCode,
			String[] issueData) {
		messages.add(new TextMessage(severity, message, source, textOffset, textLength, issueCode, issueData));
	}

	@Override
	public void accept(Severity severity, String message, INode node, String issueCode, String... issueData) {
		messages.add(new NodeMessage(severity, message, node, issueCode, issueData));
	}

	public void playBack(IMessageAcceptor acceptor) {
		for(Message message : messages)
			message.play(acceptor);
	}
}
