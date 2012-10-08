package org.docbag;

import java.util.Set;

/**
 * Generic interface that holds a collection of a key and value pairs.
 * It's main purpose is to map template expressions and Java objects.
 *
 * <p>It is possible to embed expression inside a xhtml (or theoretically inside any text-based template)
 * using the {@link org.docbag.template.TemplatePatterns#EXPRESSION_PATTERN}, for example like this:</p>
 *
 * <pre>
 *     #{context('name')}
 * </pre>
 *
 * <p>All embedded expressions will be evaluated during the document creation process using chosen
 * {@link org.docbag.expression.evaluator.ExpressionEvaluator}. Please refer to
 * {@link org.docbag.expression.evaluator.ExpressionEvaluator} interface and its implementations
 * for more detailed outlook on how to embed expressions inside templates.</p>
 *
 * <p>It is necessary to populate Context before the Document creation process starts.
 * For instance, to be able to display dynamic message "Hello, Friend!" in a document, two things need to be done.</p>
 *
 * <p>1. Expression needs to be embedded in a template:</p>
 * <pre>
 *     Hello, #{context('name')}!
 * </pre>
 * <p>2. Context object needs to be populated:</p>
 * <pre>
 *     Context context = new DefaultContext();
 *     context.put("name", "Friend");
 * </pre>
 * @see DefaultContext
 * @author Jakub Torbicki
 */
public interface Context<K, V> {
    /**
     * Store given Object o in the Context under the given name.
     * It is up to the interface implementation to decide what to do with the duplicates.
     *
     * @param name key
     * @param o value
     */
    public void put(K name, V o);

    /**
     * Retrieves associated Object with the given key or returns null if the key was not found.
     *
     * @param name key
     */
    public V get(K name);

    /**
     * Returns the set of all the stored keys
     *
     * @return set of keys
     */
    public Set<K> keys();

    /**
     * Number of key - value pairs stored in the Context
     */
    public int size();
}
