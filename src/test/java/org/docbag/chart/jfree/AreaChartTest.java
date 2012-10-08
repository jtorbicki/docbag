package org.docbag.chart.jfree;

import junit.framework.Assert;
import org.docbag.chart.Chart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.junit.Test;

/**
 * AreaChartTest
 *
 * @author Jakub Torbicki
 */
public class AreaChartTest {
    final CategoryDataset dataSet = new DefaultCategoryDataset();

    @Test
    public void testGetType() {
        AreaChart chart = new AreaChart.Builder(dataSet).build();
        Assert.assertEquals(Chart.Type.AreaChart, chart.getType());
        Assert.assertTrue(chart.dataSet == dataSet);
    }
}
