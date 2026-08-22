package com.softdreams.intern.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public final class SpecUtils {

    private SpecUtils() {}

    public static <T> Specification<T> empty() {
        return (root, query, cb) -> null;
    }

    public static <T> Specification<T> equalTo(String field, Object value) {
        if (value == null || (value instanceof String str && !StringUtils.hasText(str))) {
            return empty();
        }
        return (root, query, cb) -> {
            Object finalValue = value instanceof String str ? str.trim() : value;
            return cb.equal(root.get(field), finalValue);
        };
    }

    public static <T> Specification<T> likeIgnoreCase(String field, String value) {
        if (!StringUtils.hasText(value)) {
            return empty();
        }
        return (root, query, cb) -> cb.like(
                cb.lower(root.get(field)),
                "%" + value.trim().toLowerCase() + "%"
        );
    }
}
