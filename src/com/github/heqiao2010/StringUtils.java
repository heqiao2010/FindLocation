package com.github.heqiao2010;

/**
 * 简单的字符串处理
 * @author joel
 *
 */
public class StringUtils {
	public static final char[] WHITESPACES = { ' ', '\t', '\n', '\f', '\r' };
	
	public static boolean isWhiteSpaces(char c) {
		for (char wc : WHITESPACES) {
			if (wc == c) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isEmpty( final String str){
		return null == str || "" == str;
	}
	
	public static boolean isNotEmpty(final String str){
		return !isEmpty(str);
	}
	
	public static boolean isBlank(final String str) {
		if (isEmpty(str)) {
			return true;
		} else {
			for (int i = 0; i < str.length(); i++) {
				if (!isWhiteSpaces(str.charAt(i))) {
					return false;
				}
			}
			return true;
		}
	}
}
