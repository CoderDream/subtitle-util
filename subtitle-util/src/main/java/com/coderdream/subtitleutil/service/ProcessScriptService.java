package com.coderdream.subtitleutil.service;

import com.coderdream.subtitleutil.utils.ProcessScriptUtil;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author CoderDream
 */
@Service
public class ProcessScriptService {

    /**
     *
     * @param folderName
     */
    public void processScriptDialog(String folderName) {
        ProcessScriptUtil.process(folderName);
    }

    /**
     *
     * @param folderNameList
     */
    public void processScriptDialogList(List<String> folderNameList) {
        for (String folderName : folderNameList) {
            ProcessScriptUtil.process(folderName);
        }
    }
}
