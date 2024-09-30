package com.coderdream.subtitleutil.service;

import cn.hutool.core.util.StrUtil;
import com.coderdream.subtitleutil.utils.CdFileUtils;
import com.coderdream.subtitleutil.utils.CommonUtil;
import com.coderdream.subtitleutil.utils.ProcessScriptUtil;
import com.coderdream.subtitleutil.utils.TranslatorTextUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.coderdream.subtitleutil.utils.TranslateUtil;

/**
 * @author CoderDream
 */
public class TranslateService {


    /**
     * 翻译脚本
     *
     * @param folderName
     */
    public static void process(String folderName) {
        String fileName = "script_dialog";
        String srcFileName = CommonUtil.getFullPathFileName(folderName, fileName, ".txt");
        List<String> stringList = CdFileUtils.readFileContent(srcFileName);

        String text = stringList.stream().map(String::valueOf).collect(Collectors.joining("\r\n"));
        System.out.println("text:  " + text);
        List<String> stringListCn = TranslatorTextUtil.translatorText(text);

        List<String> newList = new ArrayList<>();
        for (int i = 0; i < stringListCn.size(); i++) {
            String temp = stringListCn.get(i);
            String[] arr = temp.split("\r\n");
            for (int j = 0; j < arr.length; j++) {
                TranslateUtil.upgradeTranslate(folderName, arr, j, stringList);

                System.out.println(arr[j]);
                newList.add(arr[j]);
                // 如果最后一行不是空格，则补一个空白字符串
                if (j == arr.length - 1 && StrUtil.isNotEmpty(arr[j])) {
                    newList.add("");
                }
            }
        }

        String srcFileNameCn = CommonUtil.getFullPathFileName(folderName, fileName, "_cn.txt");
        // 写中文翻译文本
        CdFileUtils.writeToFile(srcFileNameCn, newList);
    }

    /**
     * @param folderNameList
     */
    public void processScriptDialogList(List<String> folderNameList) {
        for (String folderName : folderNameList) {
            ProcessScriptUtil.process(folderName);
        }
    }
}
