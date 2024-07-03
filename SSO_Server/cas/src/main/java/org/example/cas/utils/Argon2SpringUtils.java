package org.example.cas.utils;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

/**
 * Spring boot版本的Argon2加密工具
 */
public class Argon2SpringUtils {
    private static final Argon2PasswordEncoder argon2PasswordEncoder = new Argon2PasswordEncoder(16, 32, 2, 65536, 1);

    public static String encoder(String password) {
        return argon2PasswordEncoder.encode(password);
    }

    public static boolean decoder(String hash, String password) {
        return argon2PasswordEncoder.matches(password, hash);
    }
}

