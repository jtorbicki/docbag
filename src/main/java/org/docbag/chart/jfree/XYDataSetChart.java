package org.docbag.chart.jfree;

import org.docbag.chart.Chart;
import org.jfree.data.xy.XYDataset;

/**
 * XYDataSetChart
 *
 * @author Jakub Torbicki
 */
public abstract class XYDataSetChart extends BaseChart {
    protected final XYDataset dataSet;

    public XYDataSetChart(Chart.Type type, Builder<? extends XYDataSetChart> builder) {
        super(type, builder);
        this.dataSet = builder.dataSet;
    }

    public static abstract class Builder<T extends XYDataSetChart> extends BaseChartBuilder<T> {
        private final XYDataset dataSet;

        public Builder(XYDataset dataSet) {
            this.dataSet = dataSet;
        }
    }
}
