package com.hailong.common.utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 计算日期间隔工具类
 * 
 * @author wangtengfei
 * @date 2016年12月8日 上午11:29:18
 */
public class DateIntervalUtil {
	
	public static final int YEAR_RETURN = 0;

	public static final int MONTH_RETURN = 1;

	public static final int DAY_RETURN = 2;

	public static final int HOUR_RETURN = 3;

	public static final int MINUTE_RETURN = 4;

	public static final int SECOND_RETURN = 5;

	public static final String YYYY = "yyyy";

	public static final String YYYYMM = "yyyy-MM";

	public static final String YYYYMMDD = "yyyy-MM-dd";

	public static final String YYYYMMDDHH = "yyyy-MM-dd HH";

	public static final String YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";

	public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 传入起始时间和结束时间，获取两个时间间隔转换为年、月、日、时、分、秒其中之一
	 * @param beginTime
	 * @param endTime
	 * @param formatPattern
	 * @param returnPattern
	 * @return
	 * @throws Exception
	 */
	public static long getBetween(String beginTime, String endTime, String formatPattern, int returnPattern) throws Exception {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatPattern);
		Date beginDate = simpleDateFormat.parse(beginTime);
		Date endDate = simpleDateFormat.parse(endTime);
		Calendar beginCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		beginCalendar.setTime(beginDate);
		endCalendar.setTime(endDate);
		switch (returnPattern) {
		case YEAR_RETURN:
			return DateIntervalUtil.getByField(beginCalendar, endCalendar, Calendar.YEAR);
		case MONTH_RETURN:
			return DateIntervalUtil.getByField(beginCalendar, endCalendar,	Calendar.YEAR) * 12	+ DateIntervalUtil.getByField(beginCalendar, endCalendar, Calendar.MONTH);
		case DAY_RETURN:
			return DateIntervalUtil.getTime(beginDate, endDate)	/ (24 * 60 * 60 * 1000);
		case HOUR_RETURN:
			return DateIntervalUtil.getTime(beginDate, endDate) / (60 * 60 * 1000);
		case MINUTE_RETURN:
			return DateIntervalUtil.getTime(beginDate, endDate) / (60 * 1000);
		case SECOND_RETURN:
			return DateIntervalUtil.getTime(beginDate, endDate) / 1000;
		default:
			return 0;
		}
	}
	
	/**
	 * 传入起始时间和结束时间和，获取两个时间间隔转换为年、月、日、时、分、秒
	 * @param beginTime
	 * @param endTime
	 * @param formatPattern
	 * @param returnPattern
	 * @return
	 * @throws Exception
	 */
	public static String getBetween(String beginTime, String endTime, String formatPattern) throws Exception {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatPattern);
		Date beginDate = simpleDateFormat.parse(beginTime);
		Date endDate = simpleDateFormat.parse(endTime);
		Calendar beginCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		beginCalendar.setTime(beginDate);
		endCalendar.setTime(endDate);
		
		/*返回年*/
		long yearReturn = DateIntervalUtil.getByField(beginCalendar, endCalendar, Calendar.YEAR);
		if(yearReturn>0){
			return yearReturn + "年";
		}
		
		/*不足一年的返回月*/
		long monthReturn = DateIntervalUtil.getByField(beginCalendar, endCalendar, Calendar.MONTH);
		if(monthReturn>0){
			return monthReturn + "个月";
		}
		
		/*不足一个月的返回天和小时*/
		long dayReturn = DateIntervalUtil.getTime(beginDate, endDate) / (24 * 60 * 60 * 1000);
		if(dayReturn > 0){
			long remainTime = DateIntervalUtil.getTime(beginDate, endDate) - (24 * 60 * 60 * 1000)*dayReturn;
			long hourReturn = remainTime / (60 * 60 * 1000);
			if(hourReturn>0){
				return dayReturn+"天"+hourReturn+"小时";
			}else{
				return dayReturn+"天";
			}
		}
		
		/*不足一天的返回小时和分钟*/
		long hourReturn = DateIntervalUtil.getTime(beginDate, endDate) / (60 * 60 * 1000);
		if(hourReturn>0){
			long remainTime = DateIntervalUtil.getTime(beginDate, endDate) - (60 * 60 * 1000)*hourReturn;
			long minuteReturn = remainTime/(60 * 1000);
			if(minuteReturn>0){
				return hourReturn+"小时"+minuteReturn+"分钟";
			}else{
				return hourReturn+"小时";
			}
		}
		
		/*不足一小时的返回分钟*/
		long minuteReturn = DateIntervalUtil.getTime(beginDate, endDate) / (60 * 1000);
		if(minuteReturn>0){
			return minuteReturn+"分钟";
		}else{
			return "一分钟内";
		}
	}

	private static long getByField(Calendar beginCalendar,Calendar endCalendar, int calendarField) {
		return endCalendar.get(calendarField) - beginCalendar.get(calendarField);
	}

	private static long getTime(Date beginDate, Date endDate) {	
		return endDate.getTime() - beginDate.getTime();
	}

	/**
	 * 根据起始时间和结束时间返回时间间隔的每个小时数
	 * @param 2017-01-20 00
	 * @param 2017-01-21 00
	 * @return 时间间隔集合
	 * @throws ParseException 
	 */
	public static List<String> getHourInterval(String startTime,String endTime) throws ParseException{
		List<String> listDate = new ArrayList<String>();
		listDate.add(startTime);//把开始时间加入集合
	    Calendar cal = Calendar.getInstance();
	    //使用给定的 Date 设置此 Calendar 的时间
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd HH");
	    Date startDate=sdf.parse(startTime);
	    Date endDate=sdf.parse(endTime);
	    cal.setTime(startDate);
	    boolean bContinue = true;
	    while (bContinue) {
	        //根据日历的规则，为给定的日历字段添加或减去指定的时间量
	        cal.add(Calendar.HOUR_OF_DAY, 1);
	        // 测试此日期是否在指定日期之后
	        if (endDate.after(cal.getTime())) {
	        	listDate.add(sdf.format(cal.getTime()));
	        } else {
	            break;
	        }
	    }
	    listDate.add(endTime);//把结束时间加入集合
	    return listDate;
	}
	
	public static void main(String[] args) {
		try {
			//System.out.println(DateIntervalUtil.getBetween("2013-05-02", "2013-05-05",	DateIntervalUtil.YYYYMMDD, DateIntervalUtil.MONTH_RETURN));
			System.out.println(DateIntervalUtil.getHourInterval("20161207 10", "20161208 10"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
