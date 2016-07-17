package com.alban42.security;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

/**
 * Created by alban on 16/07/2016.
 *
 * @author alban
 */
public class SecurityTest {

    @Test
    public void encryptDecrypt() throws Exception {
        final Security security = new Security();
        security.generateKeys(1024);

        final String messageToEncrypt = "Testing message !!!";

        final BigInteger encryptedMessage = Security.encrypt(messageToEncrypt, security.getPublicKey());
        final String decryptedMessage = Security.decrypt(encryptedMessage, security.getPrivateKey());

        Assert.assertTrue(messageToEncrypt.equals(decryptedMessage));
    }

}