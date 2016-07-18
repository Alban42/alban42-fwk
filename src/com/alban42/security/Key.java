package com.alban42.security;

import java.math.BigInteger;

/**
 * Created by alban on 17/07/2016.
 *
 * @author alban
 */
public class Key {

    private BigInteger encryptionModule;
    private BigInteger key;

    public Key(final BigInteger encryptionModule, final BigInteger key) {
        this.encryptionModule = encryptionModule;
        this.key = key;
    }

    /**
     * @return the encryption module (used for encrypt and decrypt messages).
     */
    public BigInteger getEncryptionModule() {
        return encryptionModule;
    }

    /**
     * @return the key.
     */
    public BigInteger getKey() {
        return key;
    }
}
