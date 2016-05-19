package com.demo.common.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;


import sun.misc.BASE64Encoder;

public class Md5Util {
	private static final Logger LOGGER = Logger.getLogger(Md5Util.class);

	public static String encrypt(String str) {
		String encryptStr = null;
		try {
			encryptStr = new BASE64Encoder().encode(MessageDigest.getInstance(
					"md5").digest(str.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return encryptStr;
	}

	public static void main(String[] args) {
		System.out.println(encrypt("123"));
	}
}
