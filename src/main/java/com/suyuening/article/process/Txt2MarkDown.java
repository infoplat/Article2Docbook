package com.suyuening.article.process;

import java.util.List;

import com.google.common.collect.Lists;
import com.suyuening.article.util.FileUtil;

/**
 * 稻盛和夫《活法》文本转换成MD格式
 * 
 * @author Administrator
 *
 */
public class Txt2MarkDown {

	private static final List<String> IGNORE_WORD_LIST = Lists.newArrayList("7/21/2009", "Page", "稻盛和夫");

	public static void main(String[] args) {
		List<String> originalList = FileUtil.readFile2List("C:\\Users\\Administrator\\Desktop\\活法_原格式.txt");

		List<String> editedList = Lists.newArrayList();

		StringBuilder param = new StringBuilder();
		outer: for (String line : originalList) {
			if (line == null || line.equals("")) {
				continue;
			}
			for (String word : IGNORE_WORD_LIST) {
				if (line.contains(word)) {
					continue outer;
				}
			}

			// 章
			if (line.contains("B第") || line.contains("0B 前面的话")) {
				addParam(editedList, param);
				addContent(editedList, String.format("## %s", line.split("B")[1].trim()));
				param = new StringBuilder();
				// 节
			} else if (line.contains("B ") && !line.contains("IBM")
					&& Integer.valueOf(line.trim().split("B")[0]) >= 6) {
				addParam(editedList, param);
				addContent(editedList, String.format("### %s", line.split("B")[1].trim()));
				param = new StringBuilder();
				// 段落
			} else if (line.startsWith(" ")) {
				addParam(editedList, param);
				param = new StringBuilder();
				param.append(line.trim());
			} else {
				param.append(line.trim());
			}

		}

		FileUtil.writeFileFromList(editedList, "C:\\Users\\Administrator\\Desktop\\活法_格式化后.md");
	}

	private static void addParam(List<String> editedList, StringBuilder param) {
		if (param != null && !param.toString().equals("")) {
			addContent(editedList, param.toString());
		}
	}

	private static void addContent(List<String> editedList, String content) {
		editedList.add(content);
		editedList.add("");
	}
}
