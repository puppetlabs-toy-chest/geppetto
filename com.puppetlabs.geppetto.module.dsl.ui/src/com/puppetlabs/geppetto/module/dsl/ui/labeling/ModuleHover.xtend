package com.puppetlabs.geppetto.module.dsl.ui.labeling;

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.jface.text.IRegion
import org.eclipse.jface.text.ITextViewer
import org.eclipse.jface.text.Region
import org.eclipse.xtext.Keyword
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.ui.editor.hover.DispatchingEObjectTextHover
import org.eclipse.xtext.util.Tuples
import org.eclipse.xtext.ui.editor.hover.IEObjectHoverProvider

class ModuleHover extends DispatchingEObjectTextHover {
	@Inject
	IEObjectHoverProvider hoverProvider

	override getHoverInfo(EObject first, ITextViewer textViewer, IRegion hoverRegion) {
        if (first instanceof Keyword) {
            lastCreatorProvider = hoverProvider.getHoverInfo(first, textViewer, hoverRegion)
            if(lastCreatorProvider == null) null else lastCreatorProvider.getInfo()
        } else
        	super.getHoverInfo(first, textViewer, hoverRegion);
    }

	override getXtextElementAt(XtextResource resource, int offset) {
		val node = NodeModelUtils.findLeafNodeAtOffset(resource.parseResult.rootNode, offset)
		val ge = node.grammarElement
		if(ge instanceof Keyword)
			Tuples.create(ge, new Region(node.offset, node.length))
		else
			super.getXtextElementAt(resource, offset)
	}
}
