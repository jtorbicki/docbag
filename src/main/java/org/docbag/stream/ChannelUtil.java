package org.docbag.stream;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Utility class for buffer copying.
 *
 * @author Jakub Torbicki
 */
public class ChannelUtil {
    private ChannelUtil() {
    }

    /**
     * Copies {@link ReadableByteChannel} into {@link WritableByteChannel}
     */
    public static void copyChannel(final ReadableByteChannel src, final WritableByteChannel dest) throws IOException {
        if (src == null || dest == null) {
            throw new NullPointerException("Channels: src=" + src + " dest=" + dest);
        }
        final ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
        while (src.read(buffer) != -1) {
            buffer.flip();
            dest.write(buffer);
            buffer.compact();
        }
        buffer.flip();
        while (buffer.hasRemaining()) {
            dest.write(buffer);
        }
    }
}
