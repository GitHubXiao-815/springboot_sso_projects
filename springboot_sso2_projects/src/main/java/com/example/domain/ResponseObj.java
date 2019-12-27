package com.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseObj {
    private Integer code;
    private Map<String,Object> data = new LinkedHashMap<>();
    private String message;
}
