package com.sdimdev.nnhackaton.utils.system.other;

import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

	public static double to(Date date) {
		if (date == null) {
			return 0;
		}

		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		int Y = calendar.get(GregorianCalendar.YEAR);
		int M = calendar.get(GregorianCalendar.MONTH) + 1;
		int D = calendar.get(GregorianCalendar.DAY_OF_MONTH);

		if (M <= 2) {
			Y--;
			M += 12;
		}
		int A = Y / 100;
		int B = 2 - A + (A / 4);
		int X1 = (int) (365.25 * (Y + 4716));
		int X2 = (int) (30.6001 * (M + 1));
		double jdt = X1 + X2 + D + B - 1524.5;

		int h = calendar.get(GregorianCalendar.HOUR_OF_DAY);
		int m = calendar.get(GregorianCalendar.MINUTE);
		int s = calendar.get(GregorianCalendar.SECOND);
		jdt += (h * 3600.0 + m * 60.0 + s) / 86400.0;
		return jdt;
	}

	/**
	 * Convert from julian day to Date
	 *
	 * @param jdt date in julian day format
	 * @return Date. Never return null
	 */
	@NonNull
	public static Date from(double jdt) {
		// Extract date:
		int Z = (int) (jdt + 0.5);
		int A = (int) ((Z - 1867216.25) / 36524.25);
		A = Z + 1 + A - (A / 4);
		int B = A + 1524;
		int C = (int) ((B - 122.1) / 365.25);
		int D = (int) (365.25 * C);
		int E = (int) ((B - D) / 30.6001);
		int X1 = (int) (30.6001 * E);
		D = B - D - X1;
		int MN = E < 14 ? E - 1 : E - 13;
		int Y = MN > 2 ? C - 4716 : C - 4715;

		//Extract time:
		Z = (int) (jdt + 0.5);
		int s = (int) ((jdt + 0.5 - Z) * 86400000.0 + 0.5);
		double S = 0.001 * s;
		s = (int) (S);
		S -= s;
		int H = s / 3600;
		s -= H * 3600;
		int M = s / 60;
		S += s - M * 60;
		return dateTime(Y, MN - 1, D, H, M, (int) S);
	}

	/**
	 * Get date by year, month, day, hour, minute and second
	 *
	 * @param year   int
	 * @param month  int from 0 to 11
	 * @param day    int from 0 to 30
	 * @param hour   int from 0 to 23
	 * @param minute int from 0 to 59
	 * @param second int from 0 to 59
	 * @return Date. Never return null
	 */
	@NonNull
	public static Date dateTime(int year, int month, int day, int hour, int minute, int second) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);

		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * Get date without time
	 * @param value date with time
	 * @return date or null if value is null
	 */
	public static Date dateOnly(Date value) {
		if (value == null) {
			return null;
		}

		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(value);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
}
