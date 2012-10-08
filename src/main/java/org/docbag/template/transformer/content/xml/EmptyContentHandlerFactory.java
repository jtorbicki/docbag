package org.docbag.template.transformer.content.xml;

import org.docbag.Context;

/**
 * Set this {@link ContentHandlerFactory} in {@link org.docbag.template.transformer.xslt.XSLTTemplateTransformer}
 * if you don't wish to handle embedded expressions in templates.
 *
 * @author Jakub Torbicki
 */
public class EmptyContentHandlerFactory implements ContentHandlerFactory<String> {
    public TemplateContentHandler getContentHandler() {
        return null;
    }

    public TemplateContentHandler getContentHandler(Context context) {
        return null;
    }
}
