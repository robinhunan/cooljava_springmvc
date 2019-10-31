package com.util.codeGenerator;

import java.io.UnsupportedEncodingException;

public class DataConverter {

	/**
	 * 功能描述：将整型数据转化为2字节的16进制bytes
	 * 
	 * @param len
	 * @return
	 */
	public static byte[] intToHexBytes(int len) {
		byte[] buf = new byte[2];
		int pos = 0;
		buf[pos] = (byte) ((len & 0xff00) >> 8);
		pos++;
		buf[pos] = (byte) (len & 0xff);
		return buf;
	}


	/**
	 * 方法描述：给字节数组加上2字节16进制的报文长度
	 * @param data
	 * @return
	 */
	public static byte[] addLength(byte[] data) {
		byte[] realData = new byte[data.length + 2];
		byte[] len = DataConverter.intToHexBytes(data.length);
		System.arraycopy(len, 0, realData, 0, len.length);
		System.arraycopy(data, 0, realData, 2, data.length);
		return realData;
	}

	

	/**
	 * 根据指定长度在字符串末尾补0
	 * @param str
	 * @param len
	 * @return
	 */
	public static String addFlastZero(String str, int len) {
		String reStr = "";
		if (str.length() > len) {
			return str;
		}
		while (str.length() < len) {
			str += "0";
		}
		reStr = str;
		return reStr;
	}


	/**
	 * 功能描述:字符串左补零操作
	 * 
	 * @param str
	 *            待补零的字符串
	 * @param len
	 *            补零之后的字符串长度
	 * @return 补零后的字符串内容
	 */
	public static String addZeroLeft(String str, int len) {
		StringBuffer s = new StringBuffer();
		s.append(str);
		while (s.length() < len) {
			s.insert(0, "0");
		}
		return s.toString();
	}

	/**
	 * 功能描述:字符串右补零操作
	 * 
	 * @param str
	 *            待补零的字符串
	 * @param len
	 *            补零之后的字符串长度
	 * @return 补零后的字符串内容
	 */
	public static String addZeroRight(String str, int len) {
		StringBuffer s = new StringBuffer();
		s.append(str);
		while (s.length() < len) {
			s.append("0");
		}
		return s.toString();
	}

	/**
	 * 功能描述:字符串左补空格操作
	 * 
	 * @param str
	 *            待补空格的字符串
	 * @param len
	 *            补空格之后的字符串长度
	 * @return 补空格之后的字符串内容
	 * @throws UnsupportedEncodingException
	 */
	public static String addSpaceLeft(String str, int len) throws UnsupportedEncodingException {
		StringBuffer s = new StringBuffer();
		s.append(str);
		while (s.toString().getBytes("GBK").length < len) {
			s.insert(0, " ");
		}
		return s.toString();
	}

	/**
	 * 功能描述：字符串右补空格操作
	 * 
	 * @param str
	 *            待补空格的字符串
	 * @param len
	 *            补空格后字符串的长度
	 * @return 补空格后的字符串
	 */
	public static String addSpaceRight(String str, int len) throws UnsupportedEncodingException {
		StringBuffer s = new StringBuffer();
		s.append(str);
		while (s.toString().getBytes("GBK").length < len) {
			s.append(" ");
		}
		return s.toString();
	}
}
