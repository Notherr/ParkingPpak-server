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

    public static <T> Response<T> response(Integer statusCode, T data, String message){
        return new Response<>(statusCode, data, message);
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
}
