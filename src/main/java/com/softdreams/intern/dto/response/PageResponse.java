package com.softdreams.intern.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageResponse<T> {
    int currentPage;
    int pageSize;
    int totalPages;
    Long totalElements;
    boolean isLast;
    List<T> data;

    public static <T> PageResponse<T> of(Page<?> page, List<T> data) {
        return PageResponse.<T>builder()
                .currentPage(page.getNumber() + 1) // Cộng 1 vì Spring đếm từ 0
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .isLast(page.isLast())
                .data(data)
                .build();
    }
}
