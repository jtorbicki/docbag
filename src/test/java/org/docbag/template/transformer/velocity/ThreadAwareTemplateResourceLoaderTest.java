package org.docbag.template.transformer.velocity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import junit.framework.Assert;
import org.docbag.stream.MemoryInputStream;
import org.docbag.template.MemoryTemplateStream;
import org.junit.Test;

/**
 * ThreadAwareTemplateResourceLoaderTest
 *
 * @author Jakub Torbicki
 */
public class ThreadAwareTemplateResourceLoaderTest {
    private static final int THREADS_NUMBER = 200;
    private static final int TEMPLATES_NUMBER = 1000;
    private static Exception exception;
    private static final ThreadAwareTemplateResourceLoader loader = new ThreadAwareTemplateResourceLoader();

    @Test
    public void testThreadSafety() throws Exception {
        int poolSize = THREADS_NUMBER;
        ExecutorService service = Executors.newFixedThreadPool(poolSize);
        List<Future<Runnable>> futures = new ArrayList<Future<Runnable>>();
        for (int n = 0; n < THREADS_NUMBER; n++) {
            Future f = service.submit(new DocumentGenerator());
            futures.add(f);
        }
        for (Future<Runnable> f : futures) {
            f.get();
        }
        service.shutdownNow();
        if (exception != null) {
            throw exception;
        }
    }

    @Test
    public void testIsSourceModified() throws Exception {
        ThreadAwareTemplateResourceLoader loader = new ThreadAwareTemplateResourceLoader();
        Assert.assertFalse(loader.isSourceModified(null));
    }

    @Test
    public void testGetLastModified() throws Exception {
        ThreadAwareTemplateResourceLoader loader = new ThreadAwareTemplateResourceLoader();
        Assert.assertEquals(0, loader.getLastModified(null));
    }

    private static class DocumentGenerator implements Runnable {
        public void run() {
            int n = TEMPLATES_NUMBER / THREADS_NUMBER;
            for (int i = 0; i < n; i++) {
                try {
                    MemoryInputStream stream = new MemoryInputStream(new byte[0]);
                    loader.putResource(new MemoryTemplateStream(stream, Thread.currentThread().getName()));
                    Assert.assertTrue(loader.getResourceStream(Thread.currentThread().getName()) == stream);
                    Assert.assertNull(loader.getResourceStream(Thread.currentThread().getName() + "_FALSE_1"));
                    loader.clearResource();
                    Assert.assertNull(loader.getResourceStream(Thread.currentThread().getName()));
                } catch (Exception e) {
                    exception = e;
                    break;
                }
            }
        }
    }
}
