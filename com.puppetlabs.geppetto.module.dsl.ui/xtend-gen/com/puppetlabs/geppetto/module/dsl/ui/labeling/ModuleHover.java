package com.puppetlabs.geppetto.module.dsl.ui.labeling;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.hover.DispatchingEObjectTextHover;
import org.eclipse.xtext.ui.editor.hover.IEObjectHoverProvider;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.Tuples;

@SuppressWarnings("all")
public class ModuleHover extends DispatchingEObjectTextHover {
  @Inject
  private IEObjectHoverProvider hoverProvider;
  
  public Object getHoverInfo(final EObject first, final ITextViewer textViewer, final IRegion hoverRegion) {
    Object _xifexpression = null;
    if ((first instanceof Keyword)) {
      Object _xblockexpression = null;
      {
        IEObjectHoverProvider.IInformationControlCreatorProvider _hoverInfo = this.hoverProvider.getHoverInfo(first, textViewer, hoverRegion);
        this.lastCreatorProvider = _hoverInfo;
        Object _xifexpression_1 = null;
        boolean _equals = Objects.equal(this.lastCreatorProvider, null);
        if (_equals) {
          _xifexpression_1 = null;
        } else {
          _xifexpression_1 = this.lastCreatorProvider.getInfo();
        }
        _xblockexpression = _xifexpression_1;
      }
      _xifexpression = _xblockexpression;
    } else {
      _xifexpression = super.getHoverInfo(first, textViewer, hoverRegion);
    }
    return _xifexpression;
  }
  
  public Pair<EObject, IRegion> getXtextElementAt(final XtextResource resource, final int offset) {
    Pair<EObject, IRegion> _xblockexpression = null;
    {
      IParseResult _parseResult = resource.getParseResult();
      ICompositeNode _rootNode = _parseResult.getRootNode();
      final ILeafNode node = NodeModelUtils.findLeafNodeAtOffset(_rootNode, offset);
      final EObject ge = node.getGrammarElement();
      Pair<EObject, IRegion> _xifexpression = null;
      if ((ge instanceof Keyword)) {
        int _offset = node.getOffset();
        int _length = node.getLength();
        Region _region = new Region(_offset, _length);
        _xifexpression = Tuples.<EObject, IRegion>create(ge, _region);
      } else {
        _xifexpression = super.getXtextElementAt(resource, offset);
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
}
