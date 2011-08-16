package de.uni_potsdam.hpi.wfapp2011.client;

//# Imports #
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import de.uni_potsdam.hpi.wfapp2011.general.ProcessIdentifier;
import de.uni_potsdam.hpi.wfapp2011.pageframe.Footer;
import de.uni_potsdam.hpi.wfapp2011.pageframe.Header;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ConfigurationInterface implements EntryPoint {
	
	private ConfigInterfaceDataExchangeAsync confInterface = GWT.create(ConfigInterfaceDataExchange.class);
	
	private MainMetaPanel metaconfig;
	private MainPanel projectconfig;
	private VerticalPanel verticalPanel;
	private DockLayoutPanel mainPanel;
	private ProcessIdentifier pId = ProcessIdentifier.getProcessIdentifier(""); //pId aud Admins pId setzten!!!!
	private Header headerPanel;
	private Footer footerPanel;
	
	
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
		
		Button btnLogout = new Button("Logout");
		btnLogout.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// Logout:
				// 1. sending information to server
				// 2. onSuccess: deleting Cookie: Wfapp2011.USER on Clientpage
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
		// Adding LogoutButton to Page
		verticalPanel.add(btnLogout);
	}

	private void createHeader() {
		headerPanel = new Header(pId, "Projekteverwaltung");
		
		Button btnProjektliste = new Button("Projektliste");
		btnProjektliste.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				toogleVisibility(true);
			}
		});
		headerPanel.menuPanel.add(btnProjektliste); 
		
		Button btnMetakonfigurationen = new Button("Metakonfigurationen");
		btnMetakonfigurationen.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				toogleVisibility(false);
			}
		});
		headerPanel.menuPanel.add(btnMetakonfigurationen);
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
}
