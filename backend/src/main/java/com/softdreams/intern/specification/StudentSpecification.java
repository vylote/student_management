package com.softdreams.intern.specification;

import com.softdreams.intern.entity.Student;
import com.softdreams.intern.entity.Student_;
import org.springframework.data.jpa.domain.Specification;

public final class StudentSpecification {

    private StudentSpecification() {
        /* This utility class should not be instantiated */
    }

    public static Specification<Student> hasNameLike(String name) {
        return SpecUtils.likeIgnoreCase(Student_.FULL_NAME, name);
    }

    public static Specification<Student> hasCode(String code) {
        return SpecUtils.equalTo(Student_.CODE, code);
    }

    public static Specification<Student> hasCohort(String cohort) {
        return SpecUtils.equalTo(Student_.COHORT, cohort);
    }

    public static Specification<Student> hasClass(String classroom) {
        return SpecUtils.equalTo(Student_.CLASSROOM, classroom);
    }

    public static Specification<Student> hasAccount(Boolean hasAccount) {
        if (hasAccount == null) {
            return SpecUtils.empty();
        }
        return (root, query, cb) -> hasAccount
                ? cb.isNotNull(root.get(Student_.ACCOUNT))
                : cb.isNull(root.get(Student_.ACCOUNT));
    }

}

