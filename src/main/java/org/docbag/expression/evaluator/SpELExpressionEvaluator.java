package org.docbag.expression.evaluator;

import org.docbag.Context;
import org.docbag.chart.jfree.ChartToSVGConverter;
import org.docbag.expression.evaluator.json.JSONContentResolver;
import org.docbag.table.TableToFOConverter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.expression.spel.support.StandardTypeConverter;

/**
 * Spring Expression Language based implementation of {@link ExpressionEvaluator}.
 *
 * <p>For the complete documentation of what SpEL can and can't do please refer to the following
 * documentation: <a href="http://static.springsource.org/spring/docs/current/spring-framework-reference/html/expressions.html">
 *     http://static.springsource.org/spring/docs/current/spring-framework-reference/html/expressions.html
 * </a></p>
 *
 * <p>This implementation can evaluate any expression that {@link org.docbag.expression.evaluator.PatternsAwareExpressionEvaluator}
 * can evaluate, plus a lot more.</p>
 *
 * @author Jakub Torbicki
 */
public class SpELExpressionEvaluator implements ExpressionEvaluator<String, String> {
    private final static SpelExpressionParser parser = new SpelExpressionParser();
    private final static StandardTypeConverter standardTypeConverter;

    /**
     * Register non standard type converter, so the evaluator knows how to convert Charts objects into
     * SVG representation.
     */
    static {
        DefaultConversionService defaultConversionService = new DefaultConversionService();
        defaultConversionService.addConverter(new ChartToSVGConverter());
        defaultConversionService.addConverter(new TableToFOConverter());
        standardTypeConverter = new StandardTypeConverter(defaultConversionService);
    }

    public String evaluate(Context context, org.docbag.expression.Expression<String, String> expression) {
        if (expression.evaluated()) {
            return expression.getValue();
        }
        String expressionString = expression.getSource();
        try {
            Expression exp = parser.parseExpression(expressionString);
            StandardEvaluationContext spelContext = new StandardEvaluationContext(new JSONContentResolver(context));
            spelContext.setTypeConverter(standardTypeConverter);
            return exp.getValue(spelContext, String.class);
        } catch (Exception e) {
            throw new EvaluatorException(e.getLocalizedMessage(), e);
        }
    }

    public String evaluate(org.docbag.expression.Expression<String, String> expression) {
        if (expression.evaluated()) {
            return expression.getValue();
        }
        String expressionString = expression.getSource();
        try {
            Expression exp = parser.parseExpression(expressionString);
            return exp.getValue(String.class);
        } catch (Exception e) {
            throw new EvaluatorException(e.getLocalizedMessage(), e);
        }
    }
}
