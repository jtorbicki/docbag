package org.docbag.template.transformer.velocity;

import java.io.InputStream;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;
import org.docbag.template.DocumentTemplateStream;

/**
 * ThreadAwareTemplateResourceLoader
 *
 * @author Jakub Torbicki
 */
public class ThreadAwareTemplateResourceLoader extends ResourceLoader {
    private final ThreadLocal<DocumentTemplateStream> threadLocal = new ThreadLocal<DocumentTemplateStream>();

    public ThreadAwareTemplateResourceLoader() {
    }

    public void init(ExtendedProperties configuration) {
    }

    public void putResource(DocumentTemplateStream documentTemplate) {
        if (documentTemplate == null) {
            throw new NullPointerException("DocumentTemplateStream can't be null!");
        }
        threadLocal.set(documentTemplate);
    }

    public void clearResource() {
        threadLocal.remove();
    }

    public InputStream getResourceStream(String source) throws ResourceNotFoundException {
        DocumentTemplateStream templateStream = threadLocal.get();
        if (templateStream != null && templateStream.getName().equals(source)) {
            return templateStream.getStream();
        }
        return null;
    }

    public boolean isSourceModified(Resource resource) {
        return false;
    }

    public long getLastModified(Resource resource) {
        return 0;
    }
}
