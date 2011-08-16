package de.uni_potsdam.hpi.wfapp2011.overview.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.uni_potsdam.hpi.wfapp2011.clientcore.pageframe.Footer;
import de.uni_potsdam.hpi.wfapp2011.clientcore.pageframe.Header;
import de.uni_potsdam.hpi.wfapp2011.clientcore.pageframe.ProcessInfo;
import de.uni_potsdam.hpi.wfapp2011.overview.shared.ProcessInformation;

/**
 * This class build the portal page, the start point for every phase of the process.
 * 
 * @author Jannik Marten, Yanina Yurchenko
 */

public class OverviewMain implements EntryPoint {
	private boolean debug = false;
	private RootPanel rootPanel; 
	private Label homeButton;
	private DockLayoutPanel mainPanel;
	private ScrollPanel centerPanelScrollable;
	private DockPanel centerPanel;
	private VerticalPanel overviewPanel;
	private HTMLPanel statisticPanel;
	private HorizontalPanel processPanel;
	private Anchor anchorProjectProposal;
	private Anchor anchorVoting;
	private Anchor anchorMatching;
	private VerticalPanel projectProposalPanel;
	private VerticalPanel votingPanel;
	private VerticalPanel matchingPanel;
	private String endProjectProposal = "";
	private String endVoting = "";
	private String endMatching = "";
	private String type;
	private String semester;
	private int year; 
	private Header headerPanel;
	private HTMLPanel inaktiveProsessPanel;	
	private static final ServerRequesterInterfaceAsync logger = (ServerRequesterInterfaceAsync) GWT.create(ServerRequesterInterface.class);
	static{
		ServiceDefTarget endpoint = (ServiceDefTarget) logger;
		String moduleRealtiveURL = GWT.getModuleBaseURL() + "serverRequester";
		endpoint.setServiceEntryPoint(moduleRealtiveURL);
		}
	
	@Override
	public void onModuleLoad() {
		year = ProcessInfo.getYearFromURL("");
		semester = ProcessInfo.getSemesterFromURL("");
		type = ProcessInfo.getTypeFromURL("");
		
		rootPanel = RootPanel.get();
		rootPanel.setSize("99%", "98%");
		
		mainPanel = new DockLayoutPanel(Unit.PX);
		rootPanel.add(mainPanel, 0, 0);
		mainPanel.setSize("99%", "100%");
		 
		createHeader();
		createFooter();
		
		centerPanel = new DockPanel();
		centerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		centerPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		centerPanelScrollable = new ScrollPanel();
		
		//OverviewPanel is the main panel on the page, contains everything except header and footer
		overviewPanel = new VerticalPanel();
		
		logger.getInformations(type, semester, year, new AsyncCallback<ProcessInformation>() {
			public void onFailure(Throwable caught){
				if (debug) Window.alert("RPC failed \n" + caught.getMessage());
				Window.alert("Schwerer Fehler, bitte wenden Sie sich an Administrator.");
			}
			
			/**
			 * On success of logger.getInformation(), this method build the main content of overview page: <br/> 
			 * links to other subpackages, deadlines and statistics information.
			 * <br/><br/>
			 * If the requested process isn't active, you see this on the page instead of process inforation.
			 *  
			 * @param pInfo : ProcessInformation
			 * 
			 * On failure: You see a pop-up with an error message.
			 */
			@Override
			public void onSuccess(ProcessInformation pInfo){
				if(pInfo.isActiv()){
					anchorProjectProposal = new Anchor("Themenerfassung", "/proposals");
					anchorVoting = new Anchor("Themenwahl", "/voting");
					anchorMatching = new Anchor("Themenzuordnung", "/assignment");
					
					anchorProjectProposal.setEnabled(false);
					anchorVoting.setEnabled(false);
					anchorMatching.setEnabled(false);
					
					HTMLPanel endProjectProposalPanel = new HTMLPanel(endProjectProposal);
					HTMLPanel endVotingPanel = new HTMLPanel(endVoting);
					HTMLPanel endMatchingPanel = new HTMLPanel(endMatching);

					HTML endProjectProposal = new HTML("<DIV id=\"deadlineDates\">"+pInfo.getDeadlinesString()[0]+"</DIV>");
					HTML endVoting = new HTML("<DIV id=\"deadlineDates\">"+pInfo.getDeadlinesString()[1]+"</DIV>");
					HTML endMatching = new HTML("<DIV id=\"deadlineDates\">"+pInfo.getDeadlinesString()[2]+"</DIV>");
					
					if(pInfo.isProjectProposalPhase() == true){
						anchorProjectProposal.setHTML("<img src=\"/images/proposalsDark.png\" width=\"174px\" height=\"93px\" id=\"anchorPhase1\">");
						anchorVoting.setHTML("<img  src=\"/images/votingLight.png\" width=\"174px\" height=\"93px\" id=\"anchorPhase2\">");
						anchorMatching.setHTML("<img  src=\"/images/matchingLight.png\" width=\"174px\" height=\"93px\" id=\"anchorPhase3\">");
						anchorProjectProposal.setEnabled(true);
						endProjectProposal.setStyleName("activeText");
						endVoting.setStyleName("inactiveText");
						endMatching.setStyleName("inactiveText");
					}
					else if(pInfo.isVotingPhase() == true){
						anchorVoting.setHTML("<img  src=\"/images/votingDark.png\" width=\"174px\" height=\"93px\" id=\"anchorPhase2\">");
						anchorProjectProposal.setHTML("<img src=\"/images/proposalsLight.png\" width=\"174px\" height=\"93px\" id=\"anchorPhase1\">");
						anchorMatching.setHTML("<img  src=\"/images/matchingLight.png\" width=\"174px\" height=\"93px\" id=\"anchorPhase3\">");
						anchorVoting.setEnabled(true);
						endProjectProposal.setStyleName("inactiveText");
						endVoting.setStyleName("activeText");
						endMatching.setStyleName("inactiveText");
					}
					else if(pInfo.isMatchingPhase() == true){
						anchorMatching.setHTML("<img  src=\"/images/matchingDark.png\" width=\"174px\" height=\"93px\" id=\"anchorPhase3\">");
						anchorProjectProposal.setHTML("<img src=\"/images/proposalsLight.png\" width=\"174px\" height=\"93px\" id=\"anchorPhase1\">");
						anchorVoting.setHTML("<img  src=\"/images/votingLight.png\" width=\"174px\" height=\"93px\" id=\"anchorPhase2\">");
						anchorMatching.setEnabled(true);
						endProjectProposal.setStyleName("inactiveText");
						endVoting.setStyleName("inactiveText");
						endMatching.setStyleName("activeText");
					}else{
						anchorProjectProposal.setHTML("<img src=\"/images/proposalsLight.png\" width=\"174px\" height=\"93px\" id=\"anchorPhase1\">");
						anchorVoting.setHTML("<img  src=\"/images/votingLight.png\" width=\"174px\" height=\"93px\" id=\"anchorPhase2\">");
						anchorMatching.setHTML("<img  src=\"/images/matchingLight.png\" width=\"174px\" height=\"93px\" id=\"anchorPhase3\">");
						endProjectProposal.setStyleName("inactiveText");
						endVoting.setStyleName("inactiveText");
						endMatching.setStyleName("inactiveText");
						inaktiveProsessPanel = new HTMLPanel("<DIV> Dieser Prozess ist nicht mehr aktiv </DIV>");
						inaktiveProsessPanel.addStyleName("inactiveWarning");
						overviewPanel.add(inaktiveProsessPanel);
					}
										
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
					overviewPanel.add(processPanel);
					
					projectProposalPanel.add(endProjectProposal);
					projectProposalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
					votingPanel.add(endVoting);
					votingPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
					matchingPanel.add(endMatching);
					matchingPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
					
					
					String proposalStatistic = "<DIV> "+pInfo.getStatistics() +"</DIV>";
					statisticPanel = new HTMLPanel(proposalStatistic);
					statisticPanel.addStyleName("activeText");
					overviewPanel.add(statisticPanel);
				}else{
					inaktiveProsessPanel = new HTMLPanel("<DIV> Dieser Prozess ist zurzeit nicht aktiv! </DIV>");
					inaktiveProsessPanel.addStyleName("inactiveWarning");
					overviewPanel.add(inaktiveProsessPanel);
				}
			}
		});		
		centerPanel.add(overviewPanel, DockPanel.CENTER);
		centerPanelScrollable.add(centerPanel);
		mainPanel.add(centerPanelScrollable);
		centerPanel.setSize("100%", "100%");
	}

	/**
	 * This method build an instance of Header and add it to the overview. The build header contains images, greeting, type of semester and a public menu panel.
	 * 
	 */
	private void createHeader() {
		headerPanel = new Header(type, semester, year, "Willkommen"); 
		
		//label homeButton isn't seen in the menuPanel
		//if it get some functionality, you can just add them to headerPanel.menuPanel
		homeButton = new Label("Home");

		//headerPanel.menuPanel.add(homeButton);
		homeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Window.Location.reload();
			}
		});
	
		mainPanel.addNorth(headerPanel,151);
	}

	/**
	 * This method build an instance of Footer and add it to the overview.
	 */
	private void createFooter() {
		Footer footerPanel = new Footer();
		mainPanel.addSouth(footerPanel, 40);
	}
}