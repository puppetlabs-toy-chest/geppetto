package com.puppetlabs.geppetto.module.dsl.ui.labeling

import org.eclipse.xtext.ui.editor.hover.html.DefaultEObjectHoverProvider
import org.eclipse.emf.ecore.EObject
import com.google.inject.Inject
import com.puppetlabs.geppetto.pp.dsl.ui.jdt_ersatz.ImagesOnFileSystemRegistry
import org.eclipse.jface.resource.ImageDescriptor

class ModuleHoverProvider extends DefaultEObjectHoverProvider {
	@Inject
	ImagesOnFileSystemRegistry imagesOnFileSystemRegistry

	override getFirstLine(EObject o) {
		val builder = new StringBuilder
		val image = labelProvider.getImage(o)
		if(image != null) {
			val imageURL = imagesOnFileSystemRegistry.getImageURL(ImageDescriptor.createFromImage(image))
			builder.append("<IMG src=\"").append(imageURL.toExternalForm()).append("\"/>&nbsp;")
		}
		builder.append("<b>").append(getLabel(o)).append("</b>").toString
	}
}