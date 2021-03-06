package com.suyuening.article.demo;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.suyuening.article.bean.DocBook;
import com.suyuening.article.bean.DocBookChapter;
import com.suyuening.article.bean.DocBookSection;
import com.suyuening.article.util.DocBookXmlGenerator;

public class JsoupDemo {

    private static final String URL = "http://www.5article.com/view/121272.html";
    private static final Logger LOGGER = LoggerFactory.getLogger(JsoupDemo.class);
    public static void main(String[] args) {

        LOGGER.info("start");
        Document doc = null;
        try {
            doc = Jsoup.connect(URL).get();
            // 标题
            String title = doc.title();
            DocBookSection section = new DocBookSection(title);

            // 正文
            Elements contentArticleDiv = doc.select("div[class=content article]");

            List<String> replace = Lists.newArrayList("<br>", "<br >", "<br/>", "<br />");

            String contentHtml = contentArticleDiv.html().toLowerCase();
            for (String oneElement : replace) {
                contentHtml = contentHtml.replace(oneElement, ":_:_:");
            }
            Document docContent = Jsoup.parse(contentHtml);
            LOGGER.info("docContent html={}", docContent.text());
            String[] xx = docContent.text().split(":_:_:");
            for (String string : xx) {
                section.addPara(string);
            }
            section.printInfo();

            // 下一篇文章
            Element contentNextDiv = doc.select("div[class=content-next").first();
            Document docContentNext = Jsoup.parse(contentNextDiv.html());
            Element contentNextUrl = docContentNext.select("a").first();
            LOGGER.info("contentNextUrl={}", contentNextUrl.attr("href"));

            List<DocBookSection> sections = Lists.newArrayList(section);
            List<DocBookChapter> chapters = Lists.newArrayList(new DocBookChapter("散文集", sections));

            List<String> dedicationParas = Lists.newArrayList("我是谁", "谁是我");
            DocBook book = new DocBook("测试书籍", "佚名", dedicationParas, chapters);
            DocBookXmlGenerator.makeBookXml(book, "E:\\docbook\\bookxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        LOGGER.info("end");
    }
}
