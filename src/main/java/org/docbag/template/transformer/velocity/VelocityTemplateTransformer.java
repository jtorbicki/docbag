package org.docbag.template.transformer.velocity;

import java.io.CharArrayWriter;
import java.util.Set;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.docbag.Context;
import org.docbag.DefaultContext;
import org.docbag.stream.MemoryInputStream;
import org.docbag.template.DocumentTemplateStream;
import org.docbag.template.MemoryTemplateStream;
import org.docbag.template.transformer.TemplateTransformer;
import org.docbag.template.transformer.xslt.DefaultXSLTTemplateTransformer;

/**
 * VelocityTemplateTransformer can be used as a "pre transformer" to the actual transformation process.
 * <p>It uses <a href="http://velocity.apache.org/">Velocity</a>, a great Java template engine, to pre-process
 * the templates and applies all it's templating rules.</p>
 * <p>Usage of VelocityTemplateTransformer requires one extra read \ parse step of the {@link DocumentTemplateStream}
 * which makes the {@link org.docbag.Document} creation process a bit slower.</p>
 *
 * @author Jakub Torbicki
 */
public class VelocityTemplateTransformer implements TemplateTransformer<DocumentTemplateStream> {
    private volatile boolean ready = false;
    private final VelocityEngine ve = new VelocityEngine();
    private final ThreadAwareTemplateResourceLoader resourceLoader = new ThreadAwareTemplateResourceLoader();
    private final TemplateTransformer<DocumentTemplateStream> transformer;

    public VelocityTemplateTransformer() {
        this(new DefaultXSLTTemplateTransformer());
    }

    public VelocityTemplateTransformer(TemplateTransformer<DocumentTemplateStream> transformer) {
        this.transformer = transformer;
        initVelocity();
    }

    @Override
    public DocumentTemplateStream transform(DocumentTemplateStream templateStream) {
        return transform(templateStream, new DefaultContext());
    }

    @Override
    public DocumentTemplateStream transform(DocumentTemplateStream templateStream, Context context) {
        String templateName = templateStream.getName();
        VelocityContext velocityContext = createVelocityContext(context);
        CharArrayWriter writer = new CharArrayWriter();
        resourceLoader.putResource(templateStream);
        try {
            ve.mergeTemplate(templateName, "UTF-8", velocityContext, writer);
            MemoryTemplateStream velocityStream = new MemoryTemplateStream(new MemoryInputStream(writer.toString().getBytes()), templateName);
            return transformer.transform(velocityStream, context);
        } finally {
            resourceLoader.clearResource();
        }
    }

    private VelocityContext createVelocityContext(Context<String, Object> context) {
        VelocityContext velocityContext = new VelocityContext();
        Set<String> keys = context.keys();
        for (String key : keys) {
            velocityContext.put(key, context.get(key));
        }
        return velocityContext;
    }

    private void initVelocity() {
        if (!ready) {
            synchronized (ve) {
                if (!ready) {
                    ve.setProperty("resource.loader", "docbag, file");
                    ve.setProperty("docbag.resource.loader.description", "Docbag Resource Loader For Velocity");
                    ve.setProperty("docbag.resource.loader.instance", resourceLoader);
                    ve.setProperty("docbag.resource.loader.cache", "false");
                    ve.setProperty("docbag.resource.loader.modificationCheckInterval", "0");
                    ve.init();
                    ready = true;
                }
            }
        }
    }
}
