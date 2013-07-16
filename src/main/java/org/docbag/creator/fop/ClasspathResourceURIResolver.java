package org.docbag.creator.fop;

import java.io.InputStream;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

import org.docbag.DocumentCreatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jakub Torbicki
 */
public class ClasspathResourceURIResolver implements URIResolver {

    private static final Logger LOG = LoggerFactory.getLogger(ClasspathResourceURIResolver.class);

    @Override
    public Source resolve(String href, String base) throws TransformerException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(href);
        if (stream != null) {
            return new StreamSource(stream);
        } else {
            LOG.error("Couldn't find resource {} in {}", href, getClass().getClassLoader().getResource(".").getPath());
            throw new DocumentCreatorException("Unable to find resource.");
        }
    }
}
