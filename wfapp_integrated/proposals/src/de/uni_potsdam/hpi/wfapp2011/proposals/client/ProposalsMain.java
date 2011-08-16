package de.uni_potsdam.hpi.wfapp2011.proposals.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.uni_potsdam.hpi.wfapp2011.clientcore.pageframe.Footer;
import de.uni_potsdam.hpi.wfapp2011.clientcore.pageframe.Header;
import de.uni_potsdam.hpi.wfapp2011.clientcore.pageframe.ProcessInfo;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.serviceproxies.TestdataService;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ProposalsMain implements EntryPoint {
	
	private String type;
	private String semester;
	private int year;
	
	// panels for main page structure
	private RootPanel rootPanel;
	private DockLayoutPanel mainPanel;
	private Header headerPanel;
	private ScrollPanel centerPanelScrollable;
	private DockPanel centerPanel;
	private VerticalPanel contentPanel;

	// sub pages
	private CreateOrEditProposal createOrEditProposal;
	private ListOfAllProposals listAllProposals; 
	private ListOfOwnProposals listOwnProposals;
	private DetailedViewProposal detailedViewProposal;
	
	// login
	private PopupPanel loginPanel;
	private Label menuItem_Login;
	private Label menuItem_Logout;
	
	// string constants
	String PHASE_NAME = "Themenerfassung";
	String M_HOME = "Home";
	String M_NEWPROPOSAL = "neuer Vorschlag";
	String M_OWNPROPOSALS = "eigene Themenvorschläge";
	String M_ALLPROPOSALS = "alle Themenvorschläge";
	String L_LOGIN = "Login";
	String L_LOGOUT = "Logout";
		 
	public TestdataService testdataService = new TestdataService();
	
	/**
	 * This is the entry point method.
	 * It defines the rootPanel and creates the basic page structure.
	 */
	public void onModuleLoad() {
		// TODO: Replace with URL
		type = ProcessInfo.getTypeFromURL("");
		semester = ProcessInfo.getSemesterFromURL("");
		year = ProcessInfo.getYearFromURL("");
			
//		setupTestdata();
		
		rootPanel = RootPanel.get();
		rootPanel.setSize("100%", "100%");
		rootPanel.setStyleName("rootPanel");
		
		mainPanel = new DockLayoutPanel(Unit.PX);
		rootPanel.add(mainPanel, 0, 0);
		mainPanel.setSize("99%", "100%");
		
		createHeader();
		createFooter();			
		createContent();	
	}

	/** 
	 * Creates menubar, login and background of the header 
	 * and adds everything at the top of the mainPanel. 
	 */
 	private void createHeader() {	
		// create header panel and add it at the top of the main panel
		headerPanel = new Header(type, semester, year, PHASE_NAME);
		mainPanel.addNorth(headerPanel,151);	
		
		// create menu items 
		Label menuItem_Home = new Label(M_HOME);
		Label menuItem_CreateNewProposal = new Label(M_NEWPROPOSAL);
		Label menuItem_ListOwnProposals = new Label(M_OWNPROPOSALS);
		Label menuItem_ListAllProposals = new Label(M_ALLPROPOSALS);
		
		menuItem_Login = new Label(L_LOGIN);
		menuItem_Logout = new Label(L_LOGOUT);
		
		// connect menu items with click handler
		menuItem_Home.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Window.Location.assign("/overview/");
			}
		});
		
		menuItem_CreateNewProposal.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				createOrEditProposal.createNewProposal();
				showElement(createOrEditProposal);
			}
		});
		
		menuItem_ListOwnProposals.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				listOwnProposals.renderProposals();
				showElement(listOwnProposals);
			}
		});		

		menuItem_ListAllProposals.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				listAllProposals.renderProposals();
				showElement(listAllProposals);
			}
		});
		
		// add menu items to menu panel		
		headerPanel.menuPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		headerPanel.menuPanel.add(menuItem_Home);
		headerPanel.menuPanel.add(menuItem_CreateNewProposal);
		headerPanel.menuPanel.add(menuItem_ListOwnProposals);
		headerPanel.menuPanel.add(menuItem_ListAllProposals);

		// add login to menu panel
		headerPanel.menuLoginPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		headerPanel.menuLoginPanel.add(menuItem_Logout);	
		setUpMenuLoginPanel();
	}
	
	/** 
	 * Creates and initializes login/logout functionality.
	 * Adds everything at the top of the mainPanel. 
	 * 
	 * @author AP2
	 */
	private void setUpMenuLoginPanel(){		
		menuItem_Login.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				loginPanel = new PopupPanel(false);	
				FormPanel loginForm = new FormPanel();
				loginForm.setEncoding(FormPanel.ENCODING_MULTIPART);
				loginForm.setMethod(FormPanel.METHOD_POST);
				
				VerticalPanel formContent = new VerticalPanel();
				TextBox userIdTextBox = new TextBox();
				userIdTextBox.setName("userId");
				PasswordTextBox passwortTextBox = new PasswordTextBox();
				passwortTextBox.setName("password");
			
				Button loginButton = new Button("OK");
				loginButton.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							headerPanel.menuLoginPanel.add(menuItem_Logout);
							headerPanel.menuLoginPanel.remove(menuItem_Login);	
							loginPanel.hide();
						}
				});
				
				Button cancelButton = new Button("Cancel");
				cancelButton.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							loginPanel.hide();
						}
				});
				
				HorizontalPanel loginCancelPanel = new HorizontalPanel();
				loginCancelPanel.setWidth("100%");
				loginCancelPanel.setSpacing(3);
				loginCancelPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
				loginCancelPanel.setStylePrimaryName("PopupLoginButtons");
				loginCancelPanel.add(loginButton);
				loginCancelPanel.add(cancelButton);
				
				formContent.add(new Label("Benutzername"));
				formContent.add(userIdTextBox);
				formContent.add(new Label("Passwort"));
				formContent.add(passwortTextBox);
				formContent.add(loginCancelPanel);
				loginForm.add(formContent);
				
				loginPanel.add(loginForm);
				loginPanel.setGlassEnabled(true);
				loginPanel.center();			
			}
		});
		
		menuItem_Logout.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				headerPanel.menuLoginPanel.add(menuItem_Login);
				headerPanel.menuLoginPanel.remove(menuItem_Logout);
			}
		});		
	}
	
	/**
	 * Builds structure of scrollable centerPanel for main page content.
	 * Creates and initializes the sub pages. 
	 */
	public void createContent(){		
		// setup structure of scrollable centerPanel for main page content
		centerPanelScrollable = new ScrollPanel();
		mainPanel.add(centerPanelScrollable);
		
		centerPanel = new DockPanel();
		centerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		centerPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		centerPanel.setSize("100%", "100%");
		centerPanelScrollable.add(centerPanel);
		
		contentPanel = new VerticalPanel();
		centerPanel.add(contentPanel, DockPanel.EAST);
		
		// create sub pages and connect them with the contentPanel
		createOrEditProposal = new CreateOrEditProposal(this);
		contentPanel.add(createOrEditProposal);
		createOrEditProposal.setSize("100%", "100%");
		createOrEditProposal.setVisible(false);
						
		listAllProposals = new ListOfAllProposals(this);
		contentPanel.add(listAllProposals);
		listAllProposals.setSize("100%", "100%");
		listAllProposals.setVisible(false);
		
		listOwnProposals = new ListOfOwnProposals(this);
		contentPanel.add(listOwnProposals);
		listOwnProposals.setSize("100%", "100%");
		listOwnProposals.setVisible(false);
		
		// this sub page is only accessible via other sub pages (not via main menu)
		detailedViewProposal = new DetailedViewProposal(this);
		contentPanel.add(detailedViewProposal);
		detailedViewProposal.setSize("100%", "100%");
		detailedViewProposal.setVisible(false);
	}

	/**
	 * Creates the footer of the page with site notice and bug report.
	 * @author: AP4, AP2
	 */
	private void createFooter() {
		Footer footerPanel = new Footer();
		mainPanel.addSouth(footerPanel, 40);
	}

	/**
	 * Handles switches between sub pages via hiding all sub pages 
	 * and making the given widget visible.
	 * 
	 * @param w widget to be displayed
	 */
	public void showElement(Widget w){
		listAllProposals.setVisible(false);
		listOwnProposals.setVisible(false);
		createOrEditProposal.setVisible(false);
		detailedViewProposal.setVisible(false);
		
		w.setVisible(true);
	}
	
	// public getters for manipulating sub pages
	public CreateOrEditProposal getCreateOrEditProposal() {
		return createOrEditProposal;
	}

	public ListOfAllProposals getListAllProposals() {
		return listAllProposals;
	}

	public ListOfOwnProposals getListOwnProposals() {
		return listOwnProposals;
	}

	public DetailedViewProposal getDetailedViewProposal() {
		return detailedViewProposal;
	}
	
	/**
	 * Initializes metatables and inserts departments and persons.
	 */
	private void setupTestdata(){
		testdataService.initializeMetadata(callbackMetadata);
		// departments and persons are initialized after 
		// callback with metadata returned successfully
	}
	
	AsyncCallback<Void> callbackMetadata = new AsyncCallback<Void>(){		
		public void onFailure(Throwable caught) {
			System.err.println("initializeMetadata failed: "+caught.getMessage());
		}
		public void onSuccess(Void result) {
			System.out.println("initializeMetadata successfull");
			testdataService.insertDepartments(callbackDepartments);
			testdataService.insertPersons(callbackPersons);	
		}
		
	};
	
	AsyncCallback<Void> callbackDepartments = new AsyncCallback<Void>(){		
		public void onFailure(Throwable caught) {
			System.err.println("insertDepartments failed: "+caught.getMessage());
		}
		public void onSuccess(Void result) {
			System.out.println("insertDepartments successfull");
		}
	};
	
	AsyncCallback<Void> callbackPersons = new AsyncCallback<Void>(){		
		public void onFailure(Throwable caught) {
			System.err.println("insertPersons failed: "+caught.getMessage());
		}
		public void onSuccess(Void result) {
			System.out.println("insertPersons successfull");
		}
	};
}