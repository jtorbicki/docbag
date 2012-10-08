package org.docbag.template;

/**
 * This top level interface represents an object that is used as a template to create {@link org.docbag.Document} instances.
 *
 * @see DocumentTemplateStream
 * @author Jakub Torbicki
 */
public interface DocumentTemplate {
    /**
     * Template name
     */
    public String getName();
}
