package com.iot.temphumidity.repository;

import com.iot.temphumidity.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 角色Repository接口
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    /**
     * 根据角色代码查找角色
     */
    Optional<Role> findByRoleCode(String roleCode);
    
    /**
     * 根据角色名称查找角色
     */
    Optional<Role> findByRoleName(String roleName);
    
    /**
     * 根据角色类型查找角色列表
     */
    List<Role> findByRoleType(Role.RoleType roleType);
    
    /**
     * 根据是否为系统角色查找角色列表
     */
    List<Role> findByIsSystemRole(Boolean isSystemRole);
    
    /**
     * 检查角色代码是否已存在
     */
    boolean existsByRoleCode(String roleCode);
    
    /**
     * 检查角色名称是否已存在
     */
    boolean existsByRoleName(String roleName);
    
    /**
     * 查找启用的角色
     */
    List<Role> findByEnabledTrue();
    
    /**
     * 查找禁用的角色
     */
    List<Role> findByEnabledFalse();
    
    /**
     * 查找指定用户的角色
     */
    @Query("SELECT r FROM Role r JOIN UserRole ur ON ur.role.id = r.id WHERE ur.user.id = :userId")
    List<Role> findRolesByUserId(@Param("userId") Long userId);
    
    /**
     * 查找具有特定权限的角色
     */
    @Query("SELECT DISTINCT r FROM Role r JOIN RolePermission rp ON rp.role.id = r.id WHERE rp.permission.id = :permissionId")
    List<Role> findRolesByPermissionId(@Param("permissionId") Long permissionId);
    
    /**
     * 查找系统预设角色（不可删除的角色）
     */
    @Query("SELECT r FROM Role r WHERE r.isSystemRole = true AND r.deletable = false")
    List<Role> findSystemPresetRoles();
    
    /**
     * 统计各类型角色数量
     */
    @Query("SELECT r.roleType, COUNT(r) FROM Role r GROUP BY r.roleType")
    List<Object[]> countRolesByType();
    
    /**
     * 查找最近创建的角色
     */
    @Query("SELECT r FROM Role r ORDER BY r.createdAt DESC")
    List<Role> findRecentlyCreatedRoles();
    
    /**
     * 根据角色描述模糊搜索
     */
    List<Role> findByDescriptionContaining(String keyword);
    
    /**
     * 查找可分配的角色（非系统角色或可分配的系统角色）
     */
    @Query("SELECT r FROM Role r WHERE r.enabled = true AND (r.isSystemRole = false OR r.assignable = true)")
    List<Role> findAssignableRoles();
    
    /**
     * 查找需要权限同步的角色（最近有权限变更）
     */
    @Query("SELECT r FROM Role r WHERE r.permissionsUpdatedAt IS NOT NULL AND r.permissionsUpdatedAt > :since")
    List<Role> findRolesWithRecentPermissionChanges(@Param("since") java.time.LocalDateTime since);
    
    /**
     * 根据角色级别查找角色
     */
    List<Role> findByRoleLevel(Integer roleLevel);
    
    /**
     * 查找指定级别及以上的角色
     */
    @Query("SELECT r FROM Role r WHERE r.roleLevel >= :minLevel")
    List<Role> findRolesByMinLevel(@Param("minLevel") Integer minLevel);
    
    /**
     * 查找指定级别及以下的角色
     */
    @Query("SELECT r FROM Role r WHERE r.roleLevel <= :maxLevel")
    List<Role> findRolesByMaxLevel(@Param("maxLevel") Integer maxLevel);
    
    /**
     * 查找有继承关系的父角色
     */
    @Query("SELECT r FROM Role r WHERE r.parentRole IS NOT NULL")
    List<Role> findChildRoles();
    
    /**
     * 查找没有继承关系的根角色
     */
    @Query("SELECT r FROM Role r WHERE r.parentRole IS NULL")
    List<Role> findRootRoles();
    
    /**
     * 查找特定应用域的角色
     */
    List<Role> findByDomain(String domain);
    
    /**
     * 查找默认角色（新用户自动分配的角色）
     */
    List<Role> findByIsDefaultRoleTrue();
}