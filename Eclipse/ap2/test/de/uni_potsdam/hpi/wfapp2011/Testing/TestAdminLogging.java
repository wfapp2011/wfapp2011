package de.uni_potsdam.hpi.wfapp2011.Testing;

import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.uni_potsdam.hpi.wfapp2011.server.Logging.AdminLogger;
import de.uni_potsdam.hpi.wfapp2011.server.Logging.LogDescriptions;
import de.uni_potsdam.hpi.wfapp2011.server.constants.Constants;
import de.uni_potsdam.hpi.wfapp2011.server.constants.JSONFields;
import de.uni_potsdam.hpi.wfapp2011.server.database.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.server.database.SQLTableException;
import de.uni_potsdam.hpi.wfapp2011.server.general.ProcessIdentifier;
import de.uni_potsdam.hpi.wfapp2011.server.general.ProcessIdentifierException;


public class TestAdminLogging{
	private AdminLogger logging;
	private DbInterface dbInterface; 
	private ProcessIdentifier processIdentifier;
	private Date deadline;
	private String deadlineType;
	private String email;
	private int year;
	
	
	@SuppressWarnings("deprecation")
	@Before
	public void setUp() throws SQLTableException {
		year = new GregorianCalendar().get(Calendar.YEAR);
		processIdentifier = new ProcessIdentifier("Ba", "SS", year + 2);
		logging = AdminLogger.getInstance();
		dbInterface = new DbInterface();
		
		DbInterface.initializeMetaTables();
		DbInterface.initializeDatabase(processIdentifier.getType(), processIdentifier.getSemester(), processIdentifier.getYear());
		
		email = "test@example.com";
		deadlineType = Constants.DEADLINE_PROPOSAL_COL;
		deadline = new Date();
		deadline.setDate(deadline.getDay() + 1);
		
	}
	
	/**
	 * Test if an error will be thrown, if the String for semester is not either "SS" or "WS"
	 * @throws ProcessIdentifierException: Will be thrown if the processIdentifier is not valid
	 */
	@Test(expected = ProcessIdentifierException.class) 
	public void testExceptionSemester() throws ProcessIdentifierException{
		ProcessIdentifier pId = new ProcessIdentifier("Ba", "ss", year + 2);
		logging.logNewDeadlineEntry(pId, email, deadlineType, deadline);
	}

	/**
	 * Test if years in the past will not be accecpted. 
	 * @throws ProcessIdentifierException: Will be thrown if the processIdentifier is not valid
	 */
	@Test(expected = ProcessIdentifierException.class) 
	public void testExceptionYear() throws ProcessIdentifierException{
		ProcessIdentifier pId = new ProcessIdentifier("Ba", "SS", year - 2);
		logging.logNewDeadlineEntry(pId, email, deadlineType, deadline);
	}
	
	/**
	 * Test if years more than 5 years in future will not be accepted.
	 * @throws ProcessIdentifierException: Will be thrown if the processIdentifier is not valid
	 */
	@Test(expected = ProcessIdentifierException.class) 
	public void testExceptionYearInFuture() throws ProcessIdentifierException{
		ProcessIdentifier pId = new ProcessIdentifier("Ba", "SS", year + 6);
		logging.logNewDeadlineEntry(pId, email, deadlineType, deadline);
	}
	
	@Test
	public void testAdminLogger() throws ProcessIdentifierException, SQLTableException{
		ProcessIdentifier pId = new ProcessIdentifier("Ba", "SS", year+2);
		logging.logNewDeadlineEntry(pId, email, deadlineType, deadline);
		dbInterface.connect(pId.getType(), pId.getSemester(), pId.getYear());
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
	public void deleteEntries() {
		try {
			dbInterface.connect(processIdentifier.getType(), processIdentifier.getSemester(), processIdentifier.getYear());
			String sql = "DELETE FROM logTable WHERE person = '"+email+"';";
			dbInterface.executeUpdate(sql);
		} catch (SQLTableException e) {
			e.printStackTrace();
		}
		dbInterface.disconnect();
		
	}
	
	

}
