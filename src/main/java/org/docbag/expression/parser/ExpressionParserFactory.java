package org.docbag.expression.parser;

/**
 * Creates instances of {@link ExpressionParser}
 *
 * @author Jakub Torbicki
 */
public interface ExpressionParserFactory<S, R> {
    public ExpressionParser<S, R> getParser();
}
