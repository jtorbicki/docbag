package org.docbag.template.repo;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import org.docbag.DocumentCreatorException;
import org.docbag.template.CachedDocumentTemplateStream;
import org.docbag.template.DocumentTemplateStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Iterates through all the registered {@link DocumentTemplateRepository} and uses them to find a
 * concrete {@link DocumentTemplateStream}.
 * <p>Once a concrete {@link DocumentTemplateRepository} returns a valid {@link DocumentTemplateStream} the iteration process
 * ends.</p>
 * <p>If the template is found it is stored and cached for later usage. If the template is not found then null is returned.</p>
 *
 * <p>This class is tread safe.</p>
 *
 * @author Jakub Torbicki
 */
public class CachingDocumentTemplateRepository implements DocumentTemplateRepository<DocumentTemplateStream> {
    private static final String REPOSITORY_NAME = "CachingDocumentTemplateRepository";
    private static final Logger log = LoggerFactory.getLogger(CachingDocumentTemplateRepository.class);
    private final ConcurrentHashMap<String, CachedDocumentTemplateStream> templates
        = new ConcurrentHashMap<String, CachedDocumentTemplateStream>();
    private final ConcurrentHashMap<String, DocumentTemplateRepository<DocumentTemplateStream>> finders
        = new ConcurrentHashMap<String, DocumentTemplateRepository<DocumentTemplateStream>>();

    /**
     * Register new {@link DocumentTemplateRepository}
     *
     * @param finder DocumentTemplateRepository
     * @return true if the finder was registered
     *          false if there was already an instance of DocumentTemplateRepository
     *          registered with the same {@link Class}
     */
    public boolean registerRepository(DocumentTemplateRepository<DocumentTemplateStream> finder) {
        String repositoryName = finder.getRepositoryName();
        DocumentTemplateRepository<DocumentTemplateStream> repo = finders.get(repositoryName);
        if (repo == null) {
            repo = finders.putIfAbsent(repositoryName, finder);
            if (repo == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Find and cache {@link DocumentTemplateStream}
     *
     * @param name template's name
     * @return {@link DocumentTemplateStream} if found
     *          null if not found
     */
    public DocumentTemplateStream findTemplate(String name) {
        if (name == null) {
            throw new NullPointerException("Template name can't be null!");
        }
        CachedDocumentTemplateStream documentTemplate = templates.get(name);
        if (documentTemplate == null) {
            CachedDocumentTemplateStream document = createDocumentTemplate(name);
            if (document != null) {
                documentTemplate = templates.putIfAbsent(name, document);
                if (documentTemplate == null) {
                    documentTemplate = document;
                }
            }
        }
        return documentTemplate;
    }

    public String getRepositoryName() {
        return REPOSITORY_NAME;
    }

    private CachedDocumentTemplateStream createDocumentTemplate(String name) {
        try {
            Collection<DocumentTemplateRepository<DocumentTemplateStream>> finders = this.finders.values();
            for (DocumentTemplateRepository<DocumentTemplateStream> finder : finders) {
                DocumentTemplateStream docTemplateStream = finder.findTemplate(name);
                if (docTemplateStream != null) {
                    return CachedDocumentTemplateStream.newInstance(docTemplateStream);
                }
            }
        } catch (IOException e) {
            log.error("Couldn't create DocumentTemplate", e.getLocalizedMessage(), e);
            throw new DocumentCreatorException("Couldn't create DocumentTemplate: ", e);
        }
        return null;
    }
}
