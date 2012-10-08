package org.docbag.expression.parser;

import junit.framework.Assert;
import org.docbag.expression.EvaluatedExpression;
import org.docbag.expression.Expression;
import org.docbag.expression.ExpressionFactory;
import org.docbag.template.TemplatePatterns;
import org.junit.Test;

/**
 * RegExpExpressionParserTest
 *
 * @author Jakub Torbicki
 */
public class RegExpExpressionParserTest {
    private static final String expressionPattern = TemplatePatterns.EXPRESSION_PATTERN;
    private static final String expression1 = "#{asd}";
    private static final String expression2 = "#{}";
    private static final String expression3 = "{asd}";
    private static final String expression4 = "#{asd";
    private static final String expression5 = "#{context('asd')}";
    private static final String expression6 = null;
    private static final String expression7 = "";
    private static final String expression8 = "#{chart('name', 100,    100)}";
    private static final String expression9 = "#{chart('name',100,100)}";

    @Test
    public void testIsExpression() throws Exception {
        RegExpExpressionParser parser = new RegExpExpressionParser(expressionPattern, getFactory());
        Assert.assertTrue(parser.isExpression(expression1));
        Assert.assertFalse(parser.isExpression(expression2));
        Assert.assertFalse(parser.isExpression(expression3));
        Assert.assertFalse(parser.isExpression(expression4));
        Assert.assertTrue(parser.isExpression(expression5));
        Assert.assertFalse(parser.isExpression(expression7));
        Assert.assertTrue(parser.isExpression(expression8));
        Assert.assertTrue(parser.isExpression(expression9));
    }

    @Test(expected = NullPointerException.class)
    public void testIsExpressionNull() throws Exception {
        RegExpExpressionParser parser = new RegExpExpressionParser(expressionPattern, getFactory());
        parser.isExpression(expression6);
    }

    @Test
    public void testParseExpression() throws Exception {
        RegExpExpressionParser parser = new RegExpExpressionParser(expressionPattern, getFactory());
        Assert.assertEquals("asd", parser.parseExpression(expression1).getSource());
        Assert.assertEquals("", parser.parseExpression(expression2).getSource());
        Assert.assertEquals("", parser.parseExpression(expression3).getSource());
        Assert.assertEquals("", parser.parseExpression(expression4).getSource());
        Assert.assertEquals("context('asd')", parser.parseExpression(expression5).getSource());
        Assert.assertEquals("", parser.parseExpression(expression7).getSource());
    }

    @Test(expected = NullPointerException.class)
    public void testParseExpressionNull() throws Exception {
        RegExpExpressionParser parser = new RegExpExpressionParser(expressionPattern, getFactory());
        parser.parseExpression(expression6);
    }

    private ExpressionFactory getFactory() {
        return new ExpressionFactory<String, String>() {
            public Expression createExpression(String o) {
                return new EvaluatedExpression(o, o);
            }
        };
    }
}
