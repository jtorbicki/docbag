package org.docbag.chart.jfree;

import java.awt.*;
import java.util.Collections;
import java.util.Map;

import org.jfree.chart.plot.PlotOrientation;

/**
 * Default chart attributes.
 *
 * @author Jakub Torbicki
 */
class DefaultChartAttributes {
    private DefaultChartAttributes() {
    }

    static final String title = "";
    static final String xAxisLabel = "";
    static final String yAxisLabel = "";
    static final PlotOrientation plotOrientation = PlotOrientation.VERTICAL;
    static final boolean legend = true;
    static final boolean tooltips = false;
    static final boolean urls = false;
    static final boolean thirdDimension = false;
    static final float fgAlpha = 1.0f;
    static final float bgAlpha = 1.0f;
    static final Paint bgPaint = Color.white;
    static final Paint plotBgPaint = Color.lightGray;
    static final Map<String, Style> styles = Collections.emptyMap();
}
