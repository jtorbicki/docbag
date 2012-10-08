package org.docbag.table;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Table implementation.
 *
 * @author Jakub Torbicki
 */
public class Table implements Combinable<Table> {
    private final String name;
    private final boolean dontWrap;
    private final List<Row> thead;
    private final List<Row> tbody;
    private final List<Row> tfoot;

    public Table(String name) {
        this(name, Collections.<Row>emptyList());
    }

    public Table(String name, List<Row> tbody) {
        this(name, Collections.<Row>emptyList(), tbody);
    }

    public Table(String name, List<Row> thead, List<Row> tbody) {
        this(name, thead, tbody, Collections.<Row>emptyList(), false);
    }

    public Table(String name, List<Row> thead, List<Row> tbody, boolean dontWrap) {
        this(name, thead, tbody, Collections.<Row>emptyList(), dontWrap);
    }

    @JsonCreator
    public Table(@JsonProperty("name") String name, @JsonProperty("thead") List<Row> thead,
        @JsonProperty("tbody") List<Row> tbody, @JsonProperty("tfoot") List<Row> tfoot, @JsonProperty("dontWrap") boolean dontWrap) {
        this.name = name;
        this.dontWrap = dontWrap;
        this.thead = (thead == null ? Collections.<Row>emptyList() : thead);
        this.tbody = (tbody == null ? Collections.<Row>emptyList() : tbody);
        this.tfoot = (tfoot == null ? Collections.<Row>emptyList() : tfoot);
    }

    @SuppressWarnings("unchecked")
    public Table combine(Table table) {
        List<Row> head = (List<Row>) ElementsUtil.combine(thead, table.thead, table.dontWrap);
        List<Row> body = (List<Row>) ElementsUtil.combine(tbody, table.tbody, table.dontWrap);
        List<Row> foot = (List<Row>) ElementsUtil.combine(tfoot, table.tfoot, table.dontWrap);
        return new Table(table.name, head, body, foot, table.dontWrap);
    }

    public String getName() {
        return name;
    }

    public List<Row> getThead() {
        return Collections.unmodifiableList(thead);
    }

    public List<Row> getTbody() {
        return Collections.unmodifiableList(tbody);
    }

    public List<Row> getTfoot() {
        return Collections.unmodifiableList(tfoot);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Table table = (Table) o;
        return name.equals(table.name);
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "Table{" +
            "name='" + name + '\'' +
            '}';
    }
}
