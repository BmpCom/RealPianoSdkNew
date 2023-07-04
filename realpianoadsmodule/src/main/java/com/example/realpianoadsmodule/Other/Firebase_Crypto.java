package com.example.realpianoadsmodule.Other;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/*** Encryption and Decryption of String data; PBE(Password Based Encryption and Decryption)
 * @author Vikram
 */
public class Firebase_Crypto {

    Cipher cipher;
    byte[] saltbytes = {
            (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
            (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03
    };
    int anInt = 19;

    public Firebase_Crypto() {

    }

    public String decrypt(String secretKey, String encryptedText)
            throws NoSuchAlgorithmException,
            InvalidKeySpecException,
            NoSuchPaddingException,
            InvalidKeyException,
            InvalidAlgorithmParameterException,
            IllegalBlockSizeException,
            BadPaddingException,
            IOException {
        //Key generation for enc and desc
        KeySpec pbeKeySpec = new PBEKeySpec(secretKey.toCharArray(), saltbytes, anInt);
        SecretKey secretKey1 = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(pbeKeySpec);
        // Prepare the parameter to the ciphers
        AlgorithmParameterSpec algorithmParameterSpec = new PBEParameterSpec(saltbytes, anInt);
        //Decryption process; same key will be used for decr
        cipher = Cipher.getInstance(secretKey1.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, secretKey1, algorithmParameterSpec);
        byte[] bytes;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            bytes = Base64.getDecoder().decode(encryptedText);
        } else {
            bytes = android.util.Base64.decode(encryptedText, android.util.Base64.DEFAULT);
        }
        byte[] utf8 = cipher.doFinal(bytes);
        String charSet = "UTF-8";
        return new String(utf8, charSet);
    }
}