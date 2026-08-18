package com.softdreams.intern.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateScoreRequest {
    @NotNull
    @DecimalMin(value = "0.0", message = "Điểm quá trình không được nhỏ hơn 0")
    @DecimalMax(value = "10.0", message = "Điểm quá trình không được lớn hơn 10")
    Double processScore;

    @NotNull
    @DecimalMin(value = "0.0", message = "Điểm thành phần không được nhỏ hơn 0")
    @DecimalMax(value = "10.0", message = "Điểm thành phần không được lớn hơn 10")
    Double componentScore;

    @NotNull(message = "ID Sinh viên không được để trống")
    Long studentId;

    @NotNull(message = "ID Môn học không được để trống")
    Long subjectId;
}
