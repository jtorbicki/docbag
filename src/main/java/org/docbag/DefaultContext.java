package org.docbag;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of a {@link Context} interface that stores a key - value pairs in a {@link HashMap}
 *
 * @see Context
 * @author Jakub Torbicki
 */
public class DefaultContext implements Context<String, Object> {
    private final Map<String, Object> context = new HashMap<String, Object>();

    /**
     * From {@link Map} JavaDoc:
     * <p>If the map previously contained a mapping for
     * the key, the old value is replaced by the specified value.</p>
     *
     * @param name key
     * @param o value
     */
    public void put(String name, Object o) {
        context.put(name, o);
    }

    public Object get(String name) {
        return context.get(name);
    }

    public Set<String> keys() {
        return context.keySet();
    }

    public int size() {
        return context.size();
    }

    public String toString() {
        return "Context{" +
            "context=" + context.keySet() +
            '}';
    }
}
