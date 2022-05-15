package com.luppy.parkingppak.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Response<T> {


    private Integer statusCode;
    private T data;
    private String message;

    public static <T> Response<T> JOIN_OK(T data){
        return (Response<T>) Response.builder()
                .statusCode(200)
                .data(data)
                .message("회원가입이 정상적으로 되었습니다.")
                .build();
    }

    public static <T> Response<T> JOIN_ERROR(){
        return (Response<T>) Response.builder()
                .statusCode(400)
                .data(null)
                .message("이미 존재하는 이메일입니다.")
                .build();
    }

    public static <T> Response<T> LOGIN_OK(T data){
        return (Response<T>) Response.builder()
                .statusCode(200)
                .data(data)
                .message("로그인이 정상적으로 되었습니다.")
                .build();
    }

    public static <T> Response<T> NOT_JOIN_ERROR(){
        return (Response<T>) Response.builder()
                .statusCode(4000)
                .data(null)
                .message("가입되지 않은 이메일입니다.")
                .build();
    }

    public static <T> Response<T> PASSWORD_ERROR(){
        return (Response<T>) Response.builder()
                .statusCode(4001)
                .data(null)
                .message("틀린 패스워드 입니다.")
                .build();
    }
}
