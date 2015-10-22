package com.suyuening.article.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.helper.StringUtil;

public final class StringTools {
    private StringTools() {}
    
	/**
	 * 去除字符串中的空格、回车、换行符、制表符
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		if (StringUtil.isBlank(str)) {
			return str;
		}
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(str);
		String dest = m.replaceAll("");
        return dest;
    }
}
