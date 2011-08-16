package de.uni_potsdam.hpi.wfapp2011.servercore.logger.test;

import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.uni_potsdam.hpi.wfapp2011.servercore.constants.Constants;
import de.uni_potsdam.hpi.wfapp2011.servercore.constants.JSONFields;
import de.uni_potsdam.hpi.wfapp2011.servercore.database.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.servercore.database.SQLTableException;
import de.uni_potsdam.hpi.wfapp2011.servercore.logging.AdminLogger;
import de.uni_potsdam.hpi.wfapp2011.servercore.logging.LogDescriptions;


public class TestAdminLogger {
	private AdminLogger logging;
	private DbInterface dbInterface; 
	private Date deadline;
	private String deadlineType;
	private String email;
	private String type;
	private String semester;
	private int year;
	
	
	@SuppressWarnings("deprecation")
	@Before
	public void setUp() throws SQLTableException {
		year = (new GregorianCalendar().get(Calendar.YEAR)) +20;
		type = "Ba";
		semester ="SS";
		logging = AdminLogger.getInstance();
		dbInterface = new DbInterface();
		
		DbInterface.initializeMetaTables();
		DbInterface.initializeDatabase(type, semester, year);
		
		email = "test@example.com";
		deadlineType = Constants.DEADLINE_PROPOSAL_COL;
		deadline = new Date();
		deadline.setDate(deadline.getDay() + 1);
		
	}
	
	
	@Test
	public void testAdminLogger() throws SQLTableException{
		logging.logNewDeadlineEntry(type, semester, year, email, deadlineType, deadline);
		dbInterface.connect(type, semester, year);
		String sql = "SELECT * FROM logTable WHERE person ='"+email+"' AND changeDescription = '"+LogDescriptions.NEW_DEADLINE+"';";
		try {
			ResultSet resultSet = dbInterface.executeQueryDirectly(sql);
			while(resultSet.next()){
				String json = resultSet.getString(4);
				assertTrue(json.contains(JSONFields.DEADLINE));
				assertTrue(json.contains(JSONFields.DEADLINE_TYPE));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbInterface.disconnect();
	}
	
	@After
	public void deleteDatabase() {
		try {
			dbInterface.connect(type, semester, year);
			String sql = "DELETE FROM logTable WHERE person = '"+email+"';";
			dbInterface.executeUpdate(sql);
		} catch (SQLTableException e) {
			e.printStackTrace();
		}
		dbInterface.disconnect();
		DbInterface.deleteDatabase(type, semester, year);
		
	}
	
	

}

