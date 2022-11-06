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
}
