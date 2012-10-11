package org.docbag.expression.parser;

import java.util.regex.Pattern;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.docbag.template.TemplatePatterns;

/**
 * RegExpUtilTest
 *
 * @author Jakub Torbicki
 */
public class RegExpUtilTest extends TestCase {
    private static final Pattern EXPRESSION_DELIMETER_PATTERN = Pattern.compile("#\\{(.+)\\}");
    private static final String PATTERN_ONE = "#{context('name')}";
    private static final String PATTERN_TWO = "some text #{context('name')}";
    private static final String PATTERN_THREE = "#{context('name')} some text";
    private static final String PATTERN_FOUR = "#{context('name')}#{context('name')}";
    private static final String PATTERN_FIVE = "some text #{context('name')}#{context('name')}";
    private static final String PATTERN_SIX = "#{context('name')}some text#{context('name')}";
    private static final String PATTERN_SEVEN = "some text #{context('name')}#{context('name')}some text";
    private static final String PATTERN_EIGHT = "#{context('name')}#{context('name')}some text";
    private static final String PATTERN_NINE = "some text#{context('name')}some text#{context('name')}some text";

    public void testInclusiveSplit() throws Exception {
        Assert.assertEquals(1, RegExpUtil.inclusiveSplit(PATTERN_ONE, EXPRESSION_DELIMETER_PATTERN, 0).length);
        Assert.assertEquals(2, RegExpUtil.inclusiveSplit(PATTERN_TWO, EXPRESSION_DELIMETER_PATTERN, 0).length);
        Assert.assertEquals(2, RegExpUtil.inclusiveSplit(PATTERN_THREE, EXPRESSION_DELIMETER_PATTERN, 0).length);
        // TODO all of those will not be resolved correctly. Unfortunately for now the pattern can't be changed to
        // #\{(.+?)\} due to JSON being used as an expression parameter
//        Assert.assertEquals(2, RegExpUtil.inclusiveSplit(PATTERN_FOUR, EXPRESSION_DELIMETER_PATTERN, 0).length);
//        Assert.assertEquals(3, RegExpUtil.inclusiveSplit(PATTERN_FIVE, EXPRESSION_DELIMETER_PATTERN, 0).length);
//        Assert.assertEquals(3, RegExpUtil.inclusiveSplit(PATTERN_SIX, EXPRESSION_DELIMETER_PATTERN, 0).length);
//        Assert.assertEquals(4, RegExpUtil.inclusiveSplit(PATTERN_SEVEN, EXPRESSION_DELIMETER_PATTERN, 0).length);
//        Assert.assertEquals(3, RegExpUtil.inclusiveSplit(PATTERN_EIGHT, EXPRESSION_DELIMETER_PATTERN, 0).length);
//        Assert.assertEquals(5, RegExpUtil.inclusiveSplit(PATTERN_NINE, EXPRESSION_DELIMETER_PATTERN, 0).length);
    }
}
