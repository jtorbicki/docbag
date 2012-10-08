package org.docbag.xml;

import java.io.CharArrayWriter;
import java.util.ArrayDeque;
import java.util.Deque;

import org.docbag.template.transformer.TransformerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.xml.sax.Attributes;

/**
 * Helper class.
 *
 * @author Jakub Torbicki
 */
public class XMLContent {
    private static final Logger Log = LoggerFactory.getLogger(XMLContent.class);
    private final CharArrayWriter writer = new CharArrayWriter();
    private NameSpaceStack namespaces = new NameSpaceStack();
    private boolean complete = false;

    public boolean isComplete() {
        return complete;
    }

    public void complete() {
        complete = true;
        // null namespaces to help GC, but don't null writer since we need it in the getContent() operation
        namespaces = null;
        complete = true;
    }

    public String getContent() {
        return writer.toString();
    }

    public void enterNamespace(String prefix, String uri) {
        if (namespaces.needNewContext) {
            namespaces.pushContext();
            namespaces.needNewContext = false;
        }
        namespaces.getCurrentContext().addNameSpace(prefix, uri);
    }

    public void leaveAllNamespaces() {
        namespaces.popContext();
    }

    public void append(String str) {
        checkIfNotCompleted();
        if (str != null && str.length() > 0) {
            writer.write(str.toCharArray(), 0, str.length());
        } else {
            if (Log.isDebugEnabled()) {
                Log.debug("There was a try to add empty string to XMLContent");
            }
        }
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (namespaces.needNewContext) {
            namespaces.pushContext();
        }
        namespaces.needNewContext = true;
        StringBuilder b = new StringBuilder();
        b.append("<");
        b.append(qName);
        NameSpaceStack.NameSpace[] nameSpaces = namespaces.getCurrentContext().listNameSpaces();
        if (nameSpaces.length > 0) {
            for (NameSpaceStack.NameSpace nameSpace : nameSpaces) {
                if (StringUtils.hasText(nameSpace.getPrefix())) {
                    b.append(" xmlns:").append(nameSpace.getPrefix()).append("=\"").append(nameSpace.getUri()).append("\"");
                } else {
                    b.append(" xmlns=\"").append(nameSpace.getUri()).append("\"");
                }
            }
        }
        if (attributes != null) {
            int length = attributes.getLength();
            for (int i = 0; i < length; i++) {
                String attr = " " + attributes.getQName(i) + "=\"" + attributes.getValue(i) + "\"";
                b.append(attr);
            }
        }
        b.append(">");
        append(b.toString());
    }

    public void endElement(String name) {
        append("</" + name + ">");
    }

    private void checkIfNotCompleted() {
        if (complete) {
            throw new TransformerException("XMLContent already complete! Can't add new content.");
        }
    }

    private static class NameSpaceStack {
        private final Deque<NameSpaceContext> queue = new ArrayDeque<NameSpaceContext>();
        private boolean needNewContext = true;

        public NameSpaceStack() {
        }

        public void pushContext() {
            queue.push(new NameSpaceContext());
        }

        public void popContext() {
            queue.pop();
        }

        public NameSpaceContext getCurrentContext() {
            return queue.peek();
        }

        private static class NameSpaceContext {
            private final Deque<NameSpace> queue = new ArrayDeque<NameSpace>();

            public void addNameSpace(String prefix, String uri) {
                queue.push(new NameSpace(prefix, uri));
            }

            public NameSpace[] listNameSpaces() {
                return queue.toArray(new NameSpace[queue.size()]);
            }
        }

        private static class NameSpace {
            private final String prefix;
            private final String uri;

            public NameSpace(String prefix, String uri) {
                this.prefix = prefix;
                this.uri = uri;
            }

            public String getPrefix() {
                return prefix;
            }

            public String getUri() {
                return uri;
            }
        }
    }
}
