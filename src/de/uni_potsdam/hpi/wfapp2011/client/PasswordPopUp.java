package de.uni_potsdam.hpi.wfapp2011.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

public class PasswordPopUp extends MySimplePopUp {
	
	private Boolean debug = true;
	
	private PasswordTextBox oldPasswordTextBox;
	private PasswordTextBox newPasswordTextBox;
	private PasswordTextBox newPasswordTextBox2;
	
	private String oldPwd;
	private String type;
	private ConfigInterfaceDataExchangeAsync confInterface = GWT.create(ConfigInterfaceDataExchange.class);

	public PasswordPopUp(String sType) {
		
		type = sType;
		
		// Load old password from db
		confInterface.getPassword(type.toLowerCase(), new AsyncCallback<String>() {
			public void onSuccess(String result) {
				oldPwd = result;
			}
			
			public void onFailure(Throwable caught) {
				
			}
		});
		
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		
		Label lblPleaseCompleteThe = new Label("Please complete the following Boxes to change the "+type+"-password:");
		verticalPanel.add(lblPleaseCompleteThe);
		
		// OLD PWD
		HorizontalPanel old_pwd = new HorizontalPanel();
		verticalPanel.add(old_pwd);
		
		Label lblConfirmTheOld = new Label("Confirm the old Password");
		old_pwd.add(lblConfirmTheOld);
		
		oldPasswordTextBox = new PasswordTextBox();
		old_pwd.add(oldPasswordTextBox);
		
		// NEW PWD
		HorizontalPanel new_pwd = new HorizontalPanel();
		verticalPanel.add(new_pwd);
		
		Label lblTypeTheNew = new Label("Type the new Password");
		new_pwd.add(lblTypeTheNew);
		
		newPasswordTextBox = new PasswordTextBox();
		new_pwd.add(newPasswordTextBox);
		
		// NEW PWD 2
		HorizontalPanel new_pwd2 = new HorizontalPanel();
		verticalPanel.add(new_pwd2);
		
		Label label = new Label("Retype the new Password");
		new_pwd2.add(label);
		
		newPasswordTextBox2 = new PasswordTextBox();
		new_pwd2.add(newPasswordTextBox2);
		
		// Buttonbar
		buttonbar = new HorizontalPanel();
		verticalPanel.add(buttonbar);
	}
	
	public void saveButtonPressed(PopupPanel popUp){
		if (debug) System.out.println("Checking Password");
		if (oldPwd.equals(oldPasswordTextBox.getValue())){
			// Vergleiche die beiden neuen PWDs
			if (newPasswordTextBox.getValue().equals(newPasswordTextBox2.getValue())){
				if (debug) System.out.println("Check OK - Try to save pwd");
				// Speichere neues Password in der Datenbank
				confInterface.savePassword(type.toLowerCase(), newPasswordTextBox.getValue(), new AsyncCallback<Void>() {
					public void onSuccess(Void result) {
						Window.alert("Saved password succsessfully.");
					}
					
					public void onFailure(Throwable caught) {
						Window.alert("Failed to save password.");
					}
				});
				
				if (debug) System.out.println("new pwd saved");
				// schlie�e das PopUp (dich selbst)
				clearPwdInput();
				popUp.hide();
			} else {
				// Zeige PopUp mit Fehlermeldung "neue Pwd nicht gleich"
				Window.alert("You didn't typed the same password twice.");
				clearPwdInput();
			}
		} else {
			// Zeige PopUp mit Fehlermeldung "altes Password nicht best�tigt"
			Window.alert("Please type the correct old password");
			clearPwdInput();
		}
	}

	private void clearPwdInput() {
		oldPasswordTextBox.setValue("");
		newPasswordTextBox.setValue("");
		newPasswordTextBox2.setValue("");
	}
	
}
