package com.iot.temphumidity.repository;

import com.iot.temphumidity.entity.User;
import com.iot.temphumidity.entity.Role;
import com.iot.temphumidity.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户角色关联Repository接口
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    
    List<UserRole> findByUser(User user);
    
    List<UserRole> findByRole(Role role);
    
    boolean existsByUserAndRole(User user, Role role);
    
    void deleteByUserAndRole(User user, Role role);
    
    @Query("SELECT ur.role.id FROM UserRole ur WHERE ur.user.userId = :userId")
    List<Long> findRoleIdsByUserId(@Param("userId") String userId);
    
    @Query("SELECT ur.user.userId FROM UserRole ur WHERE ur.role.id = :roleId")
    List<String> findUserIdsByRoleId(@Param("roleId") Long roleId);
    
    void deleteByUser(User user);
    
    void deleteByRole(Role role);
}