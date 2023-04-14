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
public class UserSettingDto {

    @JsonProperty(value = "user_settings")
    List<Map<String, String>> settingKeys;
}
