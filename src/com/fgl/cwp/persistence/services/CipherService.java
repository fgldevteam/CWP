/*
 * Copyright Forzani Group Ltd. 2005
 * Created on Apr 18, 2005
 */
package com.fgl.cwp.persistence.services;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
* @author bting
*/
public class CipherService {

   /**
    * Use Triple DES encryption
    */
   private static String TRIPLE_DES = "DESede";
   
   /**
    * Default crypto provider
    */
   private static String PROVIDER = "SunJCE";
   
   /**
    * Private Key used in the encryption/decryption algorithm
    * 168 bits of security
    */
   private static String KEY = "abc123def456ghi789jkl012";

   
   private static CipherService instance;
   private static Cipher cipher;
   private static Key key;
   
   
   private CipherService() {
       //emtpy
   }

   /**
    * Return an instance of the cipher service
    * @return
    * @throws Exception
    */
   public static CipherService getInstance() throws Exception {
       if (instance==null) {
           instance = new CipherService();
           cipher = Cipher.getInstance(TRIPLE_DES, PROVIDER);
           SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(TRIPLE_DES);
           DESedeKeySpec keySpec = new DESedeKeySpec(KEY.getBytes());
           key = keyFactory.generateSecret(keySpec);
       }
       return instance;
   }
   
   /**
    * Encrypt the given text
    * @param str
    * @return
    * @throws Exception
    */
   public String encrypt(String str) throws Exception {
       cipher.init(Cipher.ENCRYPT_MODE, key);        
       byte[] cipherText = cipher.doFinal(str.getBytes());
       return new String(Base64.encodeBase64(cipherText));
   }

   /**
    * Decrypt the given text
    * @param str
    * @return
    * @throws Exception
    */
   public String decrypt(String str) throws Exception {
       cipher.init(Cipher.DECRYPT_MODE, key);
       byte[] clearText = cipher.doFinal(Base64.decodeBase64(str.getBytes()));
       return new String(clearText);
   }

}
