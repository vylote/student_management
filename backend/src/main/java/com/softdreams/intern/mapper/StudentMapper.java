package com.softdreams.intern.mapper;

import com.softdreams.intern.dto.request.CreateStudentRequest;
import com.softdreams.intern.dto.response.StudentResponse;
import com.softdreams.intern.entity.Student;

import java.util.List;

public interface StudentMapper {

    Student toStudent(CreateStudentRequest request);

    StudentResponse toResponse(Student student);

    List<StudentResponse> toResponseList(List<Student> students);
}
