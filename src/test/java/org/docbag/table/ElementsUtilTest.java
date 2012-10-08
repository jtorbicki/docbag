package org.docbag.table;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import org.junit.Test;

/**
 * ElementsUtilTest
 *
 * @author Jakub Torbicki
 */
public class ElementsUtilTest {

    private static final List<Combinable> FIRST_LIST = new ArrayList<Combinable>();
    private static final List<Combinable> SECOND_LIST_SMALL = new ArrayList<Combinable>();
    private static final List<Combinable> SECOND_LIST_BIG = new ArrayList<Combinable>();

    private static final List<Combinable> SECOND_LIST_SMALL_RESULT = new ArrayList<Combinable>();
    private static final List<Combinable> SECOND_LIST_SMALL_RESULT2 = new ArrayList<Combinable>();

    private static final List<Combinable> SECOND_LIST_BIG_RESULT = new ArrayList<Combinable>();

    static {
        FIRST_LIST.add(new Cell("data"));
        FIRST_LIST.add(new Cell("data2"));
        FIRST_LIST.add(new Cell("data3"));

        SECOND_LIST_BIG.add(new Cell("big"));
        SECOND_LIST_BIG.add(new Cell("big2"));
        SECOND_LIST_BIG.add(new Cell("big3"));
        SECOND_LIST_BIG.add(new Cell("big4"));

        SECOND_LIST_SMALL.add(new Cell("small1"));
        SECOND_LIST_SMALL.add(new Cell("small2"));

        SECOND_LIST_SMALL_RESULT.add(new Cell("small1"));
        SECOND_LIST_SMALL_RESULT.add(new Cell("small2"));
        SECOND_LIST_SMALL_RESULT.add(new Cell("small1"));

        SECOND_LIST_SMALL_RESULT2.add(new Cell("small1"));
        SECOND_LIST_SMALL_RESULT2.add(new Cell("small2"));
        SECOND_LIST_SMALL_RESULT2.add(new Cell("data3"));

        SECOND_LIST_BIG_RESULT.add(new Cell("big"));
        SECOND_LIST_BIG_RESULT.add(new Cell("big2"));
        SECOND_LIST_BIG_RESULT.add(new Cell("big3"));
    }

    @Test
    public void testCombine() throws Exception {
        Assert.assertEquals(FIRST_LIST, ElementsUtil.combine(FIRST_LIST, null, true));
        Assert.assertEquals(FIRST_LIST, ElementsUtil.combine(FIRST_LIST, null, false));

        Assert.assertEquals(SECOND_LIST_SMALL_RESULT, ElementsUtil.combine(FIRST_LIST, SECOND_LIST_SMALL, false));
        Assert.assertEquals(SECOND_LIST_SMALL_RESULT2, ElementsUtil.combine(FIRST_LIST, SECOND_LIST_SMALL, true));

        Assert.assertEquals(SECOND_LIST_BIG_RESULT, ElementsUtil.combine(FIRST_LIST, SECOND_LIST_BIG, false));
        Assert.assertEquals(SECOND_LIST_BIG_RESULT, ElementsUtil.combine(FIRST_LIST, SECOND_LIST_BIG, true));
    }
}
