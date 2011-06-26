package de.uni_potsdam.hpi.wfapp2011.Testing;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

import de.uni_potsdam.hpi.wfapp2011.general.ProcessIdentifier;

public class TestProcessIdentifier {
	private int year = new GregorianCalendar().get(Calendar.YEAR);
	
	
	@Test
	public void testProcessIdentifier(){
		ProcessIdentifier pId = new ProcessIdentifier("Ma", "SS", year);
		assertTrue(pId.isComplete());
		assertTrue(pId.getSemester() == "SS");
		assertTrue(pId.getType() == "Ma");
		assertTrue(pId.getYear() == year);
		pId = new ProcessIdentifier("Ba", "WS", year);
		assertTrue(pId.getType() == "Ba");
		assertTrue(pId.getSemester() == "WS");
	} 
	
	/**
	 * tests if Dates in the past are not accepted.
	 * tests if other Strings for semester are not accepted.
	 */
	@Test 
	public void testWrongProcessIdentifier(){
		ProcessIdentifier pId = new ProcessIdentifier("Ma", "ss", year-2);
		assertTrue(pId.getType() == "Ma");
		assertTrue(pId.getSemester() == null);
		assertTrue(pId.getYear() == 0);
		assertFalse(pId.isComplete());
	};
	
	
	/**
	 * tests if Dates in the past are not accepted.
	 * tests if other Strings for type are not accepted.
	 */
	@Test
	public void testDateInFuture() {
		ProcessIdentifier pId = new ProcessIdentifier("ma", "SS", year+6);
		assertTrue(pId.getType() == null);
		assertTrue(pId.getSemester() == "SS");
		assertTrue(pId.getYear() == 0);
		assertFalse(pId.isComplete());
	}
}
