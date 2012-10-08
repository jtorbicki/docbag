package org.docbag.stream;

import java.io.Closeable;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CloseableUtil
 *
 * @author Jakub Torbicki
 */
public class CloseableUtil {
    private static final Logger log = LoggerFactory.getLogger(CloseableUtil.class);

    private CloseableUtil() {
    }

    public static void close(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
                log.error("Couldn't close: " + c, e.getLocalizedMessage(), e);
            }
        }
    }
}
