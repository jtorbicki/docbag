package org.docbag.template;

import java.io.InputStream;

import org.docbag.stream.MemoryInputStream;

/**
 * Implementation of {@link DocumentTemplateStream} that keeps {@link DocumentTemplate} in memory.
 *
 * @author Jakub Torbicki
 */
public class MemoryTemplateStream implements DocumentTemplateStream {
    private final MemoryInputStream memory;
    private final String name;

    public MemoryTemplateStream(MemoryInputStream memory, String name) {
        this.memory = memory;
        this.name = name;
    }

    public InputStream getStream() {
        return memory;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "MemoryTemplateStream{" +
            "name='" + name + '\'' +
            '}';
    }
}
