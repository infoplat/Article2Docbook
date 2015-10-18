package com.suyuening.article.bean;

import java.util.List;

import org.jsoup.nodes.Document;

import com.google.common.collect.Lists;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;

public class ArticlePage {
	private String title;
	private List<String> contents;
	private String currentUrl;
	private String nextUrl;
	
	private ArticlePage(String title, List<String> contents, String currentUrl,
			String nextUrl) {
		super();
		this.title = title;
		this.contents = contents;
		this.currentUrl = currentUrl;
		this.nextUrl = nextUrl;
	}

	public static ArticlePage parsePageByUrl(String baseUrl, String realUrl) {
		String title = null;
		List<String> content = Lists.newArrayList();
		String nextUrl = null;
		String url = String.format("%s%s", baseUrl, realUrl);
		int tryTimes = 3;
		Document doc = null;
			
		do {
			try {
				doc = Jsoup.connect(url).get();
			} catch (Exception e) {
				tryTimes--;
				continue;
		    }
			break;
		}while (tryTimes>0);
			
		// 标题
		title = doc.title();
		// 正文
		Elements contentArticleDiv = doc.select("div[class=content article]");
		Document docContent = Jsoup.parse(contentArticleDiv.html());
		Elements elementsContent = docContent.select("p");
		for (Element element : elementsContent) {
			String contentLine = getEditedContentLine(element.text());
			// 排除空行和空字符串行
			if (!StringUtil.isBlank(contentLine)) {
				content.add(element.text());
			}
		}
		// 下一篇文章
		Element contentNextDiv = doc.select("div[class=content-next")
				.first();
		Document docContentNext = Jsoup.parse(contentNextDiv.html());
		Element contentNextUrl = docContentNext.select("a").first();
		nextUrl = contentNextUrl.attr("href");

		System.out.println(String.format("currentUrl=%s", realUrl));
		System.out.println(String.format("nextUrl=%s", nextUrl));
		return new ArticlePage(title, content, realUrl, nextUrl);
	}

	private static String getEditedContentLine(String contentLine) {
		if(StringUtil.isBlank(contentLine)) {
			return contentLine;
		}
		
		return contentLine.trim().replaceAll("　", "");
	}
	/**
	 * 页面是否存在
	 * @param page
	 * @return
	 */
	public static boolean isPageExists(ArticlePage page) {
		return page != null ? true : false;
	}

	/**
	 * 下一篇文章是否存在
	 * @param page 页面
	 * @param listUrl 返回列表页URL
	 * @return
	 */
	public static boolean isNextUrlExists(ArticlePage page, String listUrl) {
    	if (!StringUtil.isBlank(page.getNextUrl()) && !page.getNextUrl().equals(listUrl)) {
    		return true;
    	}
    	return false;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getContents() {
		return contents;
	}

	public void setContents(List<String> contents) {
		this.contents = contents;
	}

	public String getCurrentUrl() {
		return currentUrl;
	}

	public void setCurrentUrl(String currentUrl) {
		this.currentUrl = currentUrl;
	}

	public String getNextUrl() {
		return nextUrl;
	}

	public void setNextUrl(String nextUrl) {
		this.nextUrl = nextUrl;
	}
	
}
