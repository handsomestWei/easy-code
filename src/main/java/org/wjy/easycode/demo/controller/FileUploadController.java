package org.wjy.easycode.demo.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

/**
 * 文件上传
 */
@Slf4j
@RestController("/api/v1/file")
public class FileUploadController {
    // 使用spring.servlet.multipart限制上传文件大小。
    // multipart/form-data既可以上传文件等二进制数据，也可以上传表单键值对。使用boundary隔离多段信息。
    // x-www-form-urlencoded只能上传键值对，并且键值对都是间隔分开的。

    // 上传单个文件
    // 前端使用<form action="/api/v1/file/upload" method="post" enctype="multipart/form-data">
    // <input type="file" name="meFile" /><p>
    @PostMapping("/upload")
    public String handleUpload(MultipartFile meFile) {
        String fileName = null;
        if (meFile != null) {
            fileName = meFile.getOriginalFilename();
            try {
                // 保存文件
                meFile.transferTo(new File("D:/temp/" + UUID.randomUUID() + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

    // 上传多个文件
    // 前端使用<form action="/api/v1/file/uploads" method="post" enctype="multipart/form-data">
    // <input type="file" name="meFile" multiple="multiple" /><p>
    @PostMapping("/uploads")
    public List<String> handleUploads(MultipartFile[] meFiles) {
        List<String> fileNames = new ArrayList<>();
        if (meFiles != null) {
            for (MultipartFile meFile : meFiles) {
                String fileName = meFile.getOriginalFilename();
                fileNames.add(fileName);
                try {
                    // 保存文件
                    meFile.transferTo(new File("D:/temp/" + UUID.randomUUID() + fileName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileNames;
    }
}
