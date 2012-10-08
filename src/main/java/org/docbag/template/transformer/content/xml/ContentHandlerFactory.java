package org.docbag.template.transformer.content.xml;

import org.docbag.Context;

/**
 * Creates instances of {@link TemplateContentHandler}
 *
 * @author Jakub Torbicki
 */
public interface ContentHandlerFactory<T> {
    /**
     * Create instance of {@link TemplateContentHandler} that is <b>*NOT*</b> {@link Context} aware
     */
    public TemplateContentHandler<T> getContentHandler();

    /**
     * Create instance of {@link TemplateContentHandler} that is {@link Context} aware
     */
    public TemplateContentHandler<T> getContentHandler(Context context);
}
