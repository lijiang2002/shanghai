package com.example.ordersystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.ordersystem.mapper.FileMapper;
import com.example.ordersystem.utils.Result;
import com.example.ordersystem.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Value("${upload-path.icon}")
    private String UPLOADED_FOLDER;

    @Autowired
    private FileMapper fileMapper;

    @PostMapping()
    public Result upload(@RequestParam(value = "file") MultipartFile file) {
        File folder = new File(UPLOADED_FOLDER);
        if (folder.exists() && folder.isDirectory()) {
            System.out.println("文件夹已经存在：" + UPLOADED_FOLDER);
        } else {
            if (folder.mkdirs()) {
                System.out.println("文件夹创建成功：" + UPLOADED_FOLDER);
            } else {
                System.out.println("文件夹创建失败：" + UPLOADED_FOLDER);
            }
        }
        long l = System.currentTimeMillis();
        //获取原始文件名称
        String originalFilename = file.getOriginalFilename();
        //获取文件后缀名
        String afterName = originalFilename.split("\\.")[1];
        String filename = l + "." + afterName;
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + filename);
            System.out.println(path);
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        com.example.ordersystem.entity.File f = new com.example.ordersystem.entity.File();
        f.setId(l + "");
        f.setName(filename);
        f.setStatus(0);
        fileMapper.insert(f);
        return Result.setDate(ResultCode.C200, "完成上传");
    }

    @GetMapping()
    public Result get() {
        QueryWrapper<com.example.ordersystem.entity.File> fileQueryWrapper = new QueryWrapper<>();
        fileQueryWrapper.eq("status", 0);
        List<com.example.ordersystem.entity.File> files = fileMapper.selectList(fileQueryWrapper);
        return Result.setDate(ResultCode.C201, files);
    }

    @PutMapping("/{id}")
    Result put(@PathVariable("id") String id) {
        QueryWrapper<com.example.ordersystem.entity.File> fileUpdateWrapper = new QueryWrapper<>();
        fileUpdateWrapper.eq("id", id);
        fileMapper.delete(fileUpdateWrapper);
        return Result.setDate(ResultCode.C201, "操作成功");
    }
}
