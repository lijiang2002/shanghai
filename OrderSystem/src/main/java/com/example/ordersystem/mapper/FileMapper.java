package com.example.ordersystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ordersystem.entity.File;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper extends BaseMapper<File> {
}

