package org.docbag.template.repo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.docbag.stream.ChannelUtil;
import org.docbag.stream.CloseableUtil;
import org.docbag.stream.MemoryInputStream;
import org.docbag.stream.MemoryOutputStream;
import org.docbag.template.DocumentTemplateStream;
import org.docbag.template.MemoryTemplateStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FileDocumentTemplateRepository reads {@link org.docbag.template.DocumentTemplate} from the specified file.
 *
 * <p>The template's name is used as a file name which is appended to the base directory.</p>
 * <p>By default it searches for files in the application's execution directory, however different locations can be specified.</p>
 *
 * @author Jakub Torbicki
 */
public class FileDocumentTemplateRepository implements DocumentTemplateRepository<DocumentTemplateStream> {
    private static final String REPOSITORY_NAME = "FileDocumentTemplateRepository";
    private static final Logger log = LoggerFactory.getLogger(FileDocumentTemplateRepository.class);
    private final List<String> baseDirList = new ArrayList<String>();

    public FileDocumentTemplateRepository() {
        this(System.getProperty("user.dir"));
    }

    public FileDocumentTemplateRepository(String baseDir, String... optional) {
        baseDirList.add(baseDir);
        if (optional != null) {
            baseDirList.addAll(Arrays.asList(optional));
        }
    }

    public DocumentTemplateStream findTemplate(String name) {
        if (name == null) {
            throw new NullPointerException("Template name can't be null!");
        }
        File file = findTemplateFile(name);
        if (file == null) {
            if (log.isDebugEnabled()) {
                log.debug(new StringBuilder("Couldn't find template '").append(name).append("' in directories: ").append(
                    Arrays.toString(baseDirList.toArray(new String[baseDirList.size()]))).toString());
            }
            return null;
        }
        final MemoryOutputStream output = new MemoryOutputStream();
        final WritableByteChannel outputChannel = Channels.newChannel(output);
        FileInputStream inputStream = null;
        FileChannel channel = null;
        try {
            inputStream = new FileInputStream(file);
            channel = new FileInputStream(file).getChannel();
            ChannelUtil.copyChannel(channel, outputChannel);
            return new MemoryTemplateStream(new MemoryInputStream(output), name);
        } catch (FileNotFoundException e) {
            log.error("File not found:" + file.getPath());
        } catch (IOException e) {
            log.error("Error reading file: " + file.getPath(), e.getLocalizedMessage(), e);
        } finally {
            CloseableUtil.close(inputStream);
            CloseableUtil.close(channel);
        }
        return null;
    }

    private File findTemplateFile(String name) {
        for (String baseDir : baseDirList) {
            String path = wrapDir(baseDir) + name;
            File file = new File(path);
            if (file.exists()) {
                return file;
            }
        }
        return null;
    }

    public String getRepositoryName() {
        return REPOSITORY_NAME;
    }

    private String wrapDir(String baseDir) {
        if (baseDir.endsWith("/") || baseDir.endsWith("\\")) {
            return baseDir;
        } else {
            return baseDir + "/";
        }
    }
}
