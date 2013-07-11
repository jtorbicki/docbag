package org.docbag.client;

import java.awt.*;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.xmlgraphics.util.MimeConstants;
import org.docbag.Context;
import org.docbag.DefaultContext;
import org.docbag.DocBag;
import org.docbag.DocumentCreator;
import org.docbag.DocumentStream;
import org.docbag.chart.jfree.*;
import org.docbag.table.Cell;
import org.docbag.table.Row;
import org.docbag.table.Table;
import org.docbag.template.DocumentTemplateStream;
import org.docbag.template.repo.ClasspathDocumentTemplateRepository;
import org.docbag.template.repo.DefaultDocumentTemplateRepository;
import org.docbag.template.repo.DocumentTemplateRepository;
import org.docbag.template.repo.FileDocumentTemplateRepository;
import org.docbag.template.transformer.xslt.DefaultXSLTTemplateTransformer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 * DocBagClient
 */
public class DocBagClient {

    private static final String FOP_CONFIG = "src/test/resources/config/fop.xml";

    public void createDocument() throws Exception {
        DocumentCreator<DocumentStream, DocumentTemplateStream> creator = DocBag.newDocumentCreator(
            MimeConstants.MIME_PDF, new DefaultXSLTTemplateTransformer(),
            new ClasspathDocumentTemplateRepository(), System.getProperty("user.dir") + "/" + FOP_CONFIG);
        DocumentStream document = creator.createDocument("templates/test-chart.html", getContext());
        saveToFile(document);
    }

    public static final void main(String[] args) throws Exception {
        new DocBagClient().createDocument();
    }

    private void saveToFile(DocumentStream document) throws Exception {
        FileOutputStream file = new FileOutputStream("C:\\out.pdf");
        int read = 0;
        byte[] bytes = new byte[1024];
        while ((read = document.getStream().read(bytes)) != -1) {
            file.write(bytes, 0, read);
        }
        file.flush();
        file.close();
    }

    private Context getContext() {
        Context documentContext = new DefaultContext();
        Map<Comparable<String>, Double> exploded = new HashMap<Comparable<String>, Double>();
        exploded.put("A", 0.3);
        float dash[] = {5.0f};
        Map<String, Style> styles = new HashMap<String, Style>();
        styles.put("A", new Style(new Color(255, 127, 0)));
        styles.put("B", new Style(new Color(114, 56, 0)));
        styles.put("C", new Style(new Color(255, 255, 255)));
        Map<String, Style> styles2 = new HashMap<String, Style>();
        styles2.put("A", new Style(new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f)));
//        documentContext.put("chart1", new PieChart.Builder(createPieDataSet()).exploded(exploded).title("Pie Chart").styles(styles).build());
        documentContext.put("chart1", new StackedBarChart.Builder(createStackedDataSet()).title("Stacked Chart").styles(styles).build());
        documentContext.put("chart2", new BarChart.Builder(getBarData()).title("Bar Chart").styles(styles).build());
        documentContext.put("chart3", new LineChart.Builder(getBarData()).title("Line Chart").styles(styles).build());
        documentContext.put("chart4", new AreaChart.Builder(getBarData()).title("Area Chart").styles(styles).build());
        documentContext.put("name", "Friend");
        documentContext.put("table1", createTable());
        documentContext.put("special", "ąęćłńóśżź");
        return documentContext;
    }

    private CategoryDataset createStackedDataSet() {
        DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();
        //Enrollment in Bachelors level
        categoryDataset.setValue(2003, "A", "2005");
        categoryDataset.setValue(1350, "A", "2006");
        categoryDataset.setValue(2408, "A", "2007");
        categoryDataset.setValue(2607, "A", "2008");
        //Enrollment in Masters level
        categoryDataset.setValue(985, "B", "2005");
        categoryDataset.setValue(1400, "B", "2006");
        categoryDataset.setValue(1634, "B", "2007");
        categoryDataset.setValue(978, "B", "2008");
        //Enrollment in PhD level
        categoryDataset.setValue(356, "C", "2005");
        categoryDataset.setValue(390, "C", "2006");
        categoryDataset.setValue(350, "C", "2007");
        categoryDataset.setValue(687, "C", "2008");
        return categoryDataset;
    }

    private DefaultCategoryDataset getBarData() {
        DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();
        //Enrollment in Bachelors level
        categoryDataset.setValue(2003, "A", "2005");
        categoryDataset.setValue(1350, "A", "2006");
        categoryDataset.setValue(2408, "A", "2007");
        categoryDataset.setValue(2607, "A", "2008");
        //Enrollment in Masters level
        categoryDataset.setValue(985, "B", "2005");
        categoryDataset.setValue(1400, "B", "2006");
        categoryDataset.setValue(1634, "B", "2007");
        categoryDataset.setValue(978, "B", "2008");
        //Enrollment in PhD level
        categoryDataset.setValue(356, "C", "2005");
        categoryDataset.setValue(390, "C", "2006");
        categoryDataset.setValue(350, "C", "2007");
        categoryDataset.setValue(687, "C", "2008");
        return categoryDataset;
    }

    private static PieDataset createPieDataSet() {
        DefaultPieDataset result = new DefaultPieDataset();
        result.setValue("A", 30);
        result.setValue("B", 30);
        result.setValue("C", 40);
        return result;
    }

    private Table createTable() {
        java.util.List<Row> headRows = new ArrayList<Row>();
        java.util.List<Cell> headCells = new ArrayList<Cell>();
        headCells.add(new Cell("head cell 1"));
        headCells.add(new Cell("head cell 2"));
        headCells.add(new Cell("head cell 3"));
        headRows.add(new Row(headCells));
        java.util.List<Row> bodyRows = new ArrayList<Row>();
        for (int i = 0; i < 100; i++) {
            java.util.List<Cell> bodyCells = new ArrayList<Cell>();
            bodyCells.add(new Cell("body cell 1" + i));
            bodyCells.add(new Cell("body cell 2" + i));
            bodyCells.add(new Cell("body cell 3" + i));
            bodyRows.add(new Row(bodyCells));
        }
        java.util.List<Row> footRows = new ArrayList<Row>();
        java.util.List<Cell> footCells = new ArrayList<Cell>();
        footCells.add(new Cell("Footer"));
        footRows.add(new Row(footCells));
        return new Table("table1", headRows, bodyRows, footRows, false);
    }

    private Table createTable2() {
        List<Row> bodyRows = new ArrayList<Row>();
        for (int i = 0; i < 100; i++) {
            java.util.List<Cell> bodyCells = new ArrayList<Cell>();
            bodyCells.add(new Cell("body cell 1" + i));
            bodyCells.add(new Cell("body cell 2" + i));
            bodyCells.add(new Cell("body cell 3" + i));
            bodyRows.add(new Row(bodyCells));
        }
        return new Table("table1", bodyRows);
    }
}
