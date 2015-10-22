package com.suyuening.article.bean;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import com.google.common.collect.Lists;

@Getter
@Setter
@AllArgsConstructor
public class DocBookChapter {
	/** 章节标题 */
	private String title;
	/** 章节所有文章 */
	private List<DocBookSection> sections;

	public DocBookChapter(String title) {
		super();
		this.title = title;
		sections = Lists.newArrayList();
	}

	public String getTitle() {
		return title;
	}

	public void addSection(DocBookSection section) {
		if (sections == null) {
			sections = Lists.newArrayList();
		}
		sections.add(section);
	}

	public void addSections(List<DocBookSection> sectionList) {
		for (DocBookSection docBookSection : sectionList) {
			sections.add(docBookSection);
		}
	}

}
