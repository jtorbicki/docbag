package org.docbag.expression;

import org.docbag.Context;
import org.docbag.expression.evaluator.ExpressionEvaluator;

/**
 * RuntimeExpression is an {@link Expression} which value is evaluated at runtime by {@link ExpressionEvaluator}
 *
 * <p>The evaluation result is not stored, so each subsequent getValue() method invocation
 * will result in the same number of evaluations.</p>
 * <p>It is not guaranteed that each evaluation will produce the same result. It may happen for instance
 * that the {@link Context} object changes between getValue(Context context) calls resulting in a
 * completely different evaluation result.</p>
 *
 * @author Jakub Torbicki
 */
public class RuntimeExpression<S, R> implements Expression<S, R> {
    private final ExpressionEvaluator<S, R> evaluator;
    private final S source;

    public RuntimeExpression(S source, ExpressionEvaluator<S, R> evaluator) {
        this.source = source;
        this.evaluator = evaluator;
    }

    public R getValue() {
        return evaluator.evaluate(this);
    }

    /**
     * Evaluates expression value in the {@link Context}
     */
    public R getValue(Context context) {
        return evaluator.evaluate(context, this);
    }

    public S getSource() {
        return source;
    }

    public String toString() {
        return "RuntimeExpression{" +
            "source=" + getSource() +
            ", evaluator=" + evaluator +
            '}';
    }

    /**
     * Even if the RuntimeExpression has already been evaluated, it'll still return false.
     * It is because the evaluation result is not stored and may change in time.
     */
    public boolean evaluated() {
        return false;
    }
}
