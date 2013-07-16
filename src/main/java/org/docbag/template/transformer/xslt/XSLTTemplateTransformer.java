package org.docbag.template.transformer.xslt;

import java.util.Date;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.docbag.Context;
import org.docbag.DefaultContext;
import org.docbag.DocumentCreatorException;
import org.docbag.creator.fop.ClasspathResourceURIResolver;
import org.docbag.stream.MemoryInputStream;
import org.docbag.template.DocumentTemplateStream;
import org.docbag.template.MemoryTemplateStream;
import org.docbag.template.transformer.TemplateTransformer;
import org.docbag.template.transformer.content.xml.ContentHandlerFactory;
import org.docbag.template.transformer.content.xml.TemplateContentHandler;

/**
 * Performs XSLT transformation on {@link DocumentTemplateStream} producing another {@link DocumentTemplateStream}.
 * <p/>
 * <p>It delegates the SAX events to the {@link TemplateContentHandler} instance which by default is
 * created with {@link DynamicContentHandlerFactory}. If there is no need for resolving dynamic content, the default
 * behavior can be overridden by replacing the {@link ContentHandlerFactory}</p>
 * <p/>
 * <p>This class is thread safe and can be reused for different transformations by many threads.</p>
 * <p/>
 * <p>However, it caches and stores XSLT template, so if there is a need for different XSLT transformations,
 * more instances of XSLTTemplateTransformer have to be created, each per one transformation.</p>
 *
 * @author Jakub Torbicki
 * @see org.docbag.template.transformer.content.xml.XMLDynamicContentHandler
 */
public class XSLTTemplateTransformer implements TemplateTransformer<DocumentTemplateStream> {
    private TransformerFactory tFactory;
    private final DocumentTemplateStream html2foTemplateStream;
    private final ContentHandlerFactory<String> contentHandlerFactory;
    private volatile Templates cachedTransformationTemplate;

    public XSLTTemplateTransformer(DocumentTemplateStream html2foTemplateStream) {
        this(html2foTemplateStream, new DynamicContentHandlerFactory());
    }

    public XSLTTemplateTransformer(DocumentTemplateStream html2foTemplateStream, ContentHandlerFactory<String> contentHandlerFactory) {
        this.html2foTemplateStream = html2foTemplateStream;
        this.contentHandlerFactory = contentHandlerFactory;
        configure();
    }

    public DocumentTemplateStream transform(DocumentTemplateStream templateStream) {
        return transform(templateStream, new DefaultContext());
    }

    public DocumentTemplateStream transform(DocumentTemplateStream templateStream, Context context) {
        if (templateStream == null) {
            throw new NullPointerException("DocumentTemplateStream can't be null");
        }
        try {
            Source xsltSrc = new StreamSource(html2foTemplateStream.getStream());
            Source src = new StreamSource(templateStream.getStream());
            String result = performTransformation(src, xsltSrc, context);
            return new MemoryTemplateStream(new MemoryInputStream(result.getBytes()), generateName(templateStream, context));
        } catch (TransformerConfigurationException e) {
            throw new DocumentCreatorException("Transformation error: " + e.getLocalizedMessage(), e);
        } catch (TransformerException e) {
            throw new DocumentCreatorException("Transformation error: " + e.getLocalizedMessage(), e);
        }
    }

    /**
     * Do the actual transformation.
     * <p/>
     * <p>If there is no {@link TemplateContentHandler} set, store result directly in a {@link StreamResult}.
     * Otherwise get the result from {@link TemplateContentHandler}</p>
     *
     * @param src     Template to be transformed
     * @param xsltSrc XSLT stylesheet
     * @param context {@link Context} object
     * @return transformation result
     * @throws TransformerException
     */
    private String performTransformation(Source src, Source xsltSrc, Context context) throws TransformerException {
        Templates templates = getCachedOrCreate(xsltSrc);
        TemplateContentHandler<String> contentHandler = contentHandlerFactory.getContentHandler(context);
        Transformer transformer = templates.newTransformer();
        if (contentHandler == null) {
            StreamResult stream = new StreamResult();
            transformer.transform(src, stream);
            return stream.getWriter().toString();
        } else {
            transformer.transform(src, new SAXResult(contentHandler));
            return contentHandler.getOutput();
        }
    }

    private String generateName(DocumentTemplateStream templateStream, Context context) {
        return "XSLTTemplateTransformer{source='" + templateStream.getName() + "', " +
                "xslt='" + html2foTemplateStream.getName() + "', " +
                "context='" + context.toString() + "', " +
                "at'=" + new Date() + "'}";
    }

    private Templates getCachedOrCreate(Source xsltSrc) throws TransformerConfigurationException {
        Templates templates = cachedTransformationTemplate;
        if (templates == null) {
            synchronized (this) {
                templates = cachedTransformationTemplate;
                if (templates == null) {
                    templates = tFactory.newTemplates(xsltSrc);
                    cachedTransformationTemplate = templates;
                }
            }
        }
        return cachedTransformationTemplate;
    }

    private void configure() {
//        System.setProperty("javax.xml.transform.TransformerFactory", "com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl");
        tFactory = TransformerFactory.newInstance();
        tFactory.setURIResolver(new ClasspathResourceURIResolver());
    }

    public String toString() {
        return "XSLTDynamicTemplateTransformer{" +
                "html2foTemplateStream=" + html2foTemplateStream +
                '}';
    }
}
