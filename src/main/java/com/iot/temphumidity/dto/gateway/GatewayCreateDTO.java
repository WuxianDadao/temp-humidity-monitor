package com.iot.temphumidity.dto.gateway;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "网关创建数据传输对象")
public class GatewayCreateDTO {
    
    @Schema(description = "网关名称", example = "4G网关-001")
    @NotBlank(message = "网关名称不能为空")
    @Size(max = 100, message = "网关名称不能超过100个字符")
    private String gatewayName;
    
    @Schema(description = "网关设备编号", example = "GW001")
    @NotBlank(message = "网关设备编号不能为空")
    @Size(max = 50, message = "网关设备编号不能超过50个字符")
    private String gatewaySerialNumber;
    
    @Schema(description = "SIM卡ICCID", example = "89860000000000000000")
    @NotBlank(message = "SIM卡ICCID不能为空")
    @Size(min = 20, max = 20, message = "SIM卡ICCID必须是20位数字")
    private String simIccid;
    
    @Schema(description = "网关型号", example = "4G-IoT-Gateway-V2.0")
    private String gatewayModel;
    
    @Schema(description = "固件版本", example = "1.0.0")
    private String firmwareVersion;
    
    @Schema(description = "安装位置", example = "仓库A区")
    @Size(max = 200, message = "安装位置不能超过200个字符")
    private String location;
    
    @Schema(description = "经度", example = "116.397128")
    private Double longitude;
    
    @Schema(description = "纬度", example = "39.916527")
    private Double latitude;
    
    @Schema(description = "网络类型", example = "4G")
    private String networkType;
    
    @Schema(description = "运营商", example = "中国移动")
    private String carrier;
    
    @Schema(description = "心跳间隔（秒）", example = "300")
    private Integer heartbeatInterval;
    
    @Schema(description = "数据上报间隔（秒）", example = "60")
    private Integer dataReportInterval;
    
    @Schema(description = "备注信息")
    @Size(max = 500, message = "备注信息不能超过500个字符")
    private String remark;
}