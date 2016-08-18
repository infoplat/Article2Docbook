package com.suyuening.article.bean;

import java.util.List;

import org.jsoup.helper.StringUtil;

public class Page {

	protected String title;
	protected List<String> contents;
	protected String currentUrl;
	protected String nextUrl;

	public Page(String title, List<String> contents, String currentUrl, String nextUrl) {
		super();
		this.title = title;
		this.contents = contents;
		this.currentUrl = currentUrl;
		this.nextUrl = nextUrl;
	}

	/**
	 * 页面是否存在
	 * 
	 * @param page
	 * @return
	 */
	public static boolean isPageExists(ArticlePage page) {
		return page != null ? true : false;
	}

	/**
	 * 下一篇文章是否存在
	 * 
	 * @param page
	 *            页面
	 * @param listUrl
	 *            返回列表页URL
	 * @return
	 */
	public static boolean isNextUrlExists(Page page, String listUrl) {
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
