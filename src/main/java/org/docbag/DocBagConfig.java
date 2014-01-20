package org.docbag;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jakub Torbicki
 */
public class DocBagConfig {
    private final String fopConfig;
    private final Map<String, Object> rendererOptions;

    private DocBagConfig(Builder builder) {
        this.fopConfig = builder.fopConfig;
        this.rendererOptions = Collections.unmodifiableMap(builder.rendererOptions);
    }

    public Map<String, Object> getRendererOptions() {
        return rendererOptions;
    }

    public String getFopConfig() {
        return fopConfig;
    }

    public static class Builder {
        private String fopConfig;
        private final Map<String, Object> rendererOptions = new HashMap<String, Object>();

        public DocBagConfig build() {
            return new DocBagConfig(this);
        }

        public Builder withConfig(String fopConfig) {
            this.fopConfig = fopConfig;
            return this;
        }

        public Builder withRendererOption(String key, Object option) {
            this.rendererOptions.put(key, option);
            return this;
        }
    }
}
