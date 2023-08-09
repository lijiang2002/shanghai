package com.example.ordersystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ordersystem.entity.Teacher;
import com.example.ordersystem.services.TeacherService;
import com.example.ordersystem.utils.Result;
import com.example.ordersystem.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/teacher")
@RestController
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/one")
    Result get(@RequestBody Teacher teacher) {
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.eq("username", teacher.getUsername());
        teacherQueryWrapper.eq("password", teacher.getPassword());
        Teacher one = teacherService.getOne(teacherQueryWrapper);
        if (one == null) return Result.setDate(ResultCode.C400, "用户名或者密码错误");
        return Result.setDate(ResultCode.C200, "登录成功", one.getUsername());
    }

    @GetMapping("/list/{current}/{size}/{name}")
    Result getList(@PathVariable("current") int currentPage, @PathVariable("size") int pageSize, @PathVariable("name") String name) {
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        if (!name.equals("nu")) teacherQueryWrapper.like("username", name);
        Page<Teacher> ordersPage = new Page<>(currentPage, pageSize);
        Page<Teacher> page = teacherService.page(ordersPage, teacherQueryWrapper);
        return Result.setDate(ResultCode.C201, "Succeed", page);
    }

    @PostMapping()
    Result save(@RequestBody Teacher teacher) {
        teacherService.save(teacher);
        return Result.setDate(ResultCode.C200, "操作成功");
    }

    @DeleteMapping("/{username}")
    Result delete(@PathVariable("username") String username) {
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.eq("username", username);
        teacherService.remove(teacherQueryWrapper);
        return Result.setDate(ResultCode.C200, "操作成功");
    }

    @PutMapping
    Result put(@RequestBody Teacher teacher) {
        UpdateWrapper<Teacher> teacherUpdateWrapper = new UpdateWrapper<>();
        teacherUpdateWrapper.eq("username", teacher.getUsername());
        teacherUpdateWrapper.set("password", teacher.getPassword());
        teacherService.update(teacherUpdateWrapper);
        return Result.setDate(ResultCode.C200, "操作成功");
    }

}
