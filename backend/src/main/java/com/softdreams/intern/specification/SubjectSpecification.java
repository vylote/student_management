package com.softdreams.intern.specification;

import com.softdreams.intern.entity.Subject;
import com.softdreams.intern.entity.Subject_;
import org.springframework.data.jpa.domain.Specification;

public final class SubjectSpecification {

    private SubjectSpecification() {}

    public static Specification<Subject> hasCode(String code) {
        return SpecUtils.equalTo(Subject_.CODE, code);
    }

    public static Specification<Subject> hasNameLike(String name) {
        return SpecUtils.likeIgnoreCase(Subject_.NAME, name);
    }
}