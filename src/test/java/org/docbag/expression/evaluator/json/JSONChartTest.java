package org.docbag.expression.evaluator.json;

import junit.framework.Assert;
import org.junit.Test;

/**
 * JSONChartTest
 *
 * @author Jakub Torbicki
 */
public class JSONChartTest {
    @Test
    public void testGetWidth() throws Exception {
        JSONChart chart = new JSONChart();
        Assert.assertEquals(0, chart.getHeight());
        Assert.assertEquals(0, chart.getWidth());
        Assert.assertNull(chart.getName());
    }
}
