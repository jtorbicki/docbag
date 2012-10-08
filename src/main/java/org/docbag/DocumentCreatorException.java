package org.docbag;

/**
 * Exception thrown by {@link DocumentCreator}
 *
 * @author Jakub Torbicki
 */
public class DocumentCreatorException extends RuntimeException {
    private static final long serialVersionUID = 8715270957927479862L;

    public DocumentCreatorException(String message) {
        super(message);
    }

    public DocumentCreatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
