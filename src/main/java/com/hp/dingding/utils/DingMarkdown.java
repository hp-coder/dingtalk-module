package com.hp.dingding.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hp
 */
public class DingMarkdown {

    /*
            标题
        # 一级标题
        ## 二级标题
        ### 三级标题
        #### 四级标题
        ##### 五级标题
        ###### 六级标题

        引用
        > A man who stands for nothing will fall for anything.

        文字加粗、斜体
        **bold**
        *italic*

        链接
        [this is a link](https://www.dingtalk.com/)

        图片
        ![](http://name.com/pic.jpg)

        无序列表
        - item1
        - item2

        有序列表
        1. item1
        2. item2

        换行(建议\n前后各添加两个空格)
          \n
     */

    private final List<String> fullContent;

    public DingMarkdown(Builder builder) {
        fullContent = builder.fullContent;
    }

    public static DingMarkdown.Builder builder(){
        return new DingMarkdown.Builder();
    }

    public static final class Builder {
        List<String> fullContent = new ArrayList<>();

        public Builder contentTitle(String contentTitle) {
            fullContent.add(contentTitle);
            return this;
        }

        public Builder level1Title(String level1Title) {
            fullContent.add("# " + level1Title);
            return this;
        }

        public Builder level2Title(String level2Title) {
            fullContent.add("## " + level2Title);
            return this;
        }

        public Builder level3Title(String level3Title) {
            fullContent.add("### " + level3Title);
            return this;
        }

        public Builder level4Title(String level4Title) {
            fullContent.add("#### " + level4Title);
            return this;
        }

        public Builder level5Title(String level5Title) {
            fullContent.add("##### " + level5Title);
            return this;
        }

        public Builder level6Title(String level6Title) {
            fullContent.add("###### " + level6Title);
            return this;
        }

        public Builder reference(String reference) {
            fullContent.add("> " + reference);
            return this;
        }

        public Builder text(String text) {
            fullContent.add(text);
            return this;
        }

        public Builder boldText(String boldText) {
            fullContent.add("**" + boldText + "**");
            return this;
        }

        public Builder italicText(String italicText) {
            fullContent.add("*" + italicText + "*");
            return this;
        }

        public Builder link(String name,String url) {
            fullContent.add("["+name+"](" + url + ")");
            return this;
        }

        public Builder image(String url) {
            fullContent.add("![](" + url + ")");
            return this;
        }

        public Builder disorderedList(String... element) {
            if (element.length > 0) {
                final String elements = Arrays.stream(element).map(i -> "- " + i).collect(Collectors.joining("  \n  "));
                fullContent.add(elements);
            }
            return this;
        }

        public Builder orderedList(String... element) {
            if (element.length > 0) {
                StringBuilder strBuilder = new StringBuilder();
                for (int i = 0; i < element.length; i++) {
                    strBuilder.append(i).append(1).append(". ").append(element[i]).append("  \n  ");
                }
                fullContent.add(strBuilder.toString());
            }
            return this;
        }

        public String build() {
            final DingMarkdown dingMarkdown = new DingMarkdown(this);
            return String.join("  \n  ", dingMarkdown.fullContent);
        }
    }
}
