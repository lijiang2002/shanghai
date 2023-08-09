package com.example.ordersystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;

@Data
public class Student {
    private String id;
    private String name;
    private String identity;
    private String status;
    @TableField(exist = false)
    private List<Examination> examinations;
}
