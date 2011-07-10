package de.uni_potsdam.hpi.wfapp2011.client;

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

	public PasswordConfirmationPopUp(AsyncCallback<Boolean> callback) {
		super(false, true);
		
		this.callback = callback;
		verticalPanel = new VerticalPanel();
		setWidget(verticalPanel);
		verticalPanel.setSize("330px", "100%");
		
		lblBitteGebenSie = new Label("Bitte geben Sie ihr Passwort zur Best\u00E4tigung erneut ein:");
		verticalPanel.add(lblBitteGebenSie);
		
		passwordTextBox = new PasswordTextBox();
		verticalPanel.add(passwordTextBox);
		passwordTextBox.setWidth("330px");
		
		buttonPanel = new HorizontalPanel();
		verticalPanel.add(buttonPanel);
		
		btnOk = new Button("OK");
		btnOk.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				verifyPwd();
			}
		});
		buttonPanel.add(btnOk);
		buttonPanel.setCellWidth(btnOk, "90");
		btnOk.setWidth("78");
		
		btnAbbrechen = new Button("Abbrechen");
		btnAbbrechen.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cancel();
			}
		});
		buttonPanel.add(btnAbbrechen);
	}
	
	private void verifyPwd() {
			databaseService.confirmPassword("Bernd das Brot", passwordTextBox.getValue(), new AsyncCallback<Boolean>(){

				@Override
				public void onFailure(Throwable caught) {
					hide();
					callback.onFailure(caught);
				}

				@Override
				public void onSuccess(Boolean result) {
					isVerified = true;
					hide();
					callback.onSuccess(isVerified);
				}
			});
	}

	public boolean isVerified() {
		return isVerified;
	}

	private void cancel() {
		isVerified = false;
		callback.onSuccess(isVerified);
		hide();
	}

}
