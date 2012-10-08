package org.docbag.expression.evaluator.json;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.docbag.expression.evaluator.EvaluatorException;
import org.docbag.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JSONContentResolver helper class.
 *
 * @author Jakub Torbicki
 */
public class JSONResolverUtil {
    private JSONResolverUtil() {
    }

    private static final Logger log = LoggerFactory.getLogger(JSONContentResolver.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.setVisibilityChecker(mapper.getSerializationConfig().getDefaultVisibilityChecker().withFieldVisibility(
            JsonAutoDetect.Visibility.ANY).withGetterVisibility(JsonAutoDetect.Visibility.NONE).withSetterVisibility(
            JsonAutoDetect.Visibility.NONE).withCreatorVisibility(JsonAutoDetect.Visibility.ANY));
    }

    public static JSONChart resolveChart(String json) {
        try {
            return mapper.readValue(json, JSONChart.class);
        } catch (IOException e) {
            log.error("Couldn't create Chart from: " + json, e.getLocalizedMessage(), e);
            throw new EvaluatorException("Couldn't create Chart from: " + json, e);
        }
    }

    public static Table resolveTable(String json) {
        try {
            return mapper.readValue(json, Table.class);
        } catch (IOException e) {
            log.error("Couldn't create Table from: " + json, e.getLocalizedMessage(), e);
            throw new EvaluatorException("Couldn't create Table from: " + json, e);
        }
    }
}
