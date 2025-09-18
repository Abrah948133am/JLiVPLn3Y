// 代码生成时间: 2025-09-18 09:32:36
// ErrorLogCollector.java

package com.example.playframework.errorlogger;

import play.mvc.Result;
import play.mvc.Controller;
import play.Play;
import play.Logger;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

// 错误日志收集器，用于记录和存储应用程序中的错误日志
public class ErrorLogCollector extends Controller {

    // 使用线程安全的ConcurrentHashMap存储错误日志
    private static final ConcurrentMap<String, StringBuilder> errorLogs = new ConcurrentHashMap<>();

    public static void logError(String errorId, String errorMessage) {
        // 使用errorId作为键，将错误消息添加到日志中
        errorLogs.computeIfAbsent(errorId, k -> new StringBuilder()).append(errorMessage).append("
");
        Logger.error("Error logged with ID: " + errorId + ", Message: " + errorMessage);
    }

    public static Result getErrorLog(String errorId) {
        try {
            // 从errorLogs中获取指定errorId的错误日志
            StringBuilder errorLog = errorLogs.get(errorId);
            if (errorLog == null) {
                return notFound("Error log not found for ID: " + errorId);
            }
            return ok(errorLog.toString());
        } catch (Exception e) {
            // 异常处理
            Logger.error("Failed to retrieve error log for ID: " + errorId, e);
            return internalServerError("Failed to retrieve error log");
        }
    }

    // 清除所有错误日志的方法
    public static void clearErrorLogs() {
        errorLogs.clear();
    }
}
