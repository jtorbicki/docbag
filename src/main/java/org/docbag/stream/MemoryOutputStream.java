package org.docbag.stream;

import java.io.ByteArrayOutputStream;

/**
 * {@link java.io.OutputStream} implementation that keeps the data in memory.
 *
 * Consider changing this implementation into something not synchronized, non blocking.
 *
 * @author Jakub Torbicki
 */
public class MemoryOutputStream extends ByteArrayOutputStream {
    byte[] getAsByteArray() {
        return buf;
    }

    public int getCount() {
        return count;
    }

    public String toString() {
        return new String(buf);
    }
}
