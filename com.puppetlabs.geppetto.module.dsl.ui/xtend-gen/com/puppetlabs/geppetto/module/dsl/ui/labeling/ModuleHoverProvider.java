package com.puppetlabs.geppetto.module.dsl.ui.labeling;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import com.puppetlabs.geppetto.module.dsl.ui.labeling.ModuleKeywordTexts;
import com.puppetlabs.geppetto.pp.dsl.ui.jdt_ersatz.ImagesOnFileSystemRegistry;
import java.net.URL;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ui.editor.hover.html.DefaultEObjectHoverProvider;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class ModuleHoverProvider extends DefaultEObjectHoverProvider {
  @Inject
  private ImagesOnFileSystemRegistry imagesOnFileSystemRegistry;
  
  @Inject
  @Extension
  private ModuleKeywordTexts _moduleKeywordTexts;
  
  public String getHoverInfoAsHtml(final EObject element) {
    String _xblockexpression = null;
    {
      if ((element instanceof Keyword)) {
        final String html = this._moduleKeywordTexts.getHoverText(((Keyword)element));
        boolean _notEquals = (!Objects.equal(html, null));
        if (_notEquals) {
          return html;
        }
      }
      _xblockexpression = super.getHoverInfoAsHtml(element);
    }
    return _xblockexpression;
  }
  
  public String getFirstLine(final EObject o) {
    String _xblockexpression = null;
    {
      Class<? extends EObject> _class = o.getClass();
      String _name = _class.getName();
      String _plus = ("Hello " + _name);
      InputOutput.<String>println(_plus);
      final StringBuilder builder = new StringBuilder();
      ILabelProvider _labelProvider = this.getLabelProvider();
      final Image image = _labelProvider.getImage(o);
      boolean _notEquals = (!Objects.equal(image, null));
      if (_notEquals) {
        ImageDescriptor _createFromImage = ImageDescriptor.createFromImage(image);
        final URL imageURL = this.imagesOnFileSystemRegistry.getImageURL(_createFromImage);
        StringBuilder _append = builder.append("<IMG src=\"");
        String _externalForm = imageURL.toExternalForm();
        StringBuilder _append_1 = _append.append(_externalForm);
        _append_1.append("\"/>&nbsp;");
      }
      StringBuilder _append_2 = builder.append("<b>");
      String _label = this.getLabel(o);
      StringBuilder _append_3 = _append_2.append(_label);
      StringBuilder _append_4 = _append_3.append("</b>");
      _xblockexpression = _append_4.toString();
    }
    return _xblockexpression;
  }
}
