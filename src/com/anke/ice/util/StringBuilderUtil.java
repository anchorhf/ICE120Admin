package com.anke.ice.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class StringBuilderUtil {

	/**
	 * 首字母转大写
	 * 
	 * @param str
	 * @return
	 */
	public static String toUpperCaseFirst(String str) {
		String tmp = str.substring(0, 1);
		str = str.substring(1, str.length());
		str = tmp.toUpperCase() + str;
		return str;
	}

	/**
	 * 获取properties文件中的内容
	 * 
	 * @param key
	 * @return
	 */
	public static String fromProperties(String file, String key) {
		InputStream fis = StringBuilderUtil.class.getResourceAsStream("/" + file);
		Properties p = new Properties();
		try {
			p.load(fis);
		} catch (IOException e) {
			return null;
		}
		return p.getProperty(key);
	}

}
