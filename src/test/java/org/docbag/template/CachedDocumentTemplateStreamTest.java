package org.docbag.template;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import junit.framework.Assert;
import org.docbag.stream.MemoryInputStream;
import org.junit.Test;

/**
 * CachedDocumentTemplateStreamTest
 *
 * @author Jakub Torbicki
 */
public class CachedDocumentTemplateStreamTest {
    private static Exception exception = null;
    private static boolean failed = false;
    private static final String name = "name";

    private class TemplateReader implements Runnable {
        private CachedDocumentTemplateStream mem;
        private int counter = 0;

        private TemplateReader(CachedDocumentTemplateStream mem) {
            this.mem = mem;
        }

        public void run() {
            try {
                InputStream stream = mem.getStream();
                while (stream.read() != -1) {
                    counter++;
                }
            } catch (IOException e) {
                exception = e;
            }
            if (mem.getCount() != counter) {
                failed = true;
            }
        }
    }

    @Test
    public void testNewInstance() throws Exception {
        Assert.assertNotNull(CachedDocumentTemplateStream.newInstance(getTemplate()));
    }

    /**
     * This test checks if all the threads read complete Stream
     */
    @Test
    public void testGetStream() throws Exception {
        CachedDocumentTemplateStream c = CachedDocumentTemplateStream.newInstance(getTemplate());
        int poolSize = 10;
        ExecutorService service = Executors.newFixedThreadPool(poolSize);
        List<Future<Runnable>> futures = new ArrayList<Future<Runnable>>();
        for (int n = 0; n < 1000; n++) {
            Future f = service.submit(new TemplateReader(c));
            futures.add(f);
        }
        for (Future<Runnable> f : futures) {
            f.get();
        }
        service.shutdownNow();
        Assert.assertFalse(failed);
        if (exception != null) {
            throw exception;
        }
    }

    @Test
    public void testGetName() throws Exception {
        Assert.assertEquals(name, CachedDocumentTemplateStream.newInstance(getTemplate()).getName());
    }

    private MemoryTemplateStream getTemplate() {
        return new MemoryTemplateStream(new MemoryInputStream(new byte[1024]), name);
    }
}
