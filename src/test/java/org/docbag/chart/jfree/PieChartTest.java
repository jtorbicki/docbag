package org.docbag.chart.jfree;

import junit.framework.Assert;
import org.docbag.chart.Chart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.junit.Test;

/**
 * PieChartTest
 *
 * @author Jakub Torbicki
 */
public class PieChartTest {
    final PieDataset dataSet = new DefaultPieDataset();

    @Test
    public void testGetType() {
        PieChart chart = new PieChart.Builder(dataSet).build();
        Assert.assertEquals(Chart.Type.PieChart, chart.getType());
    }
}
