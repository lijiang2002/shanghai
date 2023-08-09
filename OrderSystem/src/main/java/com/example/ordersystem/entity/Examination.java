package com.example.ordersystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class Examination {
    private String id;
    private String sid;
    private long date;
    private long cdate;
    private String status;
    private String sy1;
    private String sy2;
    private String sy3;
    private String sy4;
    private String qy1;
    private String qy2;
    private String qy3;
    private String qy4;
    private String s1;
    private String s2;
    private String s3;
    private String s4;
    private String s5;
    private String q1;
    private String q2;
    private String q3;
    private String q4;
    private String q5;
    @TableField(exist = false)
    private Student student;
}
