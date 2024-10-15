package com.bigboss.useramjobstore.common;

import java.util.List;

/**
 * @author: maifuwa
 * @date: 2024/10/11 16:21
 * @description: 简洁分页包装
 */
public record ConcisePage<T>(List<T> content, int pageNumber, long totalPages) {

    public static <T> ConcisePage<T> of(List<T> content, int pageNumber, long totalPages) {
        return new ConcisePage<>(content, pageNumber, totalPages);
    }
}
