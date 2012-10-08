package org.docbag.template.transformer.xslt;

import org.docbag.template.repo.DefaultDocumentTemplateRepository;
import org.docbag.template.transformer.content.xml.ContentHandlerFactory;

/**
 * DefaultXSLTTemplateTransformer differs from {@link XSLTTemplateTransformer} only in knowing
 * the default location of "xhtml to xslfo" XSLT style sheet.
 * <p>If you want to use different location simply instantiate {@link XSLTTemplateTransformer} on your own.</p>
 *
 * @author Jakub Torbicki
 */
public class DefaultXSLTTemplateTransformer extends XSLTTemplateTransformer {
    /**
     * Default location of XSLT stylesheet
     */
    public static final String DEFAULT_XSLT_TEMPLATE_LOCATION = "xslt/xhtml2fo.xsl";

    public DefaultXSLTTemplateTransformer() {
        super(DefaultDocumentTemplateRepository.getInstance().findTemplate(DEFAULT_XSLT_TEMPLATE_LOCATION));
    }

    public DefaultXSLTTemplateTransformer(ContentHandlerFactory<String> contentHandlerFactory) {
        super(DefaultDocumentTemplateRepository.getInstance().findTemplate(DEFAULT_XSLT_TEMPLATE_LOCATION), contentHandlerFactory);
    }
}
