package org.docbag.table;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.iterators.LoopingIterator;
import org.springframework.util.CollectionUtils;

/**
 * Combines all the elements from two lists together.
 * <p></p>The elements from the second list are combined to the elements
 * from the first list. If the first list is larger than the second, the dontWrap parameter is used to determine
 * whether the iteration over the second list should restart from the beginning.</p>
 *
 * @author Jakub Torbicki
 */
public class ElementsUtil {
    private ElementsUtil() {
    }

    /**
     * Combines all the elements from two lists together.
     *
     * @param first List
     * @param second List
     * @param dontWrap should iterating over second list begin from the start in case of too few elements
     * @return combined List
     */
    public static List<? extends Combinable> combine(List<? extends Combinable> first, List<? extends Combinable> second, boolean dontWrap) {
        if (CollectionUtils.isEmpty(second)) {
            return new ArrayList<Combinable>(first);
        } else if (CollectionUtils.isEmpty(first)) {
            return new ArrayList<Combinable>(second);
        }
        Iterator loop = getIterator(second, dontWrap);
        int size = (first.size() > second.size() ? first.size() : second.size());
        List<Combinable> r = new ArrayList<Combinable>(size);
        for (int i = 0; i < size; i++) {
            if (i < first.size()) {
                Combinable elem = first.get(i);
                if (loop.hasNext()) {
                    r.add((Combinable) elem.combine(loop.next()));
                } else {
                    r.add(elem);
                }
            } else {
                r.add((Combinable) loop.next());
            }

        }
        return r;
    }

    private static Iterator getIterator(List<? extends Combinable> second, boolean dontWrap) {
        if (dontWrap) {
            return second.iterator();
        } else {
            return new LoopingIterator(second);
        }
    }
}
