package org.docbag.template;

import junit.framework.Assert;
import org.docbag.stream.MemoryInputStream;
import org.junit.Test;

/**
 * MemoryTemplateStreamTest
 *
 * @author Jakub Torbicki
 */
public class MemoryTemplateStreamTest {
    private static final MemoryInputStream memory = new MemoryInputStream(new byte[0]);

    @Test
    public void testGetStream() throws Exception {
        MemoryTemplateStream t = new MemoryTemplateStream(memory, "name");
        Assert.assertTrue(t.getStream() == memory);
    }

    @Test
    public void testGetName() throws Exception {
        MemoryTemplateStream t = new MemoryTemplateStream(memory, "name");
        Assert.assertNotNull(t.getName());
    }

    @Test
    public void testToString() throws Exception {
        MemoryTemplateStream t = new MemoryTemplateStream(memory, "name");
        Assert.assertNotNull(t.toString());
    }
}
