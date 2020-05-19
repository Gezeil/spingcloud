package com.yanjun.xiang.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang.StringUtils;

import org.springframework.util.ObjectUtils;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Pattern;

/***
 *
* @ClassName: FormatUtil
* @Description: 状态格式化
* @author yzw
* @date 2018年7月16日 下午2:07:44
*
 */
public class FormatUtil {


	/**
	 * @param @param  s
	 * @param @return 设定文件
	 * @return String    返回类型
	 * @throws
	 * @Title: subZeroAndDot
	 * @Description:
	 */
	public static String subZeroAndDot(String s) {
		if (s.indexOf(".") > 0) {
			s = s.replaceAll("0+?$", "");//去掉多余的0
			s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
		}
		return s;
	}

	/**
	 *
	 * @Title: getBetweenDayLessSeven
	 * @Description: 返回小于等于7的天数
	 * @param @param date
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	/*public static String getBetweenDayLessSeven(String date) {
	    Date endDate = DateUtil.parse(date);
	    long days = DateUtil.between(new Date(), endDate, DateUnit.DAY, false);
	    if(days <= 7 && days >= 0) {
	        return String.valueOf(days);
	    }
	    return "";
	}*/

	/**
	 * 将分转换成元
	 *
	 * @param money 单位：分
	 * @return BigDecimal 单位：元
	 */
	public static BigDecimal convertCentToYuan(Double money) {
		if (null == money) return null;
		return new BigDecimal(money).divide(new BigDecimal(100));
	}


	/**
	 * 将元转换成分
	 *
	 * @param yuan 单位：元
	 * @return BigDecimal 单位：分
	 */
	public static BigDecimal convertYuanToCent(BigDecimal yuan) {
		if (null == yuan) return null;
		return yuan.multiply(BigDecimal.valueOf(100));
	}

	public static Double formatDouble(Object value) {
		return formatDouble(value, null);
	}

	public static Double formatDouble(Object value, Double defaultValue) {
		if (ObjectUtils.isEmpty(value)) {
			return defaultValue;
		}
		try {
			return Double.valueOf(String.valueOf(value));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static BigDecimal formatBigDecimal(Object value) {
		return formatBigDecimal(value, null);
	}

	public static BigDecimal formatBigDecimal(Object value, BigDecimal defaultValue) {
		if (ObjectUtils.isEmpty(value)) {
			return defaultValue;
		}
		try {
			return BigDecimal.valueOf(Double.valueOf(value.toString()));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static Integer formatInteger(Object value) {
		return formatInteger(value, null);
	}

	public static Integer formatInteger(Object value, Integer defaultValue) {
		if (ObjectUtils.isEmpty(value)) {
			return defaultValue;
		}
		try {
			return Integer.valueOf(String.valueOf(value));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static String formatString(Object value) {
		return formatString(value, "");
	}

	public static String formatString(Object value, String defaultValue) {
		if (ObjectUtils.isEmpty(value)) {
			return defaultValue;
		}
		try {
			return String.valueOf(value);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static Boolean formatBoolean(Object value) {
		return formatBoolean(value, false);
	}

	public static Boolean formatBoolean(Object value, Boolean defaultValue) {
		if (ObjectUtils.isEmpty(value)) {
			return defaultValue;
		}
		try {
			return Boolean.valueOf(value.toString());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static Date formatDate(Object value) {
		return formatDate(value, null);
	}

	public static Date formatDate(Object value, Date defaultValue) {
		if (ObjectUtils.isEmpty(value)) {
			return defaultValue;
		}
		try {
			return (Date) value;
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static Long formatLong(Object value) {
		return formatLong(value, null);
	}

	public static Long formatLong(Object value, Long defaultValue) {
		if (ObjectUtils.isEmpty(value)) {
			return defaultValue;
		}
		try {
			return Long.valueOf(String.valueOf(value));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * 生成UUID
	 *
	 * @return
	 */
	public static String randomUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static JSONArray formatJsonArray(Object value) {
		return formatJsonArray(value, new JSONArray());
	}

	public static JSONArray formatJsonArray(Object value, JSONArray defaultValue) {
		if (ObjectUtils.isEmpty(value)) {
			return defaultValue;
		}
		try {
			return JSON.parseArray(String.valueOf(value));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * 判断字符串是否可以转double
	 *
	 * @param str
	 * @return
	 */
	public static boolean isDouble(String str) {
		boolean ret = true;
		try {
			double d = Double.parseDouble(str);
			ret = true;
		} catch (Exception ex) {
			ret = false;
		}
		return ret;
	}

	public static boolean isNumbers(String str) {
		Pattern pattern = Pattern.compile("^[\\d]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断当前时间是否在startDate和endDate之间
	 *
	 * @param startLocalTime 开始时间，带时分秒
	 * @param endLocalTime   结束时间，带时分秒
	 * @return
	 */
	public static boolean nowBetweenDateTime(LocalDateTime startLocalTime, LocalDateTime endLocalTime) {
		if (null == startLocalTime && null == endLocalTime) {
			throw new IllegalArgumentException("参数不正确，开始时间与结束时间不能都为空");
		}
		Date startDateTime = null;
		if (null != startLocalTime) {
			startDateTime = Date.from(startLocalTime.atZone(ZoneId.systemDefault()).toInstant());
		}
		Date endDateTime = null;
		if (null != endLocalTime) {
			endDateTime = Date.from(endLocalTime.atZone(ZoneId.systemDefault()).toInstant());
		}
		return nowBetweenDateTime(startDateTime, endDateTime);
	}

	/**
	 * 判断当前时间是否在startDate和endDate之间
	 *
	 * @param startDateTime 开始时间，带时分秒
	 * @param endDateTime   结束时间，带时分秒
	 * @return
	 */
	public static boolean nowBetweenDateTime(Date startDateTime, Date endDateTime) {
		if (null == startDateTime && null == endDateTime) {
			throw new IllegalArgumentException("参数不正确，开始时间与结束时间不能都为空");
		}
		Date currentDate = new Date();
		if (null == startDateTime) {
			return currentDate.before(endDateTime);
		}
		if (null == endDateTime) {
			return currentDate.after(startDateTime);
		}
		if (startDateTime.compareTo(endDateTime) == 0) {
			return currentDate.compareTo(startDateTime) == 0 ? true : false;
		} else {
			return currentDate.after(startDateTime) && currentDate.before(endDateTime);
		}
	}


	/**
	 * 功能描述: 效验用户输入非法字符
	 *
	 * @param: 效验参数
	 * @return: true 合法  false 参数不合法
	 * @auther: cedar
	 * @date: 2019/4/19 14:18
	 */
	public static boolean IllegalValidity(String regex, Object searchKey) {
		if (StringUtils.isEmpty(regex)) {
			throw new IllegalArgumentException("正则表达式为空");
		}
		if (!ObjectUtils.isEmpty(searchKey)) {
			Pattern p = Pattern.compile(regex);
			return p.matcher(FormatUtil.formatString(searchKey)).find();
		}
		return false;
	}
}

