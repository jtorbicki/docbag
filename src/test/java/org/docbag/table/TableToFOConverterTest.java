package org.docbag.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class TableToFOConverterTest {
    @Test(expected = NullPointerException.class)
    public void testConvertNull() {
        TableToFOConverter converter = new TableToFOConverter();
        converter.convert(null);
    }
    
    @Test
    public void testConvert() {
        TableToFOConverter converter = new TableToFOConverter();
        Assert.assertNotNull(converter.convert(createTable()));
    }

    
    private Table createTable() {
        java.util.List<Row> headRows = new ArrayList<Row>();
        java.util.List<Cell> headCells = new ArrayList<Cell>();
        headCells.add(new Cell("head cell 1"));
        headCells.add(new Cell("head cell 2", new HashMap<String, String>(), 0, 0, ""));
        headCells.add(new Cell("head cell 3"));
        headRows.add(new Row(headCells));
        java.util.List<Row> bodyRows = new ArrayList<Row>();
        Map<String, String> style = new HashMap<String, String>();
        style.put("border", "1px");
        style.put("text-align", "center");
        for (int i = 0; i < 100; i++) {
            java.util.List<Cell> bodyCells = new ArrayList<Cell>();
            bodyCells.add(new Cell("body cell 1" + i, new HashMap<String, String>(), 0, 0, ""));
            bodyCells.add(new Cell("body cell 2" + i));
            bodyCells.add(new Cell("body cell 3" + i, style, 0, 0, ""));
            bodyRows.add(new Row(bodyCells));
        }
        java.util.List<Row> footRows = new ArrayList<Row>();
        java.util.List<Cell> footCells = new ArrayList<Cell>();
        footCells.add(new Cell("Footer"));
        footRows.add(new Row(footCells));
        return new Table("table1", headRows, bodyRows, footRows, false);
    }
}
