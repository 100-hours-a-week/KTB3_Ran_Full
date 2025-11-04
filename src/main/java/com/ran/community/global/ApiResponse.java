package com.ran.community.global;


import lombok.Getter;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

//응답 래퍼클래스
@Getter
public class ApiResponse<T> {
    private String status;
    private int code;
    private String message;
    private T data;

    public ApiResponse(String status, int code, String message, T data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    //200 : OK success
    public static <T> ResponseEntity<ApiResponse<T>> success(T data, String message) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<T>(HttpStatus.OK.getReasonPhrase(),HttpStatus.OK.value(), message, data));
    }

    //201 : Created
    public static <T> ResponseEntity<ApiResponse<T>> created(T data, String message) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<T>(HttpStatus.CREATED.getReasonPhrase(),HttpStatus.CREATED.value(), message, data));
    }
}
