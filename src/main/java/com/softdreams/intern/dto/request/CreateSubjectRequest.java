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

    @NotNull(message = "ID Sinh viên không được để trống")
    @Min(value = 30, message = "Số tiết học phải từ {value}")
    Integer totalLesson;

    @NotNull(message = "ID Sinh viên không được để trống")
    @DecimalMin(value = "0.0", message = "Trọng số quá trình không được nhỏ hơn 0")
    @DecimalMax(value = "1.0", message = "Trọng số quá trình không được lớn hơn 1.0")
    @Digits(integer = 1, fraction = 1, message = "Trọng số quá trình chỉ tối đa 1 chữ số thập phân")
    Double processWeight;

    @NotNull(message = "ID Sinh viên không được để trống")
    @DecimalMin(value = "0.0", message = "Trọng số thành phần không được nhỏ hơn 0")
    @DecimalMax(value = "1.0", message = "Trọng số thành phần không được lớn hơn 1.0")
    @Digits(integer = 1, fraction = 1, message = "Trọng số thành phần chỉ tối đa 1 chữ số thập phân")
    Double componentWeight;
}
