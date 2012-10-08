package org.docbag.template.transformer.xslt;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import org.docbag.Context;
import org.docbag.DefaultContext;
import org.docbag.chart.jfree.PieChart;
import org.docbag.template.DocumentTemplateStream;
import org.docbag.template.repo.ClasspathDocumentTemplateRepository;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.junit.Test;

/**
 * XSLTTemplateTransformerTest
 *
 * @author Jakub Torbicki
 */
public class XSLTTemplateTransformerTest {
    private static final ClasspathDocumentTemplateRepository repository = new ClasspathDocumentTemplateRepository();
    private static final XSLTTemplateTransformer transformer = new XSLTTemplateTransformer(repository.findTemplate("xslt/xhtml2fo.xsl"));
    private static final String[] templates = {"templates/test-chart.html", "templates/test-table.html", "templates/test-template.html"};

    @Test
    public void testTransformContext() throws Exception {
        Context context = getContext();
        for (String template : templates) {
            DocumentTemplateStream templateStream = transformer.transform(repository.findTemplate(template), context);
            Assert.assertNotNull(templateStream);
            Assert.assertNotNull(templateStream.getName());
        }
    }

    @Test(expected = NullPointerException.class)
    public void testNull() throws Exception {
        transformer.transform(null);
    }

    @Test
    public void testToString() throws Exception {
        Assert.assertNotNull(transformer.toString());
    }

    private Context getContext() {
        Context documentContext = new DefaultContext();
        Map<Comparable<String>, Double> exploded = new HashMap<Comparable<String>, Double>();
        exploded.put("Mac", 0.3);
        documentContext.put("chart1", new PieChart.Builder(createPieDataSet()).exploded(exploded).title("Pie Chart").build());
        documentContext.put("name", "Friend");
        return documentContext;
    }

    private static PieDataset createPieDataSet() {
        DefaultPieDataset result = new DefaultPieDataset();
        result.setValue("A", 30);
        result.setValue("B", 30);
        result.setValue("C", 40);
        return result;
    }
}
