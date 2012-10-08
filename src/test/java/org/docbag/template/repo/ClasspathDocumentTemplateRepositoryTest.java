package org.docbag.template.repo;

import junit.framework.Assert;
import org.docbag.template.DocumentTemplateStream;
import org.junit.Test;

/**
 * ClassPathDocumentTemplateRepositoryTest
 *
 * @author Jakub Torbicki
 */
public class ClasspathDocumentTemplateRepositoryTest {
    private static final String TEMPLATE_NAME = ClasspathDocumentTemplateRepositoryTest.class.getName().replace('.', '/').concat(".class");
    private static final String TEMPLATE_NAME_NOT_EXIST = "1/2/3/NOt_eXisT";

    @Test(expected = NullPointerException.class)
    public void testFindTemplateNull() throws Exception {
        ClasspathDocumentTemplateRepository r = new ClasspathDocumentTemplateRepository();
        r.findTemplate(null);
    }

    @Test
    public void testFindTemplate() throws Exception {
        ClasspathDocumentTemplateRepository r = new ClasspathDocumentTemplateRepository();
        DocumentTemplateStream template = r.findTemplate(TEMPLATE_NAME);
        Assert.assertNotNull(template);
        Assert.assertEquals(TEMPLATE_NAME, template.getName());
        Assert.assertNull(r.findTemplate(TEMPLATE_NAME_NOT_EXIST));
    }

    @Test
    public void testGetRepositoryName() throws Exception {
        ClasspathDocumentTemplateRepository repository = new ClasspathDocumentTemplateRepository();
        Assert.assertNotNull(repository.getRepositoryName());
    }
}
