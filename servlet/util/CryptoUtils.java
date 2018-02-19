package com.wethink.servlet.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;

public class CryptoUtils
{
	public static String encrypt(String value, String key, String initVector)
	{
		try
		{
			byte ivs[] = new byte[16], keys[] = new byte[16];
			System.arraycopy(initVector.getBytes("UTF-8"), 0, ivs, 0, 16);
			IvParameterSpec iv = new IvParameterSpec(ivs);
			System.arraycopy(key.getBytes("UTF-8"), 0, keys, 0, 16);
			SecretKeySpec skeySpec = new SecretKeySpec(keys, "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(value.getBytes());

			return Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return null;
	}

	public static String decrypt(String encrypted, String key, String initVector)
	{
		try
		{
			byte ivs[] = new byte[16], keys[] = new byte[16];
			System.arraycopy(initVector.getBytes("UTF-8"), 0, ivs, 0, 16);
			IvParameterSpec iv = new IvParameterSpec(ivs);
			System.arraycopy(key.getBytes("UTF-8"), 0, keys, 0, 16);
			SecretKeySpec skeySpec = new SecretKeySpec(keys, "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

			byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));

			return new String(original);
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return null;
	}
}