package com.example.iswgr.pgtest.utils;

import java.security.MessageDigest;

public abstract class MD5Utils {
	public static final String MD5(String pwd) {
		char[] md5String = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = pwd.getBytes();

			MessageDigest mdInst = MessageDigest.getInstance("MD5");

			mdInst.update(btInput);

			byte[] md = mdInst.digest();

			int j = md.length;
			char[] str = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[(k++)] = md5String[(byte0 >>> 4 & 0xF)];
				str[(k++)] = md5String[(byte0 & 0xF)];
			}

			return new String(str);
		} catch (Exception e) {
		}
		return null;
	}
}