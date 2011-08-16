package de.uni_potsdam.hpi.wfapp2011.server;

import java.io.IOException;

public class PasswordCrypter {
	
	private static PasswordCrypter theInstance = null;
	
	private PasswordCrypter(){
		
	}
	
	public synchronized static PasswordCrypter getInstance() 
	{
		if (theInstance == null)
				theInstance = new PasswordCrypter();
		return theInstance;			
	}

	public String encrypt(String pwd){
		
		if(pwd==null)	return "";
		
		String encryped = (new sun.misc.BASE64Encoder()).encode(pwd.getBytes());
				
		return encryped;
	}
	
	public String decrypt(String pwd){
		
		String decryped = "";
		
		if(pwd==null || pwd.equals("")) return null;

		try	{
			 decryped = new String((new sun.misc.BASE64Decoder()).decodeBuffer(pwd));
		} catch(IOException e) {
			return null;
		}
		
		return decryped;
	}
}
