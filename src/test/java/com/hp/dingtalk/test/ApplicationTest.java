package com.hp.dingtalk.test;

/**
 * @author hp 2023/3/17
 */
public class ApplicationTest {

    public static void main(String[] args) {
        final TestBot testBot = new TestBot();
        System.out.println(testBot.getAppId());
        System.out.println(testBot.toString());
    }

}
