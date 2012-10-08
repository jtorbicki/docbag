package org.docbag.expression;

import junit.framework.Assert;
import org.docbag.Context;
import org.docbag.DefaultContext;
import org.docbag.expression.evaluator.ExpressionEvaluator;
import org.junit.Test;

/**
 * RuntimeExpressionTest
 *
 * @author Jakub Torbicki
 */
public class RuntimeExpressionTest {
    private static final String source = "source";
    private static final String value = "value";

    @Test
    public void testGetValue() throws Exception {
        RuntimeExpression<String, String> exp = getExpression();
        Assert.assertEquals(value, exp.getValue());
        Assert.assertEquals(value, exp.getValue(new DefaultContext()));
    }

    @Test
    public void testGetSource() throws Exception {
        RuntimeExpression<String, String> exp = getExpression();
        Assert.assertEquals(source, exp.getSource());
    }

    @Test
    public void testIsRuntime() throws Exception {
        RuntimeExpression<String, String> exp = getExpression();
        Assert.assertFalse(exp.evaluated());
    }

    @Test
    public void testToString() throws Exception {
        RuntimeExpression<String, String> exp = getExpression();
        Assert.assertNotNull("Null toString", exp.toString());
    }

    private RuntimeExpression<String, String> getExpression() {
        return new RuntimeExpression<String, String>(source, new ExpressionEvaluator<String, String>() {
            public String evaluate(Context context, Expression<String, String> stringStringExpression) {
                return value;
            }

            public String evaluate(Expression<String, String> stringStringExpression) {
                return value;
            }
        });
    }
}
