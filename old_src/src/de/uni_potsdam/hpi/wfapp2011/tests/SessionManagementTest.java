package de.uni_potsdam.hpi.wfapp2011.tests;

import static org.junit.Assert.*;
import org.junit.*;
import de.uni_potsdam.hpi.wfapp2011.server.SessionManagement;

public class SessionManagementTest {
	
	/**
	 * testing SessionManagement Class
	 * Be careful while running, the testTimeOut Test takes about 10 minutes to run!
	 */
	
	private String username;
	private String pwd;
	private String falseusername;
	private String falsepwd;

	@Before
	public void setUp() throws Exception {
		username = "Chuck Norris";
		pwd = "abc123zyx987";
		falseusername = "Chucky Noris";
		falsepwd = "42";
	}

	@Test
	public void testLogin(){
		assertTrue(SessionManagement.getInstance().login(username, pwd));
		
	}
	
	@Test
	public void testLoggedIn(){
		assertTrue(SessionManagement.getInstance().isLoggedIn(username));
		assertFalse(SessionManagement.getInstance().isLoggedIn(falseusername));
	}
	
	@Test
	public void testConfimPwd(){
		assertTrue(SessionManagement.getInstance().confirmPwd(username, pwd));
		assertFalse(SessionManagement.getInstance().confirmPwd(username, falsepwd));
		assertFalse(SessionManagement.getInstance().confirmPwd(falseusername, pwd));
	}
	
	@Test
	public void testLogout(){
		SessionManagement.getInstance().logout(username);
		assertFalse(SessionManagement.getInstance().isLoggedIn(username));
	}
	
	@Test
	public void testTimeOut() throws Exception {
		SessionManagement.getInstance().login(username, pwd);
		Thread.sleep(30500);
		//Thread.sleep(600500);
		assertFalse(SessionManagement.getInstance().isLoggedIn(username));
	}
	
	@After
	public void tearDown()  {
		
	}

}