package com.atc.basictest.entity.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {

    @JsonProperty(value = "user_Data", required = true)
    private UserDto userData;

    @JsonProperty(value = "user_settings", required = true)
    private List<Map<String,String>> settingKeys;
}
