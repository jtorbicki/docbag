package org.docbag.template.transformer.content.xml;

import junit.framework.Assert;
import org.docbag.DefaultContext;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * XMLDynamicContentHandlerTest
 *
 * @author Jakub Torbicki
 */
public class XMLDynamicContentHandlerTest {
    @Test
    public void testIsComplete() throws SAXException {
        XMLDynamicContentHandler handler = new XMLDynamicContentHandler(new DefaultContext());
        handler.startDocument();
        Assert.assertFalse(handler.isComplete());
        handler.endDocument();
        Assert.assertTrue(handler.isComplete());
    }
}
