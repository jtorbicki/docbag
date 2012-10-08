package org.docbag.expression;

import junit.framework.Assert;
import org.junit.Test;

/**
 * EmptyExpressionTest
 *
 * @author Jakub Torbicki
 */
public class EmptyExpressionTest {
    @Test
    public void testExpression() {
        EmptyExpression emptyExpression = new EmptyExpression();
        Assert.assertEquals("", emptyExpression.getValue());
        Assert.assertEquals("", emptyExpression.getSource());
        Assert.assertNotNull(emptyExpression.toString());
    }
}
