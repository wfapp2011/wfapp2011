package de.uni_potsdam.hpi.wfapp2011.assignment.server;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.uni_potsdam.hpi.wfapp2011.servercore.database.DbInterface;
import de.uni_potsdam.hpi.wfapp2011.servercore.database.SQLTableException;
import de.uni_potsdam.hpi.wfapp2011.servercore.general.LdapModul;
import de.uni_potsdam.hpi.wfapp2011.servercore.general.PasswordCrypter;
import de.uni_potsdam.hpi.wfapp2011.servercore.session.SessionManagement;

/**
 * Filters all Requests to the Server and requests a login if the Requester is not logged in <br><br>
 * 
 * init(FilterConfig arg0): initializes the filter
 * doFilter(ServletRequest request, ServletResponse response, FilterChain chain): checks the "login"-status of the requester and reacts according to this
 * destroy(): destroys the filter
 *
 */
public class LoginFilter implements Filter{
	
	private boolean debug = false;
	
	//flag if the metatable-database is already created
	private static boolean created;
	
	//url to which the redirect should be performed to after the login-process
	private String redirectUrl = "/assignment/";
	private String url = "/login/";
	
	/**
	 * init(FilterConfig)
	 * 
	 * initializes the filter
	 * 
	 * @param arg0 : FilterConfig
	 */
	public void init(FilterConfig arg0) throws ServletException {
		//#################################################################################################
		//#																								  #
		//# on application start (init of filter) the metatables must be created with next server request #
		//#																								  #
		//#################################################################################################
		created = false;
		if(debug) System.out.println("Filter initialisiert!");
	}
	
	/**
	 * doFilter(ServletRequest, ServletResponse, FilterChain)
	 * 
	 * checks the "login"-status of the requester and reacts according to this
	 * 
	 * @param request : the request which has been send to the server
	 * @param response : the response which came from the server
	 * @param chain : a chain of other filters
	 */
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		//####################################################
		//#													 #
		//# if metatables are not created yet -> create them #
		//#													 #
		//####################################################
		if(!created){
			created = true;
			DbInterface.initializeMetaTables();
		}
		
		
		if(debug) System.out.println("Redirect URL: "+ redirectUrl);
		
		//###############################################################
		//#																#
		//# get all cookies which have been send via the ServletRequest #
		//#                     										#
		//###############################################################
		Cookie[] cookies = ((HttpServletRequest)request).getCookies();
		
		//##################################################################
		//#																   #
		//# try to find a free ID which could be assigned to the requester #
		//#																   #
		//##################################################################
		DbInterface db_idtest = new DbInterface();
		db_idtest.connectToMetaTables();
		Integer id = null;
		for (int i = 0; i<=100; i++){
			int temp = (int) (Math.random() * Integer.MAX_VALUE);
			if(debug) System.out.println("Neuer ID-Versuch: "+ temp);
			String sql_idtest = "SELECT id FROM onlineusers WHERE id = "+temp+";";
			
			Collection<Map<String,String>> result_idtest = db_idtest.executeQuery(sql_idtest);
			
			if (result_idtest.size() == 0){
				id = temp;
				if (debug) System.out.println("freie Id gefunden: "+id);
				break;
			}
		}
		db_idtest.disconnect();
		
		Cookie user = null;
		
		if(cookies == null){
			//##############################################################################################
			//#																							   #
			//# the requester has no cookies -> set new USER and REDIRECT cookie (needed for login process #
			//# send redirect to LoginPage so the requester can log in									   #
			//#																							   #
			//##############################################################################################
			if(debug) System.out.println("Keine Cookies vorhanden");
			Cookie redirectCookie = new Cookie("Wfapp2011.REDIRECT",id + "#" + redirectUrl);
			redirectCookie.setPath("/login");
			Cookie userCookie = new Cookie("Wfapp2011.USER", id.toString());
			((HttpServletResponse)response).addCookie(userCookie);
			((HttpServletResponse)response).addCookie(redirectCookie);
			if(debug) System.out.println("REDIRECT Cookie gesetzt.");
			((HttpServletResponse)response).sendRedirect(url);
			if(debug) System.out.println("Redirect gesendet");
		}
		else{
			//#############################################################
			//#															  #
			//# requester has cookies -> try to find the right one (USER) #
			//#															  #
			//#############################################################
			if(debug) System.out.println("Starte Suche nach User Cookie");
			for(Cookie c : cookies){
				if (debug) System.out.println(c.getName());
				if(c.getName().equals("Wfapp2011.USER")){
					user = c;
					break;
				}
			}
			if(user == null){
				//####################################################################################################
				//#																									 #
				//# requester has no valid USER-cookie -> set new USER and REDIRECT cookie (needed for login process #
				//# send redirect to LoginPage so the requester can log in									   		 #
				//#																							   		 #
				//####################################################################################################
				if(debug) System.out.println("Kein USER-Cookie vorhanden");
				Cookie redirectCookie = new Cookie("Wfapp2011.REDIRECT",id + "#" + redirectUrl);
				redirectCookie.setPath("/login");
				Cookie userCookie = new Cookie("Wfapp2011.USER", id.toString());
				((HttpServletResponse)response).addCookie(userCookie);
				((HttpServletResponse)response).addCookie(redirectCookie);
				
				((HttpServletResponse)response).sendRedirect(url);
			}
			else{
				//###################################################################################
				//#																  					#
				//# requester has valid USER-cookie -> get userdata from database (delete saved pwd #
				//#																  					#
				//###################################################################################
				if(debug) System.out.println("USER Cookie gefunden");
				DbInterface db = new DbInterface();
				db.connectToMetaTables();
				if(debug) System.out.println(user.getValue());
				String sql = "SELECT username, password FROM onlineUsers WHERE id="+ user.getValue() +";";
				Collection<Map<String,String>> result = db.executeQuery(sql);
				if(debug) System.out.println("SQL Statement in result gespeichert");
				if(result != null && debug) System.out.println("Result set ist nicht null");
				sql = "UPDATE onlineUsers SET password='' WHERE id="+user.getValue()+";";
				try{
					db.executeUpdate(sql);
				}
				catch(SQLTableException e){
					System.out.println(e.getErrorMessage());
				}
				
				db.disconnect();
				
				if(result == null){
					//############################################################################################
					//#																							 #
					//# no online-user has been found in the database -> delete USER-cookie						 #
					//# create new USER and REDIRECT cookie -> redirect to LoginPage so the requester can log in #
					//#																							 #
					//############################################################################################
					if(debug) System.out.println("Kein passenden Onlineuser gefunden.");
					user.setMaxAge(0);
					((HttpServletResponse)response).addCookie(user);
					Cookie redirectCookie = new Cookie("Wfapp2011.REDIRECT",id + "#" + redirectUrl);
					redirectCookie.setPath(url);
					Cookie userCookie = new Cookie("Wfapp2011.USER", id.toString());
					((HttpServletResponse)response).addCookie(userCookie);
					((HttpServletResponse)response).addCookie(redirectCookie);
					((HttpServletResponse)response).sendRedirect(url);
				}
				else{
					//###############################################################################################
					//#																								#
					//# a online user has been found for the given id -> get username and pwd from database request #
					//#																								#
					//###############################################################################################
					String username = "";
					String pwd = "";
					
					for(Map<String,String> s : result){
						username = s.get("username");
						pwd = PasswordCrypter.getInstance().decrypt(s.get("password"));
					}
					if(debug) System.out.println(username+ " "+pwd);
					if(SessionManagement.getInstance().isLoggedIn(username)){
						//#####################################################################
						//#																	  #
						//# if user is already logged in -> permission to pass can be granted #
						//#																	  #
						//#####################################################################
						chain.doFilter(request, response);
					}
					else{
						if(SessionManagement.getInstance().login(username, pwd, user.getValue())){
							//###############################################################################
							//#																		   		#
							//# the user wasn't logged in yet, but login process successfully finished 		#
							//# get needed data from ldap and update database tables, grant pass-permission #
							//#																		   		#
							//###############################################################################
							if(debug) System.out.println("User erfolgreich eingeloggt!");
							db.connectToMetaTables();
							sql = "UPDATE onlineUsers SET roles='"+ LdapModul.getInstance().getUserdata(username) +"' WHERE id="+ user.getValue() +";";
							try{
								db.executeUpdate(sql);
							}
							catch(SQLTableException e){
								System.out.println(e.getErrorMessage());
							}
							
							db.disconnect();
							
							chain.doFilter(request, response);
						}
						else{
							//###################################################################################################
							//#																									#
							//# user wasn't logged in yet and login process failed												#
							//# delete database entries and USER-cookie, then redirect to LoginPage so the user can login again #
							//#																									#
							//###################################################################################################
							db.connectToMetaTables();
							try{
								if(debug) System.out.println("Alles Einträge mit der ID löschen");
								db.executeUpdate("DELETE FROM onlineusers WHERE id="+ user.getValue() +";");
							}
							catch(SQLTableException e){
								System.out.println(e.getErrorMessage());
							}
							 db.disconnect();
							 
							user.setMaxAge(0);
							((HttpServletResponse)response).addCookie(user);
							((HttpServletResponse)response).sendRedirect(url);
						}
					}
				}
			}
		}
	}

	/**
	 * destroy()
	 * 
	 * destroyes the filter
	 */
	public void destroy() {
	}
}