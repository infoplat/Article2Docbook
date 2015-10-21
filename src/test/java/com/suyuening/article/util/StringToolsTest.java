package com.suyuening.article.util;

import junit.framework.Assert;

import org.junit.Test;

public class StringToolsTest {
	@Test
	public void testReplaceBlank() {
		String str = "    一      古来垂钓的类别，便有千百种，有人斜倚碧水青山，只为凝神静气地垂钓一份久逝的恬然，有人却以冰冷的文字做饵，在无数个寂寞难遣的暗夜，垂钓着一世难消的孤单。";
		str = str.replaceAll(" ", "");
		String expected = "一古来垂钓的类别，便有千百种，有人斜倚碧水青山，只为凝神静气地垂钓一份久逝的恬然，有人却以冰冷的文字做饵，在无数个寂寞难遣的暗夜，垂钓着一世难消的孤单。";
		String actual = StringTools.replaceBlank(str);
		Assert.assertEquals(expected, actual);
	}
}
