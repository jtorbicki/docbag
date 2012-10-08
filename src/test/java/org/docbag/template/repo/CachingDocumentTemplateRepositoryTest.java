package org.docbag.template.repo;

import junit.framework.Assert;
import org.docbag.stream.MemoryInputStream;
import org.docbag.template.CachedDocumentTemplateStream;
import org.docbag.template.DocumentTemplateStream;
import org.docbag.template.MemoryTemplateStream;
import org.junit.Test;

/**
 * CachingDocumentTemplateRepositoryTest
 *
 * @author Jakub Torbicki
 */
public class CachingDocumentTemplateRepositoryTest {
    private static final String REPO_NAME = "RepoName";
    private static final String PRESENT_TEMPLATE_NAME = "TemplatePresent";
    private static final String NOT_PRESENT_TEMPLATE_NAME = "TemplateNotPresent";

    @Test
    public void testRegisterRepository() throws Exception {
        CachingDocumentTemplateRepository repository = new CachingDocumentTemplateRepository();
        Assert.assertTrue(repository.registerRepository(getRepository()));
        Assert.assertFalse(repository.registerRepository(getRepository()));
    }

    @Test(expected = NullPointerException.class)
    public void testFindTemplateNull() throws Exception {
        CachingDocumentTemplateRepository repository = new CachingDocumentTemplateRepository();
        repository.findTemplate(null);
    }

    @Test
    public void testFindTemplate() throws Exception {
        CachingDocumentTemplateRepository repository = new CachingDocumentTemplateRepository();
        repository.registerRepository(getRepository());
        Assert.assertEquals(PRESENT_TEMPLATE_NAME, repository.findTemplate(PRESENT_TEMPLATE_NAME).getName());
        Assert.assertTrue(repository.findTemplate(PRESENT_TEMPLATE_NAME) instanceof CachedDocumentTemplateStream);
        Assert.assertNull(repository.findTemplate(NOT_PRESENT_TEMPLATE_NAME));
    }

    @Test
    public void testGetRepositoryName() throws Exception {
        CachingDocumentTemplateRepository repository = new CachingDocumentTemplateRepository();
        Assert.assertNotNull(repository.getRepositoryName());
    }

    private DocumentTemplateRepository<DocumentTemplateStream> getRepository() {
        return new DocumentTemplateRepository<DocumentTemplateStream>() {
            public DocumentTemplateStream findTemplate(String name) {
                if (PRESENT_TEMPLATE_NAME.equals(name)) {
                    return new MemoryTemplateStream(new MemoryInputStream(new byte[0]), name);
                }
                return null;
            }

            public String getRepositoryName() {
                return REPO_NAME;
            }
        };
    }
}
