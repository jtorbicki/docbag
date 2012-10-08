package org.docbag.template.repo;

import junit.framework.Assert;
import org.junit.Test;

/**
 * URIDocumentTemplateRepositoryTest
 *
 * @author Jakub Torbicki
 */
public class URIDocumentTemplateRepositoryTest {
    private static final String TEMPLATE_NAME = "file:///" + System.getProperty("user.dir").replaceAll("\\\\", "/")
        + "/src/test/resources/templates/test-template.html";

    @Test
    public void testFindTemplate() throws Exception {
        URIDocumentTemplateRepository repository = new URIDocumentTemplateRepository();
        Assert.assertNotNull(repository.findTemplate(TEMPLATE_NAME));
    }

    @Test
    public void testGetRepositoryName() throws Exception {
        URIDocumentTemplateRepository repository = new URIDocumentTemplateRepository();
        Assert.assertNotNull(repository.getRepositoryName());
    }
}
