package org.docbag.expression.evaluator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.docbag.Context;
import org.docbag.chart.jfree.ChartToSVGConverter;
import org.docbag.expression.Expression;
import org.docbag.expression.evaluator.json.JSONContentResolver;
import org.docbag.table.TableToFOConverter;
import org.docbag.template.TemplatePatterns;

/**
 * {@link TemplatePatterns} aware implementation of a {@link ExpressionEvaluator} interface.
 *
 * <p>The {@link Expression} evaluation depends on the pattern used in the template:</p>
 * <pre>
 *    1. If the {@link TemplatePatterns#CONTEXT_PATTERN} was used then evaluation results in a
 *       forward call to the {@link JSONContentResolver#context(String)} operation.
 *       The returned object is then converted to it's String representation by simply calling toString() method.
 *    2. In case of {@link TemplatePatterns#CHART_PATTERN} the evaluator forwards execution to
 *       {@link JSONContentResolver#chart(String)} operation.
 *       In addition it also uses the {@link ChartToSVGConverter} to convert the {@link org.docbag.chart.Chart} into
 *       it's String representation.
 *    3. If {@link TemplatePatterns#TABLE_PATTERN} is the matching pattern then the evaluator forwards call to
 *       {@link JSONContentResolver#table(String)} operation.
 *       In addition it also uses the {@link TableToFOConverter} to convert the {@link org.docbag.table.Table} into
 *       it's String representation.
 * </pre>
 *
 * <p>Example:</p>
 *
 * <pre>
 *   #{context('name')}
 * </pre>
 *
 * <p>The above expression will result in a single call to the {@link JSONContentResolver#context(String)}
 * operation with "name" as a parameter value.</p>
 *
 * <p>If the associated object is found it'll be converted to a String by invoking toString() method, otherwise
 * an {@link EvaluatorException} will be thrown.</p>
 *
 * <p>It is not possible to evaluate more complex expressions with this evaluator, for instance:</p>
 * <pre>
 *      #{'Dynamic value follows: ' + context('name')}
 * </pre>
 * <p>will throw an {@link EvaluatorException}</p>
 * <p>If you require more complex functionality use {@link SpELExpressionEvaluator} instead.</p>
 * <p>It is, however, possible to achieve the above effect with the following expression:</p>
 * <pre>
 *      Dynamic value follows: #{context('name')}
 * </pre>
 *
 * @author Jakub Torbicki
 */
public class PatternsAwareExpressionEvaluator implements ExpressionEvaluator<String, String> {
    private static final Pattern contextPattern = Pattern.compile(TemplatePatterns.CONTEXT_PATTERN);
    private static final Pattern chartPattern = Pattern.compile(TemplatePatterns.CHART_PATTERN);
    private static final Pattern tablePattern = Pattern.compile(TemplatePatterns.TABLE_PATTERN);
    private static final ChartToSVGConverter CHART_CONVERTER = new ChartToSVGConverter();
    private static final TableToFOConverter TABLE_CONVERTER = new TableToFOConverter();

    public String evaluate(Context context, Expression<String, String> expression) {
        if (expression.evaluated()) {
            return expression.getValue();
        }
        Object o = evaluateExpression(new JSONContentResolver(context), expression.getSource());
        if (o == null) {
            throw new EvaluatorException(
                new StringBuilder("Could not evaluate Expression. No Object found. Expression: '").append(expression)
                    .append("' ").append(context).toString());
        }
        return o.toString();
    }

    public String evaluate(Expression<String, String> expression) {
        if (expression.evaluated()) {
            return expression.getValue();
        }
        throw new EvaluatorException("Could not evaluate Expression without valid Context!");
    }

    private Object evaluateExpression(JSONContentResolver content, String source) {
        Matcher matcher = contextPattern.matcher(source);
        if (matcher.find()) {
            return content.context(matcher.group(1));
        } else {
            matcher = chartPattern.matcher(source);
            if (matcher.find()) {
                return CHART_CONVERTER.convert(content.chart(matcher.group(1)));
            } else {
                matcher = tablePattern.matcher(source);
                if (matcher.find()) {
                    return TABLE_CONVERTER.convert(content.table(matcher.group(1)));
                }
            }
        }
        throw new EvaluatorException("No matching pattern found for expression: " + source);
    }
}
