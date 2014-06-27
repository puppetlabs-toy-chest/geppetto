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

import com.google.common.base.Objects;
import com.google.inject.Inject;
import com.puppetlabs.geppetto.module.dsl.ModuleUtil;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonObject;
import com.puppetlabs.geppetto.module.dsl.metadata.UnrecognizedPair;
import com.puppetlabs.geppetto.module.dsl.ui.coloring.ModuleHighlightingConfiguration;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.BidiTreeIterable;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultSemanticHighlightingCalculator;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.xtext.xbase.lib.Extension;

/**
 * Highlighting for metadata.json.
 */
@SuppressWarnings("all")
public class ModuleSemanticHighlightingCalculator extends DefaultSemanticHighlightingCalculator {
  @Inject
  @Extension
  private ModuleUtil _moduleUtil;
  
  public ILeafNode getFirstVisibleChildLeaf(final ICompositeNode node) {
    for (INode child = node.getFirstChild(); (!Objects.equal(child, null)); child = child.getNextSibling()) {
      {
        if ((child instanceof ICompositeNode)) {
          return this.getFirstVisibleChildLeaf(((ICompositeNode)child));
        }
        if ((child instanceof ILeafNode)) {
          boolean _isHidden = ((ILeafNode)child).isHidden();
          boolean _not = (!_isHidden);
          if (_not) {
            return ((ILeafNode)child);
          }
        }
      }
    }
    return null;
  }
  
  public void highlightKey(final ICompositeNode node, final IHighlightedPositionAcceptor acceptor, final String id) {
    final ILeafNode keyNode = this.getFirstVisibleChildLeaf(node);
    boolean _notEquals = (!Objects.equal(keyNode, null));
    if (_notEquals) {
      int _offset = keyNode.getOffset();
      int _length = keyNode.getLength();
      acceptor.addPosition(_offset, _length, id);
    }
  }
  
  public void provideHighlightingFor(final XtextResource resource, final IHighlightedPositionAcceptor acceptor) {
    IParseResult _parseResult = null;
    if (resource!=null) {
      _parseResult=resource.getParseResult();
    }
    ICompositeNode _rootNode = null;
    if (_parseResult!=null) {
      _rootNode=_parseResult.getRootNode();
    }
    BidiTreeIterable<INode> _asTreeIterable = null;
    if (_rootNode!=null) {
      _asTreeIterable=_rootNode.getAsTreeIterable();
    }
    final BidiTreeIterable<INode> nodes = _asTreeIterable;
    boolean _tripleNotEquals = (nodes != null);
    if (_tripleNotEquals) {
      for (final INode node : nodes) {
        boolean _and = false;
        if (!(node instanceof ICompositeNode)) {
          _and = false;
        } else {
          boolean _hasDirectSemanticElement = node.hasDirectSemanticElement();
          _and = _hasDirectSemanticElement;
        }
        if (_and) {
          final EObject elem = node.getSemanticElement();
          if ((elem instanceof UnrecognizedPair)) {
            EObject _semanticElement = node.getSemanticElement();
            EObject _eContainer = _semanticElement.eContainer();
            final JsonObject container = ((JsonObject) _eContainer);
            String _name = ((UnrecognizedPair)elem).getName();
            boolean _isKnownKey = this._moduleUtil.isKnownKey(container, _name);
            boolean _not = (!_isKnownKey);
            if (_not) {
              String _xifexpression = null;
              String _name_1 = ((UnrecognizedPair)elem).getName();
              boolean _isDeprecatedKey = this._moduleUtil.isDeprecatedKey(container, _name_1);
              if (_isDeprecatedKey) {
                _xifexpression = ModuleHighlightingConfiguration.DEPRECATED_KEY_ID;
              } else {
                _xifexpression = ModuleHighlightingConfiguration.UNRECOGNIZED_KEY_ID;
              }
              this.highlightKey(((ICompositeNode) node), acceptor, _xifexpression);
            }
          }
        }
      }
    }
  }
}
