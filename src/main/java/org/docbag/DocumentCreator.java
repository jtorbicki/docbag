package org.docbag;

import org.docbag.template.DocumentTemplate;

/**
 * The DocumentCreator interface is the entry point to the {@link Document} creation process.
 *
 * <p>In general terms, the DocumentCreator takes a {@link DocumentTemplate} as an input and
 * creates a {@link Document} out of it. Behind this very simple logic there might be
 * simple or much more complicated implementation. For more details on how the creation process
 * looks like please refer to the documentation of concrete DocumentCreator implementation,
 * for instance {@link org.docbag.creator.fop.FOPDocumentCreator}</p>
 *
 * @see org.docbag.creator.fop.FOPDocumentCreator
 * @author Jakub Torbicki
 */
public interface DocumentCreator<R extends Document, T extends DocumentTemplate> {
    /**
     * Create a {@link Document} from a {@link DocumentTemplate}. Due to lack of a {@link Context}
     * this method may be used to create Documents based on DocumentTemplates that hold only static content
     * (ie. without any dynamically inserted variables, charts, etc.).
     *
     * @param template {@link DocumentTemplate}
     * @return {@link Document}
     */
    public R createDocument(T template);

    /**
     * Create a {@link Document} from a {@link DocumentTemplate}. Use this method if you have any variables
     * in the DocumentTemplate. Before invoking this method you need to create an instance of a {@link Context}
     * and populate it with all the necessary data. See the {@link Context} JavaDoc for some examples.
     *
     * @param template {@link DocumentTemplate}
     * @param context {@link Context}
     * @return {@link Document}
     */
    public R createDocument(T template, Context context);

    /**
     * Create a {@link Document} from a template name. Usually a DocumentCreator implementation will use
     * templateName to resolve an instance of a {@link DocumentTemplate}. Due to lack of a {@link Context}
     * this method may be used to create static Documents only (ie. without any dynamically inserted variables, charts, etc.).
     *
     * @param templateName template name
     * @return {@link Document}
     */
    public R createDocument(String templateName);

    /**
     * Create a {@link Document} from a template name. Usually a DocumentCreator implementation will use
     * templateName to resolve an instance of a {@link DocumentTemplate}. Before invoking this method you need
     * to create an instance of a {@link Context} and populate it with all the necessary data.
     * See the {@link Context} JavaDoc for some examples.
     *
     * @param templateName template name
     * @param context {@link Context}
     * @return {@link Document}
     */
    public R createDocument(String templateName, Context context);
}
