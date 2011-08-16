package de.uni_potsdam.hpi.wfapp2011.login.client;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.KeyboardListenerAdapter;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

@SuppressWarnings("deprecation")
public class LoginPopupContent extends Composite {

	private boolean debug = false;
	
	private loginExchangeAsync exchange = GWT.create(loginExchange.class);
	private String cookieContent;
	
	Button button = new Button("Login");
	SubmitListener sl = new SubmitListener();
	
	/**
	 * performs the redirect to the specified address
	 */
	private void redirect(){
		//username_tb.setValue("");
		//password_tb.setValue("");
		if (debug) Window.alert(Cookies.getCookie("Wfapp2011.REDIRECT").split("#")[1].replace("\"", ""));
		Window.Location.assign(Cookies.getCookie("Wfapp2011.REDIRECT").split("#")[1].replace("\"", ""));
	}

	/**
	 * Construktor creates all content within the login popup
	 */
	public LoginPopupContent() {
		//read the redirect address from the cookie
		cookieContent = Cookies.getCookie("Wfapp2011.REDIRECT").replace("\"", "");
		if(debug) Window.alert(cookieContent);
		
		//create all content
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		
		Label lblBitteGebenSie = new Label("Bitte geben Sie ihre Nutzerdaten ein.");
		verticalPanel.add(lblBitteGebenSie);
		
		HorizontalPanel panelUsername = new HorizontalPanel();
		verticalPanel.add(panelUsername);
		
		Label lblUsername = new Label("Username");
		panelUsername.add(lblUsername);
		panelUsername.setCellWidth(lblUsername, "70");
		
		final TextBox username_tb = new TextBox();
		panelUsername.add(username_tb);
		username_tb.addKeyboardListener(sl);
		username_tb.setWidth("170px");
		
		HorizontalPanel panelPassword = new HorizontalPanel();
		verticalPanel.add(panelPassword);
		
		Label lblPasswort = new Label("Passwort");
		panelPassword.add(lblPasswort);
		panelPassword.setCellWidth(lblPasswort, "70");
		
		final PasswordTextBox password_tb = new PasswordTextBox();
		password_tb.addKeyboardListener(sl);
		panelPassword.add(password_tb);
		password_tb.setWidth("170px");
		
		HorizontalPanel panelButton = new HorizontalPanel();
		verticalPanel.add(panelButton);
		
		button.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//if the login button was pressed -> perform login
				if (debug) Window.alert("Button clicked");
				exchange.login(username_tb.getValue(), password_tb.getValue(),cookieContent.split("#")[0],new AsyncCallback<Void>(){

					@Override
					public void onFailure(Throwable caught) {
						if (debug) System.out.println("Callback gescheitert");
						Window.alert("Schwerer Ausnahmefehler. Bitte wenden Sie sich an den Support. #1");
						redirect();
					}

					@Override
					public void onSuccess(Void result) {
						if (debug) Window.alert("Erfolgreich!");
						//redirect();
						Window.Location.assign(cookieContent.split("#")[1]);
						((PopupPanel)getParent()).hide();						
					}
					
				});				
			}
		});
		panelButton.add(button);
	}

	private class SubmitListener extends KeyboardListenerAdapter {
	    public void onKeyPress(Widget sender, char key, int mods) {
	      if (KeyboardListener.KEY_ENTER == key)
	        button.click();
	    }
	  }
}
