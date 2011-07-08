package de.uni_potsdam.hpi.wfapp2011.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;

public class MainMetaPanel extends Composite {
	
	private Boolean debug = true;
	
	private PasswordPopUp popUpContent;
	private PopupPanel popUp;
	private TextBox ftp_url_tb;
	private TextBox owa_url_tb;
	private TextBox ldap_url_tb;
	private TextBox ftp_name_tb;
	private TextBox owa_name_tb;
	private TextBox ldap_name_tb;
	private TextBox owa_senderdomain_tb;
	private TextBox owa_host_tb;
	
	private ConfigInterfaceDataExchangeAsync confInterface = GWT.create(ConfigInterfaceDataExchange.class);

	public MainMetaPanel() {
		
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		
		Label lblMetaConfig = new Label("Meta - Configurations");
		verticalPanel.add(lblMetaConfig);
		verticalPanel.setCellHeight(lblMetaConfig, "50");
		
		// FTP
			// URL
		HorizontalPanel ftp_url = new HorizontalPanel();
		verticalPanel.add(ftp_url);
		
		Label lblFtpUrl = new Label("FTP - URL");
		ftp_url.add(lblFtpUrl);
		ftp_url.setCellWidth(lblFtpUrl, "110");
		
		ftp_url_tb = new TextBox();
		ftp_url.add(ftp_url_tb);
		ftp_url.setCellWidth(ftp_url_tb, "100");
			// Name
		HorizontalPanel ftp_name = new HorizontalPanel();
		verticalPanel.add(ftp_name);
		
		Label lblFtpName = new Label("FTP - Name");
		ftp_name.add(lblFtpName);
		ftp_name.setCellWidth(lblFtpName, "110");
		
		ftp_name_tb = new TextBox();
		ftp_name.add(ftp_name_tb);
			// PWD
		HorizontalPanel ftp_pwd = new HorizontalPanel();
		verticalPanel.add(ftp_pwd);
		verticalPanel.setCellHeight(ftp_pwd, "50");
		
		Label lblFtpPwd = new Label("FTP - Password");
		ftp_pwd.add(lblFtpPwd);
		ftp_pwd.setCellWidth(lblFtpPwd, "110");
		
		Button btnChangePwdftp = new Button("Change Password");
		btnChangePwdftp.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				showPopup("FTP");
			}
		});
		ftp_pwd.add(btnChangePwdftp);
		
		// OWA
			// URL
		HorizontalPanel owa_url = new HorizontalPanel();
		verticalPanel.add(owa_url);
		
		Label lblOwaUrl = new Label("OWA - URL");
		owa_url.add(lblOwaUrl);
		owa_url.setCellWidth(lblOwaUrl, "110");
		
		owa_url_tb = new TextBox();
		owa_url.add(owa_url_tb);
		owa_url.setCellWidth(owa_url_tb, "100");
			// Host
		HorizontalPanel owa_host = new HorizontalPanel();
		verticalPanel.add(owa_host);
		
		Label lblOwaHost = new Label("OWA - Host");
		owa_host.add(lblOwaHost);
		owa_host.setCellWidth(lblOwaHost, "110");
		
		owa_host_tb = new TextBox();
		owa_host.add(owa_host_tb);
		owa_host.setCellWidth(owa_host_tb, "100");
			// Name
		HorizontalPanel owa_name = new HorizontalPanel();
		verticalPanel.add(owa_name);
		
		Label lblOwaName = new Label("OWA - Name");
		owa_name.add(lblOwaName);
		owa_name.setCellWidth(lblOwaName, "110");
		
		owa_name_tb = new TextBox();
		owa_name.add(owa_name_tb);
			// senderdomain
		HorizontalPanel owa_senderdomain = new HorizontalPanel();
		verticalPanel.add(owa_senderdomain);
		
		Label lblOwaSenderdomain = new Label("OWA - Domain");
		owa_senderdomain.add(lblOwaSenderdomain);
		owa_senderdomain.setCellWidth(lblOwaSenderdomain, "110");
		
		owa_senderdomain_tb = new TextBox();
		owa_senderdomain.add(owa_senderdomain_tb);
			// PWD
		HorizontalPanel owa_pwd = new HorizontalPanel();
		verticalPanel.add(owa_pwd);
		verticalPanel.setCellWidth(owa_pwd, "110");
		verticalPanel.setCellHeight(owa_pwd, "50");
		
		Label lblOwaPwd = new Label("OWA - Password");
		owa_pwd.add(lblOwaPwd);
		owa_pwd.setCellWidth(lblOwaPwd, "110");
		
		Button btnChangePwdOwa = new Button("Change Password");
		btnChangePwdOwa.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				showPopup("OWA");
			}
		});
		owa_pwd.add(btnChangePwdOwa);
		
		// LDAP
			// URL
		HorizontalPanel ldap_url = new HorizontalPanel();
		verticalPanel.add(ldap_url);
		
		Label lblLdapUrl = new Label("LDAP - URL");
		ldap_url.add(lblLdapUrl);
		ldap_url.setCellWidth(lblLdapUrl, "110");
		
		ldap_url_tb = new TextBox();
		ldap_url.add(ldap_url_tb);
		ldap_url.setCellWidth(ldap_url_tb, "100");
			// Name
		HorizontalPanel ldap_name = new HorizontalPanel();
		verticalPanel.add(ldap_name);
		
		Label lblLdapName = new Label("LDAP - Name");
		ldap_name.add(lblLdapName);
		ldap_name.setCellWidth(lblLdapName, "110");
		
		ldap_name_tb = new TextBox();
		ldap_name.add(ldap_name_tb);
			// PWD
		HorizontalPanel ldap_pwd = new HorizontalPanel();
		verticalPanel.add(ldap_pwd);
		verticalPanel.setCellHeight(ldap_pwd, "50");
		
		Label lblLdapPwd = new Label("LDAP - Password");
		ldap_pwd.add(lblLdapPwd);
		ldap_pwd.setCellWidth(lblLdapPwd, "110");
		
		Button btnChangePwdLdap = new Button("Change Password");
		btnChangePwdLdap.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				showPopup("LDAP");
			}
		});
		ldap_pwd.add(btnChangePwdLdap);
		
		// Buttonbar
		HorizontalPanel btnBar = new HorizontalPanel();
		verticalPanel.add(btnBar);
		
		Button btnSaveChanges = new Button("Save Changes");
		btnSaveChanges.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				saveChanges();
			}
		});
		btnBar.add(btnSaveChanges);
		btnSaveChanges.setWidth("110");
		btnBar.setCellWidth(btnSaveChanges, "110");
		
		// Fill in Content
		getContentFromDb();
	}

	private void createPwdPopUp(String type){
		popUpContent = new PasswordPopUp(type);
		
		Button btnSavePwd = new Button("Save Password");
		btnSavePwd.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				popUpContent.saveButtonPressed(popUp);
			}
		});
		popUpContent.getButtonBar().add(btnSavePwd);
		
		Button btnCancelPwd = new Button("Cancel");
		btnCancelPwd.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				popUp.hide();
			}
		});
		popUpContent.getButtonBar().add(btnCancelPwd);
	}

	private void showPopup(String type) {
		popUp = new PopupPanel();
		createPwdPopUp(type);
		popUp.add(popUpContent);
		popUp.center();
	}

	private void getContentFromDb(){
		if (debug) System.out.println("Try to get values from DB.");
		// getting MetaSettings (AsyncCallback)
		// save data to db
		confInterface.getMetaData(new AsyncCallback<Map<String,String>>() {
			public void onSuccess(Map<String,String> result) {
				// Store Db Information
				ftp_url_tb.setValue(result.get("ftp_url")==null?"":result.get("ftp_url"));
				owa_url_tb.setValue(result.get("owa_url")==null?"":result.get("owa_url"));
				ldap_url_tb.setValue(result.get("ldap_url")==null?"":result.get("ldap_url"));
				
				ftp_name_tb.setValue(result.get("ftp_name")==null?"":result.get("ftp_name"));
				owa_name_tb.setValue(result.get("owa_name")==null?"":result.get("owa_name"));
				ldap_name_tb.setValue(result.get("ldap_name")==null?"":result.get("ldap_name"));
				
				owa_senderdomain_tb.setValue(result.get("owa_senderdomain")==null?"":result.get("owa_senderdomain"));
				owa_host_tb.setValue(result.get("owa_host")==null?"":result.get("owa_host"));
			}
			
			public void onFailure(Throwable caught) {
				
			}
		});
		
	}
	
	private void saveChanges() {
		if (debug) System.out.println("Try to save Urls.");
		// getting Content
		String ftpUrl = ftp_url_tb.getValue();
		String owaUrl = owa_url_tb.getValue();
		String ldapUrl = ldap_url_tb.getValue();
		
		String ftpName = ftp_name_tb.getValue();
		String owaName = owa_name_tb.getValue();
		String ldapName = ldap_name_tb.getValue();
		
		String owaSenderDomain = owa_senderdomain_tb.getValue();
		String owaHost = owa_host_tb.getValue();
		
		// creating Map of new values
		Map<String,String> map = new HashMap<String, String>();
		
		map.put("ftp_url", ftpUrl);
		map.put("owa_url", owaUrl);
		map.put("ldap_url", ldapUrl);
		map.put("ftp_name", ftpName);
		map.put("owa_name", owaName);
		map.put("ldap_name", ldapName);
		map.put("owa_senderdomain", owaSenderDomain);
		map.put("owa_host", owaHost);
				
		// save data to db
		confInterface.saveMetaData(map, new AsyncCallback<Void>() {
			public void onSuccess(Void result) {
				Window.alert("Daten gespeichert");
			}
			
			public void onFailure(Throwable caught) {
				
			}
		});
		
	}
}
