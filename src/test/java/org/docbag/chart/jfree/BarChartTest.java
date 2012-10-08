package org.docbag.chart.jfree;

import junit.framework.Assert;
import org.docbag.chart.Chart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.junit.Test;

/**
 * BarChartTest
 *
 * @author Jakub Torbicki
 */
public class BarChartTest {
    final CategoryDataset dataSet = new DefaultCategoryDataset();

    @Test
    public void testGetType() {
        BarChart chart = new BarChart.Builder(dataSet).build();
        Assert.assertEquals(Chart.Type.BarChart, chart.getType());
        Assert.assertTrue(chart.dataSet == dataSet);
    }
}
