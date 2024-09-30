package com.coderdream.subtitleutil.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author CoderDream
 */
public class ExecutePythonCommand {

    public static void main(String[] args) {
        String pythonCommand = "python -c \"print('Hello, World!')\""; // Python命令
        // python -m aeneas.tools.execute_task audio.mp3 script_dialog_new.txt "task_language=eng|os_task_file_format=srt|is_text_type=plain" eng_raw.srt

        String path = "D:\\14_LearnEnglish\\6MinuteEnglish\\2019\\191010\\";

        pythonCommand = "python -m aeneas.tools.execute_task " + path + "audio.mp3 " + path
            + "script_dialog_new.txt \"task_language=eng|os_task_file_format=srt|is_text_type=plain\" " + path
            + "eng_raw.srt";

        try {
            Process process = Runtime.getRuntime().exec(pythonCommand);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = process.waitFor();
            System.out.println("Exited with error code : " + exitCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
