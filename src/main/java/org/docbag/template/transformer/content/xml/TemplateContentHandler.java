package org.docbag.template.transformer.content.xml;

import org.xml.sax.ContentHandler;

/**
 * {@link ContentHandler} that is aware of the transformation process.
 *
 * @author Jakub Torbicki
 */
public interface TemplateContentHandler<T> extends ContentHandler {
    /**
     * Transformation output. This method invocation will deliver the output even
     * if the transformation is not complete yet.
     */
    public T getOutput();

    /**
     * Is transformation complete
     */
    public boolean isComplete();
}
