package com.suyuening.article.bean;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DocBook {

    /** 书名 */
    private String               bookName;
    /** 作者名 */
    private String               authorName;
    /** 题词段落 */
    private List<String>         dedicationParas;
    /** 章节 */
    private List<DocBookChapter> chapters;
}
