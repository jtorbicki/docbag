package org.docbag.chart.jfree;

import java.awt.*;
import java.io.CharArrayWriter;
import java.io.Writer;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

/**
 * Converter implementation that knows how to convert {@link org.docbag.chart.Chart} into it's SVG representation.
 *
 * <p>Instead of converting the {@link org.docbag.chart.Chart} itself, the Wrapper wrapper object
 * is passed to the conversion. Besides the chart itself the wrapper also carries the width and height data
 * making it possible to reuse the same chart object and render it many times in a template in
 * different sizes.</p>
 *
 * @author Jakub Torbicki
 */
public class ChartToSVGConverter implements Converter<BaseChart.Wrapper, String> {
    // Empty SVG is returned in case of a conversion error
    private static final String EMPTY_SVG = "<svg xmlns=\"http://www.w3.org/2000/svg\"></svg>";
    private static final Logger log = LoggerFactory.getLogger(ChartToSVGConverter.class);

    public String convert(BaseChart.Wrapper chartHolder) {
        return convert(chartHolder.chart, chartHolder.width, chartHolder.height);
    }

    public String convert(BaseChart chart, int width, int height) {
        if (chart == null) {
            throw new NullPointerException("chart not set!");
        }
        if (width <= 0 || height <= 0) {
            log.warn("Invalid width \\ height parameters: width=" + width + ", height=" + height);
            return EMPTY_SVG;
        }
        SVGGraphics2D svgGenerator = new SVGGraphics2D(getDocument());
        // synchronized has to stay here as long as JFreeChart is not thread-safe.
        // (version 1.0.14 is not)
        synchronized (chart) {
            chart.getChart().draw(svgGenerator, new java.awt.geom.Rectangle2D.Double(0, 0, width, height));
        }
        // Set rendering properties
        svgGenerator.setSVGCanvasSize(new Dimension(width, height));
        svgGenerator.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        svgGenerator.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        return generateOutput(svgGenerator);
    }

    private String generateOutput(SVGGraphics2D svgGenerator) {
        Writer writer = new CharArrayWriter();
        try {
            svgGenerator.stream(writer, true);
            String svg = writer.toString();
            return svg.substring(svg.indexOf("<svg")); // TODO hack
        } catch (SVGGraphics2DIOException e) {
            log.error("SVGGraphics2DIOException: ", e.getLocalizedMessage(), e);
        }
        return EMPTY_SVG;
    }

    private Document getDocument() {
        DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
        return domImpl.createDocument(null, "svg", null);
    }
}
