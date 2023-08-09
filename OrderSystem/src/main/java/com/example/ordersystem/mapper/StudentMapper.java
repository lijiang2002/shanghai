package com.example.ordersystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ordersystem.entity.Examination;
import com.example.ordersystem.entity.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
}
