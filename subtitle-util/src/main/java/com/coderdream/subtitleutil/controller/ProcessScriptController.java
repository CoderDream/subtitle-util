package com.coderdream.subtitleutil.controller;

import com.coderdream.subtitleutil.bean.Result;
import com.coderdream.subtitleutil.service.ProcessScriptService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CoderDream
 */
@Slf4j
@Tag(name = "后台管理")
//@Api(tags = "后台管理")
@RequestMapping("/admin")
@RestController
@RequiredArgsConstructor
public class ProcessScriptController {

    @Resource
    private ProcessScriptService processScriptService;

    @PostMapping("/selectList")
    Result<String > selectList(@RequestBody List<String> folderNameList) {
        processScriptService.processScriptDialogList(folderNameList);
        String result = "success";
        return Result.ok(result);
    }
}
