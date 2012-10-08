package org.docbag.expression;

import junit.framework.Assert;
import org.junit.Test;

/**
 * RuntimeExpressionFactoryTest
 *
 * @author Jakub Torbicki
 */
public class RuntimeExpressionFactoryTest {
    @Test
    public void testCreateExpression() throws Exception {
        RuntimeExpressionFactory<String, String> factory = new RuntimeExpressionFactory<String, String>(null);
        Expression<String, String> exp = factory.createExpression("source");
        Assert.assertTrue(exp instanceof RuntimeExpression);
    }
}
