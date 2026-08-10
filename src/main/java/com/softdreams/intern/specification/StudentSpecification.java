package com.softdreams.intern.specification;

import com.softdreams.intern.entity.Student;
import com.softdreams.intern.entity.Student_;
import org.springframework.data.jpa.domain.Specification;

public final class StudentSpecification {
    public static Specification<Student> hasNameLike(String name) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get(Student_.FULL_NAME)), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Student> hasCode(String code) {
        return (root, query, cb) ->
                cb.equal(root.get(Student_.CODE), code);
    }

    public static Specification<Student> hasCohort(String cohort) {
        return (root, query, cb) ->
                cb.equal(root.get(Student_.COHORT), cohort);
    }

    public static Specification<Student> hasClass(String classroom) {
        return (root, query, cb) ->
                cb.equal(root.get(Student_.CLASSROOM), classroom);
    }

    public static Specification<Student> hasAccount(Boolean hasAccount) {
        return (root, query, cb) -> {
            if (hasAccount != null && hasAccount) {
                return cb.isNotNull(root.get(Student_.ACCOUNT));
            } else {
                return cb.isNull(root.get(Student_.ACCOUNT));
            }
        };
    }
}
