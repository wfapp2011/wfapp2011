package de.uni_potsdam.hpi.wfapp2011.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ConfigurationInterface implements EntryPoint {
	
	private ConfigInterfaceDataExchangeAsync confInterface = GWT.create(ConfigInterfaceDataExchange.class);
	
	private MainMetaPanel metaconfig;
	private MainPanel projectconfig;
	
	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get();
			
		VerticalPanel verticalPanel = new VerticalPanel();
		rootPanel.add(verticalPanel, 0, 0);
		verticalPanel.setSize("100%", "100%");
		
		/*
		 * Header head = new Header(input);
		 * verticalPanel.add(head);
		 * main.setWidth("100%"); 
		 */
		
		HorizontalPanel menuBar = new HorizontalPanel();
		verticalPanel.add(menuBar);
		
		Button btnProjektliste = new Button("Projektliste");
		btnProjektliste.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				toogleVisibility(true);
			}
		});
		menuBar.add(btnProjektliste);
		
		Button btnMetakonfigurationen = new Button("Metakonfigurationen");
		btnMetakonfigurationen.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				toogleVisibility(false);
			}
		});
		menuBar.add(btnMetakonfigurationen);
		
		metaconfig = new MainMetaPanel();
		verticalPanel.add(metaconfig);
		metaconfig.setWidth("100%");
		
		projectconfig = new MainPanel();
		verticalPanel.add(projectconfig);
		projectconfig.setWidth("100%");
		
		toogleVisibility(true);
		
		/*Button btnLogin = new Button("Login");
		btnLogin.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				final PopupPanel temp = new PopupPanel();
				final LoginPopUp content = new LoginPopUp();
				
				Button save = new Button();
				save.setText("Einloggen");
				save.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						Cookies.removeCookie("Wfapp2011.USER");
						Cookies.removeCookie("Wfapp2011.STATE");
						content.tryToLogin();
						temp.hide();
						
						Location.reload(); 
					}
				});
				
				content.getButtonBar().add(save);
				temp.add(content);
				temp.center();
			}
		});
		verticalPanel.add(btnLogin);
		btnLogin.setVisible(!isAdmin);*/
		
		Button btnLogout = new Button("Logout");
		btnLogout.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				confInterface.logout(Cookies.getCookie("Wfapp2011.USER"), new AsyncCallback<Void>(){

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Schwerer Ausnahmefehler. Bitte wenden Sie sich an den Support.\n" +
								"Zur Zeit können Sich nicht ausloggen, doch in 10min ist ihre Session definitiv abgelaufen.");
					}

					@Override
					public void onSuccess(Void result) {
						Cookies.removeCookie("Wfapp2011.USER");
						Location.reload();
					}
				});			
			}
		});
		verticalPanel.add(btnLogout);
		
		/*
		 * Footer foot = new Footer();
		 * verticalPanel.add(foot);
		 * main.setWidth("100%"); 
		 */
	}

	private void toogleVisibility(boolean bool){
		metaconfig.setVisible(!bool);
		projectconfig.setVisible(bool);
	}
}
