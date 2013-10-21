package org.docbag.chart.jfree;

import org.docbag.chart.Chart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
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
    private final boolean gradient;

    private BarChart(Builder builder) {
        super(Chart.Type.BarChart, builder);
        this.gradient = builder.gradient;
    }

    public BarChart(Type type, Builder builder) {
        super(type, builder);
        this.gradient = builder.gradient;
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

    public boolean isGradient() {
        return gradient;
    }

    @Override
    protected void customizeChart(JFreeChart c) {
        super.customizeChart(c);
        customizeGradient(c);
    }

    private void customizeGradient(JFreeChart chart) {
        if (!gradient) {
            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            renderer.setBarPainter(new StandardBarPainter());
        }
    }

    public static class Builder extends CategoryDataSetChart.Builder<BarChart> {
        private boolean gradient = DefaultChartAttributes.gradient;

        public Builder(CategoryDataset dataSet) {
            super(dataSet);
        }

        public BarChart build() {
            return new BarChart(this);
        }

        public Builder gradient(boolean gradient) {
            this.gradient = gradient;
            return this;
        }
    }
}
