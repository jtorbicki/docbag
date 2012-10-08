package org.docbag.expression;

import org.docbag.Context;

/**
 * EvaluatedExpression is a type of {@link Expression} that is evaluated only once.
 *
 * <p>The evaluation result is stored in the Expression and does not change in time</p>
 *
 * @author Jakub Torbicki
 */
public class EvaluatedExpression<S, R> implements Expression<S, R> {
    private final R value;
    private final S source;

    public EvaluatedExpression(S source, R value) {
        this.value = value;
        this.source = source;
    }

    public R getValue() {
        return value;
    }

    public R getValue(Context context) {
        return value;
    }

    public S getSource() {
        return source;
    }

    public String toString() {
        return "EvaluatedExpression{" +
            "value=" + value +
            '}';
    }

    public boolean evaluated() {
        return true;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EvaluatedExpression that = (EvaluatedExpression) o;
        if (!source.equals(that.source)) {
            return false;
        }
        return value.equals(that.value);
    }

    public int hashCode() {
        int result = value.hashCode();
        result = 31 * result + source.hashCode();
        return result;
    }
}
