package Encryption;

import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

/**
 * Utility class for encrypting passwords using MD5 hashing.
 */
public class EncryptPassword {

    /**
     * Encrypts a password using the MD5 hashing algorithm.
     *
     * @param password The plain-text password to be encrypted.
     * @return A byte array containing the MD5 hash of the password.
     * @throws NoSuchAlgorithmException If the MD5 algorithm is not available in the current environment.
     */
    public static byte[] encryptPassword(String password) throws NoSuchAlgorithmException {
        // Create an instance of the MD5 MessageDigest
        MessageDigest md = MessageDigest.getInstance("MD5");
        // Convert the password to a byte array
        byte[] passwordBytes = password.getBytes();
        // Update the digest with the password bytes
        md.update(passwordBytes);
        // Return the hash as a byte array
        return md.digest();
    }
}

