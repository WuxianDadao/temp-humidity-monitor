package com.iot.temphumidity.repository;

import com.iot.temphumidity.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * 根据用户名查找用户
     */
    Optional<User> findByUsername(String username);
    
    /**
     * 根据邮箱查找用户
     */
    Optional<User> findByEmail(String email);
    
    /**
     * 根据手机号查找用户
     */
    Optional<User> findByPhone(String phone);
    
    /**
     * 根据用户名检查用户是否存在
     */
    boolean existsByUsername(String username);
    
    /**
     * 根据邮箱检查用户是否存在
     */
    boolean existsByEmail(String email);
    
    /**
     * 根据角色查找用户
     */
    List<User> findByRole(String role);
    
    /**
     * 根据用户状态查找用户
     */
    List<User> findByStatus(String status);
    
    /**
     * 根据notes内容查找用户（分页）
     */
    Page<User> findByNotesContaining(String notes, Pageable pageable);
    
    /**
     * 根据用户名或邮箱查找用户
     */
    @Query("SELECT u FROM User u WHERE u.username = :identifier OR u.email = :identifier")
    Optional<User> findByUsernameOrEmail(@Param("identifier") String identifier);
    
    /**
     * 根据用户名模糊查找
     */
    List<User> findByUsernameContainingIgnoreCase(String username);
    
    /**
     * 根据姓名模糊查找
     */
    List<User> findByFullNameContainingIgnoreCase(String fullName);
    
    /**
     * 根据邮箱模糊查找
     */
    List<User> findByEmailContainingIgnoreCase(String email);
    
    /**
     * 根据公司查找用户
     */
    List<User> findByCompanyContainingIgnoreCase(String company);
    
    /**
     * 查找超级管理员用户
     */
    @Query("SELECT u FROM User u WHERE u.role = 'SUPER_ADMIN'")
    List<User> findSuperAdmins();
    
    /**
     * 查找普通用户
     */
    @Query("SELECT u FROM User u WHERE u.role = 'USER'")
    List<User> findNormalUsers();
    
    /**
     * 查找活跃用户
     */
    @Query("SELECT u FROM User u WHERE u.status = 'ACTIVE'")
    List<User> findActiveUsers();
    
    /**
     * 查找被禁用的用户
     */
    @Query("SELECT u FROM User u WHERE u.status = 'DISABLED'")
    List<User> findDisabledUsers();
    
    /**
     * 统计用户数量
     */
    @Query("SELECT u.role, COUNT(u) FROM User u GROUP BY u.role")
    List<Object[]> countUsersByRole();
    
    @Query("SELECT u.status, COUNT(u) FROM User u GROUP BY u.status")
    List<Object[]> countUsersByStatus();
}