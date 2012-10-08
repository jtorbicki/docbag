package org.docbag.chart.jfree;

import java.awt.*;
import java.util.*;
import java.util.List;

import junit.framework.Assert;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.junit.Test;

/**
 * BaseChartTest
 *
 * @author Jakub Torbicki
 */
public class BaseChartTest {
    private static class TestChartAttributes {
        private TestChartAttributes() {
        }

        static final String title = "test";
        static final String xAxisLabel = "testx";
        static final String yAxisLabel = "testy";
        static final PlotOrientation plotOrientation = PlotOrientation.HORIZONTAL;
        static final boolean legend = false;
        static final boolean tooltips = true;
        static final boolean urls = true;
        static final boolean thirdDimension = true;
        static final float fgAlpha = 0.1f;
        static final float bgAlpha = 0.2f;
        static final Paint bgPaint = Color.black;
        static final Paint plotBgPaint = Color.white;
        static final java.util.Map<String, Style> styles = new HashMap<String, Style>();

        static {
            styles.put("serie1", new Style(Color.white));
            styles.put("serie2", new Style(new BasicStroke()));
        }
    }

    private static class TestChart extends BaseChart {
        public TestChart(Type type, Builder builder) {
            super(type, builder);
        }

        protected JFreeChart createChart() {
            return null;
        }

        protected List<String> getLabels() {
            return Collections.emptyList();
        }

        public static class Builder extends BaseChartBuilder<TestChart> {
            public TestChart build() {
                return new TestChart(null, this);
            }
        }
    }

    @Test
    public void testDefaultValues() throws Exception {
        TestChart chart = new TestChart.Builder().build();
        Assert.assertEquals(DefaultChartAttributes.plotOrientation, chart.getPlotOrientation());
        Assert.assertEquals(DefaultChartAttributes.title, chart.getTitle());
        Assert.assertEquals(DefaultChartAttributes.xAxisLabel, chart.getxAxisLabel());
        Assert.assertEquals(DefaultChartAttributes.yAxisLabel, chart.getyAxisLabel());
        Assert.assertEquals(DefaultChartAttributes.fgAlpha, chart.getFgAlpha());
        Assert.assertEquals(DefaultChartAttributes.bgAlpha, chart.getBgAlpha());
        Assert.assertEquals(DefaultChartAttributes.bgPaint, chart.getBgPaint());
        Assert.assertEquals(DefaultChartAttributes.plotBgPaint, chart.getPlotBgPaint());
        Assert.assertEquals(DefaultChartAttributes.styles, chart.getStyleMap());
        Assert.assertEquals(DefaultChartAttributes.legend, chart.isLegend());
        Assert.assertEquals(DefaultChartAttributes.thirdDimension, chart.isThirdDimension());
        Assert.assertEquals(DefaultChartAttributes.tooltips, chart.isTooltips());
        Assert.assertEquals(DefaultChartAttributes.urls, chart.isUrls());
    }

    @Test
    public void testValues() throws Exception {
        TestChart chart = new TestChart.Builder().plotOrientation(TestChartAttributes.plotOrientation).title(
            TestChartAttributes.title).xAxisLabel(TestChartAttributes.xAxisLabel).yAxisLabel(TestChartAttributes.yAxisLabel).fgAlpha(
            TestChartAttributes.fgAlpha).bgAlpha(TestChartAttributes.bgAlpha).bgPaint(TestChartAttributes.bgPaint).plotBgPaint(
            TestChartAttributes.plotBgPaint).styles(TestChartAttributes.styles).legend(TestChartAttributes.legend).thirdDimension(
            TestChartAttributes.thirdDimension).tooltips(TestChartAttributes.tooltips).urls(TestChartAttributes.urls).build();
        Assert.assertEquals(TestChartAttributes.plotOrientation, chart.getPlotOrientation());
        Assert.assertEquals(TestChartAttributes.title, chart.getTitle());
        Assert.assertEquals(TestChartAttributes.xAxisLabel, chart.getxAxisLabel());
        Assert.assertEquals(TestChartAttributes.yAxisLabel, chart.getyAxisLabel());
        Assert.assertEquals(TestChartAttributes.fgAlpha, chart.getFgAlpha());
        Assert.assertEquals(TestChartAttributes.bgAlpha, chart.getBgAlpha());
        Assert.assertEquals(TestChartAttributes.bgPaint, chart.getBgPaint());
        Assert.assertEquals(TestChartAttributes.plotBgPaint, chart.getPlotBgPaint());
        Assert.assertEquals(TestChartAttributes.styles, chart.getStyleMap());
        Assert.assertEquals(TestChartAttributes.legend, chart.isLegend());
        Assert.assertEquals(TestChartAttributes.thirdDimension, chart.isThirdDimension());
        Assert.assertEquals(TestChartAttributes.tooltips, chart.isTooltips());
        Assert.assertEquals(TestChartAttributes.urls, chart.isUrls());
    }
}
