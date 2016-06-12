package com.suyuening.article.bean;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

@Getter
@Setter
@AllArgsConstructor
public class DocBookSection {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocBookSection.class);
    
    /** 文章标题 */
    private String       title;
    /** 文章所有段落 */
    private List<String> paras;

    public DocBookSection(String title){
        super();
        this.title = title;
        paras = Lists.newArrayList();
    }

    /**
     * 添加一个段落到文章
     * 
     * @param para
     */
    public void addPara(String para) {
        if (paras == null) {
            paras = Lists.newArrayList();
        }
        paras.add(para);
    }

    /**
     * 一次添加多个段落到文章
     * @param paraList 多个段落
     */
    public void addMutiParas(List<String> paraList) {
        if (paraList == null || paraList.size() == 0) {
            return;
        }
        if (paras == null) {
            paras = Lists.newArrayList();
        }
        paras.addAll(paraList);
    }

    public void printInfo() {
        LOGGER.info("title={}", title);
        int i = 1;
        for (String para : paras) {
            LOGGER.info("para[{}]={}", i++, para);
        }
    }

}
