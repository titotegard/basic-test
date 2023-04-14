package com.atc.basictest.entity.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings({ "unchecked", "rawtypes" })
public class HttpResponse<T> implements Serializable {

    public static final String OK = "201";
    private String status;
    private String code;
    private String message;
    @JsonProperty(value = "data")
    @JsonInclude(value = Include.NON_NULL)
    private ResponseDto response;

    public static <T> HttpResponse<T> ok(ResponseDto response) {
        return new HttpResponse("SUCCESS", OK, "Success", response);
    }

    public static <T> HttpResponse<T> error(String status, String code, String message) {
        return new HttpResponse(status, code, message, null);
    }
}
