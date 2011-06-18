package de.uni_potsdam.hpi.wfapp2011.general;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class DateConverter {
	
	public static String dateToISO8601(GregorianCalendar date) {
		SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return iso.format(date.getTime());
	}
}
