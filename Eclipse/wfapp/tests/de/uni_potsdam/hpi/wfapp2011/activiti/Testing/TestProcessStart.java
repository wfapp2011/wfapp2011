package de.uni_potsdam.hpi.wfapp2011.activiti.Testing;

import static org.junit.Assert.assertFalse;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

import de.uni_potsdam.hpi.wfapp2011.activiti.ProcessAdministration;

public class TestProcessStart {

	@Test
	public void testProcessStart(){
		ProcessAdministration process = new ProcessAdministration();
		GregorianCalendar startCollection = new GregorianCalendar();
		GregorianCalendar deadlineCollection = new GregorianCalendar();
		GregorianCalendar deadlineTopics = new GregorianCalendar();
		GregorianCalendar deadlineVoting = new GregorianCalendar();
		GregorianCalendar deadlineProcess = new GregorianCalendar();
		
		startCollection.add(Calendar.MINUTE, +5);
		deadlineCollection.add(Calendar.MINUTE, +10);
		deadlineTopics.add(Calendar.MINUTE, +15);
		deadlineVoting.add(Calendar.MINUTE, +20);
		deadlineProcess.add(Calendar.MINUTE, +25);
		
		String processInstanceId = process.startProcess("DegreeProjectProcessNew2", startCollection, deadlineCollection, deadlineTopics, deadlineVoting, deadlineProcess);
		Boolean notExpected = processInstanceId.equals("0");
		assertFalse(notExpected);
		
	}
}
