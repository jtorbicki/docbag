package org.docbag.table;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.docbag.xml.XMLContent;
import org.springframework.core.convert.converter.Converter;
import org.xml.sax.helpers.AttributesImpl;

/**
 * TableToFOConverter converts {@link Table} objects to the XSL-FO representation.
 *
 * @author Jakub Torbicki
 */
public class TableToFOConverter implements Converter<Table, String> {
    public String convert(Table source) {
        if (source == null) {
            throw new NullPointerException("Table can't be null!");
        }
        XMLContent xml = new XMLContent();
        convertHead(source.getThead(), xml);
        convertFoot(source.getTfoot(), xml);
        convertBody(source.getTbody(), xml);
        xml.complete();
        return xml.getContent();
    }

    private void convertHead(List<Row> rows, XMLContent out) {
        if (!CollectionUtils.isEmpty(rows)) {
            out.startElement("", "", "fo:table-header", null);
            convertRows(rows, out);
            out.endElement("fo:table-header");
        }
    }

    private void convertBody(List<Row> rows, XMLContent out) {
        if (!CollectionUtils.isEmpty(rows)) {
            if (!CollectionUtils.isEmpty(rows)) {
                out.startElement("", "", "fo:table-body", null);
                convertRows(rows, out);
                out.endElement("fo:table-body");
            }
        }
    }

    private void convertFoot(List<Row> rows, XMLContent out) {
        if (!CollectionUtils.isEmpty(rows)) {
            if (!CollectionUtils.isEmpty(rows)) {
                out.startElement("", "", "fo:table-footer", null);
                convertRows(rows, out);
                out.endElement("fo:table-footer");
            }
        }
    }

    private void convertRows(List<Row> rows, XMLContent out) {
        for (Row row : rows) {
            AttributesImpl attributes = createAttributes(row.getStyle());
            attributes.addAttribute("", "", "role", "", "html:tr");
            out.startElement("", "", "fo:table-row", attributes);
            convertCells(row.getCells(), out);
            out.endElement("fo:table-row");
        }
    }

    private AttributesImpl createAttributes(Map<String, String> style) {
        AttributesImpl attributes = new AttributesImpl();
        if (!MapUtils.isEmpty(style)) {
            Set<Map.Entry<String,String>> keys = style.entrySet();
            for(Map.Entry<String, String> key:keys){
                attributes.addAttribute("", "", key.getKey(), "", key.getValue());
            }
        }
        return attributes;
    }

    private void convertCells(List<Cell> cells, XMLContent out) {
        if (!CollectionUtils.isEmpty(cells)) {
            for (Cell cell : cells) {
                AttributesImpl attributes = createAttributes(cell.getStyle());
                attributes.addAttribute("", "", "role", "", "html:td");
                if (cell.getColspan() > 0) {
                    attributes.addAttribute("", "", "number-columns-spanned", "", Integer.toString(cell.getColspan()));
                }
                if (cell.getRowspan() > 0) {
                    attributes.addAttribute("", "", "number-rows-spanned", "", Integer.toString(cell.getRowspan()));
                }
                if (!StringUtils.isBlank(cell.getCellpadding())) {
                    if (cell.getCellpadding().contains("%")) {
                        attributes.addAttribute("", "", "padding", "", cell.getCellpadding());
                    } else {
                        attributes.addAttribute("", "", "padding", "", cell.getCellpadding() + "px");
                    }
                }
                out.startElement("", "", "fo:table-cell", attributes);
                out.startElement("", "", "fo:block", null);
                out.append(cell.getData());
                out.endElement("fo:block");
                out.endElement("fo:table-cell");
            }
        }
    }
}
