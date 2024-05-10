package com.booking.userservice.repository;

import com.booking.userservice.entity.UserDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, Integer> {
    Optional<UserDetailsEntity> findByUserNameAndIsActiveTrue(String userName);
    @Modifying
    @Query(value = "update user_details set is_active = 0 where user_name = :userName", nativeQuery = true)
    int deactivateUser(@Param("userName") String userName);
}
