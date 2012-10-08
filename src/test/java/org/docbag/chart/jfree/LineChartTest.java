package org.docbag.chart.jfree;

import junit.framework.Assert;
import org.docbag.chart.Chart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.junit.Test;

/**
 * LineChartTest
 *
 * @author Jakub Torbicki
 */
public class LineChartTest {
    final CategoryDataset dataSet = new DefaultCategoryDataset();

    @Test
    public void testGetType() {
        LineChart chart = new LineChart.Builder(dataSet).build();
        Assert.assertEquals(Chart.Type.LineChart, chart.getType());
        Assert.assertTrue(chart.dataSet == dataSet);
    }
}
