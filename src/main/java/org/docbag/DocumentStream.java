package org.docbag;

import java.io.InputStream;
import java.util.Date;

/**
 * Implementation of a {@link Document} interface that holds an {@link InputStream} containing
 * the generated Document.
 *
 * @author Jakub Torbicki
 */
public class DocumentStream implements Document {
    private final InputStream document;
    private final Date creationDate;

    public DocumentStream(Date creationDate, InputStream document) {
        this.creationDate = new Date(creationDate.getTime());
        this.document = document;
    }

    /**
     * @return {@link InputStream}
     */
    public InputStream getStream() {
        return document;
    }

    public Date getCreationDate() {
        return new Date(creationDate.getTime());
    }

    public String toString() {
        return "DocumentStream{" +
            "creationDate=" + creationDate +
            '}';
    }
}
