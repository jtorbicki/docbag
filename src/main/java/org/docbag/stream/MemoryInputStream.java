package org.docbag.stream;

import java.io.ByteArrayInputStream;

/**
 * {@link java.io.InputStream} implementation that keeps the data in memory.
 *
 * Consider changing this implementation into something not synchronized, non blocking.
 *
 * @author Jakub Torbicki
 */
public class MemoryInputStream extends ByteArrayInputStream {
    public MemoryInputStream(MemoryOutputStream memory) {
        super(memory.getAsByteArray());
        this.count = memory.getCount();
    }

    public MemoryInputStream(MemoryInputStream memory) {
        super(memory.buf);
        this.count = memory.count;
    }

    public MemoryInputStream(byte[] bytes) {
        super(bytes);
    }
}
