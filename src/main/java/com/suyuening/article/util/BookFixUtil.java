package com.suyuening.article.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public final class BookFixUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(DocBookXmlGenerator.class);

    private BookFixUtil(){
    }

    private static final String BOOK_ROOT    = "<book xmlns=\"http://docbook.org/ns/docbook\" version=\"5.0\" xmlns:xi=\"http://www.w3.org/2001/XInclude\" xml:lang=\"zh_cn\">";
    private static final String CHAPTER_ROOT = "<chapter xmlns=\"http://docbook.org/ns/docbook\" version=\"5.0\" xml:lang=\"zh_cn\">";

    /**
     * 修复book XML文件，进行下面的操作 <book version="5.0">替换成<book xmlns="http://docbook.org/ns/docbook" version="5.0"
     * xmlns:xi="http://www.w3.org/2001/XInclude" xml:lang="zh_cn"> include替换成xi:include
     * 
     * @param bookXmlPath
     */
    public static void fixBookXml(String bookXmlPath) {
        List<String> lines = FileUtil.readFile2List(bookXmlPath);
        List<String> outLines = Lists.newArrayList();
        for (String line : lines) {
            if (line.contains("<book version=\"5.0\">")) {
                line = BOOK_ROOT;
            }
            if (line.contains("include")) {
                line = line.replace("include", "xi:include");
            }
            outLines.add(line);
        }
        FileUtil.writeFileFromList(outLines, bookXmlPath);
        LOGGER.info("bookXmlPath={}, fix ok", bookXmlPath);
    }

    /**
     * 修复章节 XML文件，进行下面的操作 <chapter version="5.0">替换成<chapter xmlns="http://docbook.org/ns/docbook" version="5.0"
     * xml:lang="zh_cn">
     * 
     * @param chapterXmlPath
     */
    public static void fixChapterXml(String chapterXmlPath) {
        List<String> lines = FileUtil.readFile2List(chapterXmlPath);
        List<String> outLines = Lists.newArrayList();
        for (String line : lines) {
            if (line.contains("<chapter version=\"5.0\">")) {
                line = CHAPTER_ROOT;
            }
            outLines.add(line);
        }
        FileUtil.writeFileFromList(outLines, chapterXmlPath);
        LOGGER.info("chapterXmlPath={}, fix ok", chapterXmlPath);
    }
}
