package org.docbag.chart;

/**
 * Top level interface for {@link Chart} builders.
 *
 * @author Jakub Torbicki
 */
public interface ChartBuilder<T extends Chart> {
    /**
     * Build a {@link Chart} object
     */
    public T build();
}
