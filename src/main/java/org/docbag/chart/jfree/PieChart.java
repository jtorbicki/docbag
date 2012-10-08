package org.docbag.chart.jfree;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.PieDataset;

/**
 * PieChart
 *
 * <p>To create:</p>
 *
 * <pre>
 *      new PieChart.Builder(createPieDataSet()).build();
 * </pre>
 *
 * <p>PieCharts can be exploded. To create an exploded PieChart use the following code:</p>
 *
 * <pre>
 *      Map<Comparable, Double> exploded = new HashMap<Comparable, Double>();
 *      exploded.put("Label Name", 0.3);
 *      new PieChart.Builder(createPieDataSet()).exploded(exploded).title("Pie Chart").build();
 * </pre>
 *
 * <p>To change PieChart colors:</p>
 *
 * <pre>
 *      Map<String, Style> styles = new HashMap<String, Style>();
 *      styles.put("Label Name", new Style(Color.white));
 *      new PieChart.Builder(createPieDataSet()).title("Pie Chart").styles(styles).build();
 * </pre>
 *
 * @author Jakub Torbicki
 */
public class PieChart extends BaseChart {
    private final PieDataset dataSet;
    private final Map<Comparable<String>, Double> exploded;

    private PieChart(Builder builder) {
        super(Type.PieChart, builder);
        this.dataSet = builder.dataSet;
        this.exploded = builder.exploded;
    }

    protected org.jfree.chart.JFreeChart createChart() {
        if (isThirdDimension()) {
            return ChartFactory.createPieChart3D(getTitle(), dataSet, isLegend(), isTooltips(), isUrls());
        } else {
            return ChartFactory.createPieChart(getTitle(), dataSet, isLegend(), isTooltips(), isUrls());
        }
    }

    protected List<String> getLabels() {
        return dataSet.getKeys();
    }

    @Override
    protected void customizeChart(org.jfree.chart.JFreeChart chart) {
        super.customizeChart(chart);
        customizeExploded(chart);
    }

    private void customizeExploded(org.jfree.chart.JFreeChart chart) {
        if (exploded != null) {
            PiePlot plot = (PiePlot) chart.getPlot();
            Set<Map.Entry<Comparable<String>, Double>> set = exploded.entrySet();
            for (Map.Entry<Comparable<String>, Double> mapping : set) {
                plot.setExplodePercent(mapping.getKey(), mapping.getValue());
            }
        }
    }

    public static class Builder extends BaseChartBuilder<PieChart> {
        private final PieDataset dataSet;
        // optional
        private Map<Comparable<String>, Double> exploded;

        public Builder(PieDataset dataSet) {
            this.dataSet = dataSet;
        }

        public Builder exploded(Map<Comparable<String>, Double> exploded) {
            this.exploded = exploded;
            return this;
        }

        public PieChart build() {
            return new PieChart(this);
        }
    }
}
