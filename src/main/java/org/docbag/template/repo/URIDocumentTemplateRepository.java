package org.docbag.template.repo;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

import org.docbag.stream.ChannelUtil;
import org.docbag.stream.MemoryInputStream;
import org.docbag.stream.MemoryOutputStream;
import org.docbag.template.DocumentTemplateStream;
import org.docbag.template.MemoryTemplateStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * URIDocumentTemplateRepository reads {@link org.docbag.template.DocumentTemplate} from the specified URI.
 *
 * @author Jakub Torbicki
 */
public class URIDocumentTemplateRepository implements DocumentTemplateRepository<DocumentTemplateStream> {
    private static final String REPOSITORY_NAME = "URIDocumentTemplateRepository";
    private static final Logger log = LoggerFactory.getLogger(URIDocumentTemplateRepository.class);

    public DocumentTemplateStream findTemplate(String name) {
        if (name == null) {
            throw new NullPointerException("Template name can't be null!");
        }
        try {
            URI uri = new URI(name);
            InputStream input = uri.toURL().openStream();
            final MemoryOutputStream output = new MemoryOutputStream();
            final WritableByteChannel outputChannel = Channels.newChannel(output);
            final ReadableByteChannel inputChannel = Channels.newChannel(input);
            ChannelUtil.copyChannel(inputChannel, outputChannel);
            return new MemoryTemplateStream(new MemoryInputStream(output), name);
        } catch (URISyntaxException e) {
            log.error("Error creating URI: " + name, e.getLocalizedMessage(), e);
        } catch (MalformedURLException e) {
            log.error("Error creating URL: " + name, e.getLocalizedMessage(), e);
        } catch (IOException e) {
            log.error("Error reading from: " + name, e.getLocalizedMessage(), e);
        }
        return null;
    }

    public String getRepositoryName() {
        return REPOSITORY_NAME;
    }
}
