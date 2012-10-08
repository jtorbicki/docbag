package org.docbag.template.transformer.content.xml;

import java.util.NoSuchElementException;

import junit.framework.Assert;
import org.docbag.template.transformer.TransformerException;
import org.docbag.xml.XMLContent;
import org.junit.Test;

/**
 * XMLContentTest
 *
 * @author Jakub Torbicki
 */
public class XMLContentTest {
    private static final String XML_CONTENT = "XML content";

    @Test
    public void testIsComplete() {
        XMLContent xml = new XMLContent();
        Assert.assertFalse(xml.isComplete());
        xml.complete();
        Assert.assertTrue(xml.isComplete());
    }

    @Test
    public void testGetContent() {
        XMLContent xml = new XMLContent();
        Assert.assertNotNull(xml.getContent());
        xml.append(XML_CONTENT);
        xml.append(null);
        xml.append("");
        Assert.assertEquals(XML_CONTENT, xml.getContent());
    }

    @Test(expected = TransformerException.class)
    public void testGetContentFail() {
        XMLContent xml = new XMLContent();
        xml.complete();
        xml.append(XML_CONTENT);
    }

    @Test(expected = NoSuchElementException.class)
    public void testNamespaceFail() {
        XMLContent xml = new XMLContent();
        xml.enterNamespace("", "");
        xml.leaveAllNamespaces();
        xml.leaveAllNamespaces();
    }

    @Test
    public void testNamespace() {
        XMLContent xml = new XMLContent();
        for (int i = 0; i < 1234; i++) {
            xml.enterNamespace("", "");
        }
        xml.leaveAllNamespaces();
    }
}
