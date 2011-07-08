package de.uni_potsdam.hpi.wfapp2011.client;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.*;

public class LoginPopUp extends MySimplePopUp {
	
	private boolean debug = true;
	
	private TextBox txtbxUsername;
	private PasswordTextBox passwordTextBox;
	
	public LoginPopUp() {
		
		buttonbar = new HorizontalPanel();
		
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel.add(horizontalPanel);
		
		Label lblUsername = new Label("username");
		horizontalPanel.add(lblUsername);
		
		txtbxUsername = new TextBox();
		horizontalPanel.add(txtbxUsername);
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		verticalPanel.add(horizontalPanel_1);
		
		Label lblPassword = new Label("password");
		horizontalPanel_1.add(lblPassword);
		
		passwordTextBox = new PasswordTextBox();
		horizontalPanel_1.add(passwordTextBox);
		
		verticalPanel.add(buttonbar);
	}

	public void tryToLogin(){
		String pwd = passwordTextBox.getValue();
		String name = txtbxUsername.getValue();
		reset();
		if (Cookies.getCookie("Wfapp2011.USER") == null){
			if(debug) System.out.println("Setze User Cookie");
			String temp = name+"XXX";
			for (Byte i: pwd.getBytes()){
				String bla = i.toString();
				if (bla.length()<10) bla += 0;
				while (bla.length()<10){
					bla += (int) (Math.random()*9+1);
				}
				temp += bla;
			}
			if (debug) System.out.println(temp);
			Cookies.setCookie("Wfapp2011.USER", temp);
		}
	}
	
	public void reset(){
		txtbxUsername.setValue("");
		passwordTextBox.setValue("");
	}
}
