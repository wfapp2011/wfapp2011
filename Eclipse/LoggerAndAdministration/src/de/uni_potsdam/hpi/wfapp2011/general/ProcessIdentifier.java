package de.uni_potsdam.hpi.wfapp2011.general;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * This class provides the functionality to identify a specific process.
 *
 */

public class ProcessIdentifier implements IsSerializable {
//	private DbInterface dbInterface;
	private String type = null;
	private String semester = null;
	private int year = 0;
	
	public ProcessIdentifier() {
		//for GWT
	}
	
	public ProcessIdentifier(String type, String semester, int year){
		setType(type);
		setSemester(semester);
		setYear(year);
//		dbInterface = new DbInterface();
	}
	
	public String getType() {
		return type;
	}

	/**
	 * Sets the type of the process
	 * Only allows the Strings "Ba" or "Ma"
	 * @param semester "Ba" or "Ma"
	 */
	public void setType(String type) {
		if(type.equals("Ba") || type.equals("Ma")){
			this.type = type;
		}
	}

	public String getSemester() {
		return semester;
	}

	/**
	 * Sets the semester
	 * Only allows the Strings "SS" or "WS"
	 * @param semester "SS" or "WS"
	 */
	public void setSemester(String semester) {
		if(semester.equals("SS") || semester.equals("WS")){
			this.semester = semester;
		}
	}

	public int getYear() {
		return year;
	}

	/**
	 * Sets the year of the process
	 * @param year: The year which should be set
	 * 			Only allow 1 year in past and 5 years in future. 
	 */
	public void setYear(int year) {
		@SuppressWarnings("deprecation")
		int currentYear = new Date().getYear() + 1900;
		if(year >= currentYear - 1 && year <= currentYear + 5 ){
			this.year = year;
		}
	}
	
	/**
	 * Checks if the Object has all required values
	 * @return boolean: true, if type, semester and type have allowed values;
	 */
	public boolean isComplete(){
		return !(year == 0 || semester == null || type == null);
	}
	/**
	 * returns the process identifier parse it from the URL
	 * ATTENTION: At the moment it returns always the same ProcessIdentifier
	 * @param url: The URL of the Webside. It can be empty for now
	 * @return ProcessIdentifier, which contains the Type, Semester and year of the process. 
	 */
	public static ProcessIdentifier getProcessIdentifier(String url){
		return new ProcessIdentifier("Ba", "SS", 2014);
	}

}