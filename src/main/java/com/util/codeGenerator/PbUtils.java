package com.util.codeGenerator;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * 
 * @功能说明： 系统常用工具类
 */
public class PbUtils {

	/**
	 * 获得请求路径
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestPath(HttpServletRequest request) {
		String requestPath = request.getRequestURI();
		requestPath = requestPath.substring(request.getContextPath().length() + 1);// 去掉项目路径
		return requestPath;
	}

	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String DateToStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = format.format(date);
		return str;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str) {
		// SimpleDateFormat format = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 字符串转换成日期 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDateTime(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 计算版本号总大小 例如 V1.2.3 1*100+2*20+3=123
	 * 
	 * @param version
	 * @return
	 */
	public static Long getCoutVersion(String version) {
		String v[] = version.toUpperCase().replace("V", "").split("\\.");
		Long reault = 0L;
		for (int i = 0; i < v.length; i++) {
			if (i == 0) {
				reault += Integer.parseInt(v[i]) * 100;
			} else if (i == 1) {
				reault += Integer.parseInt(v[i]) * 10;
			} else {
				reault += Integer.parseInt(v[i]);
			}
		}
		return reault;
	}

	/**
	 * 传入一个的数字，返回版本号，例如1000,返回V1.0.0
	 * 
	 * @param num
	 * @return
	 */
	public static String fomartVersion(Long num) {
		// 取千位
		Long qw = num / 100;

		// 取十位
		Long bw = num / 10 % 10;

		// 取各位
		Long gw = num % 10;

		String result = "V" + qw + "." + bw + "." + gw;
		return result;
	}

	/**
	 * 传入一个数字，返回版本号号，例如1000，返回V1.0.0,千位补0
	 * 
	 * @param num
	 *            数字
	 * @param len
	 *            千位补零个数
	 * @return
	 */
	public static String fomartVersion(Long num, int len) {
		// 取千位
		Long qw = num / 100;

		// 取十位
		Long bw = num / 10 % 10;

		// 取各位
		Long gw = num % 10;

		String result = "V" + DataConverter.addZeroLeft(qw + "", len) + "." + bw + "." + gw;
		return result;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(Object str) {
		boolean isString = str instanceof String;
		if (isString) {
			if (str == null) {
				return true;
			} else if (String.valueOf(str).trim().equals("")) {
				return true;
			}
		} else {
			if (str == null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取uuid
	 * 
	 * @return
	 */
	public static synchronized String uuid() {
		String uuid = UUID.randomUUID().toString();
		return uuid.replace("-", "");
	}

	/**
	 * 获取当前时间
	 * 
	 * @return yyyyMMddHHmmss
	 */
	public static synchronized String getCurrentTime() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}
	
	/**
	 * 获取当前时间
	 * 
	 * @return yyyyMMddHHmmss
	 */
	public static synchronized String getCurrenForMilli() {
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	}
	
	/**
	 * 获取当前时间
	 * 
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static synchronized String getCurrentDateTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	

	/**
	 * 自定义获取获取当前系统时间
	 * 
	 * @param year
	 *            年
	 * @param monday
	 *            月
	 * @param date
	 *            日
	 * @param hour
	 *            小时
	 * @param minute
	 *            分钟
	 * @return
	 * @throws ParseException
	 */
	public static String getSysNowTime(int year, int monday, int date, int hour, int minute) {
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String sysNowTime = format.format(new Date());
		Date d = null;
		try {
			d = format.parse(sysNowTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(c.YEAR, year);// 年
		c.add(c.MONDAY, monday);// 月
		c.add(c.DATE, date); // 日
		c.add(c.HOUR, hour);// 小时
		c.add(c.MINUTE, minute);// 分钟
		Date de = c.getTime();
		return format.format(de);
	}

	/**
	 * 获取时间
	 * 
	 * @param year
	 * @param monday
	 * @param date
	 * @return
	 */
	public static String getSysNowTime(int year, int monday, int date) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String sysNowTime = format.format(new Date());
		Date d = null;
		try {
			d = format.parse(sysNowTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(c.YEAR, year);// 年
		c.add(c.MONDAY, monday);// 月
		c.add(c.DATE, date); // 日
		Date de = c.getTime();
		return format.format(de);
	}

	/**
	 * 获取当前时间
	 * 
	 * @return yyyy-MM-dd
	 */
	public static synchronized String getCurrentDate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	/**
	 * 获取当前时间
	 * 
	 * @return yyyyMMddhhmmssSSSSSS
	 */
	public static synchronized String getMillisecondTime() {
		return new SimpleDateFormat("yyyyMMddhhmmssSSSSSS").format(new Date());
	}

	/**
	 * 根据指定格式返回当前时间
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurrentTime(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}

	/**
	 * 替换掉下划线并让紧跟它后面的字母大写,例如 ad_code 转成 adCode
	 * 
	 * @param str
	 * @return
	 */
	public static String strRelplacetoLowerCase(String str) {
		StringBuffer sb = new StringBuffer();
		sb.append(str.toLowerCase());

		int count = sb.indexOf("_");
		while (count != 0) {
			int num = sb.indexOf("_", count);
			count = num + 1;
			if (num != -1) {
				char ss = sb.charAt(count);
				char ia = (char) (ss - 32);
				sb.replace(count, count + 1, ia + "");
			}
		}
		return sb.toString().replaceAll("_", "");
	}

	/**
	 * 返回首字母大写的字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String fristStrToUpperCase(String str) {
		String resultStr = str.substring(0, 1).toUpperCase() + str.substring(1);
		return resultStr;
	}

	/**
	 * 返回首字母和第二个字母小写的字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String fristAndSecondStrToLowerCase(String str) {
		String resultStr = str.substring(0, 1).toLowerCase() + str.substring(1, 2).toLowerCase() + str.substring(2);
		return resultStr;
	}

	/**
	 * 返回首字母小写的字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String fristStrToLowerCase(String str) {
		String resultStr = str.substring(0, 1).toLowerCase() + str.substring(1);
		return resultStr;
	}

	/**
	 * 取括号内的数字
	 * 
	 * @param str
	 *            例如：char(1)
	 * @return
	 */
	public static String getKhNum(String str) {
		return str.replaceAll("\\D", "");
	}

	/**
	 * 获取数组中的最大数
	 * 
	 * @param args
	 * @return
	 */
	public static Long getMaxNum(Long args[]) {
		int max = 0;
		for (int i = 0; i < args.length; i++) {
			if (args[i] > args[max])
				max = i;
		}
		return args[max];
	}

	/**
	 * 获取数组中的最小数
	 * 
	 * @param args
	 * @return
	 */
	public static Long getMinNum(Long args[]) {
		int min = 0;
		for (int i = 0; i < args.length; i++) {
			if (args[i] < args[min])
				min = i;
		}
		return args[min];
	}

	/**
	 * 打印JSON
	 * 
	 * @param object
	 * @throws IOException
	 */
	public static void writeJson(Object object, HttpServletResponse response) {
		// 定义返回报文信息
		Gson gson = new Gson();
		String json = gson.toJson(object);
		response.setContentType("text/html;charset=UTF-8");
		try {
			response.getWriter().write(json);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 使用java反射机制获取Javabean中的私有属性与值
	 * 
	 * @param obj
	 *            javaBean对象
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static String getObjReflect(Object obj) throws IllegalArgumentException, IllegalAccessException {
		String result = "";
		Field fields[] = obj.getClass().getDeclaredFields();
		String[] name = new String[fields.length];
		Object[] value = new Object[fields.length];

		Field.setAccessible(fields, true);
		for (int i = 0; i < name.length; i++) {
			name[i] = fields[i].getName();
			result += name[i] + ":";
			value[i] = fields[i].get(obj);
			result += value[i] + "|";
		}
		return result;
	}

	/**
	 * 使用java反射机制获取Javabean中的私有属性与值,返回关键字
	 * 
	 * @param obj
	 *            javaBean对象
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static String getObjReflectKeword(Object obj) throws IllegalArgumentException, IllegalAccessException {
		String result = "";
		Field fields[] = obj.getClass().getDeclaredFields();
		String[] name = new String[fields.length];
		Object[] value = new Object[fields.length];

		// 关键字数组
		String kewords[] = { "uuid", "devCd", "cerRef", "authCd", "userCd", "proCd", "batchCd", "operDataCd", "operDataVer", "insCd", "id", "operatorCd", "operCd", "parkCode", "reguserName", "roleCd", "functionCd" };
		Field.setAccessible(fields, true);
		for (int i = 0; i < name.length; i++) {
			name[i] = fields[i].getName();

			// 判断是否为关键字
			for (int j = 0; j < kewords.length; j++) {
				if (name[i].equals(kewords[j])) {
					result += name[i] + ":";
					value[i] = fields[i].get(obj);
					result += value[i] + "|";
				}
			}
		}

		return result;
	}

	public static String getObjReflectKeword(Object obj, String[] pks) throws IllegalArgumentException, IllegalAccessException {
		String result = "";
		Field fields[] = obj.getClass().getDeclaredFields();
		String[] name = new String[fields.length];
		Object[] value = new Object[fields.length];

		// 关键字数组
		String kewords[] = pks;
		Field.setAccessible(fields, true);
		for (int i = 0; i < name.length; i++) {
			name[i] = fields[i].getName();

			// 判断是否为关键字
			for (int j = 0; j < kewords.length; j++) {
				if (((PbUtils.strRelplacetoLowerCase(name[i])).toLowerCase()).equals(PbUtils.strRelplacetoLowerCase(kewords[j]).toLowerCase())) {
					result += name[i] + ":";
					value[i] = fields[i].get(obj);
					result += value[i] + "|";
				}
			}
		}

		return result;
	}

	/**
	 * 生成随机数
	 * 
	 * @param str
	 *            随机数生成范围:例如:0123456789,
	 * @param length
	 *            随机数长度
	 * @return
	 */
	public static String getRandomString(String str, int length) {
		Random rd = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = rd.nextInt(str.length());
			sb.append(str.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 获取UUID
	 * 
	 * @return
	 */
	public synchronized static String getUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 编码转换
	 * 
	 * @param str
	 * @return
	 */
	public static String iso8859ToUTF8(String str) {
		String result = "";
		try {
			result = new String(str.getBytes("iso8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @param getdOpenTime
	 * @param string
	 * @return
	 */
	public static Date StrToDateFormat(String str, String format) {
		Date date = null;
		try {
			return new SimpleDateFormat(format).parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * String 转化 Date
	 * 
	 * @param dateString
	 *            YYYY-MM-DD HH:MI:SS
	 * @return
	 */
	public static Date getDate(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(dateString);
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 产生随机数
	 * 
	 * @param randString
	 *            随机数范围，例如：0123456789
	 * @param num
	 *            随机数个数
	 */
	public static void getRandStr(String randString, int num) {
		String randomString = "";
		Random random = new Random();
		// String randString = "0123456789";// 随机产生的字符串
		for (int i = 0; i < num; i++) {
			String randomStr = String.valueOf(randString.charAt(random.nextInt(randString.length())));
			String rand = String.valueOf(randomStr);
			randomString += rand;

		}
		System.out.println(randomString);
	}
}
