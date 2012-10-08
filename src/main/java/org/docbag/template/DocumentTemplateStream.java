package org.docbag.template;

import java.io.InputStream;

/**
 * Holds an {@link InputStream} containing {@link DocumentTemplate}
 *
 * @see MemoryTemplateStream
 * @see CachedDocumentTemplateStream
 * @author Jakub Torbicki
 */
public interface DocumentTemplateStream extends DocumentTemplate {
    /**
     * @return {@link InputStream}
     */
    public InputStream getStream();
}
