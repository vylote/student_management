package com.softdreams.intern.specification;

import com.softdreams.intern.entity.Student;
import com.softdreams.intern.entity.Student_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public final class StudentSpecification {

    private static final Specification<Student> EMPTY_SPEC = (root, query, cb) -> null;

    private StudentSpecification() {
        /* This utility class should not be instantiated */
    }

    public static Specification<Student> hasNameLike(String name) {
        return likeIgnoreCase(name);
    }

    public static Specification<Student> hasCode(String code) {
        return equalTo(Student_.CODE, code);
    }

    public static Specification<Student> hasCohort(String cohort) {
        return equalTo(Student_.COHORT, cohort);
    }

    public static Specification<Student> hasClass(String classroom) {
        return equalTo(Student_.CLASSROOM, classroom);
    }

    public static Specification<Student> hasAccount(Boolean hasAccount) {
        if (hasAccount == null) {
            return EMPTY_SPEC;
        }
        return (root, query, cb) -> hasAccount
                ? cb.isNotNull(root.get(Student_.ACCOUNT))
                : cb.isNull(root.get(Student_.ACCOUNT));
    }

    private static Specification<Student> equalTo(String field, Object value) {
        if (value == null || (value instanceof String str && !StringUtils.hasText(str))) {
            return EMPTY_SPEC;
        }
        return (root, query, cb) -> {
            // Trim khoảng trắng 2 đầu nếu nó là String
            Object finalValue = value instanceof String str ? (str).trim() : value;
            return cb.equal(root.get(field), finalValue);
        };
    }

    private static Specification<Student> likeIgnoreCase(String value) {
        if (!StringUtils.hasText(value)) {
            return EMPTY_SPEC;
        }
        return (root, query, cb) -> cb.like(
                cb.lower(root.get(Student_.FULL_NAME)),
                "%" + value.trim().toLowerCase() + "%");
    }
}

