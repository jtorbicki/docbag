package org.docbag.creator.fop;

import junit.framework.Assert;
import org.docbag.DocumentCreatorException;
import org.junit.Test;

/**
 * @author Jakub Torbicki
 */
public class ClasspathResourceURIResolverTest {
    @Test(expected = DocumentCreatorException.class)
    public void testResolve() throws Exception {
        ClasspathResourceURIResolver resourceURIResolver = new ClasspathResourceURIResolver();
        Assert.assertNotNull(resourceURIResolver.resolve("xslt/attributes.xsl", ""));
        resourceURIResolver.resolve("xslt/3434_not_found_asdasd34.xsl", "");
    }
}
