package de.uni_potsdam.hpi.wfapp2011.servercore.general;

import java.util.ArrayList;

public class DummyUsers {	
	private ArrayList<String[]> accounts;
	
	public DummyUsers() {
		accounts = new ArrayList<String[]>();
		accounts.add(new String[] {"Tommy Neubert","TN","Student"});
		accounts.add(new String[] {"Martin Schoenberg","MS","Student"});
		accounts.add(new String[] {"Michael Hopstock","MH","Student"});
		accounts.add(new String[] {"Christoph Meinel","CM","Prof"});
		accounts.add(new String[] {"Mathias Weske","MW","Prof"});
		accounts.add(new String[] {"Patrick Baudisch","PB","Prof"});
		accounts.add(new String[] {"Juergen Doellner","JD","Prof"});
		accounts.add(new String[] {"Holger Giese","HG","Prof"});
		accounts.add(new String[] {"Andreas Polze","AP","Prof"});
		accounts.add(new String[] {"Robert Hirschfeld","RH","Prof"});
		accounts.add(new String[] {"Hasso Plattner","HP","Prof"});
		accounts.add(new String[] {"Ulrich Weinberg","UW2","Prof"});
		accounts.add(new String[] {"Felix Naumann","FN","Prof"});
		accounts.add(new String[] {"Ilona Pamperin","IP","Studienreferat"});
		accounts.add(new String[] {"Matthias Kunze","MK","Staff_BPT"});
		accounts.add(new String[] {"Matthias Weidlich","MW","Staff_BPT"});
		accounts.add(new String[] {"Alexander Luebbe","AL","Staff_BPT"});
		accounts.add(new String[] {"Bjoern Schuenemann","BS","Staff_INTERNET"});
		accounts.add(new String[] {"Michael Menzel","MM","Staff_INTERNET"});
		accounts.add(new String[] {"Matthias Quasthoff","MQ","Staff_INTERNET"});
		accounts.add(new String[] {"Alexander Zeier","AZ","Staff_EPIC"});
		accounts.add(new String[] {"Florian Huebner","FH","Staff_EPIC"});
		accounts.add(new String[] {"Juergen Mueller","JM","Staff_EPIC"});
		accounts.add(new String[] {"Sieglinde Tholen","ST","Staff_HCI"});
		accounts.add(new String[] {"Anne Roudaut","AR","Staff_HCI"});
		accounts.add(new String[] {"Sean Gustafson","JG","Staff_HCI"});
		accounts.add(new String[] {"Martin Beck","MB","Staff_CGS"});
		accounts.add(new String[] {"Sabine Biewendt","SB","Staff_CGS"});
		accounts.add(new String[] {"Johannes Bohnet","JB","Staff_CGS"});
		accounts.add(new String[] {"Martin Loewis","ML","Staff_OS"});
		accounts.add(new String[] {"Peter Troeger","PT","Staff_OS"});
		accounts.add(new String[] {"Bernhard Rabe","BR","Staff_OS"});
		accounts.add(new String[] {"Michael Perscheid","MP","Staff_SWA"});
		accounts.add(new String[] {"Robert Krahn","RK","Staff_SWA"});
		accounts.add(new String[] {"Jens Lincke","JL","Staff_SWA"});
		accounts.add(new String[] {"Alexander Albrecht","AA","Staff_IS"});
		accounts.add(new String[] {"Arvid Heise","AH","Staff_IS"});
		accounts.add(new String[] {"Tobias Vogel","TV","Staff_IS"});
		accounts.add(new String[] {"Stephan Hildebrandt","SH","Staff_MOD"});
		accounts.add(new String[] {"Stefan Neumann","SN","Staff_MOD"});
		accounts.add(new String[] {"Basil Becker","BB","Staff_MOD"});
		accounts.add(new String[] {"Kermit Frog","kermit","Admin"});
	}
	
	public ArrayList<String[]> getAccounts() {
		return accounts;
	}
}
