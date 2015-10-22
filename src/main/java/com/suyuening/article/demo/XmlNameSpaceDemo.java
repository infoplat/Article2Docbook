package com.suyuening.article.demo;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.Text;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XmlNameSpaceDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		XmlNameSpaceDemo xx = new XmlNameSpaceDemo();
		xx.createXmlzzsfb();
		xx.testCreate();
		

	}

	public void createXmlzzsfb() {
		Namespace nt = Namespace.getNamespace("xi",
				"http://www.w3.org/2001/XInclude");
		
		Namespace namespace = Namespace.getNamespace("http://docbook.org/ns/docbook");
		
		Document document;
		Element root = new Element("taxML", namespace);
		
		root.addNamespaceDeclaration(nt);
		root.setAttribute("lang", "zh_cn", nt);// 这里用的是上边的 Namespace
		root.setAttribute("version", "5.0");
		document = new Document(root);
		Element rqq = new Element("rqQ", namespace);
		rqq.setText("20141101");
		root.addContent(rqq);
		XMLOutputter XMLOut = new XMLOutputter();
		Format f = Format.getPrettyFormat();
		f.setEncoding("gbk");
		XMLOut.setFormat(f);
		System.out.println(XMLOut.outputString(document));
//			XMLOut.output(document, new FileOutputStream(fileName));
//			System.out.println(fileName + "创建成功");
	}
	
	public void testCreate(){ 
	    try{ 
	      Document doc = new Document(); 
	        
	      Namespace ns = Namespace.getNamespace("http://www.bromon.org"); 
	      Namespace ns2 = Namespace.getNamespace("other", "http://www.w3c.org"); 
	        
	      Element root = new Element("根元素", ns); 
	      root.addNamespaceDeclaration(ns2); 
	      doc.setRootElement(root); 
	        
	      Element el1 = new Element("元素一"); 
	      el1.setAttribute("属性", "属性一"); 
	      Text text1 = new Text("元素值"); 
	        
	      Element em = new Element("元素二").addContent("第二个元素"); 
	      el1.addContent(text1); 
	      el1.addContent(em); 
	        
	      Element el2 = new Element("元素三").addContent("第三个元素"); 
	        
	      root.addContent(el1); 
	      root.addContent(el2); 
	        
	      XMLOutputter outputter = null; 
	      Format format = Format.getPrettyFormat();
	      format.setEncoding("GB2312"); 
	      format.setIndent("    "); 
	      outputter = new XMLOutputter(format); 
	        
//	      outputter.output(doc, new FileOutputStream("C:\\a.xml")); 
	      System.out.println(outputter.outputString(doc));
	    }catch(Exception e){ 
	      e.printStackTrace(); 
	    } 
	  } 
}
