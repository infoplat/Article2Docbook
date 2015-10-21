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
	
	private ArticlePage(String title, List<String> contents, String currentUrl,
			String nextUrl) {
		super(title, contents, currentUrl, nextUrl);
	}

	public static ArticlePage parsePageByUrl(String baseUrl, String realUrl) {
		String title = null;
		List<String> content = Lists.newArrayList();
		String nextUrl = null;
		String url = String.format("%s%s", baseUrl, realUrl);
		int tryTimes = 3;
		Document doc = null;
		
		content.add(url);
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
		title = editTitle(doc.title());
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

	/**
	 * 编辑文字标题，去掉"_优美散文_精彩美文 "
	 * @param title
	 * @return
	 */
	private static String editTitle(String title) {
		checkNotNull(title, "Title can't be null!");
		return title.replace("_优美散文_精彩美文", "");
	}

	private static String getEditedContentLine(String contentLine) {
		String temp = StringTools.replaceBlank(contentLine);
		if(StringUtil.isBlank(temp)) {
			return temp;
		}
		// 替换看着想空格，其实不是空格的特殊字符
		return temp.replaceAll(" ", "");
	}
}
