package com.example.secdevlab4;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashingHelper {

    // Generate a random salt
    public static String generateSalt(){
        // Create a SecureRandom instance
        SecureRandom random = new SecureRandom();
        // Generate a 16-byte (128-bit) salt
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        // Convert the salt to a hexadecimal string
        StringBuilder sb = new StringBuilder();
        for (byte b : salt) {
            // Convert each byte to a two-digit hexadecimal string
            sb.append(String.format("%02x", b));
        }
        // Return the salt as a hexadecimal string
        return sb.toString();
    }

    // Hash password using SHA-256
    public static String hashPassword(String password, String salt) {
        try {
            // Combine the password and salt
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            // Convert the hashed bytes to a hexadecimal string
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            // Return the hashed password as a hexadecimal string
            return sb.toString();
            // Handle exceptions
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}