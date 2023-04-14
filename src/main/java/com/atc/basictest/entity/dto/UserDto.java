package com.atc.basictest.entity.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @JsonProperty(index = 10)
    private Long id;
    @JsonProperty(index = 20)
    private String ssn;
    @JsonProperty(value = "first_name", index = 30)
    private String firstName;
    @JsonProperty(value = "middle_name", index = 40)
    @JsonInclude(Include.NON_NULL)
    private String middleName;
    @JsonProperty(value = "family_name", index = 50)
    private String familyName;
    @JsonProperty(value = "birth_date", index = 60)
    private LocalDate birthDate;
    @JsonProperty(value = "created_time", index = 70)
    private LocalDateTime createdTime;
    @JsonProperty(value = "updated_time", index = 80)
    private LocalDateTime updatedTime;
    @JsonProperty(value = "created_by", index = 90)
    private String createdBy;
    @JsonProperty(value = "updated_by", index = 100)
    private String updatedBy;
    @JsonProperty(value = "is_active", index = 110)
    private boolean isActive;
    @JsonProperty(value = "deleted_time", index = 120)
    @JsonInclude(Include.NON_NULL)
    private LocalDateTime deletedTime;
}
