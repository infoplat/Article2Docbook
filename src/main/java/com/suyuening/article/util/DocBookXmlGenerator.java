package com.suyuening.article.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jsoup.helper.StringUtil;

import com.google.common.collect.Lists;
import com.suyuening.article.bean.DocBook;
import com.suyuening.article.bean.DocBookChapter;
import com.suyuening.article.bean.DocBookSection;

public final class DocBookXmlGenerator {
    
    private DocBookXmlGenerator() {
	}

    public static void makeBookXml(DocBook docBook, String outPath) { 
		Element book = createBook(docBook);
		
		String bookName = docBook.getBookName();
		String fullPath = getBookFullPath(bookName, outPath);
        writeXml(book, fullPath);
        BookFixUtil.fixBookXml(fullPath);
        
        int index = 1;
        Element chapter = null;
        String chapterName = null;
        for (DocBookChapter docBookChapter : docBook.getChapters()) {
        	chapter = createChapter(docBookChapter);
        	chapterName = getChapterFullPath(bookName, outPath, index++);
        	writeXml(chapter, chapterName);
        	BookFixUtil.fixChapterXml(chapterName);
		}
    }

    private static String getBookFullPath(String bookName, String outPath) {
    	return getFullPath(bookName, outPath);
    }
    private static String getChapterFullPath(String chapterName, String outPath, int index) {
    	String fileName = String.format("%s_%d", chapterName, index);
    	return getFullPath(fileName, outPath);
    }
	private static String getFullPath(String fileName, String outPath) {
		if (StringUtil.isBlank(outPath)) {
			System.out.println("File Path is't exists!");
		}

		String separator = File.separator;
		if (!outPath.endsWith(separator)) {
			outPath = String.format("%s%s", outPath, separator);
		}
		
		return String.format("%s%s.xml", outPath, fileName);
	}
    private static Element createBook(DocBook docBook) {
    	Element book = new Element("book");
    	book.setAttribute("version", "5.0");
    	book.addContent(createTitle(docBook.getBookName()));
    	book.addContent(createInfo(docBook.getAuthorName()));
    	Element dedication = createDedication(docBook.getDedicationParas());
    	book.addContent(dedication);

    	String fileName = null;
		for (int i = 0; i < docBook.getChapters().size(); i++) {
			fileName = String.format("%s_%d.xml", docBook.getBookName(), i+1);
			book.addContent(createInclude(fileName));
		}

    	return book;
    }
    
    private static Element createChapter(DocBookChapter chapter) {
    	Element e = new Element("chapter");
    	e.setAttribute("version", "5.0");
    	e.addContent(createTitle(chapter.getTitle()));

    	for (DocBookSection section : chapter.getSections()) {
    		e.addContent(createSection(section));
		}
    	return e;
    }

    private static Element createInfo(String personName) {
    	Element info = new Element("info");
        Element author = new Element("author");
        Element personname = new Element("personname").setText(personName);
        Element orgname = new Element("orgname").setText(personName);
        
        author.addContent(personname);
        info.addContent(author);
        info.addContent(orgname);
        
        return info;
    }
    
    private static Element createDedication(List<String> dedicationParas) {
    	Element dedication = new Element("dedication");
    	for (String para : dedicationParas) {
    		dedication.addContent(createPara(para));
		}
    	return dedication;
    }
    
    private static Element createInclude(String chapterFileName) {
    	Element include = new Element("include");
    	include.setAttribute("href", chapterFileName);
    	return include;
    }

    private static Element createTitle(String title) {
    	Element e = new Element("title").setText(title);
    	return e;
    }

    private static Element createSection(String title, List<String> paras) {
    	Element e = new Element("section");
    	e.addContent(createTitle(title));
    	for (String para : paras) {
			e.addContent(createPara(para));
		}
    	return e;
    }

    private static Element createSection(DocBookSection section) {
    	return createSection(section.getTitle(), section.getParas());
    }

    private static Element createPara(String para) {
    	return new Element("para").setText(para);
    }
    
	private static void writeXml(Element root, String outFile) {
		// 将根节点添加到文档中；
		Document doc = new Document(root);
		// 使xml文件缩进效果
		Format format = Format.getPrettyFormat();
		// 输出xml文件；
		XMLOutputter XMLOut = new XMLOutputter(format);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(outFile);
			XMLOut.output(doc, out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

    public static void main(String[] args) {
    	String bookName = "苏岳宁博客";
		String authorName = "苏岳宁";
		List<String> dedicationParas = Lists.newArrayList("小苏是我", "我是小苏");
		
		String sectionTitle = "上品生活不着痕迹";
    	List<String> paras = Lists.newArrayList(
    			"我一直不太理解父亲。"
    			,"卿自西风古寺肋，淹滞青燐；落日荒丘，零星白骨。楸榆飒飒，蓬艾萧萧。隔雾圹以啼猿，绕烟塍闻泣鬼。自为红绡帐里愁，女儿情深，始信黄土垄中，女儿命薄！汝南血泪，斑斑洒向西风；梓泽余衷，默默诉凭冷月。"
    	);

    	List<DocBookSection> sections = Lists.newArrayList();
    	sections.add(new DocBookSection(sectionTitle, paras));
    	sections.add(new DocBookSection(sectionTitle, paras));
    	
		List<DocBookChapter> chapters = Lists.newArrayList(new DocBookChapter("散文卷一", sections), new DocBookChapter("散文卷二", sections));
		DocBook docBook = new DocBook(bookName, authorName, dedicationParas, chapters);
		
		System.out.println("正在生成xml 文件...");    
		DocBookXmlGenerator.makeBookXml(docBook, "E:\\docbook\\bookxml");
		System.out.println("正在生成xml 文件 完成");    
    }  
}