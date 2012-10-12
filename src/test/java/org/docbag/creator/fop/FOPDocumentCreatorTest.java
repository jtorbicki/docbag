package org.docbag.creator.fop;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.xmlgraphics.util.MimeConstants;
import org.docbag.Context;
import org.docbag.DefaultContext;
import org.docbag.chart.jfree.PieChart;
import org.docbag.template.DocumentTemplateStream;
import org.docbag.template.repo.DefaultDocumentTemplateRepository;
import org.docbag.template.transformer.xslt.DefaultXSLTTemplateTransformer;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FOPDocumentCreatorTest
 *
 * @author Jakub Torbicki
 */
public class FOPDocumentCreatorTest {
    private static Exception exception = null;
    private static final FOPDocumentCreator creator = new FOPDocumentCreator(MimeConstants.MIME_PDF, new DefaultXSLTTemplateTransformer(),
        DefaultDocumentTemplateRepository.getInstance());
    private static final String TEMPLATE = "templates/test-template.html";
    private static final int THREADS_NUMBER = 20;
    private static final int DOCUMENTS_NUMBER = 100;
    private static final Logger Log = LoggerFactory.getLogger(FOPDocumentCreatorTest.class);
    private static final Context<String, Object> c = createContext();
    private static final String CONFIG_LOCATION = System.getProperty("user.dir") + "/src/test/resources/config/fop.xml";

    @Test(expected = NullPointerException.class)
    public void testCreateDocumentEmptyName() throws Exception {
        new FOPDocumentCreator(null, null, null).createDocument("template");
    }

    @Test(expected = NullPointerException.class)
    public void testCreateDocumentEmptyName2() throws Exception {
        new FOPDocumentCreator(null, null, null).createDocument("template", new DefaultContext());
    }

    @Test(expected = NullPointerException.class)
    public void testCreateDocumentEmptyName3() throws Exception {
        new FOPDocumentCreator(null, null, null).createDocument(getNullTemplate());
    }

    @Test(expected = NullPointerException.class)
    public void testCreateDocumentEmptyName4() throws Exception {
        new FOPDocumentCreator(null, null, null).createDocument(getNullTemplate(), new DefaultContext());
    }

    @Test(expected = NullPointerException.class)
    public void testCreateDocumentEmptyName5() throws Exception {
        new FOPDocumentCreator(null, null, null, CONFIG_LOCATION).createDocument(getNullTemplate(), new DefaultContext());
    }

    @Test
    public void testThreadSafety() throws Exception {
        System.out.println("Creating " + DOCUMENTS_NUMBER + " documents in " + THREADS_NUMBER + " threads.");
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

    private static Context<String, Object> createContext() {
        Context<String, Object> context = new DefaultContext();
        context.put("name", "name");
        context.put("chart1", new PieChart.Builder(createPieDataSet()).title("Pie Chart").build());
        return context;
    }

    private static PieDataset createPieDataSet() {
        DefaultPieDataset result = new DefaultPieDataset();
        result.setValue("A", 33);
        result.setValue("B", 33);
        result.setValue("C", 34);
        return result;
    }

    public DocumentTemplateStream getNullTemplate() {
        return null;
    }

    private static class DocumentGenerator implements Runnable {
        public void run() {
            int n = DOCUMENTS_NUMBER / THREADS_NUMBER;
            for (int i = 0; i < n; i++) {
                try {
                    creator.createDocument(TEMPLATE, c);
                    Log.debug(".");
                } catch (Exception e) {
                    exception = e;
                    break;
                }
            }
        }
    }
}
