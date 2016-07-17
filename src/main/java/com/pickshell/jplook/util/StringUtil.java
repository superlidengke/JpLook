package com.pickshell.jplook.util;

public class StringUtil {

	/**
	 * if the string is not null,and when remove the spaces at start and at end
	 * its length isn't 0,return true;
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isNotBlank(String string) {
		if (string == null) {
			return false;
		}
		String str = string.trim();
		return !str.isEmpty();
	}

	/**
	 * As long as one string is null,then return null.
	 * 
	 * @param strings
	 * @return
	 */
	public static boolean isBlank(String... strings) {
		for (String str : strings) {
			if (!isNotBlank(str)) {
				return true;
			}
		}
		return false;
	}
}
