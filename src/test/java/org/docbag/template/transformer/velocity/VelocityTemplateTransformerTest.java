package org.docbag.template.transformer.velocity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.docbag.Context;
import org.docbag.DefaultContext;
import org.docbag.template.DocumentTemplateStream;
import org.docbag.template.repo.DefaultDocumentTemplateRepository;
import org.docbag.template.repo.DocumentTemplateRepository;
import org.junit.Test;

/**
 * VelocityTemplateTransformerTest
 *
 * @author Jakub Torbicki
 */
public class VelocityTemplateTransformerTest {
    private static final VelocityTemplateTransformer transformer = new VelocityTemplateTransformer();
    private static final DocumentTemplateRepository<DocumentTemplateStream> repo = DefaultDocumentTemplateRepository.getInstance();
    private static final int THREADS_NUMBER = 200;
    private static final int TEMPLATES_NUMBER = 1000;
    private static final Context c = createContext();
    private static Exception exception;

    private static Context createContext() {
        Context<String, Object> context = new DefaultContext();
        context.put("name", "Friend");
        return context;
    }

    @Test
    public void testThreadSafety() throws Exception {
        System.out.println("Transforming " + TEMPLATES_NUMBER + " templates in " + THREADS_NUMBER + " threads.");
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

    private static class DocumentGenerator implements Runnable {
        public void run() {
            int n = TEMPLATES_NUMBER / THREADS_NUMBER;
            for (int i = 0; i < n; i++) {
                try {
                    transformer.transform(repo.findTemplate("templates/test-velocity.html"), c);
                } catch (Exception e) {
                    exception = e;
                    break;
                }
            }
        }
    }
}
