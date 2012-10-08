package org.docbag;

import org.docbag.creator.fop.FOPDocumentCreator;
import org.docbag.template.DocumentTemplateStream;
import org.docbag.template.repo.DefaultDocumentTemplateRepository;
import org.docbag.template.repo.DocumentTemplateRepository;
import org.docbag.template.transformer.TemplateTransformer;
import org.docbag.template.transformer.content.xml.EmptyContentHandlerFactory;
import org.docbag.template.transformer.xslt.DefaultXSLTTemplateTransformer;

/**
 * Factory class creating instances of {@link FOPDocumentCreator} which is the default implementation of {@link DocumentCreator}.
 *
 * <p>If the default behaviour is not sufficient then simply create an instance of DocumentCreator like this:</p>
 * <pre>
 *      DocumentCreator<DocumentStream, DocumentTemplateStream> creator = new FOPDocumentCreator(
 *          MimeConstants.MIME_POSTSCRIPT, new DefaultXSLTTemplateTransformer(), DefaultDocumentTemplateRepository.getInstance());
 * </pre>
 *
 * <p>or more general:</p>
 * <pre>
 *      DocumentCreator<Document, DocumentTemplate> creator = getDocumentCreator(...);
 * </pre>
 *
 * @author Jakub Torbicki
 */
public class DocBag {
    private DocBag() {
    }

    /**
     * Creates an instance of {@link FOPDocumentCreator} with the default output type (PDF), default {@link TemplateTransformer}
     * ({@link DefaultXSLTTemplateTransformer}) and default {@link DocumentTemplateRepository} ({@link DefaultDocumentTemplateRepository})
     */
    public static DocumentCreator<DocumentStream, DocumentTemplateStream> newDocumentCreator() {
        return new FOPDocumentCreator(new DefaultXSLTTemplateTransformer(), DefaultDocumentTemplateRepository.getInstance());
    }

    /**
     * Use this method if you want to choose whether to process embedded expressions or not.
     *
     * @param processExpressions true to process the expressions, false otherwise
     */
    public static DocumentCreator<DocumentStream, DocumentTemplateStream> newDocumentCreator(boolean processExpressions) {
        if (processExpressions) {
            return newDocumentCreator();
        } else {
            return new FOPDocumentCreator(new DefaultXSLTTemplateTransformer(new EmptyContentHandlerFactory()),
                DefaultDocumentTemplateRepository.getInstance());
        }
    }

    /**
     * Creates an instance of {@link FOPDocumentCreator} with the default output type (PDF), specified {@link TemplateTransformer}
     * and default {@link DocumentTemplateRepository} ({@link DefaultDocumentTemplateRepository})
     *
     * @param transformer instance of {@link TemplateTransformer}
     */
    public static DocumentCreator<DocumentStream, DocumentTemplateStream> newDocumentCreator(
        TemplateTransformer<DocumentTemplateStream> transformer) {
        return new FOPDocumentCreator(transformer, DefaultDocumentTemplateRepository.getInstance());
    }

    /**
     * Creates an instance of {@link FOPDocumentCreator} with the default output type (PDF), specified {@link TemplateTransformer}
     * and {@link DocumentTemplateRepository}
     *
     * @param transformer instance of {@link TemplateTransformer}
     * @param repository instance of {@link DocumentTemplateRepository}
     */
    public static DocumentCreator<DocumentStream, DocumentTemplateStream> newDocumentCreator(
        TemplateTransformer<DocumentTemplateStream> transformer, DocumentTemplateRepository<DocumentTemplateStream> repository) {
        return new FOPDocumentCreator(transformer, repository);
    }

    /**
     * Creates an instance of {@link FOPDocumentCreator} with the specified output type, specified {@link TemplateTransformer}
     * and {@link DocumentTemplateRepository}
     * @see org.apache.xmlgraphics.util.MimeConstants
     * @param mimeType {@link org.apache.xmlgraphics.util.MimeConstants}
     * @param transformer instance of {@link TemplateTransformer}
     * @param repository instance of {@link DocumentTemplateRepository}
     */
    public static DocumentCreator<DocumentStream, DocumentTemplateStream> newDocumentCreator(String mimeType,
        TemplateTransformer<DocumentTemplateStream> transformer, DocumentTemplateRepository<DocumentTemplateStream> repository) {
        return new FOPDocumentCreator(mimeType, transformer, repository);
    }
}
