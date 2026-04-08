package com.iot.temphumidity.repository;

import com.iot.temphumidity.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 组织Repository接口
 */
@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    
    /**
     * 根据组织代码查找组织
     */
    Optional<Organization> findByOrgCode(String orgCode);
    
    /**
     * 根据组织名称查找组织
     */
    Optional<Organization> findByOrgName(String orgName);
    
    /**
     * 根据组织类型查找组织列表
     */
    List<Organization> findByOrgType(Organization.OrgType orgType);
    
    /**
     * 根据父组织ID查找子组织列表
     */
    List<Organization> findByParentId(Long parentId);
    
    /**
     * 根据组织状态查找组织列表
     */
    List<Organization> findByStatus(Organization.OrgStatus status);
    
    /**
     * 检查组织代码是否已存在
     */
    boolean existsByOrgCode(String orgCode);
    
    /**
     * 检查组织名称是否已存在
     */
    boolean existsByOrgName(String orgName);
    
    /**
     * 查找启用的组织
     */
    List<Organization> findByEnabledTrue();
    
    /**
     * 查找禁用的组织
     */
    List<Organization> findByEnabledFalse();
    
    /**
     * 查找根组织（没有父组织的组织）
     */
    @Query("SELECT o FROM Organization o WHERE o.parentId IS NULL")
    List<Organization> findRootOrganizations();
    
    /**
     * 查找所有叶子组织（没有子组织的组织）
     */
    @Query("SELECT o FROM Organization o WHERE o.id NOT IN (SELECT DISTINCT o2.parentId FROM Organization o2 WHERE o2.parentId IS NOT NULL)")
    List<Organization> findLeafOrganizations();
    
    /**
     * 统计各组织的用户数量
     */
    @Query("SELECT o.id, o.orgName, COUNT(u.id) FROM Organization o LEFT JOIN User u ON u.organization.id = o.id GROUP BY o.id, o.orgName")
    List<Object[]> countUsersByOrganization();
    
    /**
     * 统计各组织的设备数量
     */
    @Query("SELECT o.id, o.orgName, COUNT(d.id) FROM Organization o LEFT JOIN Device d ON d.organization.id = o.id GROUP BY o.id, o.orgName")
    List<Object[]> countDevicesByOrganization();
    
    /**
     * 查找指定组织及其所有子组织
     */
    @Query(value = "WITH RECURSIVE org_tree AS (" +
           "SELECT * FROM organizations WHERE id = :orgId " +
           "UNION ALL " +
           "SELECT o.* FROM organizations o " +
           "INNER JOIN org_tree ot ON o.parent_id = ot.id" +
           ") SELECT * FROM org_tree", nativeQuery = true)
    List<Organization> findOrganizationTree(@Param("orgId") Long orgId);
    
    /**
     * 根据组织名称模糊搜索
     */
    List<Organization> findByOrgNameContaining(String keyword);
    
    /**
     * 根据组织代码模糊搜索
     */
    List<Organization> findByOrgCodeContaining(String keyword);
    
    /**
     * 根据联系人姓名模糊搜索
     */
    List<Organization> findByContactNameContaining(String keyword);
    
    /**
     * 根据联系人电话模糊搜索
     */
    List<Organization> findByContactPhoneContaining(String keyword);
    
    /**
     * 根据地址模糊搜索
     */
    List<Organization> findByAddressContaining(String keyword);
    
    /**
     * 查找有子组织的组织
     */
    @Query("SELECT o FROM Organization o WHERE o.id IN (SELECT DISTINCT o2.parentId FROM Organization o2 WHERE o2.parentId IS NOT NULL)")
    List<Organization> findOrganizationsWithChildren();
    
    /**
     * 查找最近创建的组织
     */
    @Query("SELECT o FROM Organization o ORDER BY o.createdAt DESC")
    List<Organization> findRecentlyCreatedOrganizations();
    
    /**
     * 查找需要续费的组织（订阅即将到期）
     */
    @Query("SELECT o FROM Organization o WHERE o.subscriptionEndDate IS NOT NULL AND o.subscriptionEndDate < :thresholdDate")
    List<Organization> findOrganizationsWithSubscriptionExpiring(@Param("thresholdDate") java.time.LocalDate thresholdDate);
    
    /**
     * 统计各类型组织数量
     */
    @Query("SELECT o.orgType, COUNT(o) FROM Organization o GROUP BY o.orgType")
    List<Object[]> countOrganizationsByType();
    
    /**
     * 统计各状态组织数量
     */
    @Query("SELECT o.status, COUNT(o) FROM Organization o GROUP BY o.status")
    List<Object[]> countOrganizationsByStatus();
    
    /**
     * 查找特定行业分类的组织
     */
    List<Organization> findByIndustryCategory(String industryCategory);
    
    /**
     * 根据组织规模查找组织
     */
    List<Organization> findByOrgScale(Organization.OrgScale orgScale);
}