package org.docbag.template;

/**
 * RegExp Patterns used in templates to embed expressions
 *
 * @author Jakub Torbicki
 */
public class TemplatePatterns {
    private TemplatePatterns() {
    }

    /**
     * Embedded expression pattern
     */
    public static final String EXPRESSION_PATTERN = "#\\{(.+)\\}";
    /**
     * Fetch value from {@link org.docbag.Context} pattern
     */
    public static final String CONTEXT_PATTERN = "context\\('(.+)'\\)";
    /**
     * Fetch {@link org.docbag.chart.Chart} from {@link org.docbag.Context} pattern
     */
    public static final String CHART_PATTERN = "chart\\('(.+)'\\)";
    /**
     * Fetch {@link org.docbag.table.Table} from {@link org.docbag.Context} pattern
     */
    public static final String TABLE_PATTERN = "table\\('(.+)'\\)";
}
