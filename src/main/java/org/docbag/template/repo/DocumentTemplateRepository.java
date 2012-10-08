package org.docbag.template.repo;

import org.docbag.template.DocumentTemplate;

/**
 * Implementations of this interface know how to find a {@link DocumentTemplate} by its name.
 *
 * @see DocumentTemplate
 * @author Jakub Torbicki
 */
public interface DocumentTemplateRepository<T extends DocumentTemplate> {
    /**
     * Returns {@link DocumentTemplate} associated with the template's name or
     * null if the template was not found.
     */
    public T findTemplate(String name);

    /**
     * Returns repository name.
     */
    public String getRepositoryName();
}
