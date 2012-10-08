package org.docbag;

import junit.framework.Assert;
import org.apache.xmlgraphics.util.MimeConstants;
import org.docbag.template.repo.DefaultDocumentTemplateRepository;
import org.docbag.template.transformer.xslt.DefaultXSLTTemplateTransformer;
import org.junit.Test;

/**
 * DocBagTest
 *
 * @author Jakub Torbicki
 */
public class DocBagTest {
    @Test
    public void testNewDocumentCreator() throws Exception {
        Assert.assertNotNull(DocBag.newDocumentCreator());
        Assert.assertNotNull(DocBag.newDocumentCreator(true));
        Assert.assertNotNull(DocBag.newDocumentCreator(false));
        Assert.assertNotNull(DocBag.newDocumentCreator(new DefaultXSLTTemplateTransformer(), DefaultDocumentTemplateRepository.getInstance()));
        Assert.assertNotNull(DocBag.newDocumentCreator(MimeConstants.MIME_POSTSCRIPT, new DefaultXSLTTemplateTransformer(),
            DefaultDocumentTemplateRepository.getInstance()));
    }
}
