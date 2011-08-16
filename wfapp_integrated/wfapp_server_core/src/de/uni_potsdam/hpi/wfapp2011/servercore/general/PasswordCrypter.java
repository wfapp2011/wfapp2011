package de.uni_potsdam.hpi.wfapp2011.servercore.general;

import java.io.IOException;

/**
 * Implemented as a Singleton. <br>
 * Provides a simple en- and decryption functionality.
 */

public class PasswordCrypter {
	
	private static PasswordCrypter theInstance = null;
	
	private PasswordCrypter(){
		
	}
	
	/**
	 * Singleton utility function
	 * @return the single instance
	 */
	public synchronized static PasswordCrypter getInstance() 
	{
		if (theInstance == null)
				theInstance = new PasswordCrypter();
		return theInstance;			
	}

	/**
	 * encrypts a given String with BASE64Encoder
	 * @param pwd : String of a plain string (often a password)
	 * @return encryped String of the given String
	 */
	public String encrypt(String pwd){
		
		if(pwd==null)	return "";
		
		String encryped = (new sun.misc.BASE64Encoder()).encode(pwd.getBytes());
				
		return encryped;
	}
	
	/**
	 * decrypts a given String with BASE64Decoder
	 * @param pwd : String of a encryped string (often a password)
	 * @return decryped String of the given String
	 */
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
