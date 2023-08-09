package com.example.ordersystem.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ordersystem.entity.Student;
import com.example.ordersystem.entity.Teacher;
import com.example.ordersystem.mapper.StudentMapper;
import com.example.ordersystem.mapper.TeacherMapper;
import com.example.ordersystem.services.TeacherService;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
}
