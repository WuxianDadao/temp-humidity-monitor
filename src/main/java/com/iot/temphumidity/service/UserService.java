package com.iot.temphumidity.service;

import com.iot.temphumidity.dto.user.UserCreateDTO;
import com.iot.temphumidity.dto.user.UserDTO;
import com.iot.temphumidity.dto.user.UserUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 用户管理服务接口
 * 定义用户相关的业务操作
 */
public interface UserService {

    /**
     * 创建新用户
     * @param userCreateDTO 用户创建信息
     * @return 创建后的用户信息
     */
    UserDTO createUser(UserCreateDTO userCreateDTO);

    /**
     * 更新用户信息
     * @param userId 用户ID
     * @param userUpdateDTO 用户更新信息
     * @return 更新后的用户信息
     */
    UserDTO updateUser(Long userId, UserUpdateDTO userUpdateDTO);

    /**
     * 获取用户详情
     * @param userId 用户ID
     * @return 用户详情
     */
    UserDTO getUserById(Long userId);

    /**
     * 根据用户名获取用户
     * @param username 用户名
     * @return 用户信息
     */
    UserDTO getUserByUsername(String username);

    /**
     * 分页查询用户列表
     * @param pageable 分页参数
     * @return 用户分页列表
     */
    Page<UserDTO> getUsers(Pageable pageable);

    /**
     * 根据组织ID分页查询用户
     * @param organizationId 组织ID
     * @param pageable 分页参数
     * @return 用户分页列表
     */
    Page<UserDTO> getUsersByOrganization(Long organizationId, Pageable pageable);

    /**
     * 删除用户
     * @param userId 用户ID
     */
    void deleteUser(Long userId);

    /**
     * 启用/禁用用户
     * @param userId 用户ID
     * @param enabled 是否启用
     */
    void toggleUserStatus(Long userId, boolean enabled);

    /**
     * 验证用户密码
     * @param username 用户名
     * @param password 密码
     * @return 验证结果
     */
    boolean validatePassword(String username, String password);

    /**
     * 重置用户密码
     * @param userId 用户ID
     * @param newPassword 新密码
     */
    void resetPassword(Long userId, String newPassword);

    /**
     * 分配角色给用户
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    void assignRole(Long userId, Long roleId);

    /**
     * 移除用户角色
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    void removeRole(Long userId, Long roleId);
}