package de.uni_potsdam.hpi.wfapp2011.admin.client;

//# Imports #
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.uni_potsdam.hpi.wfapp2011.clientcore.pageframe.Footer;
import de.uni_potsdam.hpi.wfapp2011.clientcore.pageframe.Header;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ConfigurationInterface implements EntryPoint {
	
	private ConfigInterfaceDataExchangeAsync confInterface = GWT.create(ConfigInterfaceDataExchange.class);
	
	private MainMetaPanel metaconfig;
	private MainPanel projectconfig;
	private VerticalPanel verticalPanel; 
	private DockLayoutPanel mainPanel; 
	private Header headerPanel;
	private Footer footerPanel;
	private VerticalPanel logPanel;
	private FlexTable logTable;
	
	private PopupPanel logPopup;
	private LogConfigView content;

	
	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get();
		rootPanel = RootPanel.get();
		rootPanel.setSize("100%", "100%");

		mainPanel = new DockLayoutPanel(Unit.PX);
		rootPanel.add(mainPanel, 0, 0);
		mainPanel.setSize("99%", "100%");
		
		createHeader();
		createFooter();
		
		verticalPanel = new VerticalPanel();
		mainPanel.add(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		
		metaconfig = new MainMetaPanel();
		verticalPanel.add(metaconfig);
		metaconfig.setWidth("100%");
		
		projectconfig = new MainPanel();
		verticalPanel.add(projectconfig);
		projectconfig.setWidth("100%");
		
		toogleVisibility(true);
		

	}

	private void createHeader() {
		headerPanel = new Header("","",0, "Projekteverwaltung");
		// show projectlist
		Label btnProjektliste = new Label("Projektliste");
		btnProjektliste.setStylePrimaryName("gwt-MenuLabel");
		btnProjektliste.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				toogleVisibility(true);
			}
		});
		headerPanel.menuPanel.add(btnProjektliste);
		// show metaconfig
		Label btnMetakonfigurationen = new Label("Metakonfigurationen");
		btnMetakonfigurationen.setStylePrimaryName("gwt-MenuLabel");
		btnMetakonfigurationen.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				toogleVisibility(false);
			}
		});
		headerPanel.menuPanel.add(btnMetakonfigurationen);
		// show log history
		Label btnLogging = new Label("Log-History");
		btnLogging.setStylePrimaryName("gwt-MenuLabel");
		btnLogging.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				logPopup = new PopupPanel();
				content = new LogConfigView();
				logPopup.add(content);
				logPopup.center();
				
				// request the specified log
				Button request = new Button();
				request.setText("OK");
				request.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						Date[] dates = new Date[2];
						String[] config = new String[3];
						boolean test = true;
						// get given Dates and config
						dates = content.getDate();
						config = content.getConfig();
						// test dates
						for (Date d:dates){
							if (d == null){
								test = false;
								break;
							}
						}
						
						if (test && config[2] != null){
							showLogging(config[0], config[1], (Integer.valueOf(config[2])), dates[0], dates[1]);	
						}
						else{
							content.showError(true);
						}
						
					}
				});
				content.getButtonBar().add(request);
				
				// CloseButton
				Button close = new Button();
				close.setText("Abbrechen");
				close.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						logPopup.hide();
					}
				});
				content.getButtonBar().add(close);
				
			}
		});
		
		headerPanel.menuPanel.add(btnLogging);
		// logout button
		Label btnLogout = new Label("Logout");
		btnLogout.setStylePrimaryName("gwt-MenuLabel");
		btnLogout.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// Logout:
				// 1. sending information to server
				// 2. onSuccess: deleting Cookie: Wfapp2011.USER on Clientpage
				confInterface.logout(Cookies.getCookie("Wfapp2011.USER"), new AsyncCallback<Void>(){

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Schwerer Ausnahmefehler. Bitte wenden Sie sich an den Support.\n" +
								"Zur Zeit k\u00F6nnen Sich nicht ausloggen, doch in 10min ist ihre Session definitiv abgelaufen.");
					}

					@Override
					public void onSuccess(Void result) {
						Cookies.removeCookie("Wfapp2011.USER");
						Location.reload();
					}
				});			
			}
		});
		// Adding LogoutButton to Page
		headerPanel.menuLoginPanel.add(btnLogout);
		
		mainPanel.addNorth(headerPanel,151);
	}
	private void createFooter() {
		//Footer von Pageframe
		footerPanel = new Footer();
		mainPanel.addSouth(footerPanel, 40);
	}

	private void toogleVisibility(boolean bool){
		// switching Pages
		metaconfig.setVisible(!bool);
		projectconfig.setVisible(bool);
	}
	
	private void showLogging(String type, String semester, int year, Date fromDate, Date untilDate){
			logPanel = new VerticalPanel();
			logTable = new FlexTable();
			logPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

			confInterface.getLogEntries(year,semester, type, fromDate, untilDate, new AsyncCallback<Collection<String[]>> (){
				public void onFailure(Throwable caught) {
					Window.alert("RPC to getNumberOfVotings() failed.\n\n" + caught.getMessage());
				}
				@Override
				public void onSuccess(Collection<String[]> result) {
					Button cancelButton = new Button("Cancel");
					cancelButton.addClickHandler(new ClickHandler() {
							public void onClick(ClickEvent event) {
								logPopup.hide();
							}
					});
					HorizontalPanel cancelPanel = new HorizontalPanel();
					cancelPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
					cancelPanel.add(cancelButton);
					
					Iterator<String[]> iterator = result.iterator();
					int row = 0;
					
					if(iterator.hasNext()==false){
						String noLogs = "<DIV> Noch keine Logeintr\u00E4ge vorhanden </DIV>";
						HTMLPanel noLogsPanel = new HTMLPanel(noLogs);
						noLogsPanel.addStyleName("activeText");
						logPanel.add(noLogsPanel);
						//CancelButton after text
						logPanel.add(cancelPanel); 
					}else{
						//ChancelButton above the logTable -> better usability
						logPanel.add(cancelPanel); 
						while(iterator.hasNext()){
							String[] logEntry = (String[]) iterator.next();
							
							logTable.setWidget(row, 0, new Label(logEntry[0]));
							logTable.setWidget(row, 1, new Label(logEntry[1]));
							logTable.setWidget(row, 2, new Label(logEntry[2]));
							logTable.setWidget(row, 3, new Label(logEntry[3]));
							row++;
						}
					}
					logPanel.add(logTable);
					logPopup.clear();
					logPopup.add(logPanel);
					logPopup.center();
				}
			});		 
		}
}
