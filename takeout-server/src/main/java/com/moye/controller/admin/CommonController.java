package com.moye.controller.admin;

import com.moye.constant.MessageConstant;
import com.moye.properties.JwtProperties;
import com.moye.result.Result;
import com.moye.utils.QiNiuOssUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/admin/common")
@Tag(name = "文件上传", description = "文件上传")
public class CommonController {

    @Autowired
    private QiNiuOssUtil qiNiuOssUtil;


    @PostMapping("/upload")
    @Operation(summary = "文件上传", description = "文件上传")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传：{}", file);
        try {

            String originalFilename = file.getOriginalFilename();
            String substring = originalFilename.substring(originalFilename.lastIndexOf("."));

            String objectName = UUID.randomUUID().toString() + substring;

            //文件的请求路径
            String filePath = qiNiuOssUtil.upload1(file.getBytes(), objectName);

            return Result.success(filePath);
        } catch (IOException e) {
            log.info("文件上传失败：{}", e.getMessage());
        }

        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
