package org.docbag.expression;

import org.docbag.expression.evaluator.ExpressionEvaluator;

/**
 * Implementation of {@link ExpressionFactory}.
 *
 * <p>This implementation creates {@link RuntimeExpression} instances and assigns an
 * {@link ExpressionEvaluator} to it.</p>
 *
 * @author Jakub Torbicki
 */
public class RuntimeExpressionFactory<S, R> implements ExpressionFactory<S, R> {
    private final ExpressionEvaluator<S, R> evaluator;

    public RuntimeExpressionFactory(ExpressionEvaluator<S, R> evaluator) {
        this.evaluator = evaluator;
    }

    /**
     * @see ExpressionFactory#createExpression(Object)
     */
    public Expression<S, R> createExpression(S e) {
        return new RuntimeExpression<S, R>(e, evaluator);
    }
}
