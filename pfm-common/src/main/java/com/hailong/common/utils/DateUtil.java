package com.hailong.common.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 日期常用类的整理，包括几大常用功能:<br>
 * <ol>
 * <li>java.util.Date类转为字符串</li>
 * <li>字符串转为java.util.Date类</li>
 * <li>快捷获取当前指定格式日期（字符串与java.util.Date类两种格式）</li>
 * <li>java.util.Date的日期类型的运算操作</li>
 * <li>快捷获取特定日期（本月第一天，本月最后一天）</li>
 * <li>其他</li>
 * <li></li>
 * </ol>
 */
public class DateUtil {

	public final static int YEAR = Calendar.YEAR; // 年
	public final static int MONTH = Calendar.MONTH;// 月
	public final static int DATE = Calendar.DATE;// 日
	public final static int HOUR = Calendar.HOUR_OF_DAY;// 小时
	public final static int MINUTE = Calendar.MINUTE;// 分
	public final static int SECOND = Calendar.SECOND;// 秒
	public final static int MILLISECOND = Calendar.MILLISECOND; // 毫秒
	// public final static int WEEK_OF_MONTH = Calendar.WEEK_OF_MONTH;
	// public final static int WEEK_OF_YEAR = Calendar.WEEK_OF_YEAR;
	public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
public static final int TIME_UNIT = TimeUnit.SECOND.value;
	
	public enum TimeUnit {
		SECOND(1000), MILLISECOND(1);

		private int value = 0;

		private TimeUnit(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}
	}
	// --------------------- 1. --------------------------------
	
	/**
	 * @author qinyu-s
	 * @description 新增一个日期工具，自动输出开始日期和截止日期之间的所有日期，包含开始日期和截止日期
	 */
	public static List<String> getDays(String fromDate, String endDate, String pattern){
		List<String> dayList = new ArrayList<String>();
		dayList.add(fromDate);
		SimpleDateFormat formatDate = new SimpleDateFormat(pattern);
		if(fromDate.equals(endDate)){
		    return dayList;
		}
		String tmp;
		if(fromDate.compareTo(endDate) > 0){//保证fromDate < endDate
			tmp = fromDate; 
			fromDate = endDate; 
			endDate = tmp;
		}
		tmp = formatDate.format(str2Date(fromDate, pattern).getTime() + 3600*24*1000);
        int num = 0; 
        while(tmp.compareTo(endDate) < 0){ 
        	dayList.add(tmp);
        	num++;
        	tmp = formatDate.format(str2Date(tmp, pattern).getTime() + 3600*24*1000);
        }
        dayList.add(endDate);
        if(num == 0){
        	return dayList;
        }
        return dayList;
	}

	public static Date str2Date(String str, String pattern) {
		SimpleDateFormat formatDate = new SimpleDateFormat(pattern);
		if (str == null){
			return null;
		}
		try {
			return formatDate.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
    /**
     * 获取两个日期之间的相差天数，先格式化为"yyyy-MM-dd"，在计算
     * @param beginDateStr
     * @param endDateStr
     * @return long
     */
    public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate;
		Date endDate;
		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
			day = (endDate.getTime() - beginDate.getTime())/(24 * 60 * 60 * 1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day;
	}
    
	/**
	 * 按照 yyyy-MM-dd 格式化日期（java.util.Date -> 字符串）
	 * 
	 * @throws Exception
	 *             有可能出现的转换异常
	 */
	public static String formatDate(java.util.Date date) {
		if (date == null)
			date = now();
		return (formatDate(date, "yyyy-MM-dd"));
	}

	/**
	 * 按照 yyyy-MM-dd HH:mm:ss 格式化日期（java.util.Date -> 字符串）
	 * 
	 * @throws Exception
	 *             有可能出现的转换异常
	 */
	public static String formatDateTime(java.util.Date date) {
		if (date == null)
			date = now();
		return (formatDate(date, "yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 按照 HH:mm:ss 格式化日期（java.util.Date -> 字符串）
	 * 
	 * @throws Exception
	 *             有可能出现的转换异常
	 */
	public static String formatTime(java.util.Date date) {
		if (date == null)
			date = now();
		return (formatDate(date, "HH:mm:ss"));
	}

	/**
	 * Date类型转时间核心方法，默认转换格式为今天的日期格式（java.util.Date -> 字符串）
	 * 
	 * @param date
	 *            日期，若null默认今天
	 * @param pattern
	 *            格式化字符串
	 * @return 时间格式字符串
	 * @throws Exception
	 *             有可能出现的转换异常
	 */
	public static String formatDate(java.util.Date date, String pattern) {
		if (date == null)
			date = now();

		if (pattern == null)
			pattern = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return (sdf.format(date));
	}

	// -----------------------------------------------------

	// --------------------- 2. --------------------------------
	/**
	 * 按照 yyyy-MM-dd 格式化日期（字符串 -> java.util.Date）
	 * 
	 * @throws Exception
	 *             有可能出现的转换异常
	 */
	public static java.util.Date parseDate(String date) {
		if (date == null)
			return now();
		return parseDate(date, "yyyy-MM-dd");
	}

	/**
	 * 按照 yyyy-MM-dd HH:mm:ss 格式化日期（字符串 -> java.util.Date）
	 * 
	 * @throws Exception
	 *             有可能出现的转换异常
	 */
	public static java.util.Date parseDateTime(String datetime) {
		if (datetime == null)
			return now();
		return parseDate(datetime, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 截断给定日期，只留下 yyyy-MM-dd （去除时间部分）
	 * 
	 * @param datetime
	 *            给定日期
	 * @return
	 * @throws Exception
	 *             有可能出现的转换异常
	 */
	public static java.util.Date parseDate(java.util.Date datetime) {
		if (datetime == null)
			return now();
		return parseDate(datetime, "yyyy-MM-dd");
	}

	/**
	 * 截断给定日期，只留下 yyyy-MM-dd HH:mm:ss （去除毫秒部分）
	 * 
	 * @param datetime
	 *            给定日期
	 * @return
	 * @throws Exception
	 *             有可能出现的转换异常
	 */
	public static java.util.Date parseDateTime(java.util.Date datetime) {
		if (datetime == null)
			return now();
		return parseDate(datetime, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 按照给定格式修正日期中的部分
	 * 
	 * @param datetime
	 * @param pattern
	 * @return
	 * @throws Exception
	 */
	public static java.util.Date parseDate(java.util.Date datetime,
			String pattern) {
		if (datetime == null)
			return now();
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		try {
			return formatter.parse(formatter.format(datetime));
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * Date类型转时间核心方法，默认转换格式为今天的日期格式
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 * @throws Exception
	 *             有可能出现的转换异常
	 */
	public static java.util.Date parseDate(String date, String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);

		if ((date == null) || (date.equals(""))) {
			return now();
		} else {
			try {
				return formatter.parse(date);
			} catch (ParseException e) {
				return null;
			}
		}
	}

	// -----------------------------------------------------

	// --------------------- 3. --------------------------------
	/**
	 * 按照 yyyy-MM-dd 格式化<b> 当前 </b>日期
	 * 
	 * @return 字符串
	 * @throws Exception
	 *             有可能出现的转换异常
	 */
	public static String formatDate() {
		return (formatDate(now(), "yyyy-MM-dd"));
	}
	
	/**
	 * 按照 yyyyMMddHH 格式化<b> 当前 </b>日期
	 * 
	 * @return 字符串
	 * @throws Exception
	 *             有可能出现的转换异常
	 */
	public static String formatDateHour() {
		return (formatDate(now(), "yyyyMMddHH"));
	}

	/**
	 * 按照 yyyy-MM-dd HH:mm:ss 格式化<b> 当前 </b>日期（字符串 -> java.util.Date）
	 * 
	 * @return 字符串
	 * @throws Exception
	 *             有可能出现的转换异常
	 */
	public static String formatDateTime() {
		return (formatDate(now(), "yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 按照 HH:mm:ss 格式化<b> 当前 </b>日期（字符串 -> java.util.Date）
	 * 
	 * @return 字符串
	 * @throws Exception
	 *             有可能出现的转换异常
	 */
	public static String formatTime() {
		return (formatDate(now(), "HH:mm:ss"));
	}

	/**
	 * 按照指定 <i>patten</i> 格式化<b> 当前 </b>日期（字符串 -> java.util.Date）
	 * 
	 * @return 字符串
	 * @throws Exception
	 *             有可能出现的转换异常
	 */
	public static String formatDate(String patten) {
		return (formatDate(now(), patten));
	}

	/**
	 * 获取当前 java.util.Date 格式完整日期
	 * 
	 * @return java.util.Date
	 */
	public static java.util.Date now() {
		return (new java.util.Date());
	}

	/**
	 * 获取当前 java.util.Date <i>除去时间部分<i> 日期
	 * 
	 * @return java.util.Date
	 * @throws Exception
	 *             有可能出现的转换异常
	 */
	public static java.util.Date nowDate() {
		return parseDate(formatDate());
	}

	/**
	 * 获取当前 java.util.Date <i>除去毫秒部分<i> 日期时间
	 * 
	 * @return java.util.Date
	 * @throws Exception
	 *             有可能出现的转换异常
	 */
	public static java.util.Date nowDateTime() {
		return parseDateTime(formatDateTime());
	}

	// -----------------------------------------------------

	// --------------------- 4. --------------------------------
	/**
	 * 计算单个日期的加法运算
	 * 
	 * @param field
	 *            如
	 *            <ul>
	 *            <li>DateUtil.SECOND</li>
	 *            <li>DateUtil.MINUTE</li>
	 *            <li>DateUtil.HOUR</li>
	 *            <li>DateUtil.DATE</li>
	 *            <li>...</li>
	 *            </ul>
	 * @param date
	 *            指定日期 默认今日
	 * @param amount
	 *            移动差值
	 * @return
	 */
	public static java.util.Date add(java.util.Date date, int field, int amount) {
		if (date == null)
			date = now();

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);

		return cal.getTime();
	}

	public static java.util.Date addDay(java.util.Date date, int amount) {
		return add(date, DateUtil.DATE, amount);
	}

	public static java.util.Date addMonth(java.util.Date date, int amount) {
		return add(date, DateUtil.MONTH, amount);
	}

	public static java.util.Date addHour(java.util.Date date, int amount) {
		return add(date, DateUtil.HOUR, amount);
	}
	
	public static java.util.Date preHour(java.util.Date date) {
		return add(date, DateUtil.HOUR, -1);
	}

	public static java.util.Date preMonth(java.util.Date date) {
		return add(date, DateUtil.MONTH, -1);
	}

	public static java.util.Date nextMonth(java.util.Date date) {
		return add(date, DateUtil.MONTH, 1);
	}

	public static java.util.Date preDay(java.util.Date date) {
		return add(date, DateUtil.DATE, -1);
	}
	
	public static java.util.Date preNumDay(java.util.Date date,int num) {
		return add(date, DateUtil.DATE, -num);
	}

	public static java.util.Date nextDay(java.util.Date date) {
		return add(date, DateUtil.DATE, 1);
	}

	/**
	 * 计算单个日期的加法运算（yyyy-MM-dd）（字符串参数）
	 * 
	 * @param dateStr
	 * @param field
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	public static java.util.Date addDate(String dateStr, int field, int amount) {
		java.util.Date date = parseDate(dateStr);
		if (date == null)
			date = now();

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);

		return cal.getTime();
	}

	/**
	 * 计算单个日期的加法运算（yyyy-MM-dd）（java.util.Date 参数）
	 * 
	 * @param date
	 * @param field
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	public static java.util.Date addDate(java.util.Date date, int field,
			int amount) {
		return addDate(formatDate(date), field, amount);
	}

	/**
	 * 计算单个日期时间的加法运算（yyyy-MM-dd HH:mm:ss）（字符串参数）
	 * 
	 * @param dateStr
	 * @param field
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	public static java.util.Date addDateTime(String dateStr, int field,
			int amount) {
		java.util.Date date = parseDateTime(dateStr);

		if (date == null)
			date = now();

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);

		return cal.getTime();
	}

	/**
	 * 计算单个日期时间的加法运算（yyyy-MM-dd HH:mm:ss）（java.util.Date 参数）
	 * 
	 * @param dateStr
	 * @param field
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	public static java.util.Date addDateTime(java.util.Date date, int field,
			int amount) {
		return addDateTime(formatDateTime(date), field, amount);
	}

	/**
	 * 计算两个日期之间的差值，第二参数减第一参数(d-k)
	 * 
	 * @param i
	 *            固定下面几项
	 *            <ul>
	 *            <li>DateUtil.SECOND</li>
	 *            <li>DateUtil.MINUTE</li>
	 *            <li>DateUtil.HOUR</li>
	 *            <li>DateUtil.DATE</li>
	 *            </ul>
	 * @param k
	 *            日期1
	 * @param d
	 *            日期2
	 * @return
	 */
	public static int diffDate(int i, java.util.Date k, java.util.Date d) {
		int diffnum = 0;
		int needdiff = 0;
		switch (i) {
		case DateUtil.SECOND: {
			needdiff = 1000;
			break;
		}
		case DateUtil.MINUTE: {
			needdiff = 60 * 1000;
			break;
		}
		case DateUtil.HOUR: {
			needdiff = 60 * 60 * 1000;
			break;
		}
		case DateUtil.DATE: {
			needdiff = 24 * 60 * 60 * 1000;
			break;
		}
		}
		if (needdiff != 0) {
			diffnum = (int) (d.getTime() / needdiff)
					- (int) (k.getTime() / needdiff);
			;
		}

		return diffnum;
	}

	// -----------------------------------------------------

	// --------------------- 5. --------------------------------

	/**
	 * 返回当天凌晨的时刻(0点0分0秒)
	 * 
	 * @param date
	 *            输入日期
	 * @return xx
	 * @throws Exception
	 */
	public static java.util.Date beginningOfDay(java.util.Date date) {
		if (date == null)
			date = now();
		return parseDate(date);
	}

	/**
	 * 返回当天最后的时刻(23点59分59秒)
	 * 
	 * @param date
	 *            输入日期
	 * @return xx
	 * @throws Exception
	 */
	public static java.util.Date endOfDay(java.util.Date date) {
		if (date == null)
			date = now();
		return addDate(nextDay(parseDate(date)), DateUtil.SECOND, -1);
	}

	/**
	 * 本月最后一天
	 */
	public static java.util.Date getLastDateByMonth() {
		return getLastDateByMonth(new java.util.Date());
	}

	/**
	 * 指定日期所在月最后一天
	 */
	public static java.util.Date getLastDateByMonth(java.util.Date date) {
		if (date == null)
			date = now();
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.MONTH, now.get(Calendar.MONTH) + 1);
		now.set(Calendar.DATE, 1);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - 1);
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		return now.getTime();
	}

	/**
	 * 本月第一天
	 */
	public static java.util.Date getFirstDateByMonth() {
		return getFirstDateByMonth(new java.util.Date());
	}

	/**
	 * 指定日期所在月第一天
	 */
	public static java.util.Date getFirstDateByMonth(java.util.Date d) {
		if (d == null)
			d = now();
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, 1);
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		return now.getTime();
	}

	/**
	 * 获取制定日期内的周内的第<b> K </b>天
	 * 
	 * @param date
	 * @param k
	 * @return
	 */
	public static java.util.Date getWeekDay(java.util.Date date, int k) {
		if (date == null)
			date = now();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, k);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	// -----------------------------------------------------

	// --------------------- 6. --------------------------------
	/**
	 * java.sql.Date转换到java.util.Date
	 * 
	 * @param paraDate
	 * @return
	 */
	public static java.util.Date getUtilDateFromSql(java.sql.Date paraDate) {
		return new java.util.Date(paraDate.getTime());
	}

	/**
	 * util的Date类型保存进数据库时需要的转换，若通过getTime方法会丢失时分秒部分
	 * 
	 * @param paraDate
	 * @return
	 */
	public static java.sql.Timestamp getSqlDateFromUtil(java.util.Date paraDate) {
		if (paraDate == null)
			return null;
		String dateFormat = "yyyy-MM-dd HH:mm:ss";// 注意使用HH，24小时制
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return Timestamp.valueOf(sdf.format(paraDate));
	}

	/**
	 * 猜测传入对象的类型（yyyy-MM-dd） 包含三种：String\java.util.Date\Timestamp
	 * 
	 * @param o
	 * @return
	 * @throws Exception
	 */
	public static String formatDate(Object o) {
		if (o == null)
			return "";
		if (o.getClass() == String.class)
			return formatDate((String) o);
		else if (o.getClass() == java.util.Date.class)
			return formatDate((java.util.Date) o);
		else if (o.getClass() == Timestamp.class) {
			return formatDate(new java.util.Date(((Timestamp) o).getTime()));
		} else
			return o.toString();
	}

	/**
	 * 猜测传入对象的类型（yyyy-MM-dd HH:mm:ss） 包含三种：String\java.util.Date\Timestamp
	 * 
	 * @param o
	 * @return
	 * @throws Exception
	 */
	public static String formatDateTime(Object o) {
		if (o.getClass() == String.class)
			return formatDateTime(o);
		else if (o.getClass() == java.util.Date.class)
			return formatDateTime((java.util.Date) o);
		else if (o.getClass() == Timestamp.class) {
			return formatDateTime(new java.util.Date(((Timestamp) o).getTime()));
		} else
			return o.toString();
	}

	/**
	 * 获取日期的时间日期区间（String参数）
	 * 
	 * @param fromdate
	 *            开始时间
	 * @param todate
	 *            结束时间
	 * @param format
	 *            日期转换格式
	 * @return
	 * @throws Exception
	 */
	public static List<String> getDateZone(String fromdate, String todate) {
		String format = "yyyy-MM-dd";
		List<String> dateZone = new ArrayList<String>();

		long timediff = 1000 * 60 * 60 * 24;
		dateZone.add(fromdate);
		java.util.Date nowdate = null;
		java.util.Date fd = parseDate(fromdate, format);
		java.util.Date td = parseDate(todate, format);
		nowdate = new java.util.Date(fd.getTime() + timediff);

		while (nowdate.getTime() < td.getTime()) {
			dateZone.add(formatDate(nowdate, format));
			nowdate = new java.util.Date(nowdate.getTime() + timediff);
		}
		dateZone.add(todate);

		return dateZone;
	}

	/**
	 * 获取日期的时间区间（Date 参数）
	 * 
	 * @param fromdate
	 * @param todate
	 * @return
	 * @throws Exception
	 */
	public static List<String> getDateZone(java.util.Date fromdate,
			java.util.Date todate) {
		return getDateZone(formatDate(fromdate), formatDate(todate));
	}

	/**
	 * 获取日期的时间小时区间（String参数）
	 * 
	 * @param fromdate
	 * @param todate
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static List<String> getHourZone(String fromdate, String todate) {
		String format = "yyyy-MM-dd HH:mm:ss";
		List<String> dateZone = new ArrayList<String>();

		long timediff = 1000 * 60 * 60;
		dateZone.add(fromdate);
		SimpleDateFormat sf = new SimpleDateFormat(format);
		java.util.Date nowdate = null;
		java.util.Date fd = null;
		java.util.Date td = null;
		try {
			fd = sf.parse(fromdate);
			td = sf.parse(todate);
		} catch (ParseException e) {
			return null;
		}
		nowdate = new java.util.Date(fd.getTime() + timediff);

		while (nowdate.getTime() < td.getTime()) {
			dateZone.add(sf.format(nowdate));
			nowdate = new java.util.Date(nowdate.getTime() + timediff);
		}

		dateZone.add(todate);

		return dateZone;
	}

	public static List<String> getHourZone(java.util.Date fromdate,
			java.util.Date todate) {
		return getHourZone(formatDate(fromdate), formatDate(todate));
	}

	/**
	 * 获取日期时间
	 * 
	 * @param date
	 *            默认今天
	 * @param datepart
	 * @return
	 * @throws Exception
	 */
	public static int getDatePart(java.util.Date date, int datepart) {
		if (date == null)
			date = now();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(datepart);
	}

	public static int getYear(java.util.Date date) {
		return getDatePart(date, DateUtil.YEAR);
	}

	public static int getMonth(java.util.Date date) {
		return getDatePart(date, DateUtil.MONTH);
	}

	public static int getDate(java.util.Date date) {
		return getDatePart(date, DateUtil.DATE);
	}

	public static int getHour(java.util.Date date) {
		return getDatePart(date, DateUtil.HOUR);
	}

	public static int getMinute(java.util.Date date) {
		return getDatePart(date, DateUtil.MINUTE);
	}

	public static int getSecond(java.util.Date date) {
		return getDatePart(date, DateUtil.SECOND);
	}

	// -----------------------------------------------------

	// --------------------- 1. --------------------------------
	/**
	 * 字符串转换为java.util.Date<br>
	 * 支持格式为 yyyy.MM.dd G 'at' hh:mm:ss z 如 '2002-1-1 AD at 22:10:59 PSD'<br>
	 * yy/MM/dd HH:mm:ss 如 '2002/1/1 17:55:00'<br>
	 * yy/MM/dd HH:mm:ss pm 如 '2002/1/1 17:55:00 pm'<br>
	 * yy-MM-dd HH:mm:ss 如 '2002-1-1 17:55:00' <br>
	 * yy-MM-dd HH:mm:ss am 如 '2002-1-1 17:55:00 am' <br>
	 * 
	 * @param time
	 *            String 字符串<br>
	 * @return Date 日期<br>
	 */
	public static Date stringToDate(String time) {
		time=time.trim();
		SimpleDateFormat formatter;
		int tempPos = time.indexOf("AD");
		time = time.trim();
		formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z");
		if (tempPos > -1) {
			time = time.substring(0, tempPos) + "公元"
					+ time.substring(tempPos + "AD".length());// china
			formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z");
		}
		tempPos = time.indexOf("-");
		if (tempPos > -1 && (time.indexOf(" ") < 0)) {
			formatter = new SimpleDateFormat("yyyyMMddHHmmssZ");
		} else if (time.matches("\\d{14}")) {
			formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		} else if ((time.indexOf("/") > -1) && (time.indexOf(" ") > -1)) {
			formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		} else if ((time.indexOf("-") > -1) && (time.indexOf(" ") > -1)) {
			if(time.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}"))
				formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			else if(time.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}"))
				formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			else if(time.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{1,3}"))
				formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		} else if ((time.indexOf("/") > -1) && (time.indexOf("am") > -1)
				|| (time.indexOf("pm") > -1)) {
			formatter = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");
		} else if ((time.indexOf("-") > -1) && (time.indexOf("am") > -1)
				|| (time.indexOf("pm") > -1)) {
			formatter = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");
		}
		ParsePosition pos = new ParsePosition(0);
		java.util.Date ctime = formatter.parse(time, pos);

		return ctime;
	}
	// -----------------------------------------------------

	// --------------------- 1. --------------------------------
	// -----------------------------------------------------
	public static int getPeriodDay(String startDateStr, String endDateStr){
		
		Date startDate = DateUtil.parseDate(startDateStr);
		Date endDate   = DateUtil.parseDate(endDateStr);
		
		long diff = endDate.getTime()-startDate.getTime();
		int days = (int)diff/1000/3600/24;
		
		return days;
	}
	public static Date toTimeDate(Long time) {
		
	     return new Date(time);
	}
	
	public static String getCurrTime() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}
	public static String getCurrTime1() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date());
	}
	public static String getCurrTime2() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
	
	public static String getCurrTime3() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(new Date());
	}
	public static String getCurrTime4() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}
	public static String getCurrTime5() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(new Date());
	}
	/**
	 * 获取当前月第一天 yyyyMMdd
	 * @return
	 */
	public static String getFirstDayOfMonth2() {
		SimpleDateFormat   sdf   =   new   SimpleDateFormat( "yyyyMMdd");
		 Calendar   c   =   Calendar.getInstance(); 
		 c.set(Calendar.DAY_OF_MONTH,1);
		  return sdf.format(c.getTime());//当月第一天
	}
	/**
	 * 获取七天前 yyyyMMdd
	 * @return
	 */
	public static String getSevenDaysAgo() {
		SimpleDateFormat   sdf   =   new   SimpleDateFormat( "yyyyMMdd");
		Calendar   c   =   Calendar.getInstance(); 
		c.add(Calendar.DAY_OF_MONTH,-7);
		return sdf.format(c.getTime());//当月第一天
	}
	/**
     * 获取七天前 yyyy-MM-dd
     * @return
     */
    public static String getSevenDaysAgo1() {
        SimpleDateFormat   sdf   =   new   SimpleDateFormat( "yyyy-MM-dd");
        Calendar   c   =   Calendar.getInstance(); 
        c.add(Calendar.DAY_OF_MONTH,-7);
        return sdf.format(c.getTime());
    }
	/**
	 * 精确到毫秒
	 * @return
	 */
	public static String getDateWorkflow() {
		Calendar cal = Calendar.getInstance(); 
		Date date = cal.getTime(); 
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS"); 
		String myTime = sdFormat.format(date);//产生的流水号
		return myTime;
	}
	
	/**
	 * 获取前月的最后一天
	 * @return
	 */
    public static Date getLastDayOfPreviousMonth() {
        // 获取前月的第一天
        Calendar cale = Calendar.getInstance();   
        cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天 
        String lastDay = format.format(cale.getTime());
        System.out.println("-----2------lastDay:"+lastDay);
        
        return cale.getTime();
    }
    /**
     * 获取当前日期 yyyy-MM-dd
     * @return
     */
    public static String getToday(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());
        return today;
    } 
    /**
     *  获取明天日期 yyyyMMdd  下一天
     * @return
     */
    public static String getTomorrow(){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1); 
        String tomorrow = sdf.format(calendar.getTime());
        return tomorrow;
    }
    /**
     *  获取明天日期 yyyy-MM-dd  下一天
     *  参数yyyy-MM-dd
     * @return
     */
    public static String getNextDay(String date){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	Calendar calendar = Calendar.getInstance();
    	try {
			calendar.setTime(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	calendar.add(Calendar.DATE, 1); 
    	String nextDay = sdf.format(calendar.getTime());
    	return nextDay;
    }
    /**
     *  获取明天日期 yyyyMMdd  下一天
     *  参数yyyy-MM-dd
     * @return
     */
    public static String getNextDay2(String date){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	Calendar calendar = Calendar.getInstance();
    	try {
    		calendar.setTime(sdf.parse(date));
    	} catch (ParseException e) {
    		e.printStackTrace();
    	}
    	calendar.add(Calendar.DATE, 1); 
    	String nextDay = sdf.format(calendar.getTime());
    	return nextDay;
    }
    /**
     *  获取明天日期 yyyyMMdd HH:mm:ss  下一天
     * @param date
     * @return
     */
    public static String getNextDay3(String date){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm");
    	Calendar calendar = Calendar.getInstance();
    	try {
    		calendar.setTime(sdf.parse(date));
    	} catch (ParseException e) {
    		e.printStackTrace();
    	}
    	calendar.add(Calendar.DATE, 1); 
    	String nextDay = sdf.format(calendar.getTime());
    	return nextDay;
    }
    /**
     *  获取昨天日期 yyyy-MM-dd
     * @return
     */
    public static String getYestodayDay(){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(new Date());
    	calendar.add(Calendar.DATE, -1); 
    	String yestoDay = sdf.format(calendar.getTime());
    	return yestoDay;
    }
    /**
     *  获取昨天日期 yyyyMMdd
     * @return
     */
    public static String getYestodayDay1(){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(new Date());
    	calendar.add(Calendar.DATE, -1); 
    	String yestoDay = sdf.format(calendar.getTime());
    	return yestoDay;
    }
    /**
     * 获取某一天  YYYY-MM-dd
     * @param date
     * @param num
     * @return
     */
    public static String getYMD(String date,int num){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	Calendar calendar = Calendar.getInstance();
    	try {
			calendar.setTime(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	calendar.add(Calendar.DATE, num); 
    	String day = sdf.format(calendar.getTime());
    	return day;
    }
    /**
     * 获取以当天为基准的某一天   YYYY-MM-dd
     * @param date
     * @param num
     * @return
     */
    public static String getY_M_D(int num){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(new Date());
    	calendar.add(Calendar.DATE, num); 
    	String day = sdf.format(calendar.getTime());
    	return day;
    }
    /**
     * 获取当前日期的前7天日期 yyyy-MM-dd
     * @return
     */
    public static String getSevenDay(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -7); 
        String sevenDay = sdf.format(calendar.getTime());
        return sevenDay;
    }
    /**
     * 获取当前日期的月初第一天  yyyy-MM-dd
     * @return
     */
    public static String getFirstDayOfMonth(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, 1); //当前日期的本月的第一天
        String firstDay = sdf.format(calendar.getTime());
        return firstDay;
    }
    /**
     * 获取当前日期 月日MMdd
     * @return
     */
    public static String getMD(){
        SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
        String today = sdf.format(new Date());
        return today;
    } 
    /**
     * 获取当前日期的前7天日期 MMdd
     * @return
     */
    public static String getSevenMD(){
        SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -7); 
        String sevenDay = sdf.format(calendar.getTime());
        return sevenDay;
    }
    /**
     * 获取当前日期的前7天日期
     * @return
     */
    public static Date getSevenMdForDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -7); 
        return calendar.getTime();
    }
    /**
     * 获取昨天的日期
     * @return 昨天的日期
     */
    public static Date getYesterdayForDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1); 
        return calendar.getTime();
    }
    /**
     * 获取当前日期的月初第一天  MMdd
     * @return
     */
    public static String getFirstMD(){
        SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, 1); //当前日期的本月的第一天
        String firstDay = sdf.format(calendar.getTime());
        return firstDay;
    }
    /**
     * 获取当前日期的月初第一天 
     * @return
     */
    public static Date getFirstMDForDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, 1); //当前日期的本月的第一天
        return calendar.getTime();
    }
    /**
     * 获取时间区间的每个月月份
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<String> getEveryMonth(String startDate,String endDate){
        List<String> list = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM"); 
        Calendar cal = Calendar.getInstance(); 
        int count = 0;
        String month = "";
        try {
            while(!month.equals(endDate)){
                cal.setTime(sdf.parse(startDate));
                cal.add(Calendar.MONTH, count);
                month = sdf.format(cal.getTime()); 
                count ++;
                list.add(month);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }
    /**
     * 获取两个日期之间的所有 天  yyyyMMdd
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<String> getEveryYMD(String startDate,String endDate){
    	List<String> list = new ArrayList<String>();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 
    	Calendar cal = Calendar.getInstance(); 
    	int count = 0;
    	String month = "";
    	try {
    		while(!month.equals(endDate)){
    			cal.setTime(sdf.parse(startDate));
    			cal.add(Calendar.DAY_OF_MONTH, count);
    			month = sdf.format(cal.getTime()); 
    			count ++;
    			list.add(month);
    		}
    	} catch (ParseException e) {
    		e.printStackTrace();
    	}
    	return list;
    }
    /**
     * 使用 Map按key进行排序
     * @param map
     * @return
     */
    public static Map<String, Object> sortMapByKey(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, Object> sortMap = new TreeMap<String, Object>();
        sortMap.putAll(map);
        return sortMap;
    }
    /**
     * 将10 or 13 位时间戳转为时间字符串
     * convert the number 1407449951 1407499055617 to date/time format timestamp
     */
    public static String timestamp2Date(String str_num) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long timeNum=null;
        String date="";
        if(!StringUtils.isEmpty(str_num)){
	        try{
	        	timeNum=Long.parseLong(str_num);
	        }catch(Exception e){
	        	e.printStackTrace();
	        	date="";
	        }
	        if (str_num.length() == 13) {
	             date = sdf.format(new Date(timeNum));
	        } else {
	            date = sdf.format(new Date(timeNum * 1000L));
	        }
        }
        return date;
    }
    
    /**
     * 将10 or 13 位时间戳转为时间字符串
     * convert the number 1407449951 1407499055617 to date/time format timestamp
     */
    public static String timestamp2DateYMD(String str_num) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Long timeNum=null;
        String date="";
        if(!StringUtils.isEmpty(str_num)){
	        try{
	        	timeNum=Long.parseLong(str_num);
	        }catch(Exception e){
	        	e.printStackTrace();
	        	date="";
	        }
	        if (str_num.length() == 13) {
	             date = sdf.format(new Date(timeNum));
	        } else {
	            date = sdf.format(new Date(timeNum * 1000L));
	        }
        }
        return date;
    }
    
    /**
     * 将10 or 13 位时间戳转为时间字符串
     * convert the number 1407449951 1407499055617 to date/time format timestamp
     */
    public static String timestamp2Date(Long timeNum) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String date="";
    	if(timeNum!=null){
    		if (timeNum.SIZE == 13) {
    			date = sdf.format(new Date(timeNum));
    		} else if(timeNum.SIZE==10){
    			date = sdf.format(new Date(timeNum * 1000L));
    		}else{
    		}
    	}
    	return date;
    }
    /**
     * 判断日期是否一致
     * 把二个时间的的年月日分别对比，完全相等就是同一天
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                        .get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }
    /** 
     * 计算两个日期之间相差的天数 
     * @param date1 
     * @param date2 
     * @return 
     */  
    public static int daysBetween(String date1,String date2){
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();  
        long between_days=0;
        try {
        	
			cal.setTime(sdf.parse(date1));
	        long time1 = cal.getTimeInMillis();               
	        cal.setTime(sdf.parse(date2));  
	        long time2 = cal.getTimeInMillis();       
	        between_days=(time2-time1)/(1000*3600*24);  
	       
        } catch (ParseException e) {
			e.printStackTrace();
		}  
        return Integer.parseInt(String.valueOf(between_days));        
    }  
    /** 
     * 根据开始时间和结束时间返回时间段内的时间集合 
     *  
     * @param beginDate 
     * @param endDate 
     * @return List 
     */  
    public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {  
        List<Date> lDate = new ArrayList<Date>();  
        lDate.add(beginDate);// 把开始时间加入集合  
        Calendar cal = Calendar.getInstance();  
        // 使用给定的 Date 设置此 Calendar 的时间  
        cal.setTime(beginDate);  
        boolean bContinue = true;  
        while (bContinue) {  
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量  
            cal.add(Calendar.DAY_OF_MONTH, 1);  
            // 测试此日期是否在指定日期之后  
            if (endDate.after(cal.getTime())) {  
                lDate.add(cal.getTime());  
            } else {  
                break;  
            }  
        }  
        lDate.add(endDate);// 把结束时间加入集合  
        return lDate;  
    }  
    /**
     * 获取当前的日
     * @return
     */
    public static int getDay(){
        Calendar cal=Calendar.getInstance();    
        int d=cal.get(Calendar.DATE);    
        return d;
    }
    
    /**
     * 计算两个日期时间相差分钟
     * @param dateTime1: yyyy-MM-dd HH:mm:ss
     * @param dateTime2: yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long minuteBetween(String dateTime1,String dateTime2){
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	long diff = 0;
		Date d1,d2;
		try {
			d1 = df.parse(dateTime1);
			d2 = df.parse(dateTime2); 
			diff = (d1.getTime() - d2.getTime())/1000/60;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	    return diff;
    }
    
	public static void main(String[] args) throws ParseException {
//		System.out.println(getY_M_D(1));
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
//		System.out.println(daysBetween("20161016","20161016"));
//		List<String> list=getEveryYMD("20160725","20160810");
//		for(String str:list){
//			System.out.println(str);
//		}
//		String d= DateUtil.formatDate("yyyyMMddHHmmss");
//		System.out.println(d);
//		System.out.println(DateUtil.parseDateTime(new Date()));
		
		/*String formatDate = DateUtil.formatDate(null, null);
		System.out.println("mkd="+formatDate);
		int i = DateUtil.diffDate(5, DateUtil.parseDate("2015-10-27"), new Date());
		
		int needdiff = 24 * 60 * 60 * 1000;
		
		Date date = new Date();
		long k = DateUtil.parseDate(date).getTime();
		long j = DateUtil.parseDate("2015-11-12").getTime();
		
		int diffnum = (int) (k  / needdiff) - (int) (j / needdiff);
		
		System.out.println(k-j);*/
		
	    /*Date date = null;
		try {
			DateFormat commondf = new SimpleDateFormat("yyyy-MM-dd");
			date = commondf.parse("2014-08-13 01:05:11");
		} catch (ParseException e) {
		}
		
		System.out.println(getDate(date));
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		
		System.out.println(c.getTime());*/
		//System.out.println(date.getYear()*365*1+" "+date.getMonth() +" "+date.getDay());;
//		String name="hhhhhhh.hhh";
//		System.out.println(name.contains("."));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = df.parse("2017-01-13 13:50:10");   
	    Date d2 = df.parse("2017-01-13 13:31:11");   
	    long diff = (d1.getTime() - d2.getTime())/1000/60;
	    
	    
	    System.out.println("diff: "+diff);
		
	}
/**
 * 2017-01-22  yyyy-MM-dd HH:mm:ss
 * @param lastReportTime
 * @return
 */
	public static boolean isToday(String lastReportTime) {
		boolean flag=false;
		if(lastReportTime==null||lastReportTime.equals("")||lastReportTime.length()<10){
			flag=true;
		}
		lastReportTime=lastReportTime.substring(0,10);
		if(lastReportTime.equals(DateUtil.getCurrTime4())){
			flag=true;
		}
		return flag;
	}
	
}
