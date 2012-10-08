package org.docbag.table;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Single table cell implementation.
 *
 * @author Jakub Torbicki
 */
public class Cell implements Combinable<Cell> {
    private final String data;
    private final Map<String, String> style;
    private final int colspan;
    private final int rowspan;
    private final String cellpadding;

    public Cell(String data) {
        this(data, Collections.<String, String>emptyMap(), 0, 0, "");
    }

    @JsonCreator
    public Cell(@JsonProperty("data") String data, @JsonProperty("style") Map<String, String> style, @JsonProperty("colspan") int colspan,
        @JsonProperty("rowspan") int rowspan, @JsonProperty("cellpadding") String cellpadding) {
        this.data = data;
        this.style = style;
        this.colspan = colspan;
        this.rowspan = rowspan;
        this.cellpadding = cellpadding;
    }

    public Cell combine(Cell c) {
        String combinedData = (c.data != null ? c.data : data);
        int combinedColSpan = (c.colspan != 0 ? c.colspan : colspan);
        int combinedRowSpan = (c.rowspan != 0 ? c.rowspan : rowspan);
        String combinedCellPadding = (!StringUtils.isBlank(c.cellpadding) ? c.cellpadding : cellpadding);
        Map<String, String> combinedStyle = new HashMap<String, String>();
        if (!MapUtils.isEmpty(style)) {
            combinedStyle.putAll(style);
        }
        if (!MapUtils.isEmpty(c.style)) {
            combinedStyle.putAll(c.style);
        }
        return new Cell(combinedData, combinedStyle, combinedColSpan, combinedRowSpan, combinedCellPadding);
    }

    public String getData() {
        return data;
    }

    public int getColspan() {
        return colspan;
    }

    public int getRowspan() {
        return rowspan;
    }

    public String getCellpadding() {
        return cellpadding;
    }

    public Map<String, String> getStyle() {
        return Collections.unmodifiableMap(style);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cell cell = (Cell) o;
        if (colspan != cell.colspan) {
            return false;
        }
        if (rowspan != cell.rowspan) {
            return false;
        }
        if (cellpadding != null ? !cellpadding.equals(cell.cellpadding) : cell.cellpadding != null) {
            return false;
        }
        return data.equals(cell.data);
    }

    public int hashCode() {
        return data.hashCode();
    }

    public String toString() {
        return "Cell{" +
            "data='" + data + '\'' +
            '}';
    }
}
