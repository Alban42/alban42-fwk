package com.alban42.security;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Simple RSA algorithm implementation.
 * Taken from <a href="http://www.java2s.com/Code/Java/Security/SimpleRSApublickeyencryptionalgorithmimplementation.htm">www.java2s.com</a> and adapted by Alban.
 *
 * @author Alban
 */
public class Security {

    private Key privateKey;
    private Key publicKey;

    /**
     * Encrypt the given message in parameter and return the encrypted message.
     *
     * @param message   the {@link String} message to encrypt.
     * @param publicKey the {@link Key} used for encryption.
     * @return the message encrypted.
     */
    public static BigInteger encrypt(final String message, final Key publicKey) {
        return encrypt(new BigInteger(message.getBytes()), publicKey);
    }

    /**
     * Encrypt the given message in parameter and return the encrypted message.
     *
     * @param message   the {@link BigInteger} message to encrypt.
     * @param publicKey the {@link Key} used for encryption.
     * @return the message encrypted.
     */
    public static BigInteger encrypt(final BigInteger message, final Key publicKey) {
        return message.modPow(publicKey.getKey(), publicKey.getEncryptionModule());
    }

    /**
     * Decrypt the given message in parameter and return the encrypted message.
     *
     * @param message    the {@link BigInteger} message to encrypt.
     * @param privateKey the {@link Key} used for encryption.
     * @return the message encrypted.
     */
    public static String decrypt(BigInteger message, Key privateKey) {
        return new String(message.modPow(privateKey.getKey(), privateKey.getEncryptionModule()).toByteArray());
    }

    /**
     * Generate the public and private keys.
     * <p>
     * Get them with {@link #getPublicKey()} and {@link #getPrivateKey()}.
     *
     * @param bitLength the length of the generated keys.
     */
    public void generateKeys(final int bitLength) {
        final SecureRandom random = new SecureRandom();
        final BigInteger primeNumberP = new BigInteger(bitLength / 2, 100, random);
        final BigInteger primeNumberQ = new BigInteger(bitLength / 2, 100, random);
        final BigInteger encryptionModule = primeNumberP.multiply(primeNumberQ);
        final BigInteger euler = (primeNumberP.subtract(BigInteger.ONE)).multiply(primeNumberQ.subtract(BigInteger.ONE));
        BigInteger pubKey = new BigInteger("3");
        while (euler.gcd(pubKey).intValue() > 1) {
            pubKey = pubKey.add(new BigInteger("2"));
        }

        this.publicKey = new Key(encryptionModule, pubKey);
        this.privateKey = new Key(encryptionModule, pubKey.modInverse(euler));
    }

    /**
     * Encrypt the given message in parameter and return the encrypted message.
     *
     * @param message the {@link String} message to encrypt.
     * @return the message encrypted.
     */
    public BigInteger encrypt(final String message) {
        return encrypt(new BigInteger(message.getBytes()), publicKey);
    }

    /**
     * @return the private generated {@link Key}.
     */
    public Key getPrivateKey() {
        return privateKey;
    }

    /**
     * @return the public generated {@link Key}.
     */
    public Key getPublicKey() {
        return publicKey;
    }
}
