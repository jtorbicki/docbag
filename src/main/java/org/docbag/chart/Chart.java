package org.docbag.chart;

/**
 * Top level interface for chart objects.
 *
 * @author Jakub Torbicki
 */
public interface Chart {
    /**
     * Supported chart types
     */
    public enum Type {
        PieChart, BarChart, LineChart, AreaChart, StackedBarChart
    }

    /**
     * Get chart type
     */
    public Type getType();
}
