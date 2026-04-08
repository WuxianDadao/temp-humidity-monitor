package com.iot.temphumidity.dto.common;

import lombok.Data;
import java.util.List;

@Data
public class Pagination<T> {
    private List<T> items;
    private int page;
    private int size;
    private long total;
    private int totalPages;
}
