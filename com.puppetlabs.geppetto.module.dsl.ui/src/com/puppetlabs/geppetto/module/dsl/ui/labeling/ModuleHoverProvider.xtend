package com.puppetlabs.geppetto.module.dsl.ui.labeling

import com.google.inject.Inject
import com.puppetlabs.geppetto.pp.dsl.ui.jdt_ersatz.ImagesOnFileSystemRegistry
import org.eclipse.emf.ecore.EObject
import org.eclipse.jface.resource.ImageDescriptor
import org.eclipse.xtext.Keyword
import org.eclipse.xtext.ui.editor.hover.html.DefaultEObjectHoverProvider

class ModuleHoverProvider extends DefaultEObjectHoverProvider {
	@Inject
	ImagesOnFileSystemRegistry imagesOnFileSystemRegistry
	
	@Inject
	extension ModuleKeywordTexts

	override getHoverInfoAsHtml(EObject element) {
        if (element instanceof Keyword) {
            val html = element.hoverText
            if(html != null)
            	return html
        }
        super.getHoverInfoAsHtml(element)
	}

	override getFirstLine(EObject o) {
		println('Hello ' + o.class.name)
		val builder = new StringBuilder
		val image = labelProvider.getImage(o)
		if(image != null) {
			val imageURL = imagesOnFileSystemRegistry.getImageURL(ImageDescriptor.createFromImage(image))
			builder.append("<IMG src=\"").append(imageURL.toExternalForm()).append("\"/>&nbsp;")
		}
		builder.append("<b>").append(getLabel(o)).append("</b>").toString
	}
}