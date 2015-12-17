package com.pipipark.j.system.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.pipipark.j.system.exception.PPPTipsException;


/***
 * 日期工具,
 * 常用以及特殊时间获取的工具.
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
public class PPPDate implements PPPComparable<PPPDate>{

	private static final ConcurrentMap<String, SimpleDateFormat> formats = new ConcurrentHashMap<String, SimpleDateFormat>();
	
	private Date date;
	private PPPDate(Date date){
		this.date = date;
	}
	
	/**
	 * 设置时间
	 * @param date
	 */
	public static final PPPDate set(Date date){
		return new PPPDate(date);
	}
	
	/**
	 * 设置时间
	 * @param milliSecond
	 */
	public static final PPPDate set(Long milliSecond){
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(milliSecond);
		return new PPPDate(c.getTime());
	}
	
	/**
	 * 根据时间字符串设置时间
	 * @param string
	 */
	public static final PPPDate set(String stringDate){
		SimpleDateFormat format = formater(PPPConstant.DateFormats.yyyy_MM_dd);
		try {
			return set(format.parse(stringDate));
		} catch (ParseException e) {
			throw new PPPTipsException("["+stringDate+"]日期格式解析错误!", e);
		}
	}
	
	/**
	 * 根据时间字符串和格式设置时间
	 * @param PPPDate
	 */
	public static final PPPDate set(String stringDate, String pattern){
		SimpleDateFormat format = formater(pattern);
		try {
			return set(format.parse(stringDate));
		} catch (ParseException e) {
			throw new PPPTipsException("["+stringDate+"]日期格式解析错误!", e);
		}
	}
	
	/**
	 * 当前时间.
	 * @return PPPDate
	 */
	public static final PPPDate now(){
		Calendar c = Calendar.getInstance();
		return set(c.getTime());
	}
	
	/**
	 * 今天.
	 * @return PPPDate
	 */
	public static final PPPDate today(){
		return set(defaultCalendar().getTime());
	}
	
	/**
	 * 昨天.
	 * @return PPPDate
	 */
	public static final PPPDate yesterday(){
		Calendar c = defaultCalendar();
		c.add(Calendar.DATE, -1);
		return set(c.getTime());
	}
	/**
	 * 前天....(1=昨天的昨天=前天,2=昨天的昨天的昨天=大前天.....)
	 * @return PPPDate
	 */
	public static final PPPDate yesterday(Integer add){
		Calendar c = defaultCalendar();
		c.add(Calendar.DATE, -(1+add));
		return set(c.getTime());
	}
	
	/**
	 * 明天.
	 * @return PPPDate
	 */
	public static final PPPDate tomorrow(){
		Calendar c = defaultCalendar();
		c.add(Calendar.DATE, 1);
		return set(c.getTime());
	}
	
	/**
	 * 后天....(1=明天的明天=后天,2=明天的明天的明天=大后天.....)
	 * @return PPPDate
	 */
	public static final PPPDate tomorrow(Integer add){
		Calendar c = defaultCalendar();
		c.add(Calendar.DATE, 1+add);
		return set(c.getTime());
	}
	
	/**
	 * sub秒前的时间点
	 * @param sub
	 */
	public static final PPPDate secondAgo(Integer sub){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.SECOND, -sub);
		return set(c.getTime());
	}
	
	/**
	 * sub分钟前的时间点
	 * @param sub
	 */
	public static final PPPDate minuteAgo(Integer sub){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -sub);
		return set(c.getTime());
	}
	
	/**
	 * sub小时前的时间点
	 * @param sub
	 */
	public static final PPPDate hourAgo(Integer sub){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, -sub);
		return set(c.getTime());
	}
	
	/**
	 * add分钟后时间点
	 * @param add
	 */
	public static final PPPDate secondLater(Integer add){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.SECOND, add);
		return set(c.getTime());
	}
	
	/**
	 * add分钟后时间点
	 * @param add
	 */
	public static final PPPDate minuteLater(Integer add){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, add);
		return set(c.getTime());
	}
	
	/**
	 * add小时后时间点
	 * @param add
	 */
	public static final PPPDate hourLater(Integer add){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, add);
		return set(c.getTime());
	}
	
	
	/**
	 * 当年本月第一天.
	 * @return PPPDate
	 */
	public static final PPPDate firstDayOfMonth(){
		Calendar c = defaultCalendar();
		c.set(Calendar.DAY_OF_MONTH,1);
		return set(c.getTime());
	}
	
	/**
	 * 当年某月第一天.(1=下个月第一天,-1=上个月第一天)
	 * @return PPPDate
	 */
	public static final PPPDate firstDayOfMonth(Integer month){
		Calendar c = defaultCalendar();
		c.set(c.get(Calendar.YEAR), month-1, 1);
		return set(c.getTime());
	}
	
	/**
	 * 某年某月第一天.(1=下个月第一天,-1=上个月第一天)
	 * @return PPPDate
	 */
	public static final PPPDate firstDayOfMonth(Integer year, Integer month){
		Calendar c = defaultCalendar();
		c.set(year, month-1, 1);
		return set(c.getTime());
	}
	
	/**
	 * 当年本月最后一天.
	 * @return PPPDate
	 */
	public static final PPPDate lastDayOfMonth(){
		Calendar c = defaultCalendar();
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return set(c.getTime());
	}
	
	/**
	 * 当年某月最后一天.(1=下个月最后一天,-1=上个月最后一天)
	 * @return PPPDate
	 */
	public static final PPPDate lastDayOfMonth(Integer month){
		Calendar c = defaultCalendar();
		c.set(Calendar.MONTH, month-1);
		int day = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		c.set(c.get(Calendar.YEAR), month-1, day);
		return set(c.getTime());
	}
	
	/**
	 * 某年某月最后一天.(1=下个月最后一天,-1=上个月最后一天)
	 * @return PPPDate
	 */
	public static final PPPDate lastDayOfMonth(Integer year, Integer month){
		Calendar c = defaultCalendar();
		c.set(Calendar.MONTH, month-1);
		int day = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		c.set(year, month-1, day);
		return set(c.getTime());
	}
	
	/**
	 * 本周周日
	 * @return PPPDate
	 */
	public static final PPPDate sunday(){
		Calendar c = defaultCalendar();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0) {
			day_of_week = 7;
		}
		c.add(Calendar.DATE, -day_of_week + 7);
		return set(c.getTime());
	}
	
	/**
	 * 本周周一
	 * @return PPPDate
	 */
	public static final PPPDate monday(){
		Calendar c = defaultCalendar();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0) {
			day_of_week = 7;
		}
		c.add(Calendar.DATE, -day_of_week + 1);
		return set(c.getTime());
	}
	
	/**
	 * 本周周二
	 * @return PPPDate
	 */
	public static final PPPDate tuesday(){
		Calendar c = defaultCalendar();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0) {
			day_of_week = 7;
		}
		c.add(Calendar.DATE, -day_of_week + 2);
		return set(c.getTime());
	}
	
	/**
	 * 本周周三
	 * @return PPPDate
	 */
	public static final PPPDate wednesday(){
		Calendar c = defaultCalendar();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0) {
			day_of_week = 7;
		}
		c.add(Calendar.DATE, -day_of_week + 3);
		return set(c.getTime());
	}
	
	/**
	 * 本周周四
	 * @return PPPDate
	 */
	public static final PPPDate thursday(){
		Calendar c = defaultCalendar();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0) {
			day_of_week = 7;
		}
		c.add(Calendar.DATE, -day_of_week + 4);
		return set(c.getTime());
	}
	
	/**
	 * 本周周五
	 * @return PPPDate
	 */
	public static final PPPDate friday(){
		Calendar c = defaultCalendar();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0) {
			day_of_week = 7;
		}
		c.add(Calendar.DATE, -day_of_week + 5);
		return set(c.getTime());
	}
	
	/**
	 * 本周周六
	 * @return PPPDate
	 */
	public static final PPPDate saturday(){
		Calendar c = defaultCalendar();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0) {
			day_of_week = 7;
		}
		c.add(Calendar.DATE, -day_of_week + 6);
		return set(c.getTime());
	}
	
	/**
	 * 时间相差天数,
	 * 返回 0=同一天.
	 * @param target
	 */
	public final Integer betweenDay(Date target){
		Date meDate = this.timeShort();
		Date targetDate = set(target).timeShort();
		Long between = Math.abs((meDate.getTime()-targetDate.getTime())/(1000*3600*24));
		return between.intValue();
	}
	
	/**
	 * 是否相同时间,
	 * 精确到秒.
	 * @param Date
	 */
	public final Boolean isSame(Date target){
		if(target==null){
			return false;
		}
		if((date.getTime()/1000)==(target.getTime()/1000)){
			return true;
		}
		return false;
	}
	
	/**
	 * 是否相同时间,
	 * 精确到天.
	 * @param Date
	 */
	public final Boolean isSameDay(Date target){
		if(target==null){
			return false;
		}
		if(this.formatShort().equals(set(target).formatShort())){
			return true;
		}
		return false;
	}
	
	/**
	 * 是否相同时间,
	 * 精确到月.
	 * @param Date
	 */
	public final Boolean isSameWeek(Date target){
		if(!isSameYear(target)){
			return false;
		}
		if(weeksInYear()==set(target).weeksInYear()){
			return true;
		}
		return false;
	}
	
	/**
	 * 是否相同时间,
	 * 精确到月.
	 * @param Date
	 */
	public final Boolean isSameMonth(Date target){
		if(target==null){
			return false;
		}
		String yyyy_MM = "yyyy-MM";
		if(this.format(yyyy_MM).equals(set(target).format(yyyy_MM))){
			return true;
		}
		return false;
	}
	
	/**
	 * 是否相同时间,
	 * 精确到年.
	 * @param date
	 */
	public final Boolean isSameYear(Date target){
		Calendar meCalendar = defaultCalendar();
		int meYear = meCalendar.get(Calendar.YEAR);
		Calendar targetCalendar = defaultCalendar();
		targetCalendar.setTime(target);
		int targetYear = targetCalendar.get(Calendar.YEAR);
		if(meYear==targetYear){
			return true;
		}
		return false;
	}
	
	/**
	 * 属于本年第几周
	 * @return weeks count
	 */
	public final Integer weeksInYear(){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.WEEK_OF_YEAR);
	}
	
	/**
	 * 属于本月第几周
	 * @return weeks count
	 */
	public final Integer weeksInMonth(){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.WEEK_OF_MONTH);
	}
	
	
	/**
	 * 长格式化
	 * @return 格式化后的时间字符串
	 */
	public final String format(){
		return formater(PPPConstant.DateFormats.yyyy_MM_dd_HH_mm_ss).format(this.date);
	}
	
	/**
	 * 自定义格式化
	 * @return 格式化后的时间字符串
	 */
	public final String format(String pattern){
		return formater(pattern).format(this.date);
	}
	
	/**
	 * 短格式化
	 * @return 格式化后的时间字符串
	 */
	public final String formatShort(){
		return formater(PPPConstant.DateFormats.yyyy_MM_dd).format(this.date);
	}
	
	/**
	 * 长日期
	 * yyyy-MM-dd HH:mm:ss
	 * @return 长日期时间
	 */
	public final Date time(){
		SimpleDateFormat format = formater(PPPConstant.DateFormats.yyyy_MM_dd_HH_mm_ss);
		try {
			return format.parse(format.format(this.date));
		} catch (ParseException e) {
			throw new PPPTipsException("Date format exception!", e);
		}
	}
	
	/**
	 * 短日期
	 * yyyy-MM-dd
	 * @return 短日期时间
	 */
	public final Date timeShort(){
		SimpleDateFormat format = formater(PPPConstant.DateFormats.yyyy_MM_dd);
		try {
			return format.parse(format.format(this.date));
		} catch (ParseException e) {
			throw new PPPTipsException("date format exception!", e);
		}
	}
	
	/**
	 * 根据时间获得年份.
	 * @return year
	 */
	public final Integer year(){
		Calendar c = Calendar.getInstance();
		if(date!=null){
			c.setTime(date);
		}
		return c.get(Calendar.YEAR);
	}
	
	/**
	 * 根据时间获得月份.
	 * @return month
	 */
	public final Integer month(){
		Calendar c = Calendar.getInstance();
		if(date!=null){
			c.setTime(date);
		}
		return c.get(Calendar.MONTH)+1;
	}
	
	/**
	 * 根据时间获得星期几,
	 * 星期一=1,星期日=7.
	 * @return week
	 */
	public final Integer week(){
		Calendar c = Calendar.getInstance();
		if(date!=null){
			c.setTime(date);
		}
		int week = c.get(Calendar.DAY_OF_WEEK)-1;
		if(week==0){
			week=7;
		}
		return week;
	}
	
	
	/**
	 * 根据时间获得日期.
	 * @return day
	 */
	public final Integer day(){
		Calendar c = Calendar.getInstance();
		if(date!=null){
			c.setTime(date);
		}
		return c.get(Calendar.DATE);
	}
	
	/**
	 * 默认Calendar工具,
	 * 清空时分秒和毫秒,否则会得到当前的时分秒.
	 */
	private static final Calendar defaultCalendar(){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND,0);
		c.set(Calendar.MILLISECOND, 0);
		return c;
	}
	
	/**
	 * 获取格式化对象
	 * @param pattern
	 * @return SimpleDateFormat
	 */
	private static final SimpleDateFormat formater(String pattern){
		SimpleDateFormat format = formats.get(pattern);
		if(format==null){
			format = new SimpleDateFormat(pattern);
			formats.put(pattern, format);
		}
		return format;
	}

	/**
	 * 集合中的排序算法,
	 * 时间越大越排在前面.
	 */
	@Override
	public int compareTo(PPPDate targetDate) {
		if(this == targetDate){
			return 0;
		}
		Date me = this.time();
		Date target = targetDate.time();
		if(me==null || target==null){
			throw new PPPTipsException("PPPDate time can't be Initialization!");
		}
		if(me.after(target)){
			return -1;
		}else if(me.before(target)){
			return 1;
		}
		return 0;
	}
}
