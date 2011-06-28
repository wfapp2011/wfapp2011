package de.uni_potsdam.hpi.wfapp2011.server;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchResult;

public class LdapInterface {
	public static final String ROLE_STUDENT = "Student";
    public static final String ROLE_FSR = "Fachschaftsrat";
    public static final String ROLE_STAFF = "Dozent";
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String userName = "christoph.meinel";
		String ldapServer = "nads2.hpi.uni-potsdam.de"; // 141.89.221.22
		//String ldapServer = "nads3.hpi.uni-potsdam.de"; // 141.89.221.28
		
		try {
            Hashtable env = new Hashtable();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, "ldap://"+ldapServer+":389/ou=eval,dc=hpi,dc=uni-potsdam,dc=de");
            // Authenticate as S. User and password "mysecret"
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, "martin.schoenberg");
            //env.put(Context.SECURITY_PRINCIPAL, "cn=ldap-access,ou=eval,dc=hpi,dc=uni-potsdam,dc=de");
            env.put(Context.SECURITY_CREDENTIALS, "");

            DirContext ctx = new InitialDirContext(env);
            Attributes matchAttrs = new BasicAttributes(true);
            matchAttrs.put(new BasicAttribute("uid", userName));
            NamingEnumeration answer = ctx.search("", matchAttrs);
    
            while (answer.hasMore()) {
                SearchResult sr = (SearchResult) answer.next();
                Attributes attrs = sr.getAttributes();
                NamingEnumeration ae = attrs.getIDs();
                while (ae.hasMore()) {
                    String id = (String) ae.next();
                    System.out.println("\n");
                    System.out.println(id);
                    System.out.println("==============");
                        Attribute attr = attrs.get(id);
                        NamingEnumeration ve = attr.getAll();
                        while (ve.hasMore()) {
                            String value = (String) ve.next();
                            System.out.println(value);
                        }
                }
            }
            
        } catch (NamingException e) {
            e.printStackTrace();
        }

	}

}
