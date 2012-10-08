package org.docbag.expression;

/**
 * Creates instances of {@link Expression}
 *
 * @author Jakub Torbicki
 */
public interface ExpressionFactory<S, R> {
    /**
     * Create an {@link Expression} from it's source
     */
    public Expression<S, R> createExpression(S s);
}
