package com.puppetlabs.geppetto.module.dsl.ui.labeling;

import java.net.URL;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ui.editor.hover.html.DefaultEObjectHoverProvider;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.puppetlabs.geppetto.pp.dsl.ui.jdt_ersatz.ImagesOnFileSystemRegistry;

@Singleton
public class ModuleHoverProvider extends DefaultEObjectHoverProvider {
	@Inject
	private ImagesOnFileSystemRegistry imagesOnFileSystemRegistry;

	@Inject
	private ModuleKeywordTexts moduleKeywordTexts;

	@Override
	public String getFirstLine(final EObject o) {
		StringBuilder builder = new StringBuilder();
		Image image = getLabelProvider().getImage(o);
		if(image != null) {
			URL imageURL = this.imagesOnFileSystemRegistry.getImageURL(ImageDescriptor.createFromImage(image));
			builder.append("<IMG src=\"").append(imageURL.toExternalForm()).append("\"/>&nbsp;");
		}
		return builder.append("<b>").append(getLabel(o)).append("</b>").toString();
	}

	@Override
	public String getHoverInfoAsHtml(final EObject element) {
		if((element instanceof Keyword)) {
			String html = moduleKeywordTexts.getHoverText(((Keyword) element));
			if(html != null)
				return html;
		}
		return super.getHoverInfoAsHtml(element);
	}
}
