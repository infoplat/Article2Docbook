package com.suyuening.article.bean;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.jsoup.helper.StringUtil;

@Getter
@Setter
@AllArgsConstructor
public class Page {

    protected String       title;
    protected List<String> contents;
    protected String       currentUrl;
    protected String       nextUrl;

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
     * @param page 页面
     * @param listUrl 返回列表页URL
     * @return
     */
    public static boolean isNextUrlExists(Page page, String listUrl) {
        if (!StringUtil.isBlank(page.getNextUrl()) && !page.getNextUrl().equals(listUrl)) {
            return true;
        }
        return false;
    }
}
