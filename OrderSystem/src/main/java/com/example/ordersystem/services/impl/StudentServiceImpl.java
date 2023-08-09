package com.example.ordersystem.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ordersystem.entity.Examination;
import com.example.ordersystem.entity.Student;
import com.example.ordersystem.mapper.ExaminationMapper;
import com.example.ordersystem.mapper.StudentMapper;
import com.example.ordersystem.services.ExaminationService;
import com.example.ordersystem.services.StudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
