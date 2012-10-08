package org.docbag.chart.jfree;

import java.awt.*;

/**
 * Single chart serie style. Color and \ or stroke may be {@code null}
 *
 * @author Jakub Torbicki
 */
public class Style {
    private final Color color;
    private final Stroke stroke;

    public Style(Color color) {
        this(color, null);
    }

    public Style(Stroke stroke) {
        this(null, stroke);
    }

    public Style(Color color, Stroke stroke) {
        this.color = color;
        this.stroke = stroke;
    }

    public Color getColor() {
        return color;
    }

    public Stroke getStroke() {
        return stroke;
    }

    public String toString() {
        return "Style{" +
            "color=" + color +
            ", stroke=" + stroke +
            '}';
    }
}
