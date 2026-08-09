package com.softdreams.intern.dto.request;

import com.softdreams.intern.validation.ValidSubjectWeight;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@ValidSubjectWeight
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateSubjectRequest {
    @NotNull(message = "Mã môn học không được để trống")
    String code;

    @NotBlank(message = "Tên môn học không được để trống")
    String name;

    @Min(value = 30, message = "Số tiết học phải lớn hơn 0")
    int totalLesson;

    @DecimalMin(value = "0.0", message = "Trọng số quá trình không được nhỏ hơn 0")
    @DecimalMax(value = "1.0", message = "Trọng số quá trình không được lớn hơn 1.0")
    double processWeight;

    @DecimalMin(value = "0.0", message = "Trọng số thành phần không được nhỏ hơn 0")
    @DecimalMax(value = "1.0", message = "Trọng số thành phần không được lớn hơn 1.0")
    double componentWeight;
}
