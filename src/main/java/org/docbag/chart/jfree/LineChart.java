package org.docbag.chart.jfree;

import org.jfree.chart.ChartFactory;
import org.jfree.data.category.CategoryDataset;

/**
 * LineChart
 *
 * <p>To create:</p>
 *
 * <pre>
 *      new LineChart.Builder(getBarData()).build();
 * </pre>
 *
 * <p>To create customized:</p>
 *
 * <pre>
 *     Map<String, Style> styles = new HashMap<String, Style>();
 *     styles.put("Label Name", new Style(Color.white));
 *     styles.put("Label Name 2", new Style(new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f)));
 *     new LineChart.Builder(getBarData()).title("Line Chart").styles(styles).build();
 * </pre>
 *
 * @author Jakub Torbicki
 */
public class LineChart extends CategoryDataSetChart {
    private LineChart(Builder builder) {
        super(Type.LineChart, builder);
    }

    protected org.jfree.chart.JFreeChart createChart() {
        if (isThirdDimension()) {
            return ChartFactory.createLineChart3D(getTitle(), getxAxisLabel(), getyAxisLabel(), dataSet, getPlotOrientation(), isLegend(),
                isTooltips(), isUrls());
        } else {
            return ChartFactory.createLineChart(getTitle(), getxAxisLabel(), getyAxisLabel(), dataSet, getPlotOrientation(), isLegend(),
                isTooltips(), isUrls());
        }
    }

    public static class Builder extends CategoryDataSetChart.Builder<LineChart> {
        public Builder(CategoryDataset dataSet) {
            super(dataSet);
        }

        public LineChart build() {
            return new LineChart(this);
        }
    }
}
