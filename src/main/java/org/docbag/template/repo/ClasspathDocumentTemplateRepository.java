package org.docbag.template.repo;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

import org.docbag.stream.ChannelUtil;
import org.docbag.stream.CloseableUtil;
import org.docbag.stream.MemoryInputStream;
import org.docbag.stream.MemoryOutputStream;
import org.docbag.template.DocumentTemplateStream;
import org.docbag.template.MemoryTemplateStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClasspathDocumentTemplateRepository reads {@link org.docbag.template.DocumentTemplate} from the classpath.
 *
 * @author Jakub Torbicki
 */
public class ClasspathDocumentTemplateRepository implements DocumentTemplateRepository<DocumentTemplateStream> {
    private static final String REPOSITORY_NAME = "ClasspathDocumentTemplateRepository";
    private static final Logger log = LoggerFactory.getLogger(ClasspathDocumentTemplateRepository.class);

    public DocumentTemplateStream findTemplate(String templateName) {
        if (templateName == null) {
            throw new NullPointerException("Template name can't be null!");
        }
        MemoryOutputStream output = new MemoryOutputStream();
        InputStream input = ClasspathDocumentTemplateRepository.class.getResourceAsStream("/" + templateName);
        if (input != null) {
            final WritableByteChannel outputChannel = Channels.newChannel(output);
            final ReadableByteChannel inputChannel = Channels.newChannel(input);
            try {
                ChannelUtil.copyChannel(inputChannel, outputChannel);
                return new MemoryTemplateStream(new MemoryInputStream(output), templateName);
            } catch (IOException e) {
                log.error("Error reading classpath resource: " + templateName, e.getLocalizedMessage(), e);
            } finally {
                CloseableUtil.close(input);
            }
        }
        return null;
    }

    public String getRepositoryName() {
        return REPOSITORY_NAME;
    }
}
