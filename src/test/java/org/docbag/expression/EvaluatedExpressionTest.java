package org.docbag.expression;

import junit.framework.Assert;
import org.junit.Test;

/**
 * EvaluatedExpressionTest
 *
 * @author Jakub Torbicki
 */
public class EvaluatedExpressionTest {
    private static final Integer source = new Integer(123);
    private static final Integer value = new Integer(123);

    @Test
    public void testGetValue() throws Exception {
        EvaluatedExpression<Integer, Integer> e = new EvaluatedExpression<Integer, Integer>(source, value);
        Assert.assertEquals(source, e.getSource());
        Assert.assertEquals(value, e.getValue());
    }

    @Test
    public void testIsRuntime() throws Exception {
        EvaluatedExpression<Integer, Integer> e = new EvaluatedExpression<Integer, Integer>(source, value);
        Assert.assertTrue(e.evaluated());
    }

    @Test
    public void testtoString() throws Exception {
        EvaluatedExpression<Integer, Integer> e = new EvaluatedExpression<Integer, Integer>(source, value);
        Assert.assertNotNull(e.toString());
    }
}
