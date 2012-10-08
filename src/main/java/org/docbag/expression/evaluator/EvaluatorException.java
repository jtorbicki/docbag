package org.docbag.expression.evaluator;

/**
 * EvaluatorException
 *
 * @author Jakub Torbicki
 */
public class EvaluatorException extends RuntimeException {
    private static final long serialVersionUID = -2047574915107335405L;

    public EvaluatorException(String message) {
        super(message);
    }

    public EvaluatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
