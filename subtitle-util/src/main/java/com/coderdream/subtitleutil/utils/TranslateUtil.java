package com.coderdream.subtitleutil.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;


import com.coderdream.subtitleutil.bean.ScriptEntity;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/**
 * @author CoderDream
 */
public class TranslateUtil {

    /**
     * 优化句子
     *
     * @param folderName
     * @param arr
     * @param j
     */
    public static void upgradeTranslate(String folderName, String[] arr, int j, List<String> subtitleList) {
        if ("抢".equals(arr[j])) {
            arr[j] = "罗伯";
        }
        if ("山 姆".equals(arr[j])) {
            arr[j] = "山姆";
        }
        if ("井".equals(arr[j])) {
            arr[j] = "嗯";
        }
        if ("右".equals(arr[j])) {
            arr[j] = "是的";
        }

        if ("右！".equals(arr[j])) {
            arr[j] = "好的！";
        }

        // 医 管 局！
        // （Michael Collins） 去掉大括号及大括号内的内容
        arr[j] = removeEnContent(arr[j]);
        arr[j] = arr[j].replaceAll(" 6 Minute English ", "六分钟英语");
        arr[j] = arr[j].replaceAll("医 管 局", "哈哈");
        arr[j] = arr[j].replaceAll("乔吉", "乔治");
        arr[j] = arr[j].replaceAll("成语", "谚语");
        arr[j] = arr[j].replaceAll("Rob", "罗伯");
        arr[j] = arr[j].replaceAll("伟大！", "太好了！");
        arr[j] = arr[j].replaceAll("右。", "好的。"); // Right应该翻译成好的，而不是右
        arr[j] = arr[j].replaceAll("山 姆", "山姆");
        arr[j] = arr[j].replaceAll("6分钟", "六分钟");
        arr[j] = arr[j].replaceAll(" 6 分钟", "六分钟");
        arr[j] = arr[j].replaceAll(";", "；");
        arr[j] = arr[j].replaceAll("“拯救大象”（Save the Elephants）", "“拯救大象”");
        arr[j] = arr[j].replaceAll("六分钟又到了", "六分钟时间又到了"); // 英国广播公司（BBC）
        arr[j] = arr[j].replaceAll("英国广播公司（BBC）", "英国广播公司"); // 英国广播公司（BBC）

        arr[j] = arr[j].replaceAll("——", " —— ");// ——
        if (-1 != arr[j].lastIndexOf("程序") && -1 != subtitleList.get(j).lastIndexOf("programme")) {
            arr[j] = arr[j].replaceAll("程序", "节目");
        }
        if (-1 != arr[j].lastIndexOf("课程") && -1 != subtitleList.get(j).lastIndexOf("programme")) {
            arr[j] = arr[j].replaceAll("课程", "节目");
        }

        // 维克托
        arr[j] = arr[j].replaceAll("Victor;", "维克多");

        arr[j] = arr[j].trim();//去掉前后空格
        if ("231026".equals(folderName)) {
            arr[j] = arr[j].replaceAll("一百个", "一百岁");
        }

        if ("231109".equals(folderName)) {
            if (arr[j].equals("幸运")) {
                arr[j] = arr[j].replaceAll("幸运", "幸运的是");
            }
        }

        // 针对230413的AI翻译优化
        if ("230413".equals(folderName)) {
            arr[j] = arr[j].replaceAll("垃圾场", "情绪低落");
            arr[j] = arr[j].replaceAll("垃圾堆", "情绪低落");
            arr[j] = arr[j].replaceAll("最尖锐的", "极度");
            arr[j] = arr[j].replaceAll(" Covid", "冠状病毒");
            arr[j] = arr[j].replaceAll("Covid", "冠状病毒");
            arr[j] = arr[j].replaceAll("英国广播公司（BBC）", "BBC");//英国广播公司（BBC）
            arr[j] = arr[j].replaceAll("《纪录片》（The Documentary）", "《纪录片》");//《纪录片》（The Documentary）
            arr[j] = arr[j].replaceAll("海伦·罗素（Helen Russell）", "海伦·罗素");// 海伦·罗素（Helen Russell）
            arr[j] = arr[j].replaceAll("托马斯·迪克森（Thomas Dixon）", "托马斯·迪克森");// 托马斯·迪克森（Thomas Dixon）
        }
        // 针对230302的AI翻译优化
        if ("230302".equals(folderName)) {
            arr[j] = arr[j].replaceAll("Rob", "'dunk'");
            arr[j] = arr[j].replaceAll("扣篮", "'dunk'");
            arr[j] = arr[j].replaceAll("英国广播公司（BBC）", "BBC");//英国广播公司（BBC）
            arr[j] = arr[j].replaceAll("迈克尔·罗森（Michael Rosen）", "迈克尔·罗森");
            arr[j] = arr[j].replaceAll("朱莉·塞迪维（Julie Sedivy）", "朱莉·塞迪维");
            arr[j] = arr[j].replaceAll("计划", "节目");
        }

        //
        // 针对230330的AI翻译优化
        if ("230330".equals(folderName)) {
            arr[j] = arr[j].replaceAll("历克斯·米尔克（Alex Mielke）", "历克斯·米尔克");
        }
    }


    /**
     * @param str
     * @return
     */
    @NotNull
    private static String removeEnContent(String str) {
        do {
            int startIndex = str.lastIndexOf("（");
            int endIndex = str.lastIndexOf("）");
            if (startIndex != -1 && endIndex != -1) {
                try {
                    if (startIndex >= 0 && endIndex > 0) {
                        str = str.replaceAll(str.substring(startIndex, endIndex + 1), "");
                    } else {
                        System.out.println(
                            "#####x##### ERROR: startIndex is " + startIndex + "; endIndex is " + endIndex + "; str "
                                + str);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("### str " + str);
                }
            }
        } while (str.contains("（") && str.contains("）"));
        return str;
    }
}
