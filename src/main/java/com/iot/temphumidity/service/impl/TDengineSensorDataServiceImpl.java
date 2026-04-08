package com.iot.temphumidity.service.impl;

import com.iot.temphumidity.entity.tdengine.SensorDataTD;
import com.iot.temphumidity.service.TDengineSensorDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TDengine传感器数据服务实现
 */
@Slf4j
@Service
public class TDengineSensorDataServiceImpl implements TDengineSensorDataService {
    
    private static final DateTimeFormatter TDENGINE_TIMESTAMP_FORMAT = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    
    @Autowired
    @Qualifier("tdengineJdbcTemplate")
    private JdbcTemplate tdengineJdbcTemplate;
    
    @Override
    @Transactional(transactionManager = "tdengineTransactionManager")
    public boolean insertSensorData(SensorDataTD sensorData) {
        try {
            String sql = "INSERT INTO iot_db.sensor_data (ts, temperature, humidity, pressure, battery, rssi, voltage, " +
                        "temperature_internal, humidity_internal, status_code, error_flags, data_quality, " +
                        "sample_rate, firmware_crc, raw_data, device_id, device_type, gateway_id, location, " +
                        "room_number, building, floor, zone, latitude, longitude, altitude, manufacturer, " +
                        "model, serial_number, firmware_version, hardware_version, calibration_date, " +
                        "calibration_due_date, installation_date, maintenance_interval, device_status, " +
                        "power_source, network_type, sampling_interval, reporting_interval, alarm_thresholds, " +
                        "custom_metadata) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            int affectedRows = tdengineJdbcTemplate.update(sql, 
                sensorData.getTimestamp() != null ? sensorData.getTimestamp().format(TDENGINE_TIMESTAMP_FORMAT) : null,
                sensorData.getTemperature(),
                sensorData.getHumidity(),
                sensorData.getPressure(),
                sensorData.getBattery(),
                sensorData.getRssi(),
                sensorData.getVoltage(),
                sensorData.getTemperatureInternal(),
                sensorData.getHumidityInternal(),
                sensorData.getStatusCode(),
                sensorData.getErrorFlags(),
                sensorData.getDataQuality(),
                sensorData.getSampleRate(),
                sensorData.getFirmwareCrc(),
                sensorData.getRawData(),
                // TAG字段
                sensorData.getDeviceId(),
                sensorData.getDeviceType(),
                sensorData.getGatewayId(),
                sensorData.getLocation(),
                sensorData.getRoomNumber(),
                sensorData.getBuilding(),
                sensorData.getFloor(),
                sensorData.getZone(),
                sensorData.getLatitude(),
                sensorData.getLongitude(),
                sensorData.getAltitude(),
                sensorData.getManufacturer(),
                sensorData.getModel(),
                sensorData.getSerialNumber(),
                sensorData.getFirmwareVersion(),
                sensorData.getHardwareVersion(),
                sensorData.getCalibrationDate() != null ? sensorData.getCalibrationDate().format(TDENGINE_TIMESTAMP_FORMAT) : null,
                sensorData.getCalibrationDueDate() != null ? sensorData.getCalibrationDueDate().format(TDENGINE_TIMESTAMP_FORMAT) : null,
                sensorData.getInstallationDate() != null ? sensorData.getInstallationDate().format(TDENGINE_TIMESTAMP_FORMAT) : null,
                sensorData.getMaintenanceInterval(),
                sensorData.getDeviceStatus(),
                sensorData.getPowerSource(),
                sensorData.getNetworkType(),
                sensorData.getSamplingInterval(),
                sensorData.getReportingInterval(),
                sensorData.getAlarmThresholds(),
                sensorData.getCustomMetadata()
            );
            
            return affectedRows > 0;
        } catch (Exception e) {
            log.error("插入传感器数据失败: {}", e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    @Transactional(transactionManager = "tdengineTransactionManager")
    public int batchInsertSensorData(List<SensorDataTD> sensorDataList) {
        if (sensorDataList == null || sensorDataList.isEmpty()) {
            return 0;
        }
        
        try {
            String sql = "INSERT INTO iot_db.sensor_data (ts, temperature, humidity, pressure, battery, rssi, voltage, " +
                        "temperature_internal, humidity_internal, status_code, error_flags, data_quality, " +
                        "sample_rate, firmware_crc, raw_data, device_id, device_type, gateway_id, location, " +
                        "room_number, building, floor, zone, latitude, longitude, altitude, manufacturer, " +
                        "model, serial_number, firmware_version, hardware_version, calibration_date, " +
                        "calibration_due_date, installation_date, maintenance_interval, device_status, " +
                        "power_source, network_type, sampling_interval, reporting_interval, alarm_thresholds, " +
                        "custom_metadata) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            List<Object[]> batchArgs = new ArrayList<>();
            for (SensorDataTD data : sensorDataList) {
                Object[] args = new Object[] {
                    data.getTimestamp() != null ? data.getTimestamp().format(TDENGINE_TIMESTAMP_FORMAT) : null,
                    data.getTemperature(),
                    data.getHumidity(),
                    data.getPressure(),
                    data.getBattery(),
                    data.getRssi(),
                    data.getVoltage(),
                    data.getTemperatureInternal(),
                    data.getHumidityInternal(),
                    data.getStatusCode(),
                    data.getErrorFlags(),
                    data.getDataQuality(),
                    data.getSampleRate(),
                    data.getFirmwareCrc(),
                    data.getRawData(),
                    // TAG字段
                    data.getDeviceId(),
                    data.getDeviceType(),
                    data.getGatewayId(),
                    data.getLocation(),
                    data.getRoomNumber(),
                    data.getBuilding(),
                    data.getFloor(),
                    data.getZone(),
                    data.getLatitude(),
                    data.getLongitude(),
                    data.getAltitude(),
                    data.getManufacturer(),
                    data.getModel(),
                    data.getSerialNumber(),
                    data.getFirmwareVersion(),
                    data.getHardwareVersion(),
                    data.getCalibrationDate() != null ? data.getCalibrationDate().format(TDENGINE_TIMESTAMP_FORMAT) : null,
                    data.getCalibrationDueDate() != null ? data.getCalibrationDueDate().format(TDENGINE_TIMESTAMP_FORMAT) : null,
                    data.getInstallationDate() != null ? data.getInstallationDate().format(TDENGINE_TIMESTAMP_FORMAT) : null,
                    data.getMaintenanceInterval(),
                    data.getDeviceStatus(),
                    data.getPowerSource(),
                    data.getNetworkType(),
                    data.getSamplingInterval(),
                    data.getReportingInterval(),
                    data.getAlarmThresholds(),
                    data.getCustomMetadata()
                };
                batchArgs.add(args);
            }
            
            int[] results = tdengineJdbcTemplate.batchUpdate(sql, batchArgs);
            int totalInserted = 0;
            for (int result : results) {
                if (result > 0) {
                    totalInserted++;
                }
            }
            
            log.info("批量插入传感器数据完成，成功插入 {} 条记录", totalInserted);
            return totalInserted;
        } catch (Exception e) {
            log.error("批量插入传感器数据失败: {}", e.getMessage(), e);
            return 0;
        }
    }
    
    @Override
    public List<SensorDataTD> queryRecentData(String deviceId, int limit) {
        try {
            String sql = "SELECT * FROM iot_db.sensor_data " +
                        "WHERE device_id = ? " +
                        "ORDER BY ts DESC " +
                        "LIMIT ?";
            
            return tdengineJdbcTemplate.query(sql, new SensorDataRowMapper(), deviceId, limit);
        } catch (Exception e) {
            log.error("查询最近数据失败: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }
    
    @Override
    public List<SensorDataTD> queryDataByTimeRange(String deviceId, LocalDateTime startTime, LocalDateTime endTime) {
        try {
            String sql = "SELECT * FROM iot_db.sensor_data " +
                        "WHERE device_id = ? AND ts >= ? AND ts <= ? " +
                        "ORDER BY ts ASC";
            
            String startTimeStr = startTime.format(TDENGINE_TIMESTAMP_FORMAT);
            String endTimeStr = endTime.format(TDENGINE_TIMESTAMP_FORMAT);
            
            return tdengineJdbcTemplate.query(sql, new SensorDataRowMapper(), 
                deviceId, startTimeStr, endTimeStr);
        } catch (Exception e) {
            log.error("查询时间段数据失败: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }
    
    @Override
    public Map<String, Object> getDeviceStatistics(String deviceId, LocalDateTime startTime, LocalDateTime endTime) {
        Map<String, Object> stats = new HashMap<>();
        
        try {
            String startTimeStr = startTime.format(TDENGINE_TIMESTAMP_FORMAT);
            String endTimeStr = endTime.format(TDENGINE_TIMESTAMP_FORMAT);
            
            // 统计温度
            String tempStatsSql = "SELECT " +
                                 "COUNT(*) as count, " +
                                 "AVG(temperature) as avg_temp, " +
                                 "MAX(temperature) as max_temp, " +
                                 "MIN(temperature) as min_temp, " +
                                 "STDDEV(temperature) as std_temp " +
                                 "FROM iot_db.sensor_data " +
                                 "WHERE device_id = ? AND ts >= ? AND ts <= ? " +
                                 "AND temperature IS NOT NULL";
            
            Map<String, Object> tempStats = tdengineJdbcTemplate.queryForMap(tempStatsSql, 
                deviceId, startTimeStr, endTimeStr);
            
            // 统计湿度
            String humidityStatsSql = "SELECT " +
                                     "AVG(humidity) as avg_humidity, " +
                                     "MAX(humidity) as max_humidity, " +
                                     "MIN(humidity) as min_humidity " +
                                     "FROM iot_db.sensor_data " +
                                     "WHERE device_id = ? AND ts >= ? AND ts <= ? " +
                                     "AND humidity IS NOT NULL";
            
            Map<String, Object> humidityStats = tdengineJdbcTemplate.queryForMap(humidityStatsSql, 
                deviceId, startTimeStr, endTimeStr);
            
            // 数据点数量
            String countSql = "SELECT COUNT(*) as total_count FROM iot_db.sensor_data " +
                             "WHERE device_id = ? AND ts >= ? AND ts <= ?";
            
            Long totalCount = tdengineJdbcTemplate.queryForObject(countSql, Long.class, 
                deviceId, startTimeStr, endTimeStr);
            
            stats.put("deviceId", deviceId);
            stats.put("timeRange", startTimeStr + " - " + endTimeStr);
            stats.put("totalDataPoints", totalCount);
            stats.put("temperatureStats", tempStats);
            stats.put("humidityStats", humidityStats);
            
        } catch (Exception e) {
            log.error("获取设备统计信息失败: {}", e.getMessage(), e);
            stats.put("error", e.getMessage());
        }
        
        return stats;
    }
    
    @Override
    public List<SensorDataTD> queryRealTimeData(List<String> deviceIds) {
        if (deviceIds == null || deviceIds.isEmpty()) {
            return new ArrayList<>();
        }
        
        try {
            // 构建IN查询的占位符
            StringBuilder placeholders = new StringBuilder();
            for (int i = 0; i < deviceIds.size(); i++) {
                if (i > 0) placeholders.append(",");
                placeholders.append("?");
            }
            
            String sql = "SELECT t1.* FROM iot_db.sensor_data t1 " +
                        "INNER JOIN ( " +
                        "    SELECT device_id, MAX(ts) as latest_ts " +
                        "    FROM iot_db.sensor_data " +
                        "    WHERE device_id IN (" + placeholders + ") " +
                        "    GROUP BY device_id " +
                        ") t2 ON t1.device_id = t2.device_id AND t1.ts = t2.latest_ts";
            
            return tdengineJdbcTemplate.query(sql, new SensorDataRowMapper(), deviceIds.toArray());
        } catch (Exception e) {
            log.error("查询实时数据失败: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }
    
    @Override
    public List<SensorDataTD> queryAlarmHistoryData(String deviceId, LocalDateTime alarmTime, int hoursBefore) {
        try {
            LocalDateTime startTime = alarmTime.minusHours(hoursBefore);
            LocalDateTime endTime = alarmTime;
            
            String startTimeStr = startTime.format(TDENGINE_TIMESTAMP_FORMAT);
            String endTimeStr = endTime.format(TDENGINE_TIMESTAMP_FORMAT);
            
            String sql = "SELECT * FROM iot_db.sensor_data " +
                        "WHERE device_id = ? AND ts >= ? AND ts <= ? " +
                        "ORDER BY ts ASC";
            
            return tdengineJdbcTemplate.query(sql, new SensorDataRowMapper(), 
                deviceId, startTimeStr, endTimeStr);
        } catch (Exception e) {
            log.error("查询报警历史数据失败: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }
    
    @Override
    public boolean checkDeviceConnection(String deviceId) {
        try {
            // 检查设备最近是否有数据
            String sql = "SELECT COUNT(*) FROM iot_db.sensor_data " +
                        "WHERE device_id = ? AND ts >= DATE_SUB(NOW(), INTERVAL 1 HOUR)";
            
            Long count = tdengineJdbcTemplate.queryForObject(sql, Long.class, deviceId);
            return count != null && count > 0;
        } catch (Exception e) {
            log.error("检查设备连接状态失败: {}", e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    @Transactional(transactionManager = "tdengineTransactionManager")
    public int cleanupOldData(int daysToKeep) {
        try {
            String sql = "DELETE FROM iot_db.sensor_data " +
                        "WHERE ts < DATE_SUB(NOW(), INTERVAL ? DAY)";
            
            int deletedRows = tdengineJdbcTemplate.update(sql, daysToKeep);
            log.info("清理过期数据完成，删除 {} 天前的数据，共删除 {} 条记录", daysToKeep, deletedRows);
            return deletedRows;
        } catch (Exception e) {
            log.error("清理过期数据失败: {}", e.getMessage(), e);
            return 0;
        }
    }
    
    /**
     * SensorDataTD行的映射器
     */
    private static class SensorDataRowMapper implements RowMapper<SensorDataTD> {
        @Override
        public SensorDataTD mapRow(ResultSet rs, int rowNum) throws SQLException {
            SensorDataTD data = new SensorDataTD();
            
            // 解析时间戳
            String tsStr = rs.getString("ts");
            if (tsStr != null) {
                try {
                    data.setTimestamp(LocalDateTime.parse(tsStr, TDENGINE_TIMESTAMP_FORMAT));
                } catch (Exception e) {
                    log.warn("解析时间戳失败: {}", tsStr);
                }
            }
            
            // 设置字段值
            data.setTemperature(rs.getFloat("temperature"));
            data.setHumidity(rs.getFloat("humidity"));
            data.setPressure(rs.getFloat("pressure"));
            data.setBattery(rs.getFloat("battery"));
            data.setRssi(rs.getInt("rssi"));
            data.setVoltage(rs.getFloat("voltage"));
            data.setTemperatureInternal(rs.getFloat("temperature_internal"));
            data.setHumidityInternal(rs.getFloat("humidity_internal"));
            data.setStatusCode(rs.getShort("status_code"));
            data.setErrorFlags(rs.getInt("error_flags"));
            data.setDataQuality(rs.getByte("data_quality"));
            data.setSampleRate(rs.getInt("sample_rate"));
            data.setFirmwareCrc(rs.getInt("firmware_crc"));
            data.setRawData(rs.getBytes("raw_data"));
            
            // TAG字段
            data.setDeviceId(rs.getString("device_id"));
            data.setDeviceType(rs.getString("device_type"));
            data.setGatewayId(rs.getString("gateway_id"));
            data.setLocation(rs.getString("location"));
            data.setRoomNumber(rs.getString("room_number"));
            data.setBuilding(rs.getString("building"));
            data.setFloor(rs.getString("floor"));
            data.setZone(rs.getString("zone"));
            data.setLatitude(rs.getDouble("latitude"));
            data.setLongitude(rs.getDouble("longitude"));
            data.setAltitude(rs.getFloat("altitude"));
            data.setManufacturer(rs.getString("manufacturer"));
            data.setModel(rs.getString("model"));
            data.setSerialNumber(rs.getString("serial_number"));
            data.setFirmwareVersion(rs.getString("firmware_version"));
            data.setHardwareVersion(rs.getString("hardware_version"));
            
            // 解析TAG时间字段
            String calibrationDateStr = rs.getString("calibration_date");
            if (calibrationDateStr != null) {
                try {
                    data.setCalibrationDate(LocalDateTime.parse(calibrationDateStr, TDENGINE_TIMESTAMP_FORMAT));
                } catch (Exception e) {
                    log.warn("解析校准日期失败: {}", calibrationDateStr);
                }
            }
            
            String calibrationDueDateStr = rs.getString("calibration_due_date");
            if (calibrationDueDateStr != null) {
                try {
                    data.setCalibrationDueDate(LocalDateTime.parse(calibrationDueDateStr, TDENGINE_TIMESTAMP_FORMAT));
                } catch (Exception e) {
                    log.warn("解析校准到期日期失败: {}", calibrationDueDateStr);
                }
            }
            
            String lastMaintenanceDateStr = rs.getString("last_maintenance_date");
            if (lastMaintenanceDateStr != null) {
                try {
                    data.setLastMaintenanceDate(LocalDateTime.parse(lastMaintenanceDateStr, TDENGINE_TIMESTAMP_FORMAT));
                } catch (Exception e) {
                    log.warn("解析最后维护日期失败: {}", lastMaintenanceDateStr);
                }
            }
            
            String nextMaintenanceDateStr = rs.getString("next_maintenance_date");
            if (nextMaintenanceDateStr != null) {
                try {
                    data.setNextMaintenanceDate(LocalDateTime.parse(nextMaintenanceDateStr, TDENGINE_TIMESTAMP_FORMAT));
                } catch (Exception e) {
                    log.warn("解析下次维护日期失败: {}", nextMaintenanceDateStr);
                }
            }
            
            String installationDateStr = rs.getString("installation_date");
            if (installationDateStr != null) {
                try {
                    data.setInstallationDate(LocalDateTime.parse(installationDateStr, TDENGINE_TIMESTAMP_FORMAT));
                } catch (Exception e) {
                    log.warn("解析安装日期失败: {}", installationDateStr);
                }
            }
            
            // 其他TAG字段
            data.setInstallationLocation(rs.getString("installation_location"));
            data.setInstallationPerson(rs.getString("installation_person"));
            data.setMaintenanceContact(rs.getString("maintenance_contact"));
            data.setMaintenanceInterval(rs.getInt("maintenance_interval"));
            data.setCalibrationInterval(rs.getInt("calibration_interval"));
            data.setWarrantyPeriod(rs.getInt("warranty_period"));
            data.setPurchaseDate(rs.getString("purchase_date"));
            data.setSupplier(rs.getString("supplier"));
            data.setPurchasePrice(rs.getBigDecimal("purchase_price"));
            data.setDepreciationRate(rs.getBigDecimal("depreciation_rate"));
            data.setAssetNumber(rs.getString("asset_number"));
            data.setDepartment(rs.getString("department"));
            data.setResponsiblePerson(rs.getString("responsible_person"));
            data.setContactPhone(rs.getString("contact_phone"));
            data.setContactEmail(rs.getString("contact_email"));
            data.setNotes(rs.getString("notes"));
            data.setCustomField1(rs.getString("custom_field1"));
            data.setCustomField2(rs.getString("custom_field2"));
            data.setCustomField3(rs.getString("custom_field3"));
            data.setCustomField4(rs.getString("custom_field4"));
            data.setCustomField5(rs.getString("custom_field5"));
            
            return data;
        }
    }
}