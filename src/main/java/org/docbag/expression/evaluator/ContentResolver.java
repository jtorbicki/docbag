package org.docbag.expression.evaluator;

import org.docbag.Context;
import org.docbag.chart.Chart;
import org.docbag.table.Table;

/**
 * This is the bridge between the template's embedded expressions and the {@link Context} object.
 *
 * <p>The {@link ExpressionEvaluator} can utilize this interface to simplify and unify
 * mapping between embedded expressions context calls.</p>
 *
 * <p>The values returned are dependent on the ContentResolver implementation.</p>
 *
 * @author Jakub Torbicki
 */
public interface ContentResolver {
    /**
     * Resolve Object from it's String representation
     */
    public Object context(String object);

    /**
     * Resolve {@link Chart} from it's String representation
     */
    public Chart chart(String chart);

    /**
     * Resolve {@link Table} from it's String representation
     */
    public Table table(String table);
}
