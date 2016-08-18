package com.suyuening.article.bean;

import java.util.List;

import com.google.common.collect.Lists;

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

	public DocBookChapter(String title, List<DocBookSection> sections) {
		super();
		this.title = title;
		this.sections = sections;
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

	public List<DocBookSection> getSections() {
		return sections;
	}

	public void setSections(List<DocBookSection> sections) {
		this.sections = sections;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
