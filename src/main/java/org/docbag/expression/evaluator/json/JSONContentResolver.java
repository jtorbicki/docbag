package org.docbag.expression.evaluator.json;

import org.docbag.Context;
import org.docbag.chart.Chart;
import org.docbag.chart.jfree.BaseChart;
import org.docbag.expression.evaluator.ContentResolver;
import org.docbag.expression.evaluator.EvaluatorException;
import org.docbag.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JSON implementation of {@link ContentResolver}. It is {@link Context} aware.
 *
 * @author Jakub Torbicki
 */
public class JSONContentResolver implements ContentResolver {
    private static final Logger log = LoggerFactory.getLogger(JSONContentResolver.class);

    private final Context<String, Object> context;

    public JSONContentResolver(Context<String, Object> context) {
        this.context = context;
    }

    /**
     * Find Object in the {@link Context} by it's name.
     */
    public Object context(String name) {
        return context.get(name);
    }

    /**
     * Resolve chart's name, width and height from JSON and find the {@link Chart} in the {@link Context}.
     * Wrap the {@link Chart} with the {@link org.docbag.chart.jfree.BaseChart.Wrapper} wrapper to store
     * the width and height.
     */
    public BaseChart.Wrapper chart(String json) {
        JSONChart jsonChart = validateJSONChart(JSONResolverUtil.resolveChart(json));
        BaseChart chart = (BaseChart) context.get(jsonChart.getName());
        if (chart == null) {
            log.error("No Chart found with name:" + jsonChart.getName());
            return null;
        }
        return new BaseChart.Wrapper(chart, jsonChart.getWidth(), jsonChart.getHeight());
    }

    /**
     * Resolve {@link Table} from JSON and combine it with {@link Table} from the {@link Context}.
     */
    public Table table(String json) {
        Table template = JSONResolverUtil.resolveTable(json);
        Table table = (Table) context(template.getName());
        if (table != null) {
            return table.combine(template);
        } else {
            return template;
        }
    }

    private static JSONChart validateJSONChart(JSONChart chart) {
        if (chart.getName() == null) {
            throw new EvaluatorException("Wrong chart expression. No name specified.");
        } else if (chart.getWidth() <= 0) {
            throw new EvaluatorException("Wrong chart expression. No width specified.");
        } else if (chart.getHeight() <= 0) {
            throw new EvaluatorException("Wrong chart expression. No height specified.");
        }
        return chart;
    }
}
