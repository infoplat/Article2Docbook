package com.suyuening.article.bean;

import java.util.List;

public class DocBook {
	private String bookName;
	private String authorName;
	private List<String> dedicationParas;
	private List<DocBookChapter> chapters;

	public DocBook(String bookName, String authorName,
			List<String> dedicationParas, List<DocBookChapter> chapters) {
		super();
		this.bookName = bookName;
		this.authorName = authorName;
		this.dedicationParas = dedicationParas;
		this.chapters = chapters;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public List<String> getDedicationParas() {
		return dedicationParas;
	}

	public void setDedicationParas(List<String> dedicationParas) {
		this.dedicationParas = dedicationParas;
	}

	public List<DocBookChapter> getChapters() {
		return chapters;
	}

	public void setChapters(List<DocBookChapter> chapters) {
		this.chapters = chapters;
	}

}
