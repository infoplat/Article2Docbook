package com.suyuening.article.bean;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DocBook {
    private String bookName;
    private String authorName;
    private List<String> dedicationParas;
    private List<DocBookChapter> chapters;
}
