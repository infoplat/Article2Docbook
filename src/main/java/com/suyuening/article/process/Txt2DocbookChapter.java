package com.suyuening.article.process;

import java.util.List;

import com.google.common.collect.Lists;
import com.suyuening.article.bean.DocBook;
import com.suyuening.article.bean.DocBookChapter;
import com.suyuening.article.bean.DocBookSection;
import com.suyuening.article.util.DocBookXmlGenerator;
import com.suyuening.article.util.FileUtil;

/**
 * 稻盛和夫《活法》MD转换成DocBookxml 格式
 * <p>
 * 只支持转换一个章节. 格式：
 * 
 * <pre>
 * ## 本章标题
 * ### 本章小节一
 * ### 本章小节二
 * ### 本章小节......
 * ### 本章最后一个小节
 * ### 最后必须有这个小结，代码不会转换
 * </pre>
 * 
 * @author suyuening
 *
 */
public class Txt2DocbookChapter {

	private static final String DOCBOOK_OUTPATH = "F:\\syn\\docbook\\bookxml";

	public static void main(String[] args) {
//		Txt2Docbook.convert("活法1", "稻盛和夫",
//				Lists.newArrayList("卡夫卡说过，孤独是一种享受。", "我安静下来，我的世界也安静下来。"),
//				"C:\\Users\\Administrator\\Desktop\\活法5.md");
		Txt2DocbookChapter.convert("当我放过自己的时候", "马德",
				Lists.newArrayList("卡夫卡说过，孤独是一种享受。", "我安静下来，我的世界也安静下来。"),
				"C:\\Users\\Administrator\\Desktop\\当我放过自己的时候.md");
	}
	
	public static void convert(String bookName, String authorName, List<String> dedicationParas, String mdFile) {
		List<String> originalList = FileUtil.readFile2List(mdFile);

		List<DocBookChapter> chapters = Lists.newArrayList();
		DocBookChapter chapter = null;

		List<DocBookSection> sections = Lists.newArrayList();
		DocBookSection section = null;

		List<String> paras = Lists.newArrayList();
		
		String chapterTitle = null;

		for (String line : originalList) {
			if (line == null || line.equals("")) {
				continue;
			}

			// 章
			if (!line.contains("###") && line.contains("##")) {
				chapterTitle = line.replace("## ", "");
				// 节
			} else if (line.contains("### ")) {
				section = new DocBookSection(line.replace("### ", ""));
				sections.add(section);
				if (paras != null && paras.size() > 0 && sections.size() > 1) {
					sections.get(sections.size() - 2).setParas(paras);
					paras = Lists.newArrayList();
				}
				// 段落
			} else {
				paras.add(line);
			}

		}

		chapter = new DocBookChapter(chapterTitle, sections);
		chapters.add(chapter);
		DocBook docBook = new DocBook(bookName, authorName, dedicationParas, chapters);
		DocBookXmlGenerator.makeBookXml(docBook, DOCBOOK_OUTPATH);
	}

}
