package com.coderdream.subtitleutil.utils;


import com.coderdream.subtitleutil.bean.WordInfo;
import java.io.File;
import java.util.List;

/**
 * @author CoderDream
 */
public class AdvancedWordUtil {

    public static void main(String[] args) {
        String folderName = "230223";
        AdvancedWordUtil.genCoreWordTable(folderName, "A");
    }

    public static void genCoreWordTable(String folderName, String templateType) {
        List<WordInfo> wordInfoList = getAdvancedWordList(folderName);
        String folderPath =
            CdFileUtils.getResourceRealPath() + File.separatorChar + "data" + File.separatorChar + "dict";
        String templateFileName = folderPath + File.separator + "高级词汇表.xlsx";

        if("A".equals(templateType)) {
            templateFileName = folderPath + File.separator + "高级词汇表 - 联想.xlsx";
        } else if("B".equals(templateType)) {
            templateFileName = folderPath + File.separator + "高级词汇表 - 曲面屏.xlsx";
        } else {
            templateFileName = folderPath + File.separator + "高级词汇表 - 戴尔.xlsx";
        }

        //

        // WordInfo

        // 方案1 一下子全部放到内存里面 并填充
        String excelFileName = CommonUtil.getFullPathFileName(folderName, folderName, "_高级词汇表.xlsx");
        String sheetName = "四六级及以上";

        MakeExcel.fillWordEntityList(templateFileName, excelFileName, sheetName, wordInfoList);
    }

    /**
     *
     * @return
     */
    public static List<WordInfo> getAdvancedWordList(String folderName) {
        String fileName = folderName + "_完整词汇表";
        String filePath = CommonUtil.getFullPathFileName(folderName, fileName, ".xlsx");
        List<WordInfo> wordEntityList = CdExcelUtil.genWordInfoList(filePath, "四六级及以上");

        return wordEntityList;
    }

}
