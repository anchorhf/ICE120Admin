package com.anke.ice.core;

import org.glassfish.jersey.internal.util.Base64;

import com.anke.ice.util.MD5Util;

public class IcePasswordEncoder {

	public String encodePassword(String rawPass, Object salt) {
		rawPass = MD5Util.string2MD5(rawPass);
		return Base64.encodeAsString(rawPass.getBytes());
	}

	public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
		String pass1 = encPass;
		String pass2 = encodePassword(rawPass, salt);
		return pass1.equals(pass2);
		
	}

}
