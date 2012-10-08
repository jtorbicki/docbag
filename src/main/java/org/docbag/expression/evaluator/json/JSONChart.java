package org.docbag.expression.evaluator.json;

/**
 * JSONChart
 *
 * @author Jakub Torbicki
 */
public class JSONChart {
    private int width;
    private int height;
    private String name;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JSONChart chart = (JSONChart) o;
        if (height != chart.height) {
            return false;
        }
        if (width != chart.width) {
            return false;
        }
        if (name != null ? !name.equals(chart.name) : chart.name != null) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "JSONChart{" +
            "width=" + width +
            ", height=" + height +
            ", name='" + name + '\'' +
            '}';
    }
}
