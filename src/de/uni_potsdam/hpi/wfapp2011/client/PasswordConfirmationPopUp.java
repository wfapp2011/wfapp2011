package de.uni_potsdam.hpi.wfapp2011.client;

// IMPORTS
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

/**
 * <code>PasswordConfirmationPopUp</code> is a PopupPanel to confirm the password of the user
 * when he wants to save his voting.
 *  
 * @author Stefanie Birth, Marcel Pursche
 * @version 11.08.2011 11:09
 * @see com.google.gwt.user.client.ui.PopupPanel
 */
public class PasswordConfirmationPopUp extends PopupPanel {
	private HorizontalPanel buttonPanel;
	private Button btnOk;
	private Button btnAbbrechen;
	private PasswordTextBox passwordTextBox;
	private Label lblBitteGebenSie;
	private VerticalPanel verticalPanel;
	private boolean isVerified = false;
	private AsyncCallback<Boolean> callback;
	private final VotingDatabaseServiceAsync databaseService = GWT.create(VotingDatabaseService.class);

	/**
	 * Constructor for the class
	 * 
	 * @param callback Callback which is called when the password is confirmed or rejected
	 * the failure callback is only called, when a connection error with the servlet occured.
	 */
	public PasswordConfirmationPopUp(AsyncCallback<Boolean> callback) {
		// deactivate auto hide and set the popup to modal
		super(false, true);
		// save callback in the class		
		this.callback = callback;
		// create mainpanel
		verticalPanel = new VerticalPanel();
		setWidget(verticalPanel);
		verticalPanel.setSize("330px", "100%");
		
		// add info text
		lblBitteGebenSie = new Label("Bitte geben Sie ihr Passwort zur Best\u00E4tigung erneut ein:");
		verticalPanel.add(lblBitteGebenSie);
		
		// add password edit
		passwordTextBox = new PasswordTextBox();
		verticalPanel.add(passwordTextBox);
		passwordTextBox.setWidth("330px");
		
		// add horizontal panel for the buttons
		buttonPanel = new HorizontalPanel();
		verticalPanel.add(buttonPanel);
		
		// add OK button and setup size
		btnOk = new Button("OK");
		btnOk.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				verifyPwd();
			}
		});
		buttonPanel.add(btnOk);
		buttonPanel.setCellWidth(btnOk, "90");
		btnOk.setWidth("78");
		
		// add cancel button and setup size		
		btnAbbrechen = new Button("Abbrechen");
		btnAbbrechen.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cancel();
			}
		});
		buttonPanel.add(btnAbbrechen);
	}
	
	private void verifyPwd() {
			// TODO replace dummy user name
			// call remote procedure to confirm the password
			databaseService.confirmPassword("Bernd das Brot", passwordTextBox.getValue(), new AsyncCallback<Boolean>(){

				@Override
				public void onFailure(Throwable caught) {
					// close popup and call the failure method
					hide();
					callback.onFailure(caught);
				}

				@Override
				public void onSuccess(Boolean result) {
					// close pop up, refresh the verified attribute and call the success callback method
					// TODO isVerified = result;
					isVerified = true;
					hide();
					callback.onSuccess(isVerified);
				}
			});
	}

	/**
	 * <code>isVerified</code> returns if the password has been verified
	 * @return boolean
	 */
	public boolean isVerified() {
		return isVerified;
	}

	private void cancel() {
		// reject the password, call the success callback method and close popup
		isVerified = false;
		callback.onSuccess(isVerified);
		hide();
	}

}
