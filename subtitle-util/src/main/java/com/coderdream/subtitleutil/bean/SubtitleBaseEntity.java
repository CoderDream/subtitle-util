package com.coderdream.subtitleutil.bean;


import java.io.Serializable;
import lombok.Data;

/**
 * @author CoderDream
 * @TableName t_subtitle
 */
@Data
public class SubtitleBaseEntity implements Serializable {

    /**
     * 字幕序号
     */
    private Integer subIndex;

    /**
     * 时间字符串 00:00:50,280 --> 00:00:52,800
     */
    private String timeStr;

    /**
     * 第一字幕内容
     */
    private String subtitle;

    /**
     * 第二字幕内容
     */
    private String subtitleSecond;

//    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(subIndex + "\n");
        sb.append(timeStr + "\n");
        sb.append(subtitle + "\n");
        sb.append(subtitleSecond + "\n");
        return sb.toString();
    }
}
