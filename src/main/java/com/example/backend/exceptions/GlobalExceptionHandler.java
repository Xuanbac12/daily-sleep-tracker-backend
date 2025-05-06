package com.example.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ✅ Bắt lỗi định dạng JSON sai (như ngày sai format)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidJson(HttpMessageNotReadableException ex) {
        System.out.println("🔥 JSON Parse Error: " + ex.getMostSpecificCause().getMessage());
        return Map.of("error", "Dữ liệu không hợp lệ: " + ex.getMostSpecificCause().getMessage());
    }

    // ✅ Bắt lỗi logic từ Service (trùng ngày, ngày tương lai, v.v.)
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleIllegalArgument(IllegalArgumentException ex) {
        return Map.of("error", ex.getMessage());
    }

    // ✅ Bắt mọi lỗi còn lại để không trả về 403 nhầm
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleGeneral(Exception ex) {
        System.out.println("🔥 Lỗi không xác định: " + ex.getClass() + " - " + ex.getMessage());
        return Map.of("error", "Lỗi hệ thống: " + ex.getMessage());
    }
}
