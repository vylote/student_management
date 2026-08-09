package com.softdreams.intern.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateScoreRequest {
    @NotNull(message = "ID Bảng điểm không được để trống")
    Long id;

    @DecimalMin(value = "0.0", message = "Điểm quá trình không được nhỏ hơn 0")
    @DecimalMax(value = "10.0", message = "Điểm quá trình không được lớn hơn 10")
    double processScore;

    @DecimalMin(value = "0.0", message = "Điểm thành phần không được nhỏ hơn 0")
    @DecimalMax(value = "10.0", message = "Điểm thành phần không được lớn hơn 10")
    double componentScore;

    @NotNull(message = "ID Sinh viên không được để trống")
    Long studentId;

    @NotNull(message = "ID Môn học không được để trống")
    Long subjectId;
}
