package com.ccommit.gameauctionserver.utils;

import lombok.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private static final String SUCCESS_STATUS = "Success";
    private static final String FAIL_STATUS = "Validator Error";
    private static final String ERROR_STATUS = "Exception";

    private String status;
    private T data;
    private String message;

    // API 정상적인 처리일때의 반환
    public static <T> ApiResponse<T> createSuccess(T data){
        return new ApiResponse<>(SUCCESS_STATUS, data, null);
    }

    // 유효하지않은 데이터로 인한 API 호출 거부시의 반환
    public static ApiResponse<?> createFail(BindingResult bindingResult){
        Map<String, String> errors = new HashMap<>();

        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for(ObjectError error : allErrors) {
            if(error instanceof FieldError){
                errors.put(((FieldError) error).getField(), error.getDefaultMessage());
            } else {
                errors.put(error.getObjectName(), error.getDefaultMessage());
            }
        }
        return new ApiResponse<>(FAIL_STATUS, errors, null);
    }

    //예외 발생으로 인한 API 호출 실패시의 반환
    public static ApiResponse<?> createError(String message, int code)
    {
        return new ApiResponse<>(SUCCESS_STATUS, code, message);
    }

}
