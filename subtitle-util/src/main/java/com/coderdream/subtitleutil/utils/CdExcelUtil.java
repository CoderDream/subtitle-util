package com.coderdream.subtitleutil.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.coderdream.subtitleutil.bean.AbbrevComplete;
import com.coderdream.subtitleutil.bean.Talker;
import com.coderdream.subtitleutil.bean.WordEntity;
import com.coderdream.subtitleutil.bean.WordInfo;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author CoderDream
 */
@Slf4j
public class CdExcelUtil {
    public static List<String> genHostList(String filePath) {
//        String fileName = File.separator + path + File.separator + dateStr + ".xlsx";
        ExcelReader reader = ExcelUtil.getReader(FileUtil.file(filePath), "Sheet1");
        // 应用ID	应用名称	投票数	美区标志	中文简介	英文简介	简介	字幕	历史价格	今日价格
        reader.addHeaderAlias("host", "host");
//        List<RecommendApp> recommendAppList = reader.read(1, 1, RecommendApp.class);
        List<Talker> myHostList = reader.readAll(Talker.class);
        reader.close();

        List<String> stringList = myHostList.stream().map(Talker::getTalker).collect(Collectors.toList());

        return stringList;
    }

    public static List<AbbrevComplete> genAbbrevCompleteList(String filePath) {
        ExcelReader reader = ExcelUtil.getReader(FileUtil.file(filePath), "Sheet1");
        reader.addHeaderAlias("abbrev", "abbrev");
        reader.addHeaderAlias("complete", "complete");
        List<AbbrevComplete> abbrevCompleteList = reader.readAll(AbbrevComplete.class);
        for (AbbrevComplete abbrevComplete : abbrevCompleteList) {
            abbrevComplete.setAbbrev(abbrevComplete.getAbbrev().toLowerCase());
            abbrevComplete.setComplete(abbrevComplete.getComplete().toLowerCase());
        }

        reader.close();

        return abbrevCompleteList;
    }

    /**
     * @return 单词列表
     */
    public static List<WordEntity> genWordEntityList(String filePath, String sheetName) {
//        String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
//        String path = BaseUtils.getPath();
//        String fileName = File.separator + path + File.separator + dateStr + ".xlsx";
//        ExcelReader reader = ExcelUtil.getReader(FileUtil.file(fileName), "Sheet1");
        ExcelReader reader = ExcelUtil.getReader(FileUtil.file(filePath), sheetName);
        // 单词	英音	美音	释义	等级
        reader.addHeaderAlias("单词", "word");
        reader.addHeaderAlias("英音", "uk");
        reader.addHeaderAlias("美音", "us");
        reader.addHeaderAlias("释义", "comment");
        reader.addHeaderAlias("等级", "level");
        List<WordEntity> recommendAppList = reader.readAll(WordEntity.class);
        for (WordEntity wordEntity : recommendAppList) {
            wordEntity.setWord(wordEntity.getWord().toLowerCase());
            wordEntity.setComment(wordEntity.getComment().replaceAll("\n", ";"));
        }
        reader.close();

        return recommendAppList;
    }


    public static List<WordInfo> genWordInfoList(String filePath, String sheetName) {
//        String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
//        String path = BaseUtils.getPath();
//        String fileName = File.separator + path + File.separator + dateStr + ".xlsx";
//        ExcelReader reader = ExcelUtil.getReader(FileUtil.file(fileName), "Sheet1");
        ExcelReader reader = ExcelUtil.getReader(FileUtil.file(filePath), sheetName);
        // 单词	英音	美音	释义	等级
        reader.addHeaderAlias("单词", "word");
        reader.addHeaderAlias("英音", "uk");
        reader.addHeaderAlias("美音", "us");
        reader.addHeaderAlias("释义", "comment");
        reader.addHeaderAlias("等级", "level");
        reader.addHeaderAlias("次数", "times");
        List<WordInfo> recommendAppList = reader.readAll(WordInfo.class);
        for (WordInfo wordInfo : recommendAppList) {
            wordInfo.setWord(wordInfo.getWord().toLowerCase());
            wordInfo.setComment(wordInfo.getComment().replaceAll("\n", ";"));
            wordInfo.setLevelStr(wordInfo.getLevel());
        }
        reader.close();

        return recommendAppList;
    }
}
