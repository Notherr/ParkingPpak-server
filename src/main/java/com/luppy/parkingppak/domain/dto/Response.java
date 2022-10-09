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

    public static <T> Response<T> INVALID_EMAIL_ERROR(){
        return (Response<T>) Response.builder()
                .statusCode(400)
                .data(null)
                .message("유효하지않은 이메일 형식입니다.")
                .build();
    }

    public static <T> Response<T> INVALID_PASSWORD_ERROR(){
        return (Response<T>) Response.builder()
                .statusCode(400)
                .data(null)
                .message("유효하지않은 패스워드 형식입니다.")
                .build();
    }

    public static <T> Response<T> LOGIN_OK(T data){
        return (Response<T>) Response.builder()
                .statusCode(200)
                .data(data)
                .message("Login succeeded.")
                .build();
    }

    public static <T> Response<T> NOT_JOIN_ERROR(){
        return (Response<T>) Response.builder()
                .statusCode(400)
                .data(null)
                .message("가입되지 않은 이메일입니다. email")
                .build();
    }

    public static <T> Response<T> PASSWORD_ERROR(){
        return (Response<T>) Response.builder()
                .statusCode(401)
                .data(null)
                .message("틀린 패스워드 입니다. password")
                .build();
    }

    public static <T> Response<T> SYSTEM_ERROR(){
        return (Response<T>) Response.builder()
                .statusCode(500)
                .data(null)
                .message("시스템 문제로 인한 에러가 발생하였습니다. system")
                .build();
    }

    public static <T> Response<T> REGISTER_CARD_OK(T data){
        return (Response<T>) Response.builder()
                .statusCode(200)
                .data(data)
                .message("카드정보가 정상적으로 등록되었습니다.")
                .build();
    }
    public static <T> Response<T> REGISTER_OIL_TYPE_OK(T data){
        return (Response<T>) Response.builder()
                .statusCode(200)
                .data(data)
                .message("정상적으로 유류정보가 등록되었습니다.")
                .build();
    }

    public static <T> Response<T> REGISTER_NAVI_TYPE_OK(T data){
        return (Response<T>) Response.builder()
                .statusCode(200)
                .data(data)
                .message("정상적으로 내비앱 정보가 등록되었습니다.")
                .build();
    }

    public static <T> Response<T> GET_CARD_COMP_NAME_LIST_OK(T data){
        return (Response<T>) Response.builder()
                .statusCode(200)
                .data(data)
                .message("정상적으로 카드 회사 이름 목록이 반환되었습니다.")
                .build();
    }

    public static <T> Response<T> GET_OIL_TYPE_LIST_OK(T data){
        return (Response<T>) Response.builder()
                .statusCode(200)
                .data(data)
                .message("정상적으로 유류 타입 목록이 반환되었습니다.")
                .build();
    }

    public static <T> Response<T> GET_NAVI_TYPE_LIST_OK(T data){
        return (Response<T>) Response.builder()
                .statusCode(200)
                .data(data)
                .message("정상적으로 내비 앱 목록이 반환되었습니다.")
                .build();
    }

    public static <T> Response<T> INVALID_ACCOUNT_ID(T data){
        return (Response<T>) Response.builder()
                .statusCode(400)
                .data(data)
                .message("잘못된 계정 id입니다.")
                .build();
    }

    public static <T> Response<T> INVALID_PARKINGLOT_ID(T data){
        return (Response<T>) Response.builder()
                .statusCode(400)
                .data(data)
                .message("잘못된 주차장 id입니다.")
                .build();
    }

    public static <T> Response<T> INVALID_GAS_STATION_ID(T data){
        return (Response<T>) Response.builder()
                .statusCode(400)
                .data(data)
                .message("잘못된 주유소 id입니다.")
                .build();
    }

    public static <T> Response<T> INVALID_DATATYPE(T data){
        return (Response<T>) Response.builder()
                .statusCode(400)
                .data(data)
                .message("잘못된 데이터 타입입니다.(type : parking-lot or gas-station)")
                .build();
    }

    public static <T> Response<T> ADD_FAVORITE_OK(T data){
        return (Response<T>) Response.builder()
                .statusCode(200)
                .data(data)
                .message("정상적으로 찜목록에 추가되었습니다.")
                .build();
    }

    public static <T> Response<T> REDUNDANT_FAVORITE(T data){
        return (Response<T>) Response.builder()
                .statusCode(400)
                .data(data)
                .message("이미 찜목록에 추가된 데이터입니다.")
                .build();
    }

    public static <T> Response<T> GET_FAVORITE_LIST_OK(T data){
        return (Response<T>) Response.builder()
                .statusCode(200)
                .data(data)
                .message("정상적으로 리스트가 반환되었습니다.")
                .build();
    }

    public static <T> Response<T> DELETE_FAVORITE_OK(T data){
        return (Response<T>) Response.builder()
                .statusCode(200)
                .data(data)
                .message("정상적으로 삭제되었습니다.")
                .build();
    }

    public static <T> Response<T> GET_OAUTH_KEY_OK(T data){
        return (Response<T>) Response.builder()
                .statusCode(200)
                .data(data)
                .message("정상적으로 반환되었습니다.")
                .build();
    }

    public static <T> Response<T> INVALID_RESOURCE_SERVER_NAME(T data){
        return (Response<T>) Response.builder()
                .statusCode(400)
                .data(data)
                .message("등록되지 않은 리소스 서버 네임입니다.")
                .build();
    }

    public static <T> Response<T> OAUTH_LOGIN_OK(T data){
        return (Response<T>) Response.builder()
                .statusCode(200)
                .data(data)
                .message("OAUTH로그인이 정상적으로 되었습니다.")
                .build();
    }


}
