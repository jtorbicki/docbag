package org.docbag.chart.jfree;

import java.awt.*;

import junit.framework.Assert;
import org.junit.Test;

/**
 * StyleTest
 *
 * @author Jakub Torbicki
 */
public class StyleTest {
    private final BasicStroke stroke = new BasicStroke();
    private final Color color = Color.white;
    private final Style style = new Style(color, stroke);
    private final Style styleSame = new Style(color, stroke);
    private final Style styleDiff = new Style(Color.black, stroke);

    @Test
    public void testGetters() throws Exception {
        Assert.assertEquals(color, style.getColor());
        Assert.assertEquals(stroke, style.getStroke());
    }

    @Test
    public void testToString() throws Exception {
        Assert.assertNotNull(style.toString());
    }

    @Test
    public void testEquals() throws Exception {
        Assert.assertTrue(style.equals(style));
        Assert.assertFalse(style.equals(styleSame));
        Assert.assertFalse(style.equals(styleDiff));
    }
}
