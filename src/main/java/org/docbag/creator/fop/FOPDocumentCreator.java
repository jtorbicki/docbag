package org.docbag.creator.fop;

import java.io.File;
import java.util.Date;
import java.util.Map;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.pdf.PDFEncryptionParams;
import org.docbag.*;
import org.docbag.stream.MemoryInputStream;
import org.docbag.stream.MemoryOutputStream;
import org.docbag.template.DocumentTemplateStream;
import org.docbag.template.repo.DocumentTemplateRepository;
import org.docbag.template.transformer.TemplateTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default implementation of a {@link DocumentCreator} interface. It uses Apache FOP as a
 * document generation engine.
 * <p/>
 * <p>The usual document creation flow:</p>
 * <pre>
 * 1. createDocument() method is invoked
 *   1.1. If this is the {@link FOPDocumentCreator#createDocument(String)} or {@link FOPDocumentCreator#createDocument(String, Context)}
 *       version of createDocument() try to find {@link DocumentTemplateStream} using {@link DocumentTemplateRepository}
 *       (templateRepository attribute).
 * 2. Transform {@link DocumentTemplateStream}
 *   2.1 If {@link TemplateTransformer} is set then transform DocumentTemplate into another DocumentTemplate.
 *       The default behaviour in this step is to transform XHTML to XSL-FO and evaluate all the
 *       expressions embedded in the template.
 *   2.2 If the {@link TemplateTransformer} is not set then this step is skipped and the
 *       provided DocumentTemplate is passed directly to the Apache FOP. It means it has to be already
 *       in the XSL-FO format.
 * 3. Create a {@link org.docbag.Document}
 * </pre>
 *
 * @author Jakub Torbicki
 */
public class FOPDocumentCreator implements DocumentCreator<DocumentStream, DocumentTemplateStream> {
    private static final Logger log = LoggerFactory.getLogger(FOPDocumentCreator.class);
    private final String mimeType;
    private final FopFactory fopFactory = FopFactory.newInstance();
    private final TransformerFactory tFactory = TransformerFactory.newInstance();
    private final TemplateTransformer<DocumentTemplateStream> templateTransformer;
    private final DocumentTemplateRepository<DocumentTemplateStream> templateRepository;
    private final FOUserAgent userAgent = fopFactory.newFOUserAgent();

    public FOPDocumentCreator(String mimeType, TemplateTransformer<DocumentTemplateStream> templateTransformer,
                              DocumentTemplateRepository<DocumentTemplateStream> templateRepository) {
        this(mimeType, templateTransformer, templateRepository, null);
    }

    public FOPDocumentCreator(String mimeType, TemplateTransformer<DocumentTemplateStream> templateTransformer,
                              DocumentTemplateRepository<DocumentTemplateStream> templateRepository, DocBagConfig config) {
        this.mimeType = mimeType;
        this.templateTransformer = templateTransformer;
        this.templateRepository = templateRepository;
        if (config != null) {
            configure(config);
        }
    }

    public DocumentStream createDocument(DocumentTemplateStream templateStream) {
        return createDocument(templateStream, new DefaultContext());
    }

    public DocumentStream createDocument(DocumentTemplateStream templateStream, Context context) {
        if (templateStream == null) {
            throw new NullPointerException("DocumentTemplate can't be null!");
        }
        MemoryOutputStream pdf = new MemoryOutputStream();
        try {
            // Prepare DocumentTemplate
            DocumentTemplateStream transformed = transformTemplate(templateStream, context);
            // Generate PDF
            tFactory.newTransformer().transform(new StreamSource(transformed.getStream()),
                    new SAXResult(fopFactory.newFop(mimeType, userAgent, pdf).getDefaultHandler()));
        } catch (Exception e) {
            log.error("Error creating document: " + e.getLocalizedMessage(), e);
            throw new DocumentCreatorException("Error creating document: " + e.getLocalizedMessage(), e);
        }
        return new DocumentStream(new Date(), new MemoryInputStream(pdf));
    }

    /**
     * To be able to use this version of createDocument the templateRepository attribute needs to be set
     */
    public DocumentStream createDocument(String templateName) {
        return createDocument(templateName, new DefaultContext());
    }

    /**
     * To be able to use this version of createDocument the templateRepository attribute needs to be set
     */
    public DocumentStream createDocument(String templateName, Context context) {
        if (templateRepository == null) {
            throw new NullPointerException("Default template repository not set! If you want to use 'templateName' version of"
                    + " createDocument() then you need to set valid instance of DocumentTemplateRepository");
        }
        return createDocument(templateRepository.findTemplate(templateName), context);
    }

    private DocumentTemplateStream transformTemplate(DocumentTemplateStream templateStream, Context context) {
        if (templateTransformer != null) {
            return templateTransformer.transform(templateStream, context);
        }
        return templateStream;
    }

    private void configure(DocBagConfig config) {
        if (config.getFopConfig() != null) {
            DefaultConfigurationBuilder cfgBuilder = new DefaultConfigurationBuilder();
            try {
                fopFactory.setUserConfig(cfgBuilder.buildFromFile(new File(config.getFopConfig())));
            } catch (Exception e) {
                log.error("Error configuring Apache FOP!", e.getLocalizedMessage(), e);
            }
        }
        if (config.getRendererOptions() != null) {
            for (Map.Entry<String, Object> entry : config.getRendererOptions().entrySet()) {
                userAgent.getRendererOptions().put(entry.getKey(), entry.getValue());
            }
        }
    }

    public String toString() {
        return "FOPDocumentCreator{" +
                "mimeType='" + mimeType + "\'}";
    }
}
