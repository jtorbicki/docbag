package org.docbag.expression.parser;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.docbag.expression.EmptyExpression;
import org.docbag.expression.Expression;
import org.docbag.expression.ExpressionFactory;

/**
 * Regular expressions based implementation of {@link ExpressionParser}.
 *
 * <p>The expressionPattern attribute is used to find String representations of {@link Expression} objects.</p>
 * <p>Once found, the {@link ExpressionFactory} is used to create {@link Expression} instances.</p>
 *
 * <p>This class is reusable and thread safe.</p>
 *
 * @author Jakub Torbicki
 */
public class RegExpExpressionParser<R> implements ExpressionParser<String, R> {
    private final Pattern expressionPattern;
    private final ExpressionFactory<String, R> factory;

    public RegExpExpressionParser(String expressionPattern, ExpressionFactory<String, R> factory) {
        this.expressionPattern = Pattern.compile(expressionPattern, Pattern.MULTILINE | Pattern.DOTALL);
        this.factory = factory;
    }

    /**
     * @return true if expressionSource matches EXPRESSION_PATTERN
     */
    public boolean isExpression(String expressionSource) {
        if (expressionSource == null) {
            throw new NullPointerException("expressionSource not set!");
        }
        return expressionPattern.matcher(expressionSource).matches();
    }

    /**
     * @return {@link Expression} object if the expressionSource was valid, or {@link EmptyExpression} otherwise
     */
    public Expression<String, R> parseExpression(String expressionSource) {
        if (expressionSource == null) {
            throw new NullPointerException("expressionSource not set!");
        }
        Matcher matcher = expressionPattern.matcher(expressionSource);
        if (matcher.find()) {
            return factory.createExpression(matcher.group(1));
        }
        return new EmptyExpression();
    }

    /**
     * The returned list contains all expression tokens together with tokens that don't match expression pattern.
     *
     * @param source tokens
     * @return
     */
    public List<String> split(String source) {
        if (source == null) {
            throw new NullPointerException("source can't be null!");
        }
        return Arrays.asList(RegExpUtil.inclusiveSplit(source, expressionPattern, 0));
    }
}
