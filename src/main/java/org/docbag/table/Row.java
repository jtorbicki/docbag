package org.docbag.table;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.collections.MapUtils;

/**
 * Single table row implementation.
 *
 * @author Jakub Torbicki
 */
public class Row implements Combinable<Row> {
    private final List<Cell> cells;
    private final Map<String, String> style;

    public Row(List<Cell> cells) {
        this(cells, Collections.<String, String>emptyMap());
    }

    public Row(Map<String, String> style) {
        this(Collections.<Cell>emptyList(), style);
    }

    @JsonCreator
    public Row(@JsonProperty("cells") List<Cell> cells, @JsonProperty("style") Map<String, String> style) {
        this.cells = cells;
        this.style = style;
    }

    public Row combine(Row row) {
        @SuppressWarnings("unchecked") List<Cell> combinedCells = (List<Cell>) ElementsUtil.combine(cells, row.cells, true);
        Map<String, String> combinedStyle = new HashMap<String, String>();
        if (!MapUtils.isEmpty(style)) {
            combinedStyle.putAll(style);
        }
        if (!MapUtils.isEmpty(row.style)) {
            combinedStyle.putAll(row.style);
        }
        return new Row(combinedCells, combinedStyle);
    }

    public List<Cell> getCells() {
        return Collections.unmodifiableList(cells);
    }

    public Map<String, String> getStyle() {
        return Collections.unmodifiableMap(style);
    }
}
