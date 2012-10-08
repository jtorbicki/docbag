package org.docbag.template.repo;

/**
 * DefaultDocumentTemplateRepository is a {@link CachingDocumentTemplateRepository} with a
 * {@link ClasspathDocumentTemplateRepository} registered as template finder.
 *
 * @author Jakub Torbicki
 */
public class DefaultDocumentTemplateRepository extends CachingDocumentTemplateRepository {
    private DefaultDocumentTemplateRepository() {
        registerRepository(new ClasspathDocumentTemplateRepository());
    }

    private static class DefaultTemplateRepositoryHolder {
        public static final DefaultDocumentTemplateRepository repository = new DefaultDocumentTemplateRepository();
    }

    public static DefaultDocumentTemplateRepository getInstance() {
        return DefaultTemplateRepositoryHolder.repository;
    }
}
