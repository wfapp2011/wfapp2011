package de.uni_potsdam.hpi.wfapp2011.admin.client;

// # Imports #
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

/**
 * provides the ability to change the password of different modules
 */
public class PasswordPopUp extends MySimplePopUp {
	
	private Boolean debug = false;
	
	private PasswordTextBox oldPasswordTextBox;
	private PasswordTextBox newPasswordTextBox;
	private PasswordTextBox newPasswordTextBox2;
	
	private String oldPwd;
	private String type;
	private ConfigInterfaceDataExchangeAsync confInterface = GWT.create(ConfigInterfaceDataExchange.class);

	/**
	 * Constructor
	 * loads the old password from database
	 * @param sType : type of the module where the new password should be assigned to
	 */
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
	
	/**
	 * Action happens if the savebutton has been pressed
	 * @param popUp : this popup is closed on successful passwordchange
	 */
	public void saveButtonPressed(PopupPanel popUp){
		if (debug) System.out.println("Checking Password");
		// compare retyped old password
		if (oldPwd.equals(oldPasswordTextBox.getValue())){
			// compare both new PWDs
			if (newPasswordTextBox.getValue().equals(newPasswordTextBox2.getValue())){
				if (debug) System.out.println("Check OK - Try to save pwd");
				// save new pwd to database
				confInterface.savePassword(type.toLowerCase(), newPasswordTextBox.getValue(), new AsyncCallback<Void>() {
					public void onSuccess(Void result) {
						Window.alert("Passwort erfolgreich gespeichert.");
					}
					
					public void onFailure(Throwable caught) {
						Window.alert("Passwort konnte nicht gespeichert werden.");
					}
				});
				
				if (debug) System.out.println("new pwd saved");
				// close popup
				clearPwdInput();
				popUp.hide();
			} else {
				// show PopUp with errorcode: "neue Pwd nicht gleich"
				Window.alert("Sie haben nicht 2 mal das gleiche Passwort eingegeben.");
				clearPwdInput();
			}
		} else {
			// show PopUp with errorcode: "altes Password nicht bestätigt"
			Window.alert("Sie haben nicht das richtige alte Passwort eingegeben.");
			clearPwdInput();
		}
	}

	private void clearPwdInput() {
		oldPasswordTextBox.setValue("");
		newPasswordTextBox.setValue("");
		newPasswordTextBox2.setValue("");
	}
	
}
