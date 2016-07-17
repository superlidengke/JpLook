package com.pickshell.jplook.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnicodeUtils {

	public static String toUnicode(String str) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			String temp = Integer.toHexString(str.charAt(i));
			if (temp.length() < 4) {
				builder.append("\\u00");
			} else {
				builder.append("\\u");
			}
			builder.append(temp);
		}
		return builder.toString();
	}

	static final Pattern reUnicode = Pattern.compile("\\\\u([0-9a-zA-Z]{4})");

	public static String decode(String s) {
		Matcher m = reUnicode.matcher(s);
		StringBuffer sb = new StringBuffer(s.length());
		while (m.find()) {
			m.appendReplacement(sb,
					Character.toString((char) Integer.parseInt(m.group(1), 16)));
		}
		m.appendTail(sb);
		return sb.toString();
	}
}
