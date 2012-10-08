package org.docbag.table;

/**
 * Interface representing object that can be combined with another Combinable object.
 *
 * @author Jakub Torbicki
 */
public interface Combinable<T> {
    public T combine(T c);
}
