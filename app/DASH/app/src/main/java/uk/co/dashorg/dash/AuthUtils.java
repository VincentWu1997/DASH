package uk.co.dashorg.dash;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class AuthUtils {

    public static String getSaltString(int length) {
        String sigma = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            salt.append(sigma.charAt(random.nextInt(sigma.length())));
        }
        return salt.toString();
    }

    public static String hashPassword(String password, String salt) {
        return hexFromBytes(getHash((salt + password).getBytes()));
    }

    private static byte[] getHash(byte[] input) {
        try {
            return MessageDigest.getInstance("SHA-256").digest(input);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String hexFromBytes(byte[] data) {
        return String.format("%0" + (data.length * 2) + 'x', new BigInteger(1, data));
    }
}
