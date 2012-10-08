package org.docbag.expression.parser;

import java.util.List;

import org.docbag.expression.Expression;

/**
 * Implementations of ExpressionParser are responsible for creating {@link Expression} instances
 * form the expression source S.
 * @author Jakub Torbicki
 */
public interface ExpressionParser<S, R> {
    /**
     * Checks if this is a valid expression source.
     * @return true if it is, false otherwise
     */
    public boolean isExpression(S expressionSource);

    /**
     * Creates an {@link Expression} object from the expressionSource S.
     */
    public Expression<S, R> parseExpression(S expressionSource);

    /**
     * Split source into expression tokens.
     *
     * @param source tokens
     * @return list of tokens
     */
    public List<S> split(S source);
}
