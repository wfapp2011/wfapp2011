package de.uni_potsdam.hpi.wfapp2011.clientcore.pageframe;

public class ProcessInfo {
	
	/**
	 * This class is static and get process identifying values. 
	 * When each process will have an own URL, the identifying values should be parsed from this one. 
	 * 
	 * @author Jannik Marten, Yanina Yurchenko
	 *
	 */

	//TODO: when a real process	should be get from this one (URL - parameter value)
	
	/**
	 * This method should parse the type of the process from the URL.
	 * <b>Important:</b> In the moment it returns always the String "Ba"
	 * @param url : : The URL from which the semester can be parsed.
	 * @return "Ba" as a String.
	 */
	public static String getTypeFromURL(String url) {
		return "Ba";
	}
	/**
	 * This method should parse the semester of the process from the URL.
	 * <b>Important:</b> In the moment it returns always the the String "SS"
	 * @param url : : The URL from which the semester can be parsed.
	 * @return "SS" as a String, which identifies the summer term (Sommersemester)
	 */
	
	public static String getSemesterFromURL(String url) {
		return "SS";
	}
	/**
	 * This method should parse the year of the process from the URL.
	 * <b>Important:</b> In the moment it returns always the year 2014
	 * @param url : The URL from which the year can be parsed.
	 * @return 2014
	 */
	
	public static int getYearFromURL(String url) {
		return 2014;
	}
}
	