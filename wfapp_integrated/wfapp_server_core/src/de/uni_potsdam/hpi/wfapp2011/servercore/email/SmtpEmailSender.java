package de.uni_potsdam.hpi.wfapp2011.servercore.email;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;

import de.uni_potsdam.hpi.wfapp2011.servercore.database.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.servercore.general.PasswordCrypter;

public class SmtpEmailSender{
	
  private static SmtpEmailSender theInstance = null;
  
  private String pwd;
  private String senderName; // = "martin.schoenberg";
  private String senderDomain; // = "@student.hpi.uni-potsdam.de";
  private String senderAddress; //= senderName+senderDomain;
  private String host; // = "mailout.hpi.uni-potsdam.de";
  private String url; // = "owa2.hpi.uni-potsdam.de";
  
  /**
   * updating the logindata from database
   */
  public void updateLogin(){
		Collection<Map<String,String>> result;
		DbInterface db = new DbInterface();
		db.connectToMetaTables();
		
		result = db.executeQuery("SELECT name,value FROM metaconfig WHERE name LIKE 'owa%';");
		for (Map<String,String> map:result){
			if (map.get("name").equals("owa_host")) host = map.get("value");
			if (map.get("name").equals("owa_name")) senderName = map.get("value");
			if (map.get("name").equals("owa_senderdomain")) senderDomain = map.get("value");
			if (map.get("name").equals("owa_pwd")) pwd = PasswordCrypter.getInstance().decrypt(map.get("value"));
			if (map.get("name").equals("owa_url")) url = map.get("value");
			
			if (!(senderDomain == null || senderName == null)) senderAddress = senderName+senderDomain;
		}
		
		db.disconnect();
  }
  
  
  private SmtpEmailSender()
  		{			
	  
		}
  
  /**
   * Singleton utility function
   * @return single instance of the class
   */
  public synchronized static SmtpEmailSender getInstance() 
		{
			if (theInstance == null)
					theInstance = new SmtpEmailSender();
			
			theInstance.updateLogin();
			
			return theInstance;			
		}
  
  /**
   * sends multiple Email to specified addresses
   * @param reciverAddressList : Stringlist (e.g. foo@bar.com;bar@foo.net)
   * @param reciverName : Stringlist (e.g. Foo Bar; Bar Foo)
   * @param subject : String (e.g. Test Email)
   * @param msgBody : String (e.g. This is a big messagebody./n You can use all types of escape-sequences.)
   * @throws UnsupportedEncodingException
   */
  public void sendMultipleEmails(String reciverAddressList, String reciverNames,
			String subject, String msgBody)
			throws UnsupportedEncodingException{

			XTrustProvider.install();
			
			// Set some useful Props
			Properties props = System.getProperties();
			props.put("mail.smtp.host", host);
			
			props.put("mail.transport.protocol","smtp");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.tls", "true");
			props.put("mail.smtp.ssl", "true");
			props.put("mail.smtp.user", senderAddress);
			props.put("mail.password", pwd);
			props.put("mail.smtp.host",url);
			props.put("mail.smtp.localhost", url);
			
			//props.put("mail.debug","true");
			
			// Create authenticator to connect to OWA
			javax.mail.Authenticator auth = null;
			auth = new javax.mail.Authenticator(){
			@Override
			public PasswordAuthentication getPasswordAuthentication()
			{
			return new PasswordAuthentication(senderName,pwd);
			}
			
			};
			
			// Create a new Session on OWA
			Session s = Session.getDefaultInstance(props, auth);
			
			// Try to send an Email
			try {             
			Message msg = new MimeMessage(s);
			msg.setFrom(new InternetAddress(senderAddress, senderName));
			
			String[] addressList = reciverAddressList.split(";");
			String[] nameList = reciverNames.split(";");
			for (int i=0; i<addressList.length; i++) {
				msg.addRecipient(Message.RecipientType.TO,
		                new InternetAddress(addressList[i], nameList[i]));
			}
			
			msg.setSubject(subject);
			msg.setText(msgBody);
			msg.saveChanges();
			Transport.send(msg);
			
			} catch (AddressException e) {
				System.out.println("excp 1");
				System.out.println(e.getMessage());
			} catch (MessagingException e) {
				System.out.println("excp 2");
				System.out.println(e.getMessage());
			if (e.getMessage().contains("Authentication unsuccessful"))
				 System.out.println("Falsche Nutzerdaten!");
			}
		}
  /**
   * sends a single Email to one specified address
   * @param reciverAddress : String (e.g. foo@bar.com)
   * @param reciverName : String (e.g. Foo Bar)
   * @param subject : String (e.g. Test Email)
   * @param msgBody : String (e.g. This is a big messagebody./n You can use all types of escape-sequences.)
   * @throws UnsupportedEncodingException
   */
  public void sendEmail(String reciverAddress, String reciverName,
		  				String subject, String msgBody)
  						throws UnsupportedEncodingException{
	  
	  sendMultipleEmails(reciverAddress,reciverName,subject,msgBody);
 }
}