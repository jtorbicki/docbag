package org.docbag.chart.jfree;

import java.awt.*;
import java.util.*;
import java.util.List;

import org.docbag.chart.Chart;
import org.docbag.chart.ChartBuilder;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;

/**
 * JFreeChart based implementation of the {@link Chart} interface.
 *
 * @see AreaChart
 * @see BarChart
 * @see LineChart
 * @see PieChart
 * @author Jakub Torbicki
 */
public abstract class BaseChart implements Chart {
    private JFreeChart chart;
    private final Type type;
    private final String title;
    private final String xAxisLabel;
    private final String yAxisLabel;
    private final PlotOrientation plotOrientation;
    private final boolean legend;
    private final boolean tooltips;
    private final boolean urls;
    private final float fgAlpha;
    private final float bgAlpha;
    private final boolean thirdDimension;
    private final Paint bgPaint;
    private final Paint plotBgPaint;
    private final Map<String, Style> styleMap;

    public BaseChart(Type type, BaseChartBuilder<? extends BaseChart> builder) {
        this.type = type;
        this.title = builder.title;
        this.xAxisLabel = builder.xAxisLabel;
        this.yAxisLabel = builder.yAxisLabel;
        this.plotOrientation = builder.plotOrientation;
        this.legend = builder.legend;
        this.tooltips = builder.tooltips;
        this.urls = builder.urls;
        this.fgAlpha = builder.fgAlpha;
        this.bgAlpha = builder.bgAlpha;
        this.thirdDimension = builder.thirdDimension;
        this.bgPaint = builder.bgPaint;
        this.plotBgPaint = builder.plotBgPaint;
        this.styleMap = builder.styles;
    }

    JFreeChart getChart() {
        org.jfree.chart.JFreeChart c = chart;
        if (c == null) {
            c = createChart();
            customizeChart(c);
            chart = c;
        }
        return chart;
    }

    protected abstract org.jfree.chart.JFreeChart createChart();
    protected abstract List<String> getLabels();

    protected void customizeChart(org.jfree.chart.JFreeChart c) {
        c.setBackgroundPaint(bgPaint);
        Plot plot = c.getPlot();
        plot.setBackgroundImageAlpha(bgAlpha);
        plot.setForegroundAlpha(fgAlpha);
        plot.setBackgroundPaint(plotBgPaint);
        customizeLabelColors(c);
    }

    protected void customizeLabelColors(org.jfree.chart.JFreeChart c) {
        List keys = getLabels();
        Map<String, Style> styles = getStyleMap();
        if (keys != null) {
            for (int i = 0; i < keys.size(); i++) {
                String label = (String) keys.get(i);
                Style style = styles.get(label);
                if (style != null) {
                    ChartUtil.setSerieColor(c, i, label, style);
                }
            }
        }
    }

    public Type getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getxAxisLabel() {
        return xAxisLabel;
    }

    public String getyAxisLabel() {
        return yAxisLabel;
    }

    public PlotOrientation getPlotOrientation() {
        return plotOrientation;
    }

    public boolean isLegend() {
        return legend;
    }

    public boolean isTooltips() {
        return tooltips;
    }

    public boolean isUrls() {
        return urls;
    }

    public float getFgAlpha() {
        return fgAlpha;
    }

    public float getBgAlpha() {
        return bgAlpha;
    }

    public boolean isThirdDimension() {
        return thirdDimension;
    }

    public Paint getBgPaint() {
        return bgPaint;
    }

    public Paint getPlotBgPaint() {
        return plotBgPaint;
    }

    public Map<String, Style> getStyleMap() {
        return Collections.unmodifiableMap(styleMap);
    }

    public String toString() {
        return "BaseChart{" +
            "type=" + type +
            ", title='" + title + '\'' +
            '}';
    }

    public static abstract class BaseChartBuilder<T extends BaseChart> implements ChartBuilder<T> {
        // optional
        private String title = DefaultChartAttributes.title;
        private String xAxisLabel = DefaultChartAttributes.xAxisLabel;
        private String yAxisLabel = DefaultChartAttributes.yAxisLabel;
        private PlotOrientation plotOrientation = DefaultChartAttributes.plotOrientation;
        private boolean legend = DefaultChartAttributes.legend;
        private boolean tooltips = DefaultChartAttributes.tooltips;
        private boolean urls = DefaultChartAttributes.urls;
        private boolean thirdDimension = DefaultChartAttributes.thirdDimension;
        private float fgAlpha = DefaultChartAttributes.fgAlpha;
        private float bgAlpha = DefaultChartAttributes.bgAlpha;
        private Paint bgPaint = DefaultChartAttributes.bgPaint;
        private Paint plotBgPaint = DefaultChartAttributes.plotBgPaint;
        private Map<String, Style> styles = DefaultChartAttributes.styles;

        public abstract T build();

        public BaseChartBuilder<T> title(String title) {
            this.title = title;
            return this;
        }

        public BaseChartBuilder<T> xAxisLabel(String xAxisLabel) {
            this.xAxisLabel = xAxisLabel;
            return this;
        }

        public BaseChartBuilder<T> yAxisLabel(String yAxisLabel) {
            this.yAxisLabel = yAxisLabel;
            return this;
        }

        public BaseChartBuilder<T> plotOrientation(PlotOrientation plotOrientation) {
            this.plotOrientation = plotOrientation;
            return this;
        }

        public BaseChartBuilder<T> legend(boolean legend) {
            this.legend = legend;
            return this;
        }

        public BaseChartBuilder<T> tooltips(boolean tooltips) {
            this.tooltips = tooltips;
            return this;
        }

        public BaseChartBuilder<T> urls(boolean urls) {
            this.urls = urls;
            return this;
        }

        public BaseChartBuilder<T> fgAlpha(float fgAlpha) {
            this.fgAlpha = fgAlpha;
            return this;
        }

        public BaseChartBuilder<T> bgAlpha(float bgAlpha) {
            this.bgAlpha = bgAlpha;
            return this;
        }

        public BaseChartBuilder<T> thirdDimension(boolean thirdDimension) {
            this.thirdDimension = thirdDimension;
            return this;
        }

        public BaseChartBuilder<T> bgPaint(Paint bgPaint) {
            this.bgPaint = bgPaint;
            return this;
        }

        public BaseChartBuilder<T> plotBgPaint(Paint plotBgPaint) {
            this.plotBgPaint = plotBgPaint;
            return this;
        }

        public BaseChartBuilder<T> styles(Map<String, Style> styles) {
            this.styles = styles;
            return this;
        }
    }

    public static class Wrapper implements Chart {
        final BaseChart chart;
        final int width;
        final int height;

        public Wrapper(BaseChart chart, int width, int height) {
            this.chart = chart;
            this.width = width;
            this.height = height;
        }

        public Type getType() {
            return chart.getType();
        }
    }
}
