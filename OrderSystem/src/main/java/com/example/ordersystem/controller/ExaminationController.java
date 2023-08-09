package com.example.ordersystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ordersystem.entity.Examination;
import com.example.ordersystem.entity.Student;
import com.example.ordersystem.entity.Teacher;
import com.example.ordersystem.services.ExaminationService;
import com.example.ordersystem.services.StudentService;
import com.example.ordersystem.utils.Result;
import com.example.ordersystem.utils.ResultCode;
import com.example.ordersystem.utils.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/examination")
@RestController()
public class ExaminationController {

    @Autowired
    private ExaminationService examinationService;
    @Autowired
    private StudentService studentService;

    @PostMapping("/{name}/{identity}")
    Result save(@RequestBody Examination examination, @PathVariable("name") String name, @PathVariable("identity") String identity) {
        Student student = new Student();
        student.setName(name);
        student.setIdentity(identity);
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("identity", student.getIdentity());
        Student one = studentService.getOne(queryWrapper);
        QueryWrapper<Examination> examinationQueryWrapper = new QueryWrapper<>();
        if (one == null) {
            String code = Tool.getCode(10);
            student.setId(code);
            student.setStatus("0");
            studentService.save(student);
            examination.setId(Tool.getCode(10));
            examination.setSid(code);
        } else {
            examinationQueryWrapper.eq("sid", one.getId());
            examinationQueryWrapper.eq("status", "0");
            Examination one1 = examinationService.getOne(examinationQueryWrapper);
            if (one1 != null)
                return Result.setDate(ResultCode.C400, "同学,你最近有登记考试，请勿重复申请，请你在完成考试后在申请。");
            examination.setId(Tool.getCode(10));
            examination.setSid(one.getId());
            student.setStatus("0");
        }
        examination.setStatus("0");
        examinationService.save(examination);
        return Result.setDate(ResultCode.C200, "申请成功！！！");
    }

    @GetMapping("/{current}/{size}")
    Result list(@PathVariable("current") int currentPage, @PathVariable("size") int pageSize) {
        QueryWrapper<Examination> examinationQueryWrapper = new QueryWrapper<>();
        examinationQueryWrapper.eq("status", 0);
        Page<Examination> ordersPage = new Page<>(currentPage, pageSize);
        Page<Examination> page = examinationService.page(ordersPage, examinationQueryWrapper);
        for (Examination examination : page.getRecords()) {
            QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
            studentQueryWrapper.eq("id", examination.getSid());
            Student one = studentService.getOne(studentQueryWrapper);
            examination.setStudent(one);
        }
        return Result.setDate(ResultCode.C201, page);
    }

    @GetMapping("/info/{identity}")
    Result info(@PathVariable("identity") String identity) {
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("identity", identity);
        Student student = studentService.getOne(studentQueryWrapper);
        if (student == null) return Result.setDate(ResultCode.C400, "考生不存在");
        QueryWrapper<Examination> examinationQueryWrapper = new QueryWrapper<>();
        examinationQueryWrapper.eq("status", "0");
        examinationQueryWrapper.eq("sid", student.getId());
        Examination examination = examinationService.getOne(examinationQueryWrapper);
        if (examination == null) return Result.setDate(ResultCode.C400, "考生暂时没有考试");
        examination.setStudent(student);
        return Result.setDate(ResultCode.C201, examination);
    }

    @PutMapping()
    Result put(@RequestBody Examination examination) {
        UpdateWrapper<Examination> examinationUpdateWrapper = new UpdateWrapper<>();
        examinationUpdateWrapper.set("status", examination.getStatus());
        examinationUpdateWrapper.set("date", examination.getDate());
        examinationUpdateWrapper.set("s1", examination.getS1());
        examinationUpdateWrapper.set("s2", examination.getS2());
        examinationUpdateWrapper.set("s3", examination.getS3());
        examinationUpdateWrapper.set("s4", examination.getS4());
        examinationUpdateWrapper.set("q1", examination.getQ1());
        examinationUpdateWrapper.set("q2", examination.getQ2());
        examinationUpdateWrapper.set("q3", examination.getQ3());
        examinationUpdateWrapper.set("q4", examination.getQ4());
        examinationUpdateWrapper.eq("id", examination.getId());
        boolean update = examinationService.update(examinationUpdateWrapper);
        return Result.setDate(ResultCode.C200, "成绩已保存");
    }
}
