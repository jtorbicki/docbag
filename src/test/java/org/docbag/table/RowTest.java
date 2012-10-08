package org.docbag.table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

public class RowTest {
    
    private static final List<Cell> cells = new ArrayList<Cell>();
    private static final Map<String, String> style = new HashMap<String, String>();

    private static final List<Cell> cellsSecond = new ArrayList<Cell>();
    private static final Map<String, String> styleSecond = new HashMap<String, String>();

    private static final List<Cell> cellsCombined = new ArrayList<Cell>();
    private static final Map<String, String> styleCombined = new HashMap<String, String>();

    
    static {
        cells.add(new Cell("data1"));
        cells.add(new Cell("data2"));
        style.put("border", "1px");
        style.put("padding", "2px");
        
        cellsSecond.add(new Cell("data3"));
        cellsSecond.add(new Cell("data4"));
        cellsSecond.add(new Cell("data5"));
        styleSecond.put("border", "10px");
        styleSecond.put("text-align", "center");

        cellsCombined.add(new Cell("data3"));
        cellsCombined.add(new Cell("data4"));
        styleCombined.put("border", "10px");
        styleCombined.put("padding", "2px");    
        styleCombined.put("text-align", "center");
    }
    
    @Test
    public void testCreateRow() {
        Row row = new Row(cells);
        Assert.assertEquals(cells, row.getCells());
        Assert.assertEquals(Collections.emptyMap(), row.getStyle());
    }

    @Test
    public void testCreateRow2() {
        Row row = new Row(cells, style);
        Assert.assertEquals(cells, row.getCells());
        Assert.assertEquals(style, row.getStyle());
    }

    @Test
    public void testCombine() {
        Row row = new Row(cells, style);
        Row rowSecond = new Row(cellsSecond, styleSecond);
        Row out = row.combine(rowSecond);
        Assert.assertEquals(cellsCombined, out.getCells());
        Assert.assertEquals(styleCombined, out.getStyle());
    }

    @Test
    public void testCombine2() {
        Row row = new Row(cells);
        Row rowSecond = new Row(styleSecond);
        Row out = row.combine(rowSecond);
        Assert.assertEquals(cells, out.getCells());
        Assert.assertEquals(styleSecond, out.getStyle());
    }
}
