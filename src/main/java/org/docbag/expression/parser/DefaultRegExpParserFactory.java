package org.docbag.expression.parser;

import org.docbag.expression.RuntimeExpressionFactory;
import org.docbag.expression.evaluator.SpELExpressionEvaluator;
import org.docbag.template.TemplatePatterns;

/**
 * Default {@link RegExpExpressionParser} factory. It constructs the parser with the following attributes:
 * <ul>
 *     <li>{@link TemplatePatterns#EXPRESSION_PATTERN} as an {@link org.docbag.expression.Expression} pattern</li>
 *     <li>{@link RuntimeExpressionFactory} as an {@link org.docbag.expression.Expression} object factory</li>
 *     <li>{@link SpELExpressionEvaluator} as an implementation of {@link org.docbag.expression.evaluator.ExpressionEvaluator}</li>
 * </ul>
 *
 * @author Jakub Torbicki
 */
public class DefaultRegExpParserFactory implements ExpressionParserFactory<String, String> {
    private static class ExpressionParserHolder {
        public static final ExpressionParser<String, String> parser = new RegExpExpressionParser<String>(TemplatePatterns.EXPRESSION_PATTERN,
            new RuntimeExpressionFactory<String, String>(new SpELExpressionEvaluator()));
    }

    public ExpressionParser<String, String> getParser() {
        return ExpressionParserHolder.parser;
    }
}
