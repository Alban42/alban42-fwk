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

    public BigInteger getEncryptionModule() {
        return encryptionModule;
    }

    public BigInteger getKey() {
        return key;
    }
}
