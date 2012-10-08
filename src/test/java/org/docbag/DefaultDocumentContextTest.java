package org.docbag;

import junit.framework.Assert;
import org.junit.Test;

/**
 * DefaultDocumentContextTest
 *
 * @author Jakub Torbicki
 */
public class DefaultDocumentContextTest {
    private static final String object = "test";
    private static final String object2 = "test2";
    private static final String object3 = "test3";

    @Test
    public void testPut() throws Exception {
        Context<String, Object> context = new DefaultContext();
        context.put(object, object);
        context.put(object2, object2);
        context.put(object3, object3);
        context.put(object, object2);
        context.put(object2, object);
        Assert.assertEquals(3, context.size());
    }

    @Test
    public void testGet() throws Exception {
        Context<String, Object> context = new DefaultContext();
        context.put(object, object);
        Assert.assertSame(context.get(object), object);
    }

    @Test
    public void testToString() throws Exception {
        Assert.assertNotNull(new DefaultContext().toString());
    }
}
