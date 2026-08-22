package com.softdreams.intern.specification;

import com.softdreams.intern.entity.Teacher;
import com.softdreams.intern.entity.Teacher_;
import org.springframework.data.jpa.domain.Specification;

public final class TeacherSpecification {

    private TeacherSpecification() {}

    public static Specification<Teacher> hasCode(String code) {
        return SpecUtils.equalTo(Teacher_.CODE, code);
    }

    public static Specification<Teacher> hasFullNameLike(String fullName) {
        return SpecUtils.likeIgnoreCase(Teacher_.FULL_NAME, fullName);
    }

    public static Specification<Teacher> hasGender(String gender) {
        return SpecUtils.equalTo(Teacher_.GENDER, gender);
    }

    public static Specification<Teacher> hasDepartment(String department) {
        return SpecUtils.equalTo(Teacher_.DEPARTMENT, department);
    }
}