package com.atc.basictest.entity.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings({ "unchecked", "rawtypes" })
public class HttpPageResponse<T> implements Serializable {

    @JsonInclude(value = Include.NON_NULL)
    private T data;
    private int maxRecords;
    private int offset;

    public static <T> HttpPageResponse<T> ok(T data, int maxRecords, int offset) {
        return new HttpPageResponse(data, maxRecords, offset);
    }
}
