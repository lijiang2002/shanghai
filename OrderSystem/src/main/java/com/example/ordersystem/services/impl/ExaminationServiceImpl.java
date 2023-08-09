package com.example.ordersystem.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ordersystem.entity.Examination;
import com.example.ordersystem.mapper.ExaminationMapper;
import com.example.ordersystem.services.ExaminationService;
import org.springframework.stereotype.Service;

@Service
public class ExaminationServiceImpl extends ServiceImpl<ExaminationMapper, Examination> implements ExaminationService   {

}
