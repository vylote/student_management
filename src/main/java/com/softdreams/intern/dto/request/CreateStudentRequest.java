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
public class CreateStudentRequest {
    @NotBlank(message = "Mã sinh viên không được để trống")
    @Pattern(regexp = "^SV\\d{3,}$", message = "Mã sinh viên phải có định dạng SVxxx (ít nhất 3 số, ví dụ: SV001)")
    String code;

    @NotBlank(message = "Họ và tên không được để trống")
    String fullName;

    @NotBlank(message = "Giới tính không được để trống")
    String gender;

    @NotNull(message = "Ngày sinh không được để trống")
    @ValidAge(min = 18, message = "Sinh viên phải từ 18 tuổi trở lên")
    LocalDate dateOfBirth;

    @NotBlank(message = "Lớp học không được để trống")
    String classroom;

    @NotBlank(message = "Khóa học không được để trống")
    String cohort;

}
