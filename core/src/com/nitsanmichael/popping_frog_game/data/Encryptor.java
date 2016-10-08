package com.nitsanmichael.popping_frog_game.data;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by MichaelBond on 9/26/2016.
 */
public final class Encryptor {

    private static final String MD5_ENCRYPTION_NAME = "MD5";
    private static final int POSITIVE_SIGN = 1;
    private static final long LONG_VALUE_OFFSET = 5511235L;


    public static String encrypt(int value, long time) {
        return md5(String.valueOf((long)value + time - LONG_VALUE_OFFSET));
    }

    public static String md5(String input) {
        String md5 = null;
        try {
            if (null != input) {
                MessageDigest md = MessageDigest.getInstance(MD5_ENCRYPTION_NAME);
                md.update(input.getBytes(), 0, input.length());
                md5 = (new BigInteger(POSITIVE_SIGN, md.digest())).toString(16);
            }
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }
}
