package org.docbag.expression.parser;

import junit.framework.Assert;
import org.junit.Test;

/**
 * DefaultRegExpParserFactoryTest
 *
 * @author Jakub Torbicki
 */
public class DefaultRegExpParserFactoryTest {
    private static final String EXPRESSION_MATCHING_DEFAULT_PATTERN = "#{context('name')}";
    private static final String EXPRESSION_NOT_MATCHING_DEFAULT_PATTERN = "#[context('name')]";

    @Test
    public void testGetParser() throws Exception {
        DefaultRegExpParserFactory factory = new DefaultRegExpParserFactory();
        Assert.assertNotNull(factory.getParser());
        Assert.assertTrue(factory.getParser().isExpression(EXPRESSION_MATCHING_DEFAULT_PATTERN));
        Assert.assertFalse(factory.getParser().isExpression(EXPRESSION_NOT_MATCHING_DEFAULT_PATTERN));
    }
}
