package de.uni_potsdam.hpi.wfapp2011.overview.client;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
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
import de.uni_potsdam.hpi.wfapp2011.general.ProcessIdentifier;
import de.uni_potsdam.hpi.wfapp2011.pageframe.Footer;
import de.uni_potsdam.hpi.wfapp2011.pageframe.Header;
import de.uni_potsdam.hpi.wfapp2011.themenwahl_test.HTMLContent;

public class OverviewMain implements EntryPoint {

	private RootPanel rootPanel;
	private HTMLPanel htmlHeader;
	private DockLayoutPanel  menuPanel;
	private HorizontalPanel menuPanel2;
	private Label homeButton;
	private Label login;
	private Label logout;
	private PopupPanel loginPanel;
	private HTML htmlFooter;
	private Frame frame;
	private DockLayoutPanel mainPanel;
	private ScrollPanel centerPanelScrollable;
	private DockPanel centerPanel;
	private VerticalPanel overviewPanel;
	private HTMLPanel startProcessInfoPanel;
	private HTMLPanel proposalStatisticPanel;
	private HTMLPanel votingStatisticPanel;
	private HorizontalPanel processPanel;
	private Anchor anchorProjectProposal;
	private Anchor anchorVoting;
	private Anchor anchorMatching;
	private VerticalPanel projectProposalPanel;
	private VerticalPanel votingPanel;
	private VerticalPanel matchingPanel;
	Command command;
	String numberOfVotings = "No Result";
	String endProjectProposal = "";//"<DIV>Ha</DIV>";
	String endVoting = "";//<DIV>Hi</DIV>";
	String endMatching = "";//"<DIV>Ho</DIV>";
	FlexTable logTable;
	PopupPanel logPopup;
	VerticalPanel logPanel;
	ProcessIdentifier pId;
	private Header headerPanel;
	private static final StatisticProviderInterfaceAsync logger = 	 
									(StatisticProviderInterfaceAsync) GWT.create(StatisticProviderInterface.class);
	static{
		ServiceDefTarget endpoint = (ServiceDefTarget) logger;
		String moduleRealtiveURL = GWT.getModuleBaseURL() + "statisticProvider";
		endpoint.setServiceEntryPoint(moduleRealtiveURL);
		}
	
	@Override
	public void onModuleLoad() {
		pId = ProcessIdentifier.getProcessIdentifier("");
		rootPanel = RootPanel.get();
		rootPanel.setSize("100%", "100%");
		
		mainPanel = new DockLayoutPanel(Unit.PX);
		rootPanel.add(mainPanel, 0, 0);
		mainPanel.setSize("99%", "100%");
		 
		createHeader();
		createFooter();
		
		anchorProjectProposal = new Anchor("Themenerfassung", "https://google.de");
		anchorVoting = new Anchor("Themenwahl", "https://www.hpi.uni-potsdam.de");
		anchorMatching = new Anchor("Themenzuordnung", "https://google.de");
		
		anchorProjectProposal.setHTML("<img src=\"/images/themenerfassung.png\" width=\"174px\" height=\"93px\" id=\"anchorPhase1\">");
		anchorVoting.setHTML("<img  src=\"/images/themenwahl.png\" width=\"174px\" height=\"93px\" id=\"anchorPhase2\">");
		anchorMatching.setHTML("<img  src=\"/images/themenzuordnung.png\" width=\"174px\" height=\"93px\" id=\"anchorPhase3\">");
	
		//DOM.setElementAttribute(anchorProjectProposal.getElement(), "id", "anchorPhase1");
		
		centerPanel = new DockPanel();
		centerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		centerPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		centerPanelScrollable = new ScrollPanel();
		
		//ProcessPanel vorbereiten/ erzeugen
		logger.getDeadlines(pId, new AsyncCallback<String[]>(){
			public void onFailure(Throwable caught) {
				Window.alert("RPC to getDeadlines() failed.\n\n" + caught.getMessage());
			}
			@Override
			public void onSuccess(String[] result) {
				String[] deadlines = result;
				//aufräumen!
				HTML endProjectProposal = new HTML("<DIV id=\"deadlineDates\">"+deadlines[0]+"</DIV>");
				HTML endVoting = new HTML("<DIV id=\"deadlineDates\">"+deadlines[1]+"</DIV>");
				HTML endMatching = new HTML("<DIV id=\"deadlineDates\">"+deadlines[2]+"</DIV>");
				projectProposalPanel.add(endProjectProposal);
				projectProposalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
				votingPanel.add(endVoting);
				votingPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
				matchingPanel.add(endMatching);
				matchingPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
			}
		});
		
		//while(endMatching.equals(""));
			
		HTMLPanel endProjectProposalPanel = new HTMLPanel(endProjectProposal);
		HTMLPanel endVotingPanel = new HTMLPanel(endVoting);
		HTMLPanel endMatchingPanel = new HTMLPanel(endMatching);
		
		projectProposalPanel = new VerticalPanel();
		projectProposalPanel.add(anchorProjectProposal);
		projectProposalPanel.add(endProjectProposalPanel);
		projectProposalPanel.setCellHorizontalAlignment(endProjectProposalPanel, HasHorizontalAlignment.ALIGN_RIGHT);
		
		votingPanel = new VerticalPanel();
		votingPanel.add(anchorVoting);
		votingPanel.add(endVotingPanel);
		votingPanel.setCellHorizontalAlignment(endVotingPanel, HasHorizontalAlignment.ALIGN_RIGHT);
		
		matchingPanel = new VerticalPanel();
		matchingPanel.add(anchorMatching);
		matchingPanel.add(endMatchingPanel);
		matchingPanel.setCellHorizontalAlignment(endMatchingPanel, HasHorizontalAlignment.ALIGN_RIGHT);
		
		processPanel = new HorizontalPanel();
		processPanel.add(projectProposalPanel);
		processPanel.add(votingPanel);
		processPanel.add(matchingPanel);
		
		
		//setzt den OverviewPanel zusammen
		overviewPanel = new VerticalPanel();
		
		//StartProcessInfoPanel
		String startProcessInfo= "<DIV> Themenerfassung startet am: 19.09.2014 </DIV>";
		startProcessInfoPanel = new HTMLPanel(startProcessInfo);
		//overviewPanel.add(startProcessInfoPanel);
		overviewPanel.add(processPanel);
		
		//ProposalStatistic
		logger.getNumberOfProposals(pId, new AsyncCallback<String>(){
			public void onFailure(Throwable caught) {
				Window.alert("RPC to getNumberOfProposals() failed.\n\n" + caught.getMessage());
			}
			@Override
			public void onSuccess(String result) {
				String proposalStatistic = "<DIV> "+result +"</DIV>";
				proposalStatisticPanel = new HTMLPanel(proposalStatistic);
				overviewPanel.add(proposalStatisticPanel);
			}
		});
		//VotingStatistic
		logger.getNumberOfVotings(pId, new AsyncCallback<String>(){
			public void onFailure(Throwable caught) {
				Window.alert("RPC to getNumberOfVotings() failed.\n\n" + caught.getMessage());
			}
			@Override
			public void onSuccess(String result) {
				String statistics = "<DIV>"+result +"</DIV>";
				votingStatisticPanel = new HTMLPanel(statistics);
				overviewPanel.add(votingStatisticPanel);
			}
		});
		centerPanel.add(overviewPanel, DockPanel.CENTER);
		centerPanelScrollable.add(centerPanel);
		mainPanel.add(centerPanelScrollable);
		centerPanel.setSize("100%", "100%");
		
	}

	private void createHeader() {
		//Header von Pageframe
		headerPanel = new Header(pId, "Willkommen"); 
		
		homeButton = new Label("Home");
		login = new Label("Login");
		logout = new Label("Logout");
		Label logLabel = new Label("Logtabelle");

		headerPanel.menuPanel.add(homeButton);
		headerPanel.menuPanel.add(logLabel);
		headerPanel.menuLoginPanel.add(login);
		
		homeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Window.alert("Blubb new!");
			}
		});
		
		logLabel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				showLogging();				
			}
		});
				
		login.addClickHandler(new ClickHandler() {
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
							headerPanel.menuLoginPanel.add(logout);
							headerPanel.menuLoginPanel.remove(login);	
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
		logout.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				headerPanel.menuLoginPanel.add(login);
				headerPanel.menuLoginPanel.remove(logout);
			}
		});
		mainPanel.addNorth(headerPanel,151);
	}

	private void createFooter() {
		//Footer von Pageframe
		Footer footerPanel = new Footer();
		mainPanel.addSouth(footerPanel, 40);
	}
	
	private void showLogging(){
		
		logPopup = new PopupPanel();
		logPanel = new VerticalPanel();
		logTable = new FlexTable();
		

		Button cancelButton = new Button("Cancel");
		cancelButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					logPopup.hide();
				}
		});
		HorizontalPanel cancelPanel = new HorizontalPanel();
		cancelPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		cancelPanel.add(cancelButton);
		logPanel.add(cancelPanel);
		
		logger.getLogEntries(pId, new AsyncCallback<Collection<String[]>> (){
			public void onFailure(Throwable caught) {
				Window.alert("RPC to getNumberOfVotings() failed.\n\n" + caught.getMessage());
			}
			@Override
			public void onSuccess(Collection<String[]> result) {
				Iterator iterator = result.iterator();
				int row = 0;
				while(iterator.hasNext()){
					String[] logEntry = (String[]) iterator.next();
					Date date = new Date();
					date.setTime(Long.parseLong(logEntry[0]));
					@SuppressWarnings("deprecation")
					int year = date.getYear()+1900;
					int month = date.getMonth()+1;
					String dateString = date.getDate()+"."+month+"."+ year+ " "+date.getHours() +":"+date.getMinutes();
					logTable.setWidget(row, 0, new Label(dateString));
					logTable.setWidget(row, 1, new Label(logEntry[1]));
					logTable.setWidget(row, 2, new Label(logEntry[2]));
					logTable.setWidget(row, 3, new Label(logEntry[3]));
					row++;
				}
				logPanel.add(logTable);
				logPopup.add(logPanel);
				logPopup.center();
			}
		});		 
	}
}