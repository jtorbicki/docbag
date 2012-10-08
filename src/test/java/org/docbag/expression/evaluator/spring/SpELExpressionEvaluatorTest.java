package org.docbag.expression.evaluator.spring;

import junit.framework.Assert;
import org.docbag.Context;
import org.docbag.DefaultContext;
import org.docbag.expression.EvaluatedExpression;
import org.docbag.expression.RuntimeExpression;
import org.docbag.expression.evaluator.EvaluatorException;
import org.docbag.expression.evaluator.SpELExpressionEvaluator;
import org.junit.Test;

/**
 * SpELExpressionEvaluatorTest
 *
 * @author Jakub Torbicki
 */
public class SpELExpressionEvaluatorTest {
    private static final String KEY_CONTEXT = "key";
    private static final String VALUE_CONTEXT = "value";

    @Test
    public void testEvaluate() throws Exception {
        SpELExpressionEvaluator evaluator = new SpELExpressionEvaluator();
        Assert.assertEquals("100", evaluator.evaluate(new EvaluatedExpression<String, String>("100", "100")));
        Assert.assertEquals("100", evaluator.evaluate(new RuntimeExpression<String, String>("50+50", evaluator)));
    }

    @Test(expected = EvaluatorException.class)
    public void testEvaluateRuntimeFails() throws Exception {
        SpELExpressionEvaluator evaluator = new SpELExpressionEvaluator();
        evaluator.evaluate(new RuntimeExpression<String, String>("context('name')", evaluator));
    }

    @Test
    public void testEvaluateContext() throws Exception {
        SpELExpressionEvaluator evaluator = new SpELExpressionEvaluator();
        Context<String, Object> context = getContext();
        Assert.assertEquals("100", evaluator.evaluate(context, new EvaluatedExpression<String, String>("100", "100")));
        Assert.assertEquals("100", evaluator.evaluate(context, new RuntimeExpression<String, String>("50+50", evaluator)));
        Assert.assertEquals(VALUE_CONTEXT,
            evaluator.evaluate(context, new RuntimeExpression<String, String>("context('" + KEY_CONTEXT + "')", evaluator)));
    }

    public Context<String, Object> getContext() {
        Context<String, Object> context = new DefaultContext();
        context.put(KEY_CONTEXT, VALUE_CONTEXT);
        return context;
    }
}
