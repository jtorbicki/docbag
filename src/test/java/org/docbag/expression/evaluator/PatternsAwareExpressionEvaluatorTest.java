package org.docbag.expression.evaluator;

import junit.framework.Assert;
import org.docbag.Context;
import org.docbag.DefaultContext;
import org.docbag.expression.EvaluatedExpression;
import org.docbag.expression.RuntimeExpression;
import org.junit.Test;

/**
 * PatternsAwareExpressionEvaluatorTest
 *
 * @author Jakub Torbicki
 */
public class PatternsAwareExpressionEvaluatorTest {
    private static final String KEY_CONTEXT = "key";
    private static final String VALUE_CONTEXT = "value";

    @Test
    public void testEvaluate() throws Exception {
        PatternsAwareExpressionEvaluator evaluator = new PatternsAwareExpressionEvaluator();
        Assert.assertEquals("100", evaluator.evaluate(new EvaluatedExpression<String, String>("100", "100")));
    }

    @Test(expected = EvaluatorException.class)
    public void testEvaluateRuntimeFails() throws Exception {
        PatternsAwareExpressionEvaluator evaluator = new PatternsAwareExpressionEvaluator();
        evaluator.evaluate(new RuntimeExpression<String, String>("context('name')", evaluator));
    }

    @Test(expected = EvaluatorException.class)
    public void testEvaluateRuntimeEmpty() throws Exception {
        PatternsAwareExpressionEvaluator evaluator = new PatternsAwareExpressionEvaluator();
        evaluator.evaluate(getContext(), new RuntimeExpression<String, String>("context('invalid_input')", evaluator));
    }

    @Test
    public void testEvaluateRuntime() throws Exception {
        PatternsAwareExpressionEvaluator evaluator = new PatternsAwareExpressionEvaluator();
        Assert.assertEquals(VALUE_CONTEXT,
            evaluator.evaluate(getContext(), new RuntimeExpression<String, String>("context('" + KEY_CONTEXT + "')", evaluator)));
    }

    public Context<String, Object> getContext() {
        Context<String, Object> context = new DefaultContext();
        context.put(KEY_CONTEXT, VALUE_CONTEXT);
        return context;
    }
}
