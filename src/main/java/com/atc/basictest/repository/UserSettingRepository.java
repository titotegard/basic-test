package com.atc.basictest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atc.basictest.entity.UserSetting;

public interface UserSettingRepository extends JpaRepository<UserSetting, Long> {
    List<UserSetting> findByUserId(Long userId);
}
