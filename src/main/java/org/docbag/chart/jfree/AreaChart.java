package org.docbag.chart.jfree;

import org.docbag.chart.Chart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;

/**
 * AreaChart
 *
 * <p>To Create:</p>
 *
 * <pre>
 *     new AreaChart.Builder(getData()).build();
 * </pre>
 *
 * <p>To create customized:</p>
 *
 * <pre>
 *     Map<String, Style> styles = new HashMap<String, Style>();
 *     styles.put("Label Name", new Style(Color.white));
 *     new AreaChart.Builder(createDataSet()).title("Area Chart").styles(styles).build();
 * </pre>
 *
 * @author Jakub Torbicki
 */
public class AreaChart extends CategoryDataSetChart {
    private AreaChart(Builder builder) {
        super(Chart.Type.AreaChart, builder);
    }

    protected JFreeChart createChart() {
        return ChartFactory.createAreaChart(getTitle(), getxAxisLabel(), getyAxisLabel(), dataSet, getPlotOrientation(), isLegend(),
            isTooltips(), isUrls());
    }

    public static class Builder extends CategoryDataSetChart.Builder<AreaChart> {
        public Builder(CategoryDataset dataSet) {
            super(dataSet);
        }

        public AreaChart build() {
            return new AreaChart(this);
        }
    }
}
