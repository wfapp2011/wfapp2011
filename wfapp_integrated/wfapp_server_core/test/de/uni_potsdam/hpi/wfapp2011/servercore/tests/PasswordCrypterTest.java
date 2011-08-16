package de.uni_potsdam.hpi.wfapp2011.servercore.tests;


import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.uni_potsdam.hpi.wfapp2011.servercore.general.PasswordCrypter;

public class PasswordCrypterTest {
	
	private String password = "hallo";

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void encryptPwd() throws Exception{
		String encrypedPwd = PasswordCrypter.getInstance().encrypt(password);
		System.out.println(encrypedPwd);
	}
	
	@Test
	public void decryptPwd() throws Exception{
		String temp = PasswordCrypter.getInstance().encrypt(password);
		assertEquals(password,PasswordCrypter.getInstance().decrypt(temp));
	}	
	
	@After
	public void tearDown() throws Exception {
	}

}
