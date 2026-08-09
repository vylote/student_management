package com.softdreams.intern.dto.request;

import com.softdreams.intern.validation.StrongPassword;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssignAccountRequest {
    @StrongPassword
    String password;
}
