package com.iot.temphumidity.repository.postgresql;

import com.iot.temphumidity.entity.postgresql.UserEntity;
import com.iot.temphumidity.enums.UserRole;
import com.iot.temphumidity.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 用户数据访问接口
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
    Optional<UserEntity> findByUsername(String username);
    
    Optional<UserEntity> findByEmail(String email);
    
    Optional<UserEntity> findByPhone(String phone);
    
    List<UserEntity> findByRole(UserRole role);
    
    List<UserEntity> findByStatus(UserStatus status);
    
    Page<UserEntity> findByStatus(UserStatus status, Pageable pageable);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    boolean existsByPhone(String phone);
    
    @Query("SELECT u FROM UserEntity u WHERE u.username LIKE %:keyword% OR u.email LIKE %:keyword% OR u.realName LIKE %:keyword%")
    Page<UserEntity> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
    
    @Query("SELECT COUNT(u) FROM UserEntity u WHERE u.role = :role AND u.status = :status")
    long countByRoleAndStatus(@Param("role") UserRole role, @Param("status") UserStatus status);
    
    @Query("SELECT new map(u.id as id, u.username as username, u.realName as realName, " +
           "u.role as role, u.status as status, u.lastLoginTime as lastLoginTime, " +
           "COUNT(g) as gatewayCount) " +
           "FROM UserEntity u LEFT JOIN u.gateways g " +
           "GROUP BY u.id, u.username, u.realName, u.role, u.status, u.lastLoginTime")
    List<Object[]> findAllUserSummary();
}