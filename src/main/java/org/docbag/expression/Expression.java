package org.docbag.expression;

import org.docbag.Context;

/**
 * Top level expression interface. Each instance of Expression is created from
 * source S and gets evaluated to value R.
 *
 * @author Jakub Torbicki
 */
public interface Expression<S, R> {
    /**
     * Get expression value
     */
    public R getValue();

    /**
     * Get expression value
     */
    public R getValue(Context context);

    /**
     * Get expression source
     */
    public S getSource();

    /**
     * @return true if the Expression was evaluated, false otherwise
     */
    public boolean evaluated();
}
