package de.uni_potsdam.hpi.wfapp2011.assignment.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.googlecode.gwtTableToExcel.client.TableToExcelClient;

import de.uni_potsdam.hpi.wfapp2011.clientcore.pageframe.Footer;
import de.uni_potsdam.hpi.wfapp2011.clientcore.pageframe.Header;
import de.uni_potsdam.hpi.wfapp2011.clientcore.pageframe.ProcessInfo;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class AP5_main implements EntryPoint {
	
	//General Menu Buttons
	private Label btnHome;
	private Label btnVotesTable;
	private Label btnAssignment;
	private Label btnStatistics;
	private Label btnLogout;
	
	//Panels for the different sub-pages
	private DockPanel votesPage;
	private HTML statisticsPage;
	private HTML welcomePage;
	private AbsolutePanel assignmentPage;
	
	//General Background Panels
	private RootPanel rootPanel;
	private ScrollPanel scrollPanel;
	private DockLayoutPanel mainPanel;
	private DockPanel dockPanel_1;

	//Data on the current Process for use by all classes
	public static Student[] DBStudents;
	public static Project[] DBProjects;
	
	public static String type;
	public static String semester;
	public static int year;
	public static String processTitle;
	public static AssignmentDataExchangeServiceAsync assignmentDataInterface;
	
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// TODO: Insert URL, so that project can be parsed. 
		type = ProcessInfo.getTypeFromURL("");
		semester = ProcessInfo.getSemesterFromURL("");
		year = ProcessInfo.getYearFromURL("");
		
		rootPanel = RootPanel.get();
		
		//Main Panel
		mainPanel = new DockLayoutPanel(Unit.PX);
		rootPanel.add(mainPanel,0,0);
		mainPanel.setSize("100%", "100%");
		
		scrollPanel = new ScrollPanel();
		
		dockPanel_1 = new DockPanel();
		dockPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		dockPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		dockPanel_1.setSize("99.7%","100%");
		scrollPanel.add(dockPanel_1);
		
		//Add Header, Footer & DockPanel
		createHeader();
		createFooter();
		
		mainPanel.add(scrollPanel);
		//End MainPanel

		//Initialize Interface-RemoteService
		if (assignmentDataInterface == null){
			assignmentDataInterface = GWT.create(AssignmentDataExchangeService.class);
		}

		getProjects();
	}

	/**
	 * Method call to import project data from database. 
	 * Continues with getStudents().
	 */
	private void getProjects() {
		assignmentDataInterface.getProjects(type, semester, year, new AsyncCallback<Project[]>() {
			 public void onFailure(Throwable caught) {
				 System.out.println("Failed to get projects");
//				 displayFailurePage(dockPanel_1);
		      }

		      public void onSuccess(Project[] result) {
		    	  DBProjects = result;
		    	  getStudents();
		      }
		});
	}
	
	/**
	 * Method call to import student data from database. 
	 * Continues with buildMain().
	 */
	private void getStudents() {
		assignmentDataInterface.getStudents(type, semester, year, new AsyncCallback<Student[]>() {
			public void onFailure(Throwable caught) {
				 displayFailurePage(dockPanel_1);
			}

			public void onSuccess(Student[] result) {
				DBStudents = result;
				for (Student st : DBStudents){
						st.initPlacement();
				}
		    	VotesTableGenerator.VotesMatrix = HungarianAlgorithm.generateMatrix(DBStudents, DBProjects);
		      	buildMain();
		    }
		});
		
	}
	
	/**
	 * Method where the main parts of the page (different sub-pages) are created
	 */
	private void buildMain() {
		assignmentPage = new AbsolutePanel();
		dockPanel_1.add(assignmentPage, DockPanel.NORTH);
	 	assignmentPage.setSize("100%","100%");
		assignmentPage.setVisible(false);
	 	
		
		if (DBStudents[0].placement == null) {
			firstUseInit();						//sets welcomePage variable
		}
		else{
			welcomePage = new HTML("<html><body><br><h1>Willkommen</h1>" +
					"<h3>Ihre zuletzt gespeicherte Zuordnung wurde geladen.</body></html>");
		}
		
		dockPanel_1.add(welcomePage, DockPanel.NORTH);

		// set up Votes Page (Wahltabelle)	
		votesPage = new DockPanel();
		FlexTable VotesTable = VotesTableGenerator.createVotesTable();
        TableToExcelClient votesTableAsExcel = new TableToExcelClient(VotesTable,"Excel Export","VotesTable "+processTitle);
		votesPage.add(VotesTable, DockPanel.WEST);
        votesPage.setWidth("100%");
		votesPage.add(votesTableAsExcel.build(), DockPanel.NORTH);
		dockPanel_1.add(votesPage, DockPanel.CENTER);
		votesPage.setVisible(false);

		// set up Assignment Page (Zuordnung)
		final AssignmentGenerator assignmentGenerator = new AssignmentGenerator();
		assignmentGenerator.setUpAssignmentPage(assignmentPage);
        
		// set up Statistics Page
		statisticsPage = StatisticsCalculator.calculateStatistics();
		dockPanel_1.add(statisticsPage, DockPanel.NORTH);
		statisticsPage.setVisible(false);
	}

	/**
	 * Generates the assignment using Hungarian Algorithm
	 * and creates the Start Page HTML depending on its result.
	 */
	private void firstUseInit() {
		boolean succeeded = HungarianAlgorithm.initHg(DBProjects, DBStudents);	
		if (!succeeded) {
			welcomePage = new HTML("<html><body><br><h1>Willkommen</h1><h3>Mit den aktuell vorliegenden Daten " +
					"l\u00e4sst sich keine Zuordnung generieren!</body></html>");
		}
		else{
			assignmentDataInterface.writePlacements(DBStudents, type, semester, year ,new AsyncCallback<Void>() {
				 public void onFailure(Throwable caught) {
					 welcomePage = new HTML("<html><body><br><h1>Willkommen</h1><h3>Eine Zuordnung mit den " +
					 		"vorliegenden Daten ist m�glich, jedoch trat ein Fehler beim Schreiben der " +
					 		"Datenbank auf. </body></html>");
			      }

			      public void onSuccess(Void v) {
			    	  welcomePage = new HTML("<html><body><br><h1>Willkommen</h1><h3>Es traten keine Probleme " +
			    	  		"bei der Zuordnung auf.</body></html>");
			      }
			});
		}
	}

	/**
	 * Adds a general Failure Message to the Web Page.
	 * 
	 * @param panel the Panel that the failure message will be displayed on.
	 */
	public static void displayFailurePage(Panel panel){
		HTML message = new HTML("Sorry, an unexpected Failure occured.<br> " +
				"Please contact your Administrator for help.");
		panel.add(message);
	}

	/**
	 * Adds the pages header using methods offered by AP2.
	 * There is a button for each sub-page (when clicked it hides all other sub-pages), 
	 * and a logout button.
	 */
	private void createHeader() {
		//Header von Pageframe
		Header headerPanel = new Header(type, semester, year, "Zuordnung");
		mainPanel.addNorth(headerPanel,151);
		
		//"Home" Button
		btnHome = new Label("Home");
		btnHome.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Window.Location.assign("/overview/");
			}
		});
		headerPanel.menuPanel.add(btnHome);
		
		//"Wahltabelle" Button
		btnVotesTable = new Label("Wahltabelle");
		btnVotesTable.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				welcomePage.setVisible(false);
				votesPage.setVisible(true);
				assignmentPage.setVisible(false);
				statisticsPage.setVisible(false);
			}
		});
		headerPanel.menuPanel.add(btnVotesTable);	
		
		//"Zuordnung" Button
		btnAssignment = new Label("Zuordnung");
		btnAssignment.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				welcomePage.setVisible(false);
				votesPage.setVisible(false);
				assignmentPage.setVisible(true);
				statisticsPage.setVisible(false);
			}
		});
		headerPanel.menuPanel.add(btnAssignment);
		
		//"Statistik" Button
		btnStatistics = new Label("Statistiken");
		btnStatistics.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				welcomePage.setVisible(false);
				votesPage.setVisible(false);
				assignmentPage.setVisible(false);
				if(AssignmentGenerator.hasChanged()){
					dockPanel_1.remove(statisticsPage);
					statisticsPage = StatisticsCalculator.calculateStatistics();
					dockPanel_1.add(statisticsPage, DockPanel.NORTH);
				}
				statisticsPage.setVisible(true);
			}
		});
		headerPanel.menuPanel.add(btnStatistics);
		
		//"Logout" Button
		btnLogout = new Label("Logout");
		btnLogout.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// Logout:
				// 1. sending information to server
				// 2. onSuccess: deleting Cookie: Wfapp2011.USER on Clientpage
				assignmentDataInterface.logout(Cookies.getCookie("Wfapp2011.USER"), new AsyncCallback<Void>(){

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Schwerer Ausnahmefehler. Bitte wenden Sie sich an den Support.\n" +
								"Zur Zeit k�nnen Sich nicht ausloggen, doch in 10min ist ihre Session definitiv abgelaufen.");
					}

					@Override
					public void onSuccess(Void result) {
						Cookies.removeCookie("Wfapp2011.USER");
						Location.reload();
					}
				});			
			}
		});
		headerPanel.menuLoginPanel.add(btnLogout);	
	}

	/**
	 * Adds the page's footer using methods offered by AP2
	 */
	private void createFooter() {
		Footer footerPanel = new Footer();
		mainPanel.addSouth(footerPanel, 40);
	}

}
