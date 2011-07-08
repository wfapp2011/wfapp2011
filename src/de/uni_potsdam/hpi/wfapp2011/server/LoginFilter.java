package de.uni_potsdam.hpi.wfapp2011.server;

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

public class LoginFilter implements Filter{
	
	private boolean debug = true;
	
	private static boolean created;
	
	String redirectUrl = "/adminconfig/ConfigurationInterface.html";
	
	public void init(FilterConfig arg0) throws ServletException {
		created = false;
		if(debug) System.out.println("Filter initialisiert!");
	}
	
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		if(!created){
			created = true;
			DbInterface.initializeMetaTables();
		}
		
		String url = "/login/LoginPage.html";
		if(debug) System.out.println("Redirect URL: "+ redirectUrl);
		
		Cookie[] cookies = ((HttpServletRequest)request).getCookies();
		
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
			if(debug) System.out.println("Starte Suche nach User Cookie");
			for(Cookie c : cookies){
				if (debug) System.out.println(c.getName());
				if(c.getName().equals("Wfapp2011.USER")){
					user = c;
					break;
				}
			}
			if(user == null){
				if(debug) System.out.println("Kein USER-Cookie vorhanden");
				Cookie redirectCookie = new Cookie("Wfapp2011.REDIRECT",id + "#" + redirectUrl);
				redirectCookie.setPath("/login");
				Cookie userCookie = new Cookie("Wfapp2011.USER", id.toString());
				((HttpServletResponse)response).addCookie(userCookie);
				((HttpServletResponse)response).addCookie(redirectCookie);
				
				((HttpServletResponse)response).sendRedirect(url);
			}
			else{
				if(debug) System.out.println("USER Cookie gefunden");
				DbInterface db = new DbInterface();
				db.connectToMetaTables();
				
				String sql = "SELECT username, password FROM onlineUsers WHERE id="+ user.getValue() +";";
				Collection<Map<String,String>> result = db.executeQuery(sql);
				
				sql = "UPDATE onlineUsers SET password='' WHERE id="+user.getValue()+";";
				try{
					db.executeUpdate(sql);
				}
				catch(SQLTableException e){
					System.out.println(e.getErrorMessage());
				}
				
				db.disconnect();
				
				if(result == null){
					user.setMaxAge(0);
					((HttpServletResponse)response).addCookie(user);
					Cookie redirectCookie = new Cookie("Wfapp2011.REDIRECT",id + "#" + redirectUrl);
					redirectCookie.setPath("/login");
					Cookie userCookie = new Cookie("Wfapp2011.USER", id.toString());
					((HttpServletResponse)response).addCookie(userCookie);
					((HttpServletResponse)response).addCookie(redirectCookie);
					((HttpServletResponse)response).sendRedirect(url);
				}
				else{
					String username = "";
					String pwd = "";
					
					for(Map<String,String> s : result){
						username = s.get("username");
						pwd = PasswordCrypter.getInstance().decrypt(s.get("password"));
					}
					
					if(SessionManagement.getInstance().isLoggedIn(username)){
						chain.doFilter(request, response);
					}
					else{
						if(SessionManagement.getInstance().login(username, pwd, user.getValue())){
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
							db.connectToMetaTables();
							try{
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

	public void destroy() {
	}



//@Override
//public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
//		throws IOException, ServletException {
//	// TODO Auto-generated method stub
//	String redirectUrl = ((HttpServletRequest)arg0).getRequestURL().toString();
//	if(debug) System.out.println("Redirect URL: "+ redirectUrl);
//	
//	arg2.doFilter(arg0, arg1);
//}
}