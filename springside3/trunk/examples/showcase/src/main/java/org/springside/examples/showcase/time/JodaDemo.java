package org.springside.examples.showcase.time;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.util.Assert;

public class JodaDemo {

	public static void main(String[] args) {
		testDateAndZone();
		testGetAge();
	}

	public static void testDateAndZone() {

		System.out.println(new DateTime().getMillis() + " " + System.currentTimeMillis());

		DateTime fooDate = new DateTime(1978, 6, 1, 12, 10, 8, 0);
		System.out.println(fooDate.toString("yyyy-MM-dd HH:mm:sszZ") + " " + fooDate.getMillis()); //"1978-06-01 12:10:08" 

		DateTime zoneWithUTC = fooDate.withZone(DateTimeZone.UTC);
		System.out.println(zoneWithUTC.toString("yyyy-MM-dd HH:mm:sszZ") + " " + zoneWithUTC.getMillis());//"1978-06-01 04:10:08", sameMills

		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		DateTimeZone timeZone = DateTimeZone.forID("US/Pacific");
		DateTime parserResult1 = fmt.withZone(timeZone).parseDateTime("1978-06-01 12:10:08");
		DateTime parserResult2 = fmt.withZone(DateTimeZone.UTC).parseDateTime("1978-06-01 12:10:08");
		System.out.println(parserResult1.toString("yyyy-MM-dd HH:mm:sszZ") + " " + parserResult1.getMillis());
		System.out.println(parserResult2.toString("yyyy-MM-dd HH:mm:sszZ") + " " + parserResult2.getMillis());
	}

	public static void testGetAge() {
		DateTime now = new DateTime();

		DateTime oneYearsAgo = now.minusYears(2).plusDays(20);
		DateTime twoYearsAgo = now.minusYears(2);
		Assert.isTrue(1 == getAge(oneYearsAgo));
		Assert.isTrue(2 == getAge(twoYearsAgo));
	}

	public static int getAge(DateTime birthDate) {
		return Years.yearsBetween(birthDate, new DateTime()).getYears();
	}

}
