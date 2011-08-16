package de.uni_potsdam.hpi.wfapp2011.servercore.activiti.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.junit.Test;

import de.uni_potsdam.hpi.wfapp2011.servercore.constants.Constants;
import de.uni_potsdam.hpi.wfapp2011.servercore.database.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.servercore.database.SQLTableException;




public class TestDateparser {
	
	private String type = "Ba";
	private String semester = "SS";
	private int year;
	
	
	@SuppressWarnings("deprecation")
	@Test
	public void testDateParser() {
		year = (new GregorianCalendar()).get(Calendar.YEAR) + 20;
		DbInterface dbInterface = new DbInterface();
		HashMap<String, Date> mapDeadlines = new HashMap<String, Date>();
		String sql = "SELECT * FROM configurations WHERE name = '"+ Constants.DEADLINE_PROPOSAL_COL+"' OR " +
				"name = '"+ Constants.DEADLINE_TOPICS_PUBL+"' OR name = '"+Constants.DEADLINE_VOTING+"' OR name = '"+
				Constants.DEADLINE_MATCHING+"' OR name = '"+Constants.DEADLINE_PROCESS+"'";
		
		try {
			dbInterface.connect(type, semester, year);
			ResultSet resultSet = dbInterface.executeQueryDirectly(sql);
			while(resultSet.next()){
				//###############################################				
				//# Parse String into Date     					#
				//###############################################
				DateFormat df = new SimpleDateFormat(Constants.DATE_PATTERN, Constants.DATE_LOCALE);
				String deadlineString = resultSet.getString(2);
				System.out.println("Deadlines wie folgt ausgelesen: "+ deadlineString);
				Date deadline = df.parse(deadlineString);
				deadline.setHours(23);
				deadline.setMinutes(59);
				deadline.setSeconds(59);
				mapDeadlines.put(resultSet.getString(1), deadline);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (SQLTableException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		dbInterface.disconnect();
		DbInterface.deleteDatabase(type, semester, year);
	}
}
