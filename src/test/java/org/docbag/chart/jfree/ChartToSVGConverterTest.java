package org.docbag.chart.jfree;

import java.util.Collections;
import java.util.List;

import junit.framework.Assert;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.junit.Test;

/**
 * ChartToSVGConverterTest
 *
 * @author Jakub Torbicki
 */
public class ChartToSVGConverterTest {
    // Empty SVG is returned in case of a conversion error
    private static final String EMPTY_SVG = "<svg xmlns=\"http://www.w3.org/2000/svg\"></svg>";

    @Test(expected = NullPointerException.class)
    public void testConvertWrapperNull() throws Exception {
        ChartToSVGConverter converter = new ChartToSVGConverter();
        converter.convert(new BaseChart.Wrapper(null, 0, 0));
    }

    @Test(expected = NullPointerException.class)
    public void testConvertChartNull() throws Exception {
        ChartToSVGConverter converter = new ChartToSVGConverter();
        converter.convert(null, 0, 0);
    }

    @Test
    public void testConvertWrapperEmpty() throws Exception {
        ChartToSVGConverter converter = new ChartToSVGConverter();
        Assert.assertEquals(EMPTY_SVG, converter.convert(new BaseChart.Wrapper(getEmptyChart(), 0, 10)));
        Assert.assertEquals(EMPTY_SVG, converter.convert(new BaseChart.Wrapper(getEmptyChart(), 10, 0)));
    }

    @Test
    public void testConvertChartEmpty() throws Exception {
        ChartToSVGConverter converter = new ChartToSVGConverter();
        Assert.assertEquals(EMPTY_SVG, converter.convert(getEmptyChart(), 0, 10));
        Assert.assertEquals(EMPTY_SVG, converter.convert(getEmptyChart(), 10, 0));
    }

    @Test
    public void testConvertWrapper() throws Exception {
        ChartToSVGConverter converter = new ChartToSVGConverter();
        Assert.assertFalse(EMPTY_SVG.equals(converter.convert(new BaseChart.Wrapper(getChart(), 100, 100))));
    }

    @Test
    public void testConvertChart() throws Exception {
        ChartToSVGConverter converter = new ChartToSVGConverter();
        Assert.assertFalse(EMPTY_SVG.equals(converter.convert(getChart(), 100, 100)));
    }

    private BaseChart getChart() {
        return new PieChart.Builder(getData()).build();
    }

    private static PieDataset getData() {
        DefaultPieDataset result = new DefaultPieDataset();
        result.setValue("A", 33);
        result.setValue("B", 33);
        result.setValue("C", 34);
        return result;
    }

    public BaseChart getEmptyChart() {
        return new BaseChart(null, new BaseChart.BaseChartBuilder<org.docbag.chart.jfree.BaseChart>() {
            public BaseChart build() {
                return null;
            }
        }) {
            protected JFreeChart createChart() {
                return null;
            }

            protected List<String> getLabels() {
                return Collections.emptyList();
            }

            public Type getType() {
                return null;
            }
        };
    }
}
