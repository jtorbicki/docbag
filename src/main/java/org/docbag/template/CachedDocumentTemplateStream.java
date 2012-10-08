package org.docbag.template;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

import org.docbag.stream.ChannelUtil;
import org.docbag.stream.CloseableUtil;
import org.docbag.stream.MemoryInputStream;
import org.docbag.stream.MemoryOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link DocumentTemplateStream} that reads another DocumentTemplateStream into memory
 * and caches it for the later use.
 *
 * <p>This behavior is particularly useful for {@link org.docbag.template.repo.DocumentTemplateRepository}</p>
 *
 * @author Jakub Torbicki
 */
public class CachedDocumentTemplateStream implements DocumentTemplateStream {
    private MemoryInputStream memory;
    private final String name;
    private int count;

    public static final CachedDocumentTemplateStream newInstance(DocumentTemplateStream templateStream) throws IOException {
        return new CachedDocumentTemplateStream(templateStream.getName()).cacheTemplate(templateStream);
    }

    private CachedDocumentTemplateStream(String name) {
        this.name = name;
    }

    private CachedDocumentTemplateStream cacheTemplate(DocumentTemplateStream templateStream) throws IOException {
        final ReadableByteChannel inputChannel = Channels.newChannel(templateStream.getStream());
        MemoryOutputStream output = new MemoryOutputStream();
        final WritableByteChannel outputChannel = Channels.newChannel(output);
        try {
            ChannelUtil.copyChannel(inputChannel, outputChannel);
            memory = new MemoryInputStream(output);
            count = output.getCount();
        } finally {
            CloseableUtil.close(inputChannel);
            CloseableUtil.close(outputChannel);
        }
        return this;
    }

    /**
     * @see DocumentTemplateStream#getStream()
     */
    public InputStream getStream() {
        // Wrap memory up with new object, so the "position" attribute is reset, thus allowing stream to be reused across different threads
        return new MemoryInputStream(memory);
    }

    /**
     * @see DocumentTemplateStream#getName()
     */
    public String getName() {
        return name;
    }

    /**
     * Size of the stream in bytes
     */
    public int getCount() {
        return count;
    }
}
