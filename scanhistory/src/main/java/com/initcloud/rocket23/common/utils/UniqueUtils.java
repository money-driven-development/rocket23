package com.initcloud.rocket23.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UniqueUtils {

    public static String getUUID(String prefix) {
        return prefix + UUID.randomUUID();
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
}
