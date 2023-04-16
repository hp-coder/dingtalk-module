package com.hp.dingtalk.constant;

import java.util.Objects;

/**
 * @author hp 2023/3/21
 */
public interface BaseEnum<T extends Enum<T> & BaseEnum<T, E>, E> {
    E getCode();

    String getName();

    static <T extends Enum<T> & BaseEnum<T, E>, E> T parseByCode(Class<T> cls, E code) {
        if (code == null) {
            return null;
        }
        for (T t : cls.getEnumConstants()) {
            if (Objects.equals(code, t.getCode())) {
                return t;
            }
        }
        return null;
    }

    static <T extends Enum<T> & BaseEnum<T, E>, E> T parseByName(Class<T> cls, String name) {
        if (name == null || name.length() == 0) {
            return null;
        }
        for (T t : cls.getEnumConstants()) {
            if (t.getName().equals(name)) {
                return t;
            }
        }
        return null;
    }
}
