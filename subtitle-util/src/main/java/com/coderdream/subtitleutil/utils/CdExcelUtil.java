package com.coderdream.subtitleutil.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.coderdream.subtitleutil.bean.Talker;
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


}
