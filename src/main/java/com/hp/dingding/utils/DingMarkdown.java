package com.hp.dingding.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 符合钉钉规范的markdown builder
 * 钉钉不让空行, 暂时没想到其他办法，用了一个点隔开一行，这个点在钉钉主题是浅色的时候和背景相同
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

    private static final String DING_MSG_BACKGROUND_COLOR = "#FFFFFF";
    private final List<String> fullContent;

    public DingMarkdown(DingMarkdownBuilder builder) {
        fullContent = builder.fullContent;
    }

    public static DingMarkdownBuilder builder() {
        return new DingMarkdownBuilder();
    }

    public static final class DingMarkdownBuilder {
        List<String> fullContent = new ArrayList<>();

        public DingMarkdownBuilder contentTitle(String contentTitle) {
            fullContent.add(contentTitle);
            return this;
        }

        public DingMarkdownBuilder level1Title(String level1Title) {
            fullContent.add("# " + level1Title);
            return this;
        }

        public DingMarkdownBuilder level2Title(String level2Title) {
            fullContent.add("## " + level2Title);
            return this;
        }

        public DingMarkdownBuilder level3Title(String level3Title) {
            fullContent.add("### " + level3Title);
            return this;
        }

        public DingMarkdownBuilder level4Title(String level4Title) {
            fullContent.add("#### " + level4Title);
            return this;
        }

        public DingMarkdownBuilder level5Title(String level5Title) {
            fullContent.add("##### " + level5Title);
            return this;
        }

        public DingMarkdownBuilder level6Title(String level6Title) {
            fullContent.add("###### " + level6Title);
            return this;
        }

        public DingMarkdownBuilder reference(String reference) {
            fullContent.add("> " + reference);
            return this;
        }

        public DingMarkdownBuilder text(String text) {
            fullContent.add(text);
            return this;
        }

        public Font textWithFont(String text) {
            return new Font(this, text);
        }

        public DingMarkdownBuilder boldText(String boldText) {
            fullContent.add("**" + boldText + "**");
            return this;
        }

        public DingMarkdownBuilder italicText(String italicText) {
            fullContent.add("*" + italicText + "*");
            return this;
        }

        public DingMarkdownBuilder link(String name, String url) {
            fullContent.add("[" + name + "](" + url + ")");
            return this;
        }

        public DingMarkdownBuilder image(String url) {
            fullContent.add("![](" + url + ")");
            return this;
        }

        public DingMarkdownBuilder disorderedList(String... element) {
            if (element.length > 0) {
                final String elements = Arrays.stream(element).map(i -> "- " + i).collect(Collectors.joining("  \n  "));
                fullContent.add(elements);
            }
            return this;
        }

        public DingMarkdownBuilder orderedList(String... element) {
            if (element.length > 0) {
                StringBuilder strBuilder = new StringBuilder();
                for (int i = 0; i < element.length; i++) {
                    strBuilder.append(i).append(1).append(". ").append(element[i]).append("  \n  ");
                }
                fullContent.add(strBuilder.toString());
            }
            return this;
        }

        public DingMarkdownBuilder newLine() {
            return this
                    .textWithFont("new-line")
                    .color(DING_MSG_BACKGROUND_COLOR)
                    .face("monospace")
                    .builder();
        }

        public String build() {
            final DingMarkdown dingMarkdown = new DingMarkdown(this);
            return String.join("  \n  ", dingMarkdown.fullContent);
        }
    }


    public static class Font {

        private String temp = "<font face=\"{face}\" color=\"{color}\">{text}</font>";
        private final DingMarkdownBuilder builder;


        public Font(DingMarkdownBuilder builder, String text) {
            this.builder = builder;
            format("text", text);
        }

        public Font color(String hexColor) {
            format("color", StringUtils.isEmpty(hexColor) ? "#FFFFFF" : hexColor);
            return this;
        }

        public Font face(String fontFace) {
            format("face", StringUtils.isNotEmpty(fontFace) ? fontFace : "monospace");
            return this;
        }

        public DingMarkdownBuilder builder() {
            correctTemp();
            builder.fullContent.add(temp);
            return builder;
        }

        private void correctTemp() {
            temp = temp.replace("{face}", "monospace")
                    .replace("{color}", "#FFFFFF");
        }

        private void format(String mark, String replacement) {
            temp = temp.replace("{" + mark + "}", replacement);
        }
    }
}
