/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.jass2125.redis.core.util;

import io.github.jass2125.redis.core.exceptions.CryptographyException;
import io.github.jass2125.redis.core.exceptions.EncodingException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author <a href="mailto:jair_anderson_bs@hotmail.com">Anderson Souza</a>
 * @since Jul 4, 2017 1:10:41 PM
 */
public class PasswordEncriptorSHA implements PasswordEncriptor {

    private MessageDigest instance;

    public PasswordEncriptorSHA() {
    }

    @Override
    public String encryptPassword(String password) throws CryptographyException, EncodingException {
        createMessageDigest();
        byte[] digest = encoding(password);
        StringBuilder builder = new StringBuilder();
        for (byte b : digest) {
            builder.append(String.format("%02X", 0xFF & b));
        }
        return builder.toString();
    }

    @Override
    public boolean comparatePassword(String passwordUser, String passwordForm) throws EncodingException, CryptographyException {
        createMessageDigest();
        byte[] digest = encoding(passwordForm);
        String passwordFormEncrypted = encryptPassword(passwordForm);
        return passwordUser.equals(passwordFormEncrypted);
    }

    private void createMessageDigest() throws CryptographyException {
        try {
            this.instance = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            Logger.getLogger(PasswordEncriptorSHA.class.getName()).log(Level.SEVERE, null, e);
            throw new CryptographyException(e, "Não foi possível criar o algoritmo de criptografia especificado!");
        }
    }

    private byte[] encoding(String password) throws EncodingException {
        try {
            return instance.digest(password.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new EncodingException(e, "Não foi possível realizar a codificação da senha!");
        }
    }

    public static void main(String[] args) throws EncodingException, CryptographyException {
        PasswordEncriptorSHA enc = new PasswordEncriptorSHA();
        String encryptPassword = enc.encryptPassword("123");
        System.out.println(encryptPassword);
    }

}
