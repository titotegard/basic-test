package com.atc.basictest.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_atc")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(index = 10, access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Column(nullable = false, length = 16, unique = true)
    @JsonProperty(index = 20)
    private String ssn;

    @Column(name = "first_name", nullable = false, length = 100)
    @Size(min = 3, max = 100, message = "First Name must be between 3 and 100 characters")
    @JsonProperty(value = "first_name", index = 30)
    private String firstName;

    @Column(name = "middle_name", length = 100)
    @Size(min = 3, max = 100, message = "First Name must be between 3 and 100 characters")
    @JsonProperty(value = "middle_name", index = 40)
    @JsonInclude(Include.NON_NULL)
    private String middleName;

    @Column(name = "family_name", nullable = false, length = 100)
    @Size(min = 3, max = 100, message = "First Name must be between 3 and 100 characters")
    @JsonProperty(value = "family_name", index = 50)
    private String familyName;

    @Column(name = "birth_date", nullable = false, columnDefinition = "date")
    @JsonProperty(value = "birth_date", index = 60)
    private LocalDate birthDate;

    @Column(columnDefinition = "timestamp not null")
    @JsonProperty(index = 70)
    private LocalDateTime createdTime = LocalDateTime.now();

    @Column(columnDefinition = "timestamp not null")
    @JsonProperty(index = 80)
    private LocalDateTime updatedTime = LocalDateTime.now();

    @Column(length = 100, columnDefinition = "VARCHAR(100) not null")
    @JsonProperty(index = 90)
    private String createdBy = "system";

    @Column(length = 100, columnDefinition = "VARCHAR(100) not null")
    @JsonProperty(index = 100)
    private String updatedBy = "system";

    @Column(columnDefinition = "boolean not null")
    @JsonProperty(index = 110)
    private boolean isActive = true;

    @Column(columnDefinition = "timestamp")
    @JsonProperty(index = 120)
    @JsonInclude(Include.NON_NULL)
    private LocalDateTime deletedTime;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    @OrderBy("point DESC")
    private Set<UserSetting> userSettings;
}
