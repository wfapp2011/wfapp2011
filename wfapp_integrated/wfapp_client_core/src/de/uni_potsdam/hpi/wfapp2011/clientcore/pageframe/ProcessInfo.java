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
	public static String getTypeFromURL(String url) {
		return "Ba";
	}
	public static String getSemesterFromURL(String url) {
		return "SS";
	}
	public static int getYearFromURL(String url) {
		return 2011;
	}
}
	