package com.example.secdevlab4;

import android.util.Base64;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESHelper {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    // Hardcoded AES Key (16 bytes for AES-128)
    private static final String KEY = "2b7e151628aed2a6abf7158809cf4f3c";  // DISCLAIMER!!! Do not ever save this key in the code for production. Key is hardcoded for assignment purposes.

    public static String encrypt(String data) throws Exception {
        // Initialize the cipher
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        // Generate a secret key
        SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
        // Initialize the cipher with the secret key
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        // Encrypt the data
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());

        // Convert the encrypted bytes to a Base64-encoded string
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
    }

    public static String decrypt(String encryptedData) throws Exception {
        // Initialize the cipher
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        // Generate a secret key
        SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
        // Initialize the cipher with the secret key
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        // Decrypt the data
        byte[] decodedBytes = Base64.decode(encryptedData, Base64.DEFAULT);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);

        // Convert the decrypted bytes to a String
        return new String(decryptedBytes);
    }
}
