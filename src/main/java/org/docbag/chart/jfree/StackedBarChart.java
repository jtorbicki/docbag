package org.docbag.chart.jfree;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;

/**
 * StackedBarChart
 *
 * <p>To create:</p>
 *
 * <pre>
 *     new StackedBarChart.Builder(getBarData()).build());
 * </pre>
 *
 * <p>To create customized:</p>
 *
 * <pre>
 *     Map<String, Style> styles = new HashMap<String, Style>();
 *     styles.put("Label Name", new Style(Color.white));
 *     new StackedBarChart.Builder(createDataSet()).title("Bar Chart").styles(styles).build();
 * </pre>
 *
 * @author Jakub Torbicki
 */
public class StackedBarChart extends BarChart {

    private StackedBarChart(Builder builder) {
        super(Type.StackedBarChart, builder);
    }

    @Override
    protected JFreeChart createChart() {
        if (isThirdDimension()) {
            return ChartFactory.createStackedBarChart3D(getTitle(), getxAxisLabel(), getyAxisLabel(), dataSet, getPlotOrientation(), isLegend(),
                    isTooltips(), isUrls());
        } else {
            return ChartFactory.createStackedBarChart(getTitle(), getxAxisLabel(), getyAxisLabel(), dataSet, getPlotOrientation(), isLegend(),
                    isTooltips(), isUrls());
        }
    }

    public static class Builder extends BarChart.Builder {
        public Builder(CategoryDataset dataSet) {
            super(dataSet);
        }

        public StackedBarChart build() {
            return new StackedBarChart(this);
        }
    }
}
