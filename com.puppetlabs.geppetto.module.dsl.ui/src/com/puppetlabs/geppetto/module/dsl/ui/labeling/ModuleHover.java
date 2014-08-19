package com.puppetlabs.geppetto.module.dsl.ui.labeling;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.hover.DispatchingEObjectTextHover;
import org.eclipse.xtext.ui.editor.hover.IEObjectHoverProvider;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.Tuples;

import com.google.inject.Inject;

public class ModuleHover extends DispatchingEObjectTextHover {
	@Inject
	private IEObjectHoverProvider hoverProvider;

	@Override
	public Object getHoverInfo(final EObject first, final ITextViewer textViewer, final IRegion hoverRegion) {
		if(first instanceof Keyword) {
			lastCreatorProvider = hoverProvider.getHoverInfo(first, textViewer, hoverRegion);
			return lastCreatorProvider == null
					? null
							: lastCreatorProvider.getInfo();
		}
		return super.getHoverInfo(first, textViewer, hoverRegion);
	}

	@Override
	public Pair<EObject, IRegion> getXtextElementAt(final XtextResource resource, final int offset) {
		ILeafNode node = NodeModelUtils.findLeafNodeAtOffset(resource.getParseResult().getRootNode(), offset);
		EObject ge = node.getGrammarElement();
		return ge instanceof Keyword
				? Tuples.<EObject, IRegion> create(ge, new Region(node.getOffset(), node.getLength()))
						: super.getXtextElementAt(resource, offset);
	}
}
