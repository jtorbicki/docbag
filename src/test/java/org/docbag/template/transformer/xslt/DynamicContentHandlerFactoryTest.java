package org.docbag.template.transformer.xslt;

import junit.framework.Assert;
import org.junit.Test;

/**
 * DynamicContentHandlerFactoryTest
 *
 * @author Jakub Torbicki
 */
public class DynamicContentHandlerFactoryTest {
    @Test(expected = UnsupportedOperationException.class)
    public void testGetContentHandler() throws Exception {
        DynamicContentHandlerFactory factory = new DynamicContentHandlerFactory();
        factory.getContentHandler();
    }

    @Test
    public void testGetContentHandlerContext() throws Exception {
        DynamicContentHandlerFactory factory = new DynamicContentHandlerFactory();
        Assert.assertNotNull(factory.getContentHandler(null));
    }
}
