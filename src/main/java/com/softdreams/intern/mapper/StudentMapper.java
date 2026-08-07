package com.softdreams.intern.mapper;

import com.softdreams.intern.dto.request.CreateStudentRequest;
import com.softdreams.intern.dto.response.StudentResponse;
import com.softdreams.intern.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "account", ignore = true)
    @Mapping(target = "scores", ignore = true)
    Student toStudent(CreateStudentRequest request);

    StudentResponse toResponse(Student student);
}
