package de.uni_potsdam.hpi.wfapp2011.activiti.Testing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.junit.Test;

import de.uni_potsdam.hpi.wfapp2011.constants.Constants;
import de.uni_potsdam.hpi.wfapp2011.database.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.database.SQLTableException;
import de.uni_potsdam.hpi.wfapp2011.general.ProcessIdentifier;


public class Dateparser {

	@Test
	public void testDateParser() {
		ProcessIdentifier processIdentifier = new ProcessIdentifier("Ba", "SS", 2014);
		DbInterface dbInterface = new DbInterface();
		HashMap<String, Date> mapDeadlines = new HashMap<String, Date>();
		String sql = "SELECT * FROM configurations WHERE name = '"+ Constants.DEADLINE_PROPOSAL_COL+"' OR " +
				"name = '"+ Constants.DEADLINE_TOPICS_PUBL+"' OR name = '"+Constants.DEADLINE_VOTING+"' OR name = '"+
				Constants.DEADLINE_MATCHING+"' OR name = '"+Constants.DEADLINE_PROCESS+"'";
		
		try {
			dbInterface.connect(processIdentifier.getType(), processIdentifier.getSemester(), processIdentifier.getYear());
			ResultSet resultSet = dbInterface.executeQueryDirectly(sql);
			while(resultSet.next()){
				//###############################################				
				//# Parse String into Date     					#
				//###############################################
				DateFormat df = new SimpleDateFormat("d MMM yyyy", Locale.ENGLISH);
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
	}
}
