package com.bigboss.useramjobstore.common;

import java.util.List;

/**
 * @author: maifuwa
 * @date: 2024/10/11 16:21
 * @description:
 */
public record ConcisePage<T>(List<T> content, int pageNumber, long totalPages) {
}
