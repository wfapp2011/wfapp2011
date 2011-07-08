package de.uni_potsdam.hpi.wfapp2011.general;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
	private static SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	
	/**
	 * Converts a given Date into a String
	 * The String specifies the date in ISO8601 format
	 *  
	 * @param date the date, which should be transformed
	 * @return the date as String in ISO8601 format
	 */
	
	public static String dateToISO8601(Date date) {
		return iso.format(date);
	}
	
	
	/**
	 * Converts a given String in ISO8601 format into a Date
	 * @param stringDate The String, which should be converted
	 * @return a Date object representing the same date as the given String
	 * 			returns null, if the String is not in ISO8601 format
	 */
	public static Date ISO8601ToDate(String stringDate){
		try {
			return iso.parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
