package com.pipipark.j.system.testing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.pipipark.j.system.core.PPPDate;

public class TestDate {

	public static void main(String[] args) {
		System.out.println("年: "+PPPDate.now().year());
		System.out.println("月:　"+PPPDate.now().month());
		System.out.println("日:　"+PPPDate.now().day());
		System.out.println("周:　"+PPPDate.set("2015-11-22").week());
		System.out.println("今天: "+PPPDate.today().format("yyyy-MM-dd HH:mm:ss"));
		System.out.println("昨天: "+PPPDate.yesterday().format());
		System.out.println("前天: "+PPPDate.yesterday(1).format());
		System.out.println("明天: "+PPPDate.tomorrow().format());
		System.out.println("后天: "+PPPDate.tomorrow(1).format());
		System.out.println("本月第一天: "+PPPDate.firstDayOfMonth().format());
		System.out.println("二月第一天: "+PPPDate.firstDayOfMonth(2).format());
		System.out.println("2014年5月第一天: "+PPPDate.firstDayOfMonth(2014,5).format());
		System.out.println("本月最后一天: "+PPPDate.lastDayOfMonth().format());
		System.out.println("十月最后一天: "+PPPDate.lastDayOfMonth(10).format());
		System.out.println("2013年5月最后一天: "+PPPDate.lastDayOfMonth(2013,5).format());
		System.out.println("星期天: "+PPPDate.sunday().format());
		System.out.println("星期一: "+PPPDate.monday().format());
		System.out.println("本年第几周(当天): "+PPPDate.today().weeksInYear());
		System.out.println("本月第几周(当天): "+PPPDate.today().weeksInMonth());
		System.out.println("本年第几周(指定): "+PPPDate.set(new Date()).weeksInYear());
		System.out.println("本月第几周(指定): "+PPPDate.set("2015-1-18").weeksInMonth());
		
		System.out.println("毫秒: "+PPPDate.set(new Date().getTime()).format());
		
		System.out.println("6秒钟前: "+PPPDate.secondAgo(6).format());
		System.out.println("6分钟前: "+PPPDate.minuteAgo(6).format());
		System.out.println("6小时前: "+PPPDate.hourAgo(6).format());
		
		List<PPPDate> dates = new ArrayList<PPPDate>();
		dates.add(PPPDate.hourAgo(10));
		dates.add(PPPDate.now());
		dates.add(PPPDate.minuteAgo(10));
		Collections.sort(dates);
		for (PPPDate date : dates) {
			System.out.println("排序: "+date.format());
		}
		
		System.out.println("相同时间(时): "+PPPDate.set("2015-11-18 12:56:26", PPPDate.Dateformat.yyyyMMddHHmmss.value()).isSameHour(PPPDate.set("2015-11-18 12:51:16", PPPDate.Dateformat.yyyyMMddHHmmss.value()).time()));
		System.out.println("相同时间(分): "+PPPDate.set("2015-11-18 12:56:26", PPPDate.Dateformat.yyyyMMddHHmmss.value()).isSameMinute(PPPDate.set("2015-11-18 11:56:36", PPPDate.Dateformat.yyyyMMddHHmmss.value()).time()));
		System.out.println("相同时间(秒): "+PPPDate.set("2015-11-18 12:56:26", PPPDate.Dateformat.yyyyMMddHHmmss.value()).isSameSecond(PPPDate.set("2015-11-18 12:57:26", PPPDate.Dateformat.yyyyMMddHHmmss.value()).time()));
		System.out.println("相同天: "+PPPDate.set("2015-10-18").isSameDay(PPPDate.set("2015-10-18 14:40:01", PPPDate.Dateformat.yyyyMMddHHmmss.value()).time()));
		System.out.println("相同月: "+PPPDate.set("2015-10-18").isSameMonth(PPPDate.set("2015-10-35 14:40:01", PPPDate.Dateformat.yyyyMMddHHmmss.value()).time()));
		System.out.println("相同周: "+PPPDate.set("2015-10-18").isSameWeek(PPPDate.set("2015-10-22 14:40:01", PPPDate.Dateformat.yyyyMMddHHmmss.value()).time()));
		System.out.println("相同年: "+PPPDate.set("2015-11-18").isSameYear(PPPDate.set("2015-11-18 14:40:01", PPPDate.Dateformat.yyyyMMddHHmmss.value()).time()));
		
		System.out.println(PPPDate.set("2015-11-18").betweenDay(PPPDate.set("2015-11-18 14:40:01", PPPDate.Dateformat.yyyyMMddHHmmss.value()).time()));
		
	}

}
