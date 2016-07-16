package com.alban42.security;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author Alban
 */
public class Security {

    private BigInteger encryptionModule, privateKey, publicKey;

    public Security(int bitLength) {
        final SecureRandom random = new SecureRandom();
        final BigInteger primeNumberP = new BigInteger(bitLength / 2, 100, random);
        final BigInteger primeNumberQ = new BigInteger(bitLength / 2, 100, random);
        encryptionModule = primeNumberP.multiply(primeNumberQ);
        final BigInteger euler = (primeNumberP.subtract(BigInteger.ONE)).multiply(primeNumberQ.subtract(BigInteger.ONE));
        publicKey = new BigInteger("3");
        while (euler.gcd(publicKey).intValue() > 1) {
            publicKey = publicKey.add(new BigInteger("2"));
        }
        privateKey = publicKey.modInverse(euler);
    }

    public BigInteger encrypt(String message) {
        return encrypt(new BigInteger(message.getBytes()));
    }

    public BigInteger encrypt(BigInteger message) {
        return message.modPow(publicKey, encryptionModule);
    }


    public String decrypt(BigInteger message) {
        return new String(message.modPow(privateKey, encryptionModule).toByteArray());
    }

    public BigInteger getPrivateKey() {
        return privateKey;
    }

    public BigInteger getPublicKey() {
        return publicKey;
    }
}
