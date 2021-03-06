package com.suyuening.article.bean;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

import org.jsoup.nodes.Document;

import com.google.common.collect.Lists;
import com.suyuening.article.util.StringTools;

import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;

public class ArticlePage extends Page {

    private static final String       SPLIT_STR = ":_:_:";
    private static final List<String> REPLACE   = Lists.newArrayList("<br>", "<br >", "<br/>", "<br />");

    private ArticlePage(String title, List<String> contents, String currentUrl, String nextUrl){
        super(title, contents, currentUrl, nextUrl);
    }

    public static ArticlePage parsePageByUrl(String baseUrl, String realUrl) {

        checkNotNull(realUrl, "realUrl can't be null");
        String title = null;
        List<String> content = Lists.newArrayList();
        String nextUrl = null;
        String url = null;
        if (realUrl.contains("5article.com")) {
            url = realUrl;
        } else {
            url = formatUrl(baseUrl, realUrl);
        }
        int tryTimes = 3;
        Document doc = null;

        content.add(url);
        do {
            try {
                doc = Jsoup.connect(url).userAgent("Mozilla").get();
            } catch (Exception e) {
                tryTimes--;
                continue;
            }
            break;
        } while (tryTimes > 0);

        // 标题
        title = editTitle(doc.title());
        // 正文
        Elements contentArticleDiv = doc.select("div[class=content article]");
        String contentHtml = contentArticleDiv.html().toLowerCase();

        Document docContent = Jsoup.parse(contentHtml);
        Elements elementsContent = docContent.select("p");
        String contentFlag = null;
        int size = elementsContent.size();
        if (size > 3) {
            contentFlag = "P";
            for (Element element : elementsContent) {
                addContentLine(content, element.text());
            }
        } else {
            contentFlag = "BR";
            for (String oneElement : REPLACE) {
                contentHtml = contentHtml.replace(oneElement, SPLIT_STR);
            }
            Document temp = Jsoup.parse(contentHtml);
            String[] contentLines = temp.text().split(SPLIT_STR);
            for (String line : contentLines) {
                addContentLine(content, line);
            }
        }

        // 下一篇文章
        Element contentNextDiv = doc.select("div[class=content-next").first();
        Document docContentNext = null;
        if (contentNextDiv != null) {
        	docContentNext = Jsoup.parse(contentNextDiv.html());
        }

        Element contentNextUrl = null; 
        if (docContentNext != null) {
        	contentNextUrl = docContentNext.select("a").first();
        }
        
        if (contentNextUrl != null) {
        	nextUrl = contentNextUrl.attr("href");
        }
        content.add(formatUrl(baseUrl, nextUrl));

        System.out.println(String.format("currentUrl=%s,contentFlag=%s", realUrl, contentFlag));
        System.out.println(String.format("nextUrl=%s", nextUrl));
        return new ArticlePage(title, content, realUrl, nextUrl);
    }

    private static void addContentLine(List<String> content, String contentLine) {
        String line = getEditedContentLine(contentLine);
        if (StringUtil.isBlank(line)) {
            return;
        }
        if (line.contains("民间故事")) {
            content.add(line.split("民间故事")[0]);
            return;
        }
        if (line.contains("阅读全文")) {
            return;
        }
        content.add(line);
    }

    private static String formatUrl(String baseUrl, String realUrl) {
        checkNotNull(baseUrl, "baseUrl can't be null");
        checkNotNull(realUrl, "realUrl can't be null");
        if (realUrl.contains(baseUrl)) {
            return realUrl;
        }
        return String.format("%s%s", baseUrl, realUrl);
    }

    /**
     * 编辑文字标题，去掉"_优美散文_精彩美文 "
     * 
     * @param title
     * @return
     */
    private static String editTitle(String title) {
        checkNotNull(title, "Title can't be null!");
        return title.replace("_优美散文_精彩美文", "");
    }

    private static String getEditedContentLine(String contentLine) {
        String temp = StringTools.replaceBlank(contentLine);
        if (StringUtil.isBlank(temp)) {
            return "";
        }
        // 替换看着想空格，其实不是空格的特殊字符
        return temp.replaceAll(" ", "");
    }
}
