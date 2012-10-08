package org.docbag.expression.evaluator;

import org.docbag.Context;
import org.docbag.expression.Expression;

/**
 * Implementations of this interface are responsible for expression evaluation.
 *
 * <p>An {@link Expression} can be anything, a math expression, a function call,
 * an object creation etc. It is up to the ExpressionEvaluator implementation
 * to recognize the form and to deal with it.</p>
 *
 * <p>The most commonly used functionality, however, would probably be simply
 * retrieving objects from the {@link Context}.</p>
 *
 * @see org.docbag.expression.evaluator.PatternsAwareExpressionEvaluator
 * @see SpELExpressionEvaluator
 * @author Jakub Torbicki
 */
public interface ExpressionEvaluator<S, R> {
    /**
     * Evaluate {@link Expression} in a concrete {@link Context}
     */
    public R evaluate(Context context, Expression<S, R> expression) throws EvaluatorException;

    /**
     * Evaluate {@link Expression}
     */
    public R evaluate(Expression<S, R> expression) throws EvaluatorException;
}
