package org.docbag.template.repo;

import junit.framework.Assert;
import org.docbag.template.DocumentTemplateStream;
import org.junit.Test;

/**
 * FileDocumentTemplateRepositoryTest
 *
 * @author Jakub Torbicki
 */
public class FileDocumentTemplateRepositoryTest {
    private static final String TEMPLATE_NAME = "src/test/resources/templates/test-template.html";
    private static final String WRONG_TEMPLATE_NAME = "1/2/3/no_such_file";

    @Test(expected = NullPointerException.class)
    public void testFindTemplateNull() throws Exception {
        FileDocumentTemplateRepository repository = new FileDocumentTemplateRepository("NON_EXISTING_BASE_DIR/1/2");
        repository.findTemplate(null);
    }

    @Test
    public void testFindTemplate() throws Exception {
        FileDocumentTemplateRepository repository = new FileDocumentTemplateRepository();
        Assert.assertNull(repository.findTemplate(WRONG_TEMPLATE_NAME));
        DocumentTemplateStream template = repository.findTemplate(TEMPLATE_NAME);
        Assert.assertNotNull(template);
        Assert.assertEquals(TEMPLATE_NAME, template.getName());
    }

    @Test
    public void testGetRepositoryName() throws Exception {
        FileDocumentTemplateRepository repository = new FileDocumentTemplateRepository();
        Assert.assertNotNull(repository.getRepositoryName());
    }
}
