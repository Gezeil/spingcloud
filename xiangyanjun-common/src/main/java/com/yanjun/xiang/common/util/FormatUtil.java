package com.yanjun.xiang.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang.StringUtils;

import org.spin.core.util.DateUtils;
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
	 *
	* @Title: subZeroAndDot
	* @Description:
	* @param @param s
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	public static String subZeroAndDot(String s){
        if(s.indexOf(".") > 0){
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
	 * @param money 单位：分
	 * @return BigDecimal 单位：元
	 */
	public static BigDecimal convertCentToYuan(Double money) {
		if (null == money) return null;
		return new BigDecimal(money).divide(new BigDecimal(100));
	}


	/**
	 * 将元转换成分
	 * @param yuan 单位：元
	 * @return BigDecimal 单位：分
	 */
	public static BigDecimal convertYuanToCent(BigDecimal yuan) {
		if (null == yuan) return null;
		return yuan.multiply(BigDecimal.valueOf(100));
	}

	public static Double formatDouble(Object value) {
		return formatDouble(value,null);
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
		return formatBigDecimal(value,null);
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
		return formatInteger(value,null);
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
		return formatString(value,"");
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
		return formatBoolean(value,false);
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
		return formatDate(value,null);
	}

	public static Date formatDate(Object value, Date defaultValue) {
		if (ObjectUtils.isEmpty(value)) {
			return defaultValue;
		}
		try {
			return (Date)value;
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static Long formatLong(Object value) {
		return formatLong(value,null);
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
	 * @return
	 */
	public static String randomUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static JSONArray formatJsonArray(Object value) {
		return formatJsonArray(value,new JSONArray());
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
	 * @param str
	 * @return
	 */
	public static boolean isDouble(String str){
		boolean ret = true;
		try{
			double d = Double.parseDouble(str);
			ret = true;
		}catch(Exception ex){
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
	 * 若当前时间与开始日期或结束日期在同一天，也返回true
	 * @param startDate 开始日期，不带日期
	 * @param endDate  结束日期，不带日期
	 * @return
	 */
	public static boolean nowBetweenDate(LocalDateTime startDate, LocalDateTime endDate) {
		if (null == startDate && null == endDate) {
			throw new IllegalArgumentException("参数不正确，开始日期与结束日期不能都为空");
		}
		LocalDateTime currentDate = LocalDateTime.now();
		if (null == startDate) {
			return DateUtils.isSameDay(currentDate, endDate) || currentDate.isBefore(endDate);
		}
		if (null == endDate) {
			return DateUtils.isSameDay(currentDate, startDate) || currentDate.isAfter(startDate);
		}
		if (startDate.compareTo(endDate) == 0) {
			return DateUtils.isSameDay(currentDate, startDate);
		} else {
			// 先判断边界值
			boolean startFlag = DateUtils.isSameDay(currentDate, startDate);
			boolean endFlag = DateUtils.isSameDay(currentDate, endDate);
			if (startFlag || endFlag) {
				// 在同一天的时候可以操作
				return true;
			}
			return currentDate.isAfter(startDate) && currentDate.isBefore(endDate);
		}
	}

	/**
	 * 判断当前时间是否在startDate和endDate之间
	 *
	 * @param startLocalTime 开始时间，带时分秒
	 * @param endLocalTime  结束时间，带时分秒
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
	 * @param endDateTime  结束时间，带时分秒
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
	 *
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

	/**
	 *
	 * 功能描述:获取今天日期
	 *
	 * @param:
	 * @return:
	 * @auther: cedar
	 * @date: 2019/4/18 11:29
	 */
	public static String getTodayDate(){
		Calendar cal = Calendar.getInstance();
		//今天
//		String todayTime = DateUtils.format(cal.getTime(), "yyyyMMdd");
		return DateUtils.format(cal.getTime(), "yyyyMMdd");
	}

	/**
	 *
	 * 功能描述:获取昨天日期
	 *
	 * @param:
	 * @return:
	 * @auther: cedar
	 * @date: 2019/4/18 11:29
	 */
	public static String getYesterdayDate(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE,-1);
		return DateUtils.format(cal.getTime(), "yyyyMMdd");
	}

	/**
	 *
	 * 功能描述:获取最近七天日期（不包括今天）
	 *
	 * @param:
	 * @return:
	 * @auther: cedar
	 * @date: 2019/4/18 11:29
	 */
	public static String getSevenDayDate(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE,-7);
		return DateUtils.format(cal.getTime(), "yyyyMMdd");
	}

	/**
	 *
	 * 功能描述:获取本月1日日期
	 *
	 * @param:
	 * @return:
	 * @auther: cedar
	 * @date: 2019/4/18 11:28
	 */
	public static String getMonthFirstDayDate(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH,1);
		return DateUtils.format(cal.getTime(), "yyyyMMdd");
	}

	/**
	 *
	 * 功能描述: 获取本周周一
	 *
	 * @param:
	 * @return:
	 * @auther: cedar
	 * @date: 2019/4/18 11:28
	 */
	public static String getMondayDate(){
		Calendar cal = Calendar.getInstance();
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int day = cal.get(Calendar.DAY_OF_WEEK);
		// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		return DateUtils.format(cal.getTime(), "yyyyMMdd");
	}

	/**
	 * 获取上个月的年月
	 * @author whf
	 * @date 2019-5-30
	 * @return
	 */
	public static String getBeforeMonthDate(){
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		return DateUtils.format(calendar.getTime(),"yyyy-MM");
	}

	/**
	 * 获取上个月 第一天日期：yyyy-MM-dd HH:mm:ss
	 * @author whf
	 * @date 2019-5-30
	 * @return
	 */
	public static String getBeforeFirstMonthDate(){
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		return DateUtils.format(calendar.getTime(),"yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取上个月 最后一天日期：yyyy-MM-dd HH:mm:ss
	 * @author whf
	 * @date 2019-5-30
	 * @return
	 */
	public static String getBeforeLastMonthDate(){
//		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY,23);
		calendar.set(Calendar.MINUTE,59);
		calendar.set(Calendar.SECOND,59);
		return DateUtils.format(calendar.getTime(),"yyyy-MM-dd HH:mm:ss");
	}
}
