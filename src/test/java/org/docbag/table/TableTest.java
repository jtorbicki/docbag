package org.docbag.table;

import java.util.Collections;

import junit.framework.Assert;

import org.junit.Test;

public class TableTest {
    
    private static final String NAME = "name";
    
    @Test
    public void testCreate() {
        Table table = new Table(NAME, Collections.<Row>emptyList());
        Assert.assertEquals(NAME, table.getName());
        Assert.assertEquals(Collections.emptyList(), table.getTbody());
        Assert.assertEquals(Collections.emptyList(), table.getThead());
        Assert.assertEquals(Collections.emptyList(), table.getTfoot());
    }
}
