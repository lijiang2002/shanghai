package com.example.ordersystem.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.exception.*;
import cn.dev33.satoken.util.SaResult;
import com.example.ordersystem.utils.Result;
import com.example.ordersystem.utils.ResultCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import static cn.dev33.satoken.exception.NotLoginException.*;

@RestControllerAdvice
public class GlobalExceptionHandler {


    // 全局异常拦截 拦截：其它所有异常
    @ExceptionHandler
    public SaResult handlerException(Exception e) {
        e.printStackTrace();
        return SaResult.error(e.getMessage());
    }

    // 拦截：未登录异常
    @ExceptionHandler(NotLoginException.class)
    public Result handlerException(NotLoginException e) {

        // messagingTemplate.convertAndSend();
        SaHolder.getResponse().deleteCookie("items-token");
        // 打印堆栈，以供调试
        // e.printStackTrace();
        // 表示未提供token
        if (NOT_TOKEN.equals(e.getType())) {
            return Result.setDate(ResultCode.C408, "未登录");
        }
        // 表示token无效
        else if (INVALID_TOKEN.equals(e.getType())) {
            return Result.setDate(ResultCode.C408, "账户登录无效,请重新登录");
        }
        // 表示token已过期
        else if (TOKEN_TIMEOUT.equals(e.getType())) {
            return Result.setDate(ResultCode.C408, "账户登录过期");
        }
        // 表示token已被顶下线
        else if (BE_REPLACED.equals(e.getType())) {
            return Result.setDate(ResultCode.C408, "账户在其它地点登录");
        }
        // 表示token已被踢下线
        else if (KICK_OUT.equals(e.getType())) {
            return Result.setDate(ResultCode.C408, "账户被强制下线");
        }
        return Result.setDate(ResultCode.C408, "未登录");
    }

    // 拦截：缺少权限异常
    @ExceptionHandler(NotPermissionException.class)
    public SaResult handlerException(NotPermissionException e) {
        e.printStackTrace();
        return SaResult.error("缺少权限");
    }

    // 拦截：缺少角色异常
    @ExceptionHandler(NotRoleException.class)
    public SaResult handlerException(NotRoleException e) {
        e.printStackTrace();
        return SaResult.error("缺少角色：" + e.getRole());
    }

    // 拦截：二级认证校验失败异常
    @ExceptionHandler(NotSafeException.class)
    public SaResult handlerException(NotSafeException e) {
        e.printStackTrace();
        return SaResult.error("二级认证校验失败：" + e.getService());
    }

    // 拦截：服务封禁异常
    @ExceptionHandler(DisableServiceException.class)
    public SaResult handlerException(DisableServiceException e) {
        e.printStackTrace();
        return SaResult.error("当前账号 " + e.getService() + " 服务已被封禁 (level=" + e.getLevel() + ")：" + e.getDisableTime() + "秒后解封");
    }

    // 拦截：Http Basic 校验失败异常
    @ExceptionHandler(NotBasicAuthException.class)
    public SaResult handlerException(NotBasicAuthException e) {
        e.printStackTrace();
        return SaResult.error(e.getMessage());
    }

}
