package com.example.ordersystem.utils;

import java.util.Random;

public class Tool {

    /**
     * 生成指定长度验证码
     *
     * @param length
     * @return
     */
    public static String getCode(int length) {
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10); // 生成0-9之间的随机数
            sb.append(digit);
        }
        return sb.toString();
    }
}
