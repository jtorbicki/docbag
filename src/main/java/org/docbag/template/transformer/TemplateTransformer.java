package org.docbag.template.transformer;

import org.docbag.Context;
import org.docbag.template.DocumentTemplate;

/**
 * Transforms {@link DocumentTemplate} into another {@link DocumentTemplate}.
 *
 * <p>The most obvious implementation would be an XSLT transformation, adding dynamic content,
 * evaluating expressions, etc.</p>
 *
 * @author Jakub Torbicki
 */
public interface TemplateTransformer<R extends DocumentTemplate> {
    /**
     * Transform {@link DocumentTemplate} into another {@link DocumentTemplate}.
     */
    public R transform(R template);

    /**
     * Transform {@link DocumentTemplate} into another {@link DocumentTemplate}.
     * The concrete implementation may use information stored in {@link Context} if necessary.
     */
    public R transform(R template, Context context);
}
