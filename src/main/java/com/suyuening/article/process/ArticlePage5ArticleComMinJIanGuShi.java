package com.suyuening.article.process;

import java.util.List;

import com.google.common.collect.Lists;
import com.suyuening.article.bean.ArticlePage;
import com.suyuening.article.bean.DocBook;
import com.suyuening.article.bean.DocBookChapter;
import com.suyuening.article.bean.DocBookSection;
import com.suyuening.article.util.DocBookXmlGenerator;

public class ArticlePage5ArticleComMinJIanGuShi {

    private static final String DOCBOOK_OUTPATH      = "E:\\docbook\\bookxml";
    private static final String LIST_URL             = "/minjian/chuanshuo/";
    private static final int    BOOK_CHAPTER_SIZE    = 10;
    private static final int    CHAPTER_ARTICLE_SIZE = 30;
    private static final String BOOK_NAME            = "民间故事一";

    public static void main(String[] args) {
        String baseUrl = "http://story.5article.com";
//        String realUrl = "/view/25173.html";  // 第四卷
//        String realUrl = "/view/25473.html";  // 第三卷
//        String realUrl = "/view/26037.html";  // 第二卷
         String realUrl = "/view/26680.html"; // 第一卷

        ArticlePage page = ArticlePage.parsePageByUrl(baseUrl, realUrl);
        DocBookSection section = null;
        List<DocBookSection> sections = Lists.newArrayList();

        List<DocBookChapter> chapters = Lists.newArrayList();
        DocBookChapter chapter = null;
        int articleSize = 1;
        int chapterIndex = 1;
        int totalArticleCount = BOOK_CHAPTER_SIZE * CHAPTER_ARTICLE_SIZE;
        while (true) {
            if (!ArticlePage.isPageExists(page)) {
                System.out.println("Page not exists");
                break;
            }
            if (!ArticlePage.isNextUrlExists(page, LIST_URL)) {
                System.out.println("Next url not exists");
                break;
            }

            section = new DocBookSection(page.getTitle(), page.getContents());
            sections.add(section);

            if (articleSize % CHAPTER_ARTICLE_SIZE == 0) {
                chapter = new DocBookChapter(String.format("民间故事%d", chapterIndex++), sections);
                chapters.add(chapter);
                sections = Lists.newArrayList();
            }

            System.out.println(String.format("..........%d/%d..........", articleSize, totalArticleCount));
            articleSize++;

            if (chapters.size() == BOOK_CHAPTER_SIZE) {
                break;
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 解析下一篇
            page = ArticlePage.parsePageByUrl(baseUrl, page.getNextUrl());
        }

        List<String> dedicationParas = Lists.newArrayList("民间故事是民间文学中的重要门类之一。从广义上讲，民间故事就是劳动人民创作并传播的、具有虚构内容的散文形式的口头文学作品，是所有民间散文作品的通称，有的地方叫“瞎话”、“古话”、“古经”等等。",
                                                          "民间故事是从远古时代起就在人们口头流传的一种以奇异的语言和象征的形式讲述人与人之间的种种关系，题材广泛而又充满幻想的叙事体故事。民间故事从生活本身出发，但又并不局限于实际情况以及人们认为真实的和合理范围之内。它们往往包含着自然的、异想天开的成分。");
        DocBook docBook = new DocBook(BOOK_NAME, "佚名", dedicationParas, chapters);
        DocBookXmlGenerator.makeBookXml(docBook, DOCBOOK_OUTPATH);
    }
}
