package org.docbag.expression.evaluator;

import junit.framework.Assert;
import org.docbag.Context;
import org.docbag.DefaultContext;
import org.docbag.chart.Chart;
import org.docbag.chart.jfree.BaseChart;
import org.docbag.chart.jfree.PieChart;
import org.docbag.expression.evaluator.json.JSONContentResolver;
import org.docbag.table.Table;
import org.junit.Test;

/**
 * JSONContentResolverTest
 *
 * @author Jakub Torbicki
 */
public class JSONContentResolverTest {
    private static final String VALUE_KEY = "value_key";
    private static final String VALUE_VALUE = "value_value";
    private static final String CHART_KEY = "chart_key";
    private static final Chart CHART_VALUE = new PieChart.Builder(null).build();
    private static final String TABLE_KEY = "table_key";
    private static final Table TABLE_VALUE = new Table(TABLE_KEY);
    private static final int CHART_WIDTH = 200;
    private static final int CHART_HEIGHT = 300;

    @Test
    public void testContext() throws Exception {
        JSONContentResolver resolver = new JSONContentResolver(getContext());
        Assert.assertEquals(VALUE_VALUE, resolver.context(VALUE_KEY));
    }

    @Test
    public void testChart() throws Exception {
        JSONContentResolver resolver = new JSONContentResolver(getContext());
        BaseChart.Wrapper chart = resolver.chart("{name: \"" + CHART_KEY + "\", width: " + CHART_WIDTH + ", height: " + CHART_HEIGHT + "}");
        Assert.assertEquals(CHART_VALUE.getType(), chart.getType());
    }

    @Test
    public void testTable() throws Exception {
        JSONContentResolver resolver = new JSONContentResolver(getContext());
        Table table = resolver.table("{ name: \"" + TABLE_KEY + "\", tbody: [ { style: { \"background-color\":\"#dddddd\" } } ] }");
        Assert.assertEquals(TABLE_VALUE, table);
    }

    private Context<String, Object> getContext() {
        Context<String, Object> context = new DefaultContext();
        context.put(VALUE_KEY, VALUE_VALUE);
        context.put(CHART_KEY, CHART_VALUE);
        context.put(TABLE_KEY, TABLE_VALUE);
        return context;
    }
}
