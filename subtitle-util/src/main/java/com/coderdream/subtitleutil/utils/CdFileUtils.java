package com.coderdream.subtitleutil.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.coderdream.subtitleutil.bean.ScriptEntity;
import com.coderdream.subtitleutil.bean.SubtitleBaseEntity;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;


/**
 * Java按一行一行进行文件的读取或写入 https://blog.csdn.net/yuanhaiwn/article/details/83090540
 *
 * @author CoderDream
 */
@Slf4j
public class CdFileUtils {

    public static void writeToFile(String fileName, String content) {
        try {
//            String[] arrs = {
//                    "zhangsan,23,福建",
//                    "lisi,30,上海",
//                    "wangwu,43,北京",
//                    "laolin,21,重庆",
//                    "ximenqing,67,贵州"
//            };
            String[] contentList = content.split(" ");
            //写入中文字符时解决中文乱码问题
            FileOutputStream fos = null;

//            fos = new FileOutputStream(new File("E:/phsftp/evdokey/evdokey_201103221556.txt"));

            fos = new FileOutputStream(fileName);

            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            BufferedWriter bw = new BufferedWriter(osw);
            //简写如下：
            //BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
            //        new FileOutputStream(new File("E:/phsftp/evdokey/evdokey_201103221556.txt")), "UTF-8"));

            for (String arr : contentList) {
                bw.write(arr + "\t\n");
            }

            //注意关闭的先后顺序，先打开的后关闭，后打开的先关闭
            bw.close();
            osw.close();
            fos.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void writeToFile(String fileName, List<String> contentList) {
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            //写入中文字符时解决中文乱码问题
            fos = new FileOutputStream(fileName);

            osw = new OutputStreamWriter(fos, "UTF-8");
            bw = new BufferedWriter(osw);
            int size = contentList.size();
            for (int i = 0; i < size; i++) {
                String str = contentList.get(i);
                //  str = "\uFEFF" + str; BOM格式，剪映不认识，Subindex 合并字幕时要打开
                // 如果不是最后一行，就加上回车换行
                if (i != size - 1) {
                    if (str != null) {
                        str = str.trim().replaceAll("  ", " ") + "\r\n";
                    }
                }

                if (str != null) {
                    bw.write(str);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            //注意关闭的先后顺序，先打开的后关闭，后打开的先关闭
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    public static List<String> readFileContent(String fileName) {
        List<String> stringList = new ArrayList<>();
        File file = new File(fileName);//定义一个file对象，用来初始化FileReader
        FileReader reader;//定义一个fileReader对象，用来初始化BufferedReader
        try {
            reader = new FileReader(file);
            BufferedReader bReader = new BufferedReader(reader);//new一个BufferedReader对象，将文件内容读取到缓存
//            StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
            String s = "";
            while ((s = bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
//                sb.append(s + "\n");//将读取的字符串添加换行符后累加存放在缓存中
                // 去掉 '\uFEFF' 65279
                s = DictUtils.specialUnicode(s);
                stringList.add(s.trim().replaceAll("\"", "'"));
//                System.out.println(s);
            }
            // 补空行
            stringList.add("");
            bReader.close();
//            String str = sb.toString();
//            System.out.println(str);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return stringList;
    }

    /**
     * 返回 D:\04_GitHub\java-architect-util\free-apps\src\main\resources //
     * https://blog.csdn.net/qq_38319289/article/details/115236819 // SpringBoot获取resources文件路径 // File directory = new
     * File("src/main/resources"); // String reportPath = directory.getCanonicalPath(); // String resource =reportPath +
     * "/static/template/resultTemplate.docx";
     *
     * @return 资源文件夹路径
     */
    public static String getResourceRealPath() {
        File directory = new File("src/main/resources");
        String reportPath = "";
        try {
            reportPath = directory.getCanonicalPath();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reportPath;
    }

    /**
     * 获取对话实体列表
     *
     * @param fileName 脚本位置
     * @return 对话实体列表
     */

    public static List<ScriptEntity> genScriptEntityList(String fileName) {
        List<String> stringList = new ArrayList<>();
        File file = new File(fileName);//定义一个file对象，用来初始化FileReader
        FileReader reader;//定义一个fileReader对象，用来初始化BufferedReader
        try {
            reader = new FileReader(file);
            BufferedReader bReader = new BufferedReader(reader);//new一个BufferedReader对象，将文件内容读取到缓存
            String s = "";
            while ((s = bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
                stringList.add(s.trim());
            }
            stringList.add("");// 补最后一行的空格
            bReader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<ScriptEntity> result = new ArrayList<>();

        ScriptEntity scriptEntity;
        if (CollectionUtil.isNotEmpty(stringList)) {
            int size = stringList.size();
            if (size % 3 != 0) {
                System.out.println("文件格式有问题，行数应该是3的倍数，实际为：" + size + "; fileName: " + fileName);
                return null;
            }

            for (int i = 0; i < stringList.size(); i += 3) {
                scriptEntity = new ScriptEntity();
                scriptEntity.setTalker(stringList.get(i));
                scriptEntity.setContent(stringList.get(i + 1));
                result.add(scriptEntity);
            }
        }

        return result;
    }

    public static List<SubtitleBaseEntity> readSrcFileContent(String... fileName) {
        List<String> stringList = new ArrayList<>();
        if (fileName == null) {
            return null;
        }
        File file = new File(fileName[0]);//定义一个file对象，用来初始化FileReader
        FileReader reader;//定义一个fileReader对象，用来初始化BufferedReader
        try {
            reader = new FileReader(file);
            BufferedReader bReader = new BufferedReader(reader);//new一个BufferedReader对象，将文件内容读取到缓存
//            StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
            String s = "";
            while ((s = bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
//                sb.append(s + "\n");//将读取的字符串添加换行符后累加存放在缓存中
                if (fileName.length == 0) {
                    s = s.replaceAll("“", "\"");

                    s = s.replaceAll("”", "\"");
                }

                stringList.add(processStr(s.trim()));
//                System.out.println(s);
            }
            stringList.add("");// 补最后一行的空格
            bReader.close();
//            String str = sb.toString();
//            System.out.println(str);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<SubtitleBaseEntity> result = new ArrayList<>();
        int firstSpaceIndex = 0;
        String subIndexStr = "";
        SubtitleBaseEntity subtitleBaseEntity;
        if (CollectionUtil.isNotEmpty(stringList)) {

            int size = stringList.size();
            if (size % 4 != 0 && StrUtil.isEmpty(stringList.get(size - 1)) && StrUtil.isEmpty(
                stringList.get(size - 2))) {
                stringList.remove(size - 1);
            }

            for (int i = 0; i < stringList.size(); i++) {
                if (StrUtil.isEmpty(stringList.get(i))) {
                    subtitleBaseEntity = new SubtitleBaseEntity();
                    if (firstSpaceIndex == 0) {
                        subIndexStr = stringList.get(0);
                        subtitleBaseEntity.setSubIndex(Integer.parseInt(processStr(subIndexStr)));
                        subtitleBaseEntity.setTimeStr(processStr(stringList.get(1)));
                        subtitleBaseEntity.setSubtitle(processStr(stringList.get(2)));
                    } else {
                        if (StrUtil.isNotEmpty(stringList.get(firstSpaceIndex))) {
                            subtitleBaseEntity.setSubIndex(
                                Integer.parseInt(processStr(stringList.get(firstSpaceIndex))));
                        }
                        subtitleBaseEntity.setTimeStr(processStr(stringList.get(firstSpaceIndex + 1)));
                        subtitleBaseEntity.setSubtitle(processStr(stringList.get(firstSpaceIndex + 2)));
                    }
                    firstSpaceIndex = i + 1;

                    result.add(subtitleBaseEntity);
                }
            }
        }

        return result;
    }

    public static final String UTF8_BOM = "\uFEFF";

    public static String processStr(String string) {
        if (string.startsWith(UTF8_BOM)) {
            return string = string.substring(1);
        }
        return string;
    }

    public static List<String> readFileAddEndEmptyList(String newFileName) {
        List<String> stringList = FileUtil.readLines(newFileName, "UTF-8");
        // 文本末尾补空行
        if (CollectionUtil.isNotEmpty(stringList) && StrUtil.isNotEmpty(stringList.get(stringList.size() - 1))) {
            stringList.add("");
        }

        return stringList;
    }
}
