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
        final Security security = new Security(1024);
        final String messageToEncrypt = "Testing message !!!";

        final BigInteger encryptedMessage = security.encrypt(messageToEncrypt);
        final String decryptedMessage = security.decrypt(encryptedMessage);

        Assert.assertTrue(messageToEncrypt.equals(decryptedMessage));
    }

}