package de.uni_potsdam.hpi.wfapp2011.proposals.server.filter;

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
import de.uni_potsdam.hpi.wfapp2011.servercore.session.SessionManagement;

/**
 * Filters all requests to the server and only allows access from persons with a role
 * which has the permission to enter the page
 * 
 * init(FilterConfig arg0): initializes the filter
 * doFilter(ServletRequest request, ServletResponse response, FilterChain chain): filters the requests and checks roles
 * destroy(): destroyes the filter
 */
public class RoleFilter implements Filter{
	
	private boolean debug = false;
	
	//String redirectUrl = "/adminconfig/ConfigurationInterface.html";
	
	//String[] of all roles which are allowd to enter the page
	private String[] allowedRoles = {"Prof", "Studienreferat", "Staff_BPT",
			   "Staff_INTERNET", "Staff_EPIC", "Staff_HCI", "Staff_CGS", 
			   "Staff_OS", "Staff_", "Staff_SWA", "Staff_IS", "Staff_MOD"};
	
	/**
	 * destroy()
	 * 
	 * destroyes the filter
	 */
	@Override
	public void destroy() {	
	}

	/**
	 * doFilter(ServletRequest, ServletResponse, FilterChain)
	 * 
	 * filters the requests and checks roles
	 * 
	 * @param request : the request which has been send to the server
	 * @param response : the response which came from the server
	 * @param chain : a chain of other filters
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//########################################################################
		//#																		 #
		//# get all cookies from the request and check if there is a USER cookie #
		//#																		 #
		//########################################################################
		Cookie[] cookies = ((HttpServletRequest)request).getCookies();
		
		Cookie user = null;
		boolean permission = false;
		
		for(Cookie c : cookies){
			if(c.getName().equals("Wfapp2011.USER")){
				if(debug) System.out.println("USER Cookie gefunden: "+ c.getValue());
				user = c;
				break;
			}
		}
		
		if(user == null){
			//##########################################################
			//#														   #
			//# no USER-cookie was found -> permission cant be granted #
			//# 													   #
			//##########################################################
			if(debug) System.out.println("Kein USER-Cookie vorhanden");
			
			permission = false;
		}
		else{
			//###################################################
			//#													#
			//# USER-cookie found -> get userdata from database #
			//#													#
			//###################################################
			if(debug) System.out.println("Hole Daten aus der DB");
			
			DbInterface db = new DbInterface();
			db.connectToMetaTables();
	
			String sql = "SELECT username, roles FROM onlineUsers WHERE id="+ user.getValue() +";";
			Collection<Map<String,String>> result = db.executeQuery(sql);
			
			db.disconnect();
			
			if(result == null){
				//##############################################################
				//#															   #
				//# no userdata found in the db -> permission can't be granted #
				//#															   #
				//##############################################################
				if(debug) System.out.println("Kein Daten in der DB vorhanden");
				
				permission = false;
			}
			else{
				//##############################################################################
				//#																			   #
				//# userdata where found in the database -> check if there is a role performed #
				//# by the requester which is allowed to enter the page 					   #
				//#																			   #
				//##############################################################################
				String[] roles = null;
				
				for(Map<String,String> m : result){
					roles = m.get("roles").replace(" ", "").split(",");
				}
				
				for(String checkAllowedRole : allowedRoles){
					for(String checkRole : roles){
						if(checkAllowedRole.equals(checkRole)){
							if(debug) System.out.println("User hat Zugriffsrechte");
							
							permission = true;
						}
					}
				}
			}
		}
		
		//#################################################################
		//#																  #
		//# if permission was granted during the checking -> allow access #
		//# otherwise -> logout the requester and deny access			  #
		//#																  #
		//#################################################################
		if(permission){
			if(debug) System.out.println("Filter leitet weiter");
			
			chain.doFilter(request, response);
		}
		else{
			if(debug) System.out.println("Filter sperrt");
			SessionManagement.getInstance().logout(Integer.valueOf(user.getValue()));
			
			((HttpServletResponse)response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	/**
	 * init(FilterConfig)
	 * 
	 * initializes the filter
	 * 
	 * @param arg0 : FilterConfig
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
