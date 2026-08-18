package com.softdreams.intern.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class UsernameGenerator {

    private static final Pattern DIACRITICS_PATTERN =
            Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

    private UsernameGenerator() {}

    public static String generate(String fullName, String code) {
        // 1. Tách lấy từ cuối cùng trong họ tên
        String[] parts = fullName.trim().split("\\s+");
        String givenName = parts[parts.length - 1];

        // 2. Chuẩn hóa: xóa dấu, xử lý Đ/đ, chuyển thường
        String temp = Normalizer.normalize(givenName, Normalizer.Form.NFD);
        String nameWithoutAccents = DIACRITICS_PATTERN.matcher(temp).replaceAll("")
                .replace("Đ", "D").replace("đ", "d")
                .toLowerCase();

        return nameWithoutAccents + code.toLowerCase();
    }
}
