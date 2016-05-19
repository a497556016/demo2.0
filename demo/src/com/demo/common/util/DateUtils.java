package com.demo.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
	private static final String date_pattern = "yyyy-MM-dd";
	private static final String time_pattern = "yyyy-MM-dd HH:mm:ss";
	private static final String year_month_pattern = "yyyy-MM";
	
	public static String getCurTime() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat(time_pattern);
		return format.format(date);
	}
	
	public static String getCurDate() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat(date_pattern);
		return format.format(date);
	}
	
	public static String getCurDate(String fmt) {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat(fmt);
		return format.format(date);
	}

	public static String getFirstDateOfCurMonth() {
		String curYearMonth = getCurYearMonth();
		return curYearMonth+"-01";
	}

	public static String getLastDateOfCurMonth() {
		String curYearMonth = getCurYearMonth();

		Calendar calendar = Calendar.getInstance();
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return curYearMonth+"-"+maxDay;
	}
	
	public static String getCurYear(){
		String curTime = getCurDate();
		String curYear = curTime.split("-")[0];
		return curYear;
	}
	
	public static String getCurMonth(){
		String curTime = getCurDate();
		String curMonth = curTime.split("-")[1];
		return curMonth;
	}
	
	public static String getCurYearMonth(){
		String curTime = getCurDate();
		String curYearMonth = curTime.substring(0, 7);
		return curYearMonth;
	}
	
	/**
	 * 获取下一个月
	 * @param yearMonth
	 * @return
	 */
	public static String getNextYearMonth(String yearMonth){
		SimpleDateFormat format = new SimpleDateFormat(year_month_pattern);
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(format.parse(yearMonth));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.add(Calendar.MONTH, 1);
		return format.format(calendar.getTime());
	}
	
	/**
	 * 比较两个日期，返回-1表明date1小于date2
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compare(Date date1,Date date2){
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date2);
		return calendar1.compareTo(calendar2);
	}
	
	public static Date parseDate(String date,String format){
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(compare(parseDate("2015-07", "yyyy-MM"), parseDate("2015-06", "yyyy-MM")));
	}

}
