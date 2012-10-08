package org.docbag.template.transformer.content.xml;

import junit.framework.Assert;
import org.junit.Test;

/**
 * EmptyContentHandlerFactoryTest
 *
 * @author Jakub Torbicki
 */
public class EmptyContentHandlerFactoryTest {
    @Test
    public void testGetContentHandler() throws Exception {
        EmptyContentHandlerFactory factory = new EmptyContentHandlerFactory();
        Assert.assertNull(factory.getContentHandler());
        Assert.assertNull(factory.getContentHandler(null));
    }
}
