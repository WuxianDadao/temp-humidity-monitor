package com.iot.temphumidity.dto.gateway;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "网关简单数据传输对象")
public class GatewaySimpleDTO {
    
    @Schema(description = "网关ID", example = "1")
    private Long id;
    
    @Schema(description = "网关序列号", example = "GW001")
    private String gatewaySn;
    
    @Schema(description = "网关名称", example = "4G网关-001")
    private String name;
    
    @Schema(description = "网关状态", example = "ACTIVE")
    private String status;
    
    @Schema(description = "是否在线", example = "true")
    private Boolean isOnline;
    
    @Schema(description = "位置", example = "仓库A区")
    private String location;
    
    @Schema(description = "位置描述", example = "东北角货架旁")
    private String locationDescription;
    
    @Schema(description = "最后上线时间")
    private LocalDateTime lastSeenAt;
    
    @Schema(description = "设备总数", example = "10")
    private Integer deviceCount;
    
    @Schema(description = "在线设备数", example = "8")
    private Integer onlineDeviceCount;
}