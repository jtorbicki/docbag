package org.docbag.expression;

/**
 * EmptyExpression is an {@link EvaluatedExpression} which value and source
 * attributes are empty String, ie: "".
 *
 * @author Jakub Torbicki
 */
public class EmptyExpression extends EvaluatedExpression {
    private static final String NAME = "EmptyExpression";

    public EmptyExpression() {
        super("", "");
    }

    public String toString() {
        return NAME;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof EmptyExpression;
    }

    @Override
    public int hashCode() {
        return NAME.hashCode();
    }
}
