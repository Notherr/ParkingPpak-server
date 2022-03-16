package com.luppy.parkingppak.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "test")
public class TestController {

    @Operation(summary = "테스트용 api입니다.")
    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
