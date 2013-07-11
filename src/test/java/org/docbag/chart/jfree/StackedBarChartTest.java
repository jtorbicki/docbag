package org.docbag.chart.jfree;

import junit.framework.Assert;
import org.docbag.chart.Chart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.junit.Test;

/**
 * @author Jakub Torbicki
 */
public class StackedBarChartTest {
    final CategoryDataset dataSet = new DefaultCategoryDataset();

    @Test
    public void testGetType() {
        StackedBarChart chart = new StackedBarChart.Builder(dataSet).build();
        Assert.assertEquals(Chart.Type.StackedBarChart, chart.getType());
        Assert.assertTrue(chart.dataSet == dataSet);
    }
}
