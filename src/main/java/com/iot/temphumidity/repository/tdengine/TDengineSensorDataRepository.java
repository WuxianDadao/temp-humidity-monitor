package com.iot.temphumidity.repository.tdengine;

import com.iot.temphumidity.dto.sensor.SensorDataHistoryDTO;
import com.iot.temphumidity.dto.sensor.SensorDataRealtimeDTO;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TDengineSensorDataRepository {
    
    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.taosdata.jdbc.rs.RestfulDriver");
            return DriverManager.getConnection("jdbc:TAOS-RS://localhost:6041/iot_db", "root", "taosdata");
        } catch (ClassNotFoundException e) {
            throw new SQLException("TDengine JDBC driver not found", e);
        }
    }
    
    public void insertSensorData(Long sensorTagId, String sensorSerialNumber, 
                                LocalDateTime timestamp, Double temperature, Double humidity,
                                Double battery, Integer rssi, Double longitude, Double latitude,
                                Integer dataQuality, String rawData) throws SQLException {
        String sql = "INSERT INTO sensor_data (ts, sensor_id, device_id, location, sensor_type, " +
                    "temperature, humidity, battery, rssi, longitude, latitude, data_quality, raw_data) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setTimestamp(1, Timestamp.valueOf(timestamp));
            pstmt.setString(2, sensorSerialNumber);
            pstmt.setString(3, "device_" + sensorTagId);
            pstmt.setString(4, "default_location");
            pstmt.setString(5, "temperature_humidity");
            pstmt.setDouble(6, temperature);
            pstmt.setDouble(7, humidity);
            pstmt.setDouble(8, battery);
            pstmt.setInt(9, rssi);
            pstmt.setDouble(10, longitude);
            pstmt.setDouble(11, latitude);
            pstmt.setInt(12, dataQuality);
            pstmt.setString(13, rawData);
            
            pstmt.executeUpdate();
        }
    }
    
    public List<Map<String, Object>> querySensorDataHistory(SensorDataHistoryDTO queryDTO) throws SQLException {
        List<Map<String, Object>> result = new ArrayList<>();
        
        StringBuilder sql = new StringBuilder("SELECT ");
        
        if (queryDTO.getAggregate() != null && queryDTO.getAggregate()) {
            String aggFunc = queryDTO.getAggregationFunction() != null ? 
                           queryDTO.getAggregationFunction() : "AVG";
            sql.append(aggFunc).append("(temperature) as avg_temperature, ")
               .append(aggFunc).append("(humidity) as avg_humidity, ")
               .append("COUNT(*) as data_count, ")
               .append("MIN(temperature) as min_temperature, ")
               .append("MAX(temperature) as max_temperature, ")
               .append("MIN(humidity) as min_humidity, ")
               .append("MAX(humidity) as max_humidity, ")
               .append("TBNAME as sensor_id, ")
               .append("INTERVAL(");
            
            if (queryDTO.getIntervalMinutes() != null) {
                sql.append(queryDTO.getIntervalMinutes()).append("m");
            } else {
                sql.append("1h");
            }
            
            sql.append(") as time_window ");
        } else {
            sql.append("ts, sensor_id, temperature, humidity, battery, rssi, longitude, latitude, data_quality ");
        }
        
        sql.append("FROM sensor_data WHERE 1=1 ");
        
        if (queryDTO.getSensorTagId() != null) {
            sql.append("AND sensor_id = 'sensor_").append(queryDTO.getSensorTagId()).append("' ");
        }
        
        if (queryDTO.getStartTime() != null) {
            sql.append("AND ts >= '").append(queryDTO.getStartTime()).append("' ");
        }
        
        if (queryDTO.getEndTime() != null) {
            sql.append("AND ts <= '").append(queryDTO.getEndTime()).append("' ");
        }
        
        if (queryDTO.getAggregate() != null && queryDTO.getAggregate()) {
            sql.append("GROUP BY TBNAME, INTERVAL(");
            if (queryDTO.getIntervalMinutes() != null) {
                sql.append(queryDTO.getIntervalMinutes()).append("m");
            } else {
                sql.append("1h");
            }
            sql.append(") ");
        }
        
        if (queryDTO.getOrderBy() != null) {
            sql.append("ORDER BY ts ").append(queryDTO.getOrderBy());
        } else {
            sql.append("ORDER BY ts DESC");
        }
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = rs.getObject(i);
                    row.put(columnName, value);
                }
                result.add(row);
            }
        }
        
        return result;
    }
    
    public SensorDataRealtimeDTO getLatestSensorData(Long sensorTagId) throws SQLException {
        String sql = "SELECT ts, temperature, humidity, battery, rssi " +
                    "FROM sensor_data " +
                    "WHERE sensor_id = ? " +
                    "ORDER BY ts DESC " +
                    "LIMIT 1";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "sensor_" + sensorTagId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    SensorDataRealtimeDTO dto = new SensorDataRealtimeDTO();
                    dto.setSensorTagId(sensorTagId);
                    dto.setLatestTimestamp(rs.getTimestamp("ts").toLocalDateTime());
                    dto.setLatestTemperature(rs.getDouble("temperature"));
                    dto.setLatestHumidity(rs.getDouble("humidity"));
                    dto.setLatestBattery(rs.getDouble("battery"));
                    dto.setLatestRssi(rs.getInt("rssi"));
                    dto.setIsOnline(true);
                    dto.setLastOnlineTime(rs.getTimestamp("ts").toLocalDateTime());
                    
                    return dto;
                }
            }
        }
        
        return null;
    }
    
    public Long countSensorData(Long sensorTagId, LocalDateTime startTime, LocalDateTime endTime) throws SQLException {
        String sql = "SELECT COUNT(*) as total " +
                    "FROM sensor_data " +
                    "WHERE sensor_id = ? " +
                    "AND ts >= ? " +
                    "AND ts <= ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "sensor_" + sensorTagId);
            pstmt.setTimestamp(2, Timestamp.valueOf(startTime));
            pstmt.setTimestamp(3, Timestamp.valueOf(endTime));
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("total");
                }
            }
        }
        
        return 0L;
    }
}