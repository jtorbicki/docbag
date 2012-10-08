package org.docbag;

import java.util.Date;

/**
 * This is the top level interface of an object that is created with {@link DocumentCreator}
 *
 * <p>Abstract example:</p>
 * <pre>
 *     Document document = creator.createDocument(...);
 * </pre>
 *
 * <p>The more concrete example using a {@link DocumentStream} subclass:</p>
 *
 * <pre>
 *     DocumentCreator<DocumentStream, DocumentTemplateStream> creator = DocBag.newDocumentCreator();
 *     DocumentStream document = creator.createDocument("templates/template.html", getContext());
 * </pre>
 *
 * @see DocumentStream
 * @see DocumentCreator
 * @author Jakub Torbicki
 */
public interface Document {
    /**
     * @return Successful Document creation Date
     */
    public Date getCreationDate();
}
