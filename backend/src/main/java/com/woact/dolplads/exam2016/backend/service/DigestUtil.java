package com.woact.dolplads.exam2016.backend.service;

import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by dolplads on 03/10/2016.
 */
public class DigestUtil {
    public static String getSalt() {
        SecureRandom random = new SecureRandom();
        int bitsPerChar = 5;
        int twoPowerOfBits = 32; // 2^5
        int n = 26;
        assert n * bitsPerChar >= 128;

        return new BigInteger(n * bitsPerChar, random).toString(twoPowerOfBits);
    }

    public static String computeHash(String password, String salt) {
        return DigestUtils.sha256Hex(password + salt);
    }
}
