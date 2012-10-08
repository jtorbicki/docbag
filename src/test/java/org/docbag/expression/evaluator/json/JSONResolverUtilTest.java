package org.docbag.expression.evaluator.json;

import junit.framework.Assert;
import org.docbag.table.Table;
import org.junit.Test;

/**
 * JSONResolverUtilTest
 *
 * @author Jakub Torbicki
 */
public class JSONResolverUtilTest {
    private static final String CHART_KEY = "chart_key";
    private static final String TABLE_KEY = "table_key";
    private static final Table TABLE_VALUE = new Table(TABLE_KEY);
    private static final int CHART_WIDTH = 200;
    private static final int CHART_HEIGHT = 300;

    @Test
    public void testResolveChart() throws Exception {
        JSONChart chart = JSONResolverUtil.resolveChart(
            "{name: \"" + CHART_KEY + "\", width: " + CHART_WIDTH + ", height: " + CHART_HEIGHT + "}");
        Assert.assertEquals(CHART_KEY, chart.getName());
        Assert.assertEquals(CHART_WIDTH, chart.getWidth());
        Assert.assertEquals(CHART_HEIGHT, chart.getHeight());
    }

    @Test
    public void testResolveTable() throws Exception {
        Table table = JSONResolverUtil.resolveTable(
            "{ name: \"" + TABLE_KEY + "\", tbody: [ { style: { \"background-color\":\"#dddddd\" } } ] }");
        Assert.assertEquals(TABLE_VALUE, table);
    }
}
