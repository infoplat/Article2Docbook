package com.suyuening.article.process;

import java.util.List;

import com.google.common.collect.Lists;
import com.suyuening.article.bean.ArticlePage;
import com.suyuening.article.bean.DocBook;
import com.suyuening.article.bean.DocBookChapter;
import com.suyuening.article.bean.DocBookSection;
import com.suyuening.article.util.DocBookXmlGenerator;

public class ArticlePage5ArticleCom {
	private static final String LIST_URL = "/wz/sanwen/youmei/";
	private static final int BOOK_CHAPTER_SIZE = 5;
	private static final int CHAPTER_ARTICLE_SIZE = 10;
	private static final String BOOK_NAME = "精彩美文卷十三";
	public static void main(String[] args) {
        String baseUrl = "http://www.5article.com";
//        String realUrl = "/view/135427.html";
//        String realUrl = "/view/132229.html";
//        String realUrl = "/view/131827.html";
//        String realUrl = "/view/131475.html";
//        String realUrl = "/view/131159.html";
//        String realUrl = "/view/130867.html";
//        String realUrl = "/view/130565.html";
//        String realUrl = "/view/130298.html";
//        String realUrl = "/view/129992.html";
//        String realUrl = "/view/129438.html";
//        String realUrl = "/view/128919.html";
//        String realUrl = "/view/128521.html";
        String realUrl = "/view/127990.html";

        ArticlePage page = ArticlePage.parsePageByUrl(baseUrl, realUrl);
        DocBookSection section = null;
        List<DocBookSection> sections = Lists.newArrayList();
        
        List<DocBookChapter> chapters = Lists.newArrayList();
        DocBookChapter chapter = null;
        int articleZise = 1;
        int chapterIndex = 1;
        int totalArticleCount = BOOK_CHAPTER_SIZE * CHAPTER_ARTICLE_SIZE;
        while(true) {
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
        	
        	if (articleZise % CHAPTER_ARTICLE_SIZE == 0) {
        		chapter = new DocBookChapter(String.format("散文%d", chapterIndex++), sections);
        		chapters.add(chapter);
        		sections = Lists.newArrayList();
        	}
            
        	System.out.println(String.format("..........%d/%d..........", articleZise, totalArticleCount));
        	articleZise++;
        	
        	if (chapters.size() == BOOK_CHAPTER_SIZE) {
        		break;
        	}

        	try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

        	// 解析下一篇
        	page = ArticlePage.parsePageByUrl(baseUrl, page.getNextUrl());
        }
        
        List<String> dedicationParas = Lists.newArrayList("品精彩美文", "享快乐生活");
		DocBook docBook = new DocBook(BOOK_NAME, "佚名", dedicationParas , chapters);
		DocBookXmlGenerator.makeBookXml(docBook, "E:\\docbook\\bookxml");
	}
}
