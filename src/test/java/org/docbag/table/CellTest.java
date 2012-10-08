package org.docbag.table;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import org.junit.Test;

/**
 * CellTest
 *
 * @author Jakub Torbicki
 */
public class CellTest {

    private static final String DATA = "data";
    private static final String CELL_PADDING = "30";
    private static final int COL_SPAN = 2;
    private static final int ROW_SPAN = 3;
    private static final Map<String, String> STYLE = new HashMap<String, String>();

    @Test
    public void testCombine() throws Exception {
    }

    @Test
    public void testGetData() throws Exception {
        Cell cell = new Cell(DATA);
        Assert.assertEquals(DATA, cell.getData());
        Assert.assertEquals("", cell.getCellpadding());
        Assert.assertTrue(cell.getStyle().isEmpty());
        Assert.assertEquals(0, cell.getColspan());
        Assert.assertEquals(0, cell.getRowspan());
    }

    @Test
    public void testGetData2() throws Exception {
        Cell cell = new Cell(DATA, STYLE, COL_SPAN, ROW_SPAN, CELL_PADDING);
        Assert.assertEquals(DATA, cell.getData());
        Assert.assertEquals(CELL_PADDING, cell.getCellpadding());
        Assert.assertEquals(STYLE, cell.getStyle());
        Assert.assertEquals(COL_SPAN, cell.getColspan());
        Assert.assertEquals(ROW_SPAN, cell.getRowspan());
    }

    @Test
    public void testToString() throws Exception {
        Cell cell = new Cell(DATA);
        Assert.assertNotNull(cell.toString());
    }
}

