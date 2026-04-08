package com.iot.temphumidity.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 地理位置实体类
 * 对应PostgreSQL中的geographic_locations表
 */
@Entity
@Table(name = "geographic_locations",
       indexes = {
           @Index(name = "idx_geographic_locations_type", columnList = "location_type"),
           @Index(name = "idx_geographic_locations_parent", columnList = "parent_id"),
           @Index(name = "idx_geographic_locations_code", columnList = "code"),
           @Index(name = "idx_geographic_locations_geo", columnList = "latitude, longitude")
       },
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_geographic_locations_code", columnNames = {"location_type", "code"})
       })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GeographicLocation extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "location_type", nullable = false, length = 32)
    private String locationType;  // 位置类型
    
    @Column(name = "code", nullable = false, length = 32)
    private String code;  // 位置代码
    
    @Column(name = "name", nullable = false, length = 128)
    private String name;  // 位置名称
    
    @Column(name = "full_name", length = 256)
    private String fullName;  // 完整名称
    
    @Column(name = "parent_id")
    private Long parentId;  // 父位置ID
    
    @Column(name = "level")
    private Integer level;  // 层级
    
    @Column(name = "latitude", precision = 10, scale = 6)
    private BigDecimal latitude;  // 纬度
    
    @Column(name = "longitude", precision = 10, scale = 6)
    private BigDecimal longitude;  // 经度
    
    @Column(name = "altitude", precision = 8, scale = 2)
    private BigDecimal altitude;  // 海拔
    
    @Column(name = "timezone", length = 64)
    private String timezone;  // 时区
    
    @Column(name = "area_code", length = 16)
    private String areaCode;  // 区号
    
    @Column(name = "postal_code", length = 16)
    private String postalCode;  // 邮政编码
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;  // 描述
    
    @Column(name = "extra_data", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String extraData;  // 额外数据 (JSON)
    
    @Column(name = "boundary", columnDefinition = "TEXT")
    private String boundary;  // 边界信息
    
    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;  // 是否活跃
    
    @Column(name = "sort_order")
    @Builder.Default
    private Integer sortOrder = 0;  // 排序序号
    
    @Column(name = "created_by")
    private Long createdBy;  // 创建者
    
    @Column(name = "updated_by")
    private Long updatedBy;  // 更新者
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // 位置类型枚举
    public enum LocationType {
        COUNTRY("country"),      // 国家
        PROVINCE("province"),    // 省份
        CITY("city"),            // 城市
        DISTRICT("district"),    // 区县
        STREET("street"),        // 街道
        BUILDING("building"),    // 建筑
        FLOOR("floor"),          // 楼层
        ROOM("room"),            // 房间
        WAREHOUSE("warehouse"),  // 仓库
        FACTORY("factory"),      // 工厂
        DATA_CENTER("data_center"),  // 数据中心
        OTHER("other");          // 其他
        
        private final String value;
        
        LocationType(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
    }
    
    // 检查是否有坐标
    public boolean hasCoordinates() {
        return latitude != null && longitude != null;
    }
    
    // 获取坐标字符串
    public String getCoordinatesString() {
        if (hasCoordinates()) {
            return String.format("%.6f,%.6f", latitude, longitude);
        }
        return null;
    }
    
    // 计算两点之间的距离（简单球面距离，单位：米）
    public Double calculateDistance(BigDecimal otherLat, BigDecimal otherLon) {
        if (!hasCoordinates() || otherLat == null || otherLon == null) {
            return null;
        }
        
        double lat1 = Math.toRadians(latitude.doubleValue());
        double lon1 = Math.toRadians(longitude.doubleValue());
        double lat2 = Math.toRadians(otherLat.doubleValue());
        double lon2 = Math.toRadians(otherLon.doubleValue());
        
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2) + 
                   Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        
        // 地球半径（米）
        double r = 6371000;
        
        return c * r;
    }
    
    // 检查是否为叶子节点
    public boolean isLeaf() {
        // 这个方法需要在服务层实现，这里只是占位符
        return false;
    }
    
    // 获取层级路径
    public String getLevelPath() {
        // 这个方法需要在服务层实现，这里只是占位符
        return "";
    }
    
    // 预持久化回调
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        if (createdAt == null) {
            createdAt = now;
        }
        updatedAt = now;
    }
    
    // 预更新回调
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}