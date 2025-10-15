package com.ran.community.global;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice //전역적 에러 처리
public class GlobalExceptionHandler {

    //유효성 검사 validation
    //MethodArgumentNotValidException ex: 이 예외가 요청값 검증을 실패함.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validationException(MethodArgumentNotValidException ex){

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "invalid_request");
        response.put("data", null);

        List<Map<String,String>> errors = new ArrayList<>(); //errors로 배열로 출력하기 위한.

        //errors[field,errorCode,message]

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            Map<String, String> error = new LinkedHashMap<>();
            error.put("field", fieldError.getField());
            error.put("errorCode", fieldError.getCode());
            error.put("message", fieldError.getDefaultMessage());
            errors.add(error);
        }

        response.put("errors", errors);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);




    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException ex) {

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "register_failed");  // 전체 상태 메시지
        response.put("data", null);                  // 실패니까 null

        List<Map<String, String>> errors = new ArrayList<>();//이게 반복된 객체를 가지고 있어야됨.
        errors.add(Map.of(
                "field", "unknown",                   // 나중에 특정 필드명 지정 가능
                "errorCode", "ILLEGAL_ARGUMENT",      // 예외 유형 코드
                "message", ex.getMessage()            // 실제 예외 메시지 가져옴
        ));

        response.put("errors", errors);              // 에러 배열 추가


        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

}
