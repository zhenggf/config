package cn.orgid.funny.config.domain.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import ch.qos.logback.core.encoder.ByteArrayUtil;
import cn.orgid.funny.config.domain.exception.ApplicationException;

public class EncrypUtil {

	private static final String AES = "AES";
	
	private static Cipher c;
	
	static {
		
		 try {
			c = Cipher.getInstance(AES);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String encryt(String str, String key) {

		try {
			Cipher c = Cipher.getInstance(AES);
			SecretKeySpec keySpec = new SecretKeySpec(
					ByteArrayUtil.hexStringToByteArray(key), AES);
			c.init(Cipher.ENCRYPT_MODE, keySpec);
			byte[] src = str.getBytes();
			byte[] cipherByte = c.doFinal(src);
			return MD5.byte2hex(cipherByte, cipherByte.length);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "";

	}

	public static String decrypt(String hex, String key) {

		try {
			//Cipher c = Cipher.getInstance(AES);
			SecretKeySpec keySpec = new SecretKeySpec(
					ByteArrayUtil.hexStringToByteArray(key), AES);
			c.init(Cipher.DECRYPT_MODE, keySpec);
			byte[] cipherByte = c.doFinal(MD5.hex2byte(hex));
			return new String(cipherByte);
		} catch (Throwable e) {
			e.printStackTrace();
			return "";
		}

	}

	public static String getEncryptionKey() {

		String encryptionKey = null;
		try {

			KeyGenerator keygen = KeyGenerator.getInstance(AES);
			SecureRandom random = new SecureRandom();
			keygen.init(random);
			SecretKey key = keygen.generateKey();
			encryptionKey = ByteArrayUtil.toHexString(key.getEncoded());

		} catch (Throwable e) {
			throw new ApplicationException(e.getMessage());
		}
		return encryptionKey;

	}

}
