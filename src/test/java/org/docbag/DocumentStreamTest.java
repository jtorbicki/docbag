package org.docbag;

import java.util.Date;

import junit.framework.Assert;
import org.docbag.stream.MemoryInputStream;
import org.junit.Before;
import org.junit.Test;

/**
 * DocumentStreamTest
 *
 * @author Jakub Torbicki
 */
public class DocumentStreamTest {
    private DocumentStream stream;

    @Before
    public void setUp() throws Exception {
        stream = new DocumentStream(new Date(), new MemoryInputStream(new byte[1]));
    }

    @Test
    public void testGetStream() throws Exception {
        Assert.assertNotNull("null", stream.getStream());
    }

    @Test
    public void testGetCreationDate() throws Exception {
        Assert.assertNotNull("null", stream.getCreationDate());
    }

    @Test
    public void testToString() throws Exception {
        Assert.assertNotNull("null", stream.toString());
    }
}
