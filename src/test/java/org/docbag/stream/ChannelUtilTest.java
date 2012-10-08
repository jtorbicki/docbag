package org.docbag.stream;

import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Arrays;

import junit.framework.Assert;
import org.junit.Test;

/**
 * ChannelUtilTest
 *
 * @author Jakub Torbicki
 */
public class ChannelUtilTest {
    private static final int SIZE = 1538;
    private static final byte VALUE = (byte) 0xD7;

    @Test
    public void testCopyChannel() throws Exception {
        final MemoryOutputStream output = new MemoryOutputStream();
        final WritableByteChannel outputChannel = Channels.newChannel(output);
        final MemoryInputStream input = new MemoryInputStream(getBytes());
        final ReadableByteChannel inputChannel = Channels.newChannel(input);
        ChannelUtil.copyChannel(inputChannel, outputChannel);
        Assert.assertEquals(SIZE, output.getCount());
        byte[] bytes = output.getAsByteArray();
        for (byte aByte : bytes) {
            Assert.assertEquals(VALUE, aByte);
        }
    }

    @Test(expected = NullPointerException.class)
    public void testNullSrc() throws Exception {
        final MemoryOutputStream output = new MemoryOutputStream();
        final WritableByteChannel outputChannel = Channels.newChannel(output);
        ChannelUtil.copyChannel(null, outputChannel);
    }

    @Test(expected = NullPointerException.class)
    public void testNullDest() throws Exception {
        final MemoryInputStream input = new MemoryInputStream(getBytes());
        final ReadableByteChannel inputChannel = Channels.newChannel(input);
        ChannelUtil.copyChannel(inputChannel, null);
    }

    private byte[] getBytes() {
        byte[] bytes = new byte[SIZE];
        Arrays.fill(bytes, VALUE);
        return bytes;
    }
}
