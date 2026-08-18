package com.softdreams.intern.dto.request;

import com.softdreams.intern.validation.ValidAge;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateTeacherRequest {
    @NotBlank(message = "Mã giảng viên không được để trống")
    @Pattern(regexp = "^GV\\d{3,}$", message = "Mã giảng viên phải có định dạng GVxxx (ít nhất 3 số, ví dụ: GV001)")
    String code;

    @NotBlank(message = "Họ và tên không được để trống")
    String fullName;

    @NotBlank(message = "Giới tính không được để trống")
    String gender;

    @NotNull(message = "Ngày sinh không được để trống")
    @ValidAge(min = 22, message = "Phải đủ {min} tuổi trở lên")
    LocalDate dateOfBirth;

    @NotBlank(message = "Phòng ban/Khoa không được để trống")
    String department;
}
