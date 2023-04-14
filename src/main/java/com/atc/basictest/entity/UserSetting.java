package com.atc.basictest.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_setting")
@Data
@NoArgsConstructor
public class UserSetting {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(index = 10, access = JsonProperty.Access.READ_ONLY)
    private Long id;
    
    @Column(name = "param_key", nullable = false, length = 100)
    @Size(min = 3, max = 100, message = "Key must be between 3 and 100 characters")
    @JsonProperty(index = 20)
    private String key;

    @Column(name = "param_value", nullable = false, length = 100)
    @Size(min = 3, max = 100, message = "Value must be between 3 and 100 characters")
    @JsonProperty(index = 30)
    private String value;

    @ManyToOne(optional = false)
    @JoinColumn(name="user_id", nullable = false)
    private User user;
}
