package org.docbag.chart.jfree;

import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.AbstractCategoryItemRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;

/**
 * ChartUtil
 *
 * @author Jakub Torbicki
 */
public class ChartUtil {
    private ChartUtil() {
    }

    /**
     * Set color of single chart style
     */
    public static void setSerieColor(org.jfree.chart.JFreeChart chart, int index, String serieName, Style style) {
        Plot plot = chart.getPlot();
        if (plot instanceof CategoryPlot) {
            AbstractCategoryItemRenderer renderer = (AbstractCategoryItemRenderer) ((CategoryPlot) plot).getRenderer();
            renderer.setSeriesPaint(index, style.getColor());
        } else if (plot instanceof PiePlot) {
            ((PiePlot) plot).setSectionPaint(serieName, style.getColor());
        }
    }

    /**
     * Set style of single chart style
     */
    public static void setSerieStyle(org.jfree.chart.JFreeChart chart, int index, Style style) {
        Plot plot = chart.getPlot();
        if (plot instanceof CategoryPlot) {
            CategoryItemRenderer renderer = chart.getCategoryPlot().getRenderer();
            renderer.setSeriesStroke(index, style.getStroke());
        } else if (plot instanceof XYPlot) {
            XYItemRenderer renderer = chart.getXYPlot().getRenderer();
            renderer.setSeriesStroke(index, style.getStroke());
        }
    }
}
