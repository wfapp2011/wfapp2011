package de.uni_potsdam.hpi.wfapp2011.general;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateConverter {
	private static SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	public static String dateToISO8601(Date date) {
		return iso.format(date);
	}
	
	public static Date ISO8601ToDate(String stringDate){
		try {
			return iso.parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
