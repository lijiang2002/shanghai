package com.example.ordersystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ordersystem.entity.Examination;
import com.example.ordersystem.entity.Student;
import com.example.ordersystem.services.ExaminationService;
import com.example.ordersystem.services.StudentService;
import com.example.ordersystem.utils.Result;
import com.example.ordersystem.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/student")
@RestController()
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private ExaminationService examinationService;

    @PutMapping
    Result put(@RequestBody Student student) {
        UpdateWrapper<Student> studentUpdateWrapper = new UpdateWrapper<>();
        studentUpdateWrapper.eq("id", student.getId());
        studentUpdateWrapper.set("name", student.getName());
        studentUpdateWrapper.set("identity", student.getIdentity());
        studentService.update(studentUpdateWrapper);
        return Result.setDate(ResultCode.C200, "操作成功");
    }


    @DeleteMapping("/{identity}")
    Result delete(@PathVariable("identity") String identity) {
        UpdateWrapper<Student> studentQueryWrapper = new UpdateWrapper<>();
        studentQueryWrapper.eq("identity", identity);
        studentQueryWrapper.set("status", "1");
        studentService.update(studentQueryWrapper);
        return Result.setDate(ResultCode.C200, "操作成功");
    }

    @GetMapping("/list/{current}/{size}/{sous}")
    Result getList(@PathVariable("current") int currentPage, @PathVariable("size") int pageSize,
                   @PathVariable("sous") String sous) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        if (!sous.equals("nu")) queryWrapper.like("name", sous).or().like("identity", sous);
        queryWrapper.ne("status", "1");
        Page<Student> ordersPage = new Page<>(currentPage, pageSize);
        Page<Student> page = studentService.page(ordersPage, queryWrapper);
        return Result.setDate(ResultCode.C201, "Succeed", page);
    }

    @GetMapping("/e/{identity}")
    Result get(@PathVariable("identity") String identity) {
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("identity", identity);
        Student one = studentService.getOne(studentQueryWrapper);
        QueryWrapper<Examination> examinationQueryWrapper = new QueryWrapper<>();
        examinationQueryWrapper.eq("sid", one.getId());
        examinationQueryWrapper.orderByDesc("cdate");
        List<Examination> list = examinationService.list(examinationQueryWrapper);
        one.setExaminations(list);
        return Result.setDate(ResultCode.C201,one);
    }
}
