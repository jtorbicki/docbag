package org.docbag.template.transformer.xslt;

import org.docbag.Context;
import org.docbag.template.transformer.content.xml.ContentHandlerFactory;
import org.docbag.template.transformer.content.xml.TemplateContentHandler;
import org.docbag.template.transformer.content.xml.XMLDynamicContentHandler;

/**
 * Creates instances of {@link XMLDynamicContentHandler}
 *
 * @author Jakub Torbicki
 */
public class DynamicContentHandlerFactory implements ContentHandlerFactory<String> {
    public TemplateContentHandler<String> getContentHandler() {
        throw new UnsupportedOperationException("XMLDynamicContentHandler requires Context!");
    }

    /**
     * Create new instance of {@link XMLDynamicContentHandler}
     */
    public TemplateContentHandler<String> getContentHandler(Context context) {
        return new XMLDynamicContentHandler(context);
    }
}
