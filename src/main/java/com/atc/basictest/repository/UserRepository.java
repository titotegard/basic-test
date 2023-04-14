package com.atc.basictest.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.atc.basictest.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM user_atc ORDER BY id", nativeQuery = true)
    Page<User> findAll(Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE user_atc SET is_active = :isActive, deleted_time = NULL WHERE id = :id", nativeQuery = true)
    void setActiveAndDeletedTime(@Param("id") Long id, @Param("isActive") Boolean isActive);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE user_atc SET is_active = false, deleted_time=:deletedTime WHERE id = :id", nativeQuery = true)
    void setActiveAndDeletedTime(@Param("id") Long id, @Param("deletedTime") LocalDateTime deletedTime);

}
