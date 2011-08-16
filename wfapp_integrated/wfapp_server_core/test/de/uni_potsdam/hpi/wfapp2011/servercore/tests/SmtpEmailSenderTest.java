package de.uni_potsdam.hpi.wfapp2011.servercore.tests;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.uni_potsdam.hpi.wfapp2011.servercore.email.SmtpEmailSender;


public class SmtpEmailSenderTest {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testSendEmail() throws Exception{
		Calendar cal = Calendar.getInstance();
		String date = cal.getTime().toString();
		
		
		String reciverName = "Martin Schoenberg";
		String reciverAddress = "uulohehe@spaml.de";
		String subject = "TEST - Do NOT Answer!!!!!!";
		String msgBody = "Hallo liebes Opfer,\n\n"+
						 "das ist eine automatisch generierte Email, welche am "+date+" generiert wurde.\n"+
						 "Direktes Löschen wird dringend empfohlen.\n\n MfG";
		
		SmtpEmailSender.getInstance().sendEmail(reciverAddress, reciverName, subject, msgBody);
	}

	@After
	public void tearDown() throws Exception {
	}

}
