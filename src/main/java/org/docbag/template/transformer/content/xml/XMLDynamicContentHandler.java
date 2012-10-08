package org.docbag.template.transformer.content.xml;

import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.docbag.Context;
import org.docbag.expression.Expression;
import org.docbag.expression.parser.DefaultRegExpParserFactory;
import org.docbag.expression.parser.ExpressionParser;
import org.docbag.expression.parser.ExpressionParserFactory;
import org.docbag.template.transformer.TransformerException;
import org.docbag.xml.XMLContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Implementation of {@link org.xml.sax.ContentHandler} that knows about embedded expressions.
 *
 * <p>It uses {@link ExpressionParser} to find embedded expressions within the template,
 * evaluates them and stores the evaluation result in the transformation.</p>
 *
 * <p>This class is NOT thread safe.</p>
 *
 * @author Jakub Torbicki
 */
public class XMLDynamicContentHandler extends DefaultHandler implements TemplateContentHandler<String> {
    private static final Logger log = LoggerFactory.getLogger(XMLDynamicContentHandler.class);
    private final ExpressionParser<String, String> parser;
    private final Context context;
    private XMLContent content;
    private StringBuilder currentElement;

    public XMLDynamicContentHandler(Context context) {
        this(context, new DefaultRegExpParserFactory());
    }

    public XMLDynamicContentHandler(Context context, ExpressionParserFactory<String, String> parserFactory) {
        this.context = context;
        this.parser = parserFactory.getParser();
    }

    public String getOutput() {
        return content.getContent();
    }

    public boolean isComplete() {
        return content.isComplete();
    }

    public void startDocument() throws SAXException {
        content = new XMLContent();
        currentElement = new StringBuilder();
        if (log.isDebugEnabled()) {
            log.debug("Starting to parse template");
        }
    }

    public void endDocument() throws SAXException {
        content.complete();
        currentElement = null;
        if (log.isDebugEnabled()) {
            log.debug("Template parsed!");
            log.debug(content.getContent());
        }
    }

    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        content.enterNamespace(prefix, uri);
    }

    public void endPrefixMapping(String prefix) throws SAXException {
        content.leaveAllNamespaces();
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        content.startElement(uri, localName, qName, attributes);
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        String s = currentElement.toString().trim();
        currentElement = new StringBuilder();
        if (s.length() > 0) {
            List<String> tokens = parser.split(s);
            for (String token : tokens) {
                if (StringUtils.isNotEmpty(token)) {
                    content.append(resolveDynamicContent(token));
                }
            }
        }
        content.endElement(qName);
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        currentElement.append(ch, start, length);
    }

    private String resolveDynamicContent(String token) {
        String value = token;
        if (parser.isExpression(value)) {
            Expression<String, String> expression = parser.parseExpression(value);
            value = expression.getValue(context);
            if (value == null) {
                log.error("Could not evaluate Expression: " + token);
                throw new TransformerException("Could not evaluate Expression: " + token);
            }
        } else {
            // TODO Find out how to stop SAX from escaping text.
            value = StringEscapeUtils.escapeXml(value);
        }
        return value;
    }
}
