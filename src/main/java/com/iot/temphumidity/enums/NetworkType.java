package com.iot.temphumidity.enums;

import lombok.Getter;

@Getter
public enum NetworkType {
    WIFI("Wi-Fi", 1),
    ETHERNET("以太网", 2),
    LTE("4G LTE", 3),
    GPRS("2G GPRS", 4),
    NB_IOT("NB-IoT", 5);

    private final String description;
    private final int code;

    NetworkType(String description, int code) {
        this.description = description;
        this.code = code;
    }
}
