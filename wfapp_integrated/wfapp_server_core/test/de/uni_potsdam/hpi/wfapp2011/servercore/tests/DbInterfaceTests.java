package de.uni_potsdam.hpi.wfapp2011.servercore.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.uni_potsdam.hpi.wfapp2011.servercore.database.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.servercore.database.SQLTableException;
import de.uni_potsdam.hpi.wfapp2011.servercore.general.KerberosModul;
import de.uni_potsdam.hpi.wfapp2011.servercore.general.LdapModul;


public class DbInterfaceTests {
	
	private static DbInterface db;
	
	
	//#######
	//#SETUP#
	//#######
	@BeforeClass
	public static void setUpClass(){
		try {
			DbInterface.initializeMetaTables();
			DbInterface.initializeDatabase("Ba","SS",2011);
			
			db = new DbInterface();
			
			db.connect("Ba","SS",2011);
			
		} catch (SQLTableException e1) {
			e1.printStackTrace();
		}
		
		try{
			db.executeUpdate("CREATE TABLE testtable (name CHAR(30), alter INT, PRIMARY KEY(name,alter));");
			db.executeUpdate("INSERT INTO testtable(name,alter) VALUES('test',23)");
			db.executeUpdate("INSERT INTO testtable(name,alter) VALUES('test1',21)");
			db.executeUpdate("INSERT INTO testtable(name,alter) VALUES('test2',17)");
			db.executeUpdate("INSERT INTO testtable(name,alter) VALUES('test3',25)");
		}
		catch(SQLTableException e){
			
		}
	}
	
	
	//###########
	//#TESTCASES#
	//###########
	
	//@Ignore
	@Test
	public void testAggregations(){
		Collection<Map<String,String>> result = db.executeQuery("SELECT count(alter) FROM testtable;");
		
		Iterator<Map<String,String>> it = result.iterator();
		Map<String,String> tuple = it.next();
		
		assertEquals("4", tuple.get("count(alter)"));
	}
	
	//@Ignore
	@Test
	public void testStarOperator(){
		Collection<Map<String,String>> result = db.executeQuery("SELECT * FROM testtable WHERE name='test2';");
		
		for(Map<String,String> m : result){
			String name = m.get("name");
			Integer alter = Integer.valueOf(m.get("alter"));
			
			assertEquals("test2", name);
			assertEquals(new Integer(17), alter);
		}
	}
	
	//@Ignore
	@Test
	public void testTableCreation(){
		try {
			db.executeUpdate("CREATE TABLE testtable1 (id INT, name CHAR(30));");
			db.executeUpdate("INSERT INTO testtable1(id,name) VALUES(1, 'test');");
			Collection<Map<String,String>> result = db.executeQuery("SELECT name FROM testtable1 WHERE id=1;");
			
			assertTrue(result.size() == 1);
		} catch (SQLTableException e) {
			e.printStackTrace();
		}
	}
	
	//@Ignore
	@Test(expected = SQLTableException.class)
	public void testDoubleTableCreation() throws SQLTableException{
		db.executeUpdate("CREATE TABLE testtable1 (id INT);");
		db.executeUpdate("CREATE TABLE testtable1 (id INT);");
	}
	
	//@Ignore
	@Test
	public void testCLOBs(){
		try{
			db.executeUpdate("INSERT INTO logTable(changeDate,person,changeDescription,changedValues) VALUES('12.09.1967','TestPerson','Zum Test','" +
					"1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890" +		//each line are 100 symbols
					"1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890" +
					"1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890" +
					"1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890');");
		}
		catch(SQLTableException e){
		}
		
		Collection<Map<String,String>> result = db.executeQuery("SELECT changedValues FROM logTable;");
		
		for(Map<String,String> m : result){
			assertTrue(m.get("changedvalues").length() == 400);
			assertTrue(m.get("changedvalues").equals("" +
					"1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890" +		//each line are 100 symbols
					"1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890" +
					"1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890" +
					"1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890"));
		}
	}
	
	//@Ignore
	@Test
	public void testJoins(){
		try{
			db.executeUpdate("CREATE TABLE testtable2 (name VARCHAR(30), beruf VARCHAR(255));");
			db.executeUpdate("INSERT INTO testtable2(name,beruf) VALUES('test2','Student');");
		}
		catch(SQLTableException e){
			System.out.println(e.getErrorMessage());
		}
		
		
		Collection<Map<String,String>> result = db.executeQuery("SELECT testtable.alter, testtable2.beruf FROM testtable, testtable2 WHERE testtable.name = testtable2.name;");
		
		for(Map<String,String> m : result){
			System.out.println(m.get("testtable.alter")+ " | "+ m.get("testtable2.beruf"));
		}
	}
	
	//@Ignore
	@Test
	public void testAuthentication(){
		assertTrue(KerberosModul.getInstance().authenticate("Tommy Neubert", "TN"));
		assertFalse(KerberosModul.getInstance().authenticate("Test", "Test"));
		assertTrue(KerberosModul.getInstance().authenticate("Martin Schönberg", "MS"));
	}
	
	//@Ignore
	@Test
	public void testLdapModul(){
		LdapModul ldap = LdapModul.getInstance();
		
		assertEquals("Prof", ldap.getUserdata("felix.naumann"));
		assertEquals("Studienreferat", ldap.getUserdata("ilona.pamperin"));
		assertFalse("Prof".equals(ldap.getUserdata("tommy.neubert")));
		assertEquals("Staff_BPT", ldap.getUserdata("matthias.kunze"));
	}
	
	//@Ignore
	@Test
	public void testDatabaseDeletion(){
		try{
			DbInterface metatables = new DbInterface();
			metatables.connectToMetaTables();
			
			DbInterface.initializeDatabase("Ma", "SS", 2011);
			
			DbInterface testdb = new DbInterface();
			testdb.connect("Ma","SS",2011);
			
			testdb.executeUpdate("INSERT INTO configurations (name,value) VALUES ('pwd','testPWD');");
			testdb.executeUpdate("INSERT INTO configurations (name,value) VALUES ('pwd2','testPWD2');");
			testdb.executeUpdate("INSERT INTO configurations (name,value) VALUES ('pwd3','testPWD3');");
			testdb.executeUpdate("INSERT INTO configurations (name,value) VALUES ('pwd4','testPWD4');");
			
			testdb.executeUpdate("INSERT INTO department (departmentID, name, shortCut, professor) VALUES (1,'Buisness Proj. Tech.','BPT',1);");
			testdb.executeUpdate("INSERT INTO department (departmentID, name, shortCut, professor) VALUES (2,'Computergrafische Systeme','CGS',2);");
			
			testdb.executeUpdate("INSERT INTO contactPerson (personID,projectID) VALUES (1,2);");
			testdb.executeUpdate("INSERT INTO contactPerson (personID,projectID) VALUES (3,4);");
			testdb.executeUpdate("INSERT INTO contactPerson (personID,projectID) VALUES (5,6);");
			
			testdb.disconnect();
			
			Collection<Map<String,String>> result = metatables.executeQuery("SELECT count(*) FROM existing_projects WHERE name='2011_SS_Ma'");
			for(Map<String,String> m : result){
				assertTrue("1".equals(m.get("count(*)")));
			}
			
			DbInterface.deleteDatabase("Ma", "SS", 2011);
			
			result = metatables.executeQuery("SELECT count(*) FROM existing_projects WHERE name='2011_SS_Ma'");
			for(Map<String,String> m : result){
				assertTrue("0".equals(m.get("count(*)")));
			}
		}
		catch(SQLTableException e){
			System.out.println(e.getErrorMessage());
		}
	}
	
	//###########
	//#TEAR DOWN#
	//###########
	@After
	public void tearDown(){
		try{
			db.executeUpdate("DROP TABLE IF EXISTS testtable1;");
			db.executeUpdate("DROP TABLE IF EXISTS testtable2;");
		}
		catch(SQLTableException e){
		}
	}
	@AfterClass
	public static void tearDownClass(){
		
		try{
			
			db.executeUpdate("DROP TABLE testtable;");
			db.executeUpdate("DROP TABLE logTable;");
			db.executeUpdate("DROP TABLE projectProposal;");
			db.executeUpdate("DROP TABLE projectTopic;");
			db.executeUpdate("DROP TABLE files;");
			db.executeUpdate("DROP TABLE person;");
			db.executeUpdate("DROP TABLE contactPerson;");
			db.executeUpdate("DROP TABLE comments;");
			db.executeUpdate("DROP TABLE department;");
			db.executeUpdate("DROP TABLE vote;");
			db.executeUpdate("DROP TABLE configurations;");
			
			DbInterface meta = new DbInterface();
			meta.connectToMetaTables();
			
			meta.executeUpdate("DROP TABLE IF EXISTS metaconfig;");
			meta.executeUpdate("DROP TABLE IF EXISTS existing_projects;");
			
			meta.disconnect();
		}
		catch(SQLTableException e){	
		}

		db.disconnect();
	}
}
