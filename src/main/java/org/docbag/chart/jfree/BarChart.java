package org.docbag.chart.jfree;

import org.docbag.chart.Chart;
import org.jfree.chart.ChartFactory;
import org.jfree.data.category.CategoryDataset;

/**
 * BarChart
 *
 * <p>To create:</p>
 *
 * <pre>
 *     new BarChart.Builder(getBarData()).build());
 * </pre>
 *
 * <p>To create customized:</p>
 *
 * <pre>
 *     Map<String, Style> styles = new HashMap<String, Style>();
 *     styles.put("Label Name", new Style(Color.white));
 *     new BarChart.Builder(createDataSet()).title("Bar Chart").styles(styles).build();
 * </pre>
 *
 * @author Jakub Torbicki
 */
public class BarChart extends CategoryDataSetChart {
    private BarChart(Builder builder) {
        super(Chart.Type.BarChart, builder);
    }

    protected org.jfree.chart.JFreeChart createChart() {
        if (isThirdDimension()) {
            return ChartFactory.createBarChart3D(getTitle(), getxAxisLabel(), getyAxisLabel(), dataSet, getPlotOrientation(), isLegend(),
                isTooltips(), isUrls());
        } else {
            return ChartFactory.createBarChart(getTitle(), getxAxisLabel(), getyAxisLabel(), dataSet, getPlotOrientation(), isLegend(),
                isTooltips(), isUrls());
        }
    }

    public static class Builder extends CategoryDataSetChart.Builder<BarChart> {
        public Builder(CategoryDataset dataSet) {
            super(dataSet);
        }

        public BarChart build() {
            return new BarChart(this);
        }
    }
}
