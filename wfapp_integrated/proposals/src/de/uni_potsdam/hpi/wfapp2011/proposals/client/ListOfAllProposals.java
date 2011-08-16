package de.uni_potsdam.hpi.wfapp2011.proposals.client;


import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.uni_potsdam.hpi.wfapp2011.proposals.client.data.ProjectProposal;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.interfaces.ProjectProposalInterface;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.interfaces.ProjectProposalInterfaceAsync;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.serviceproxies.ProposalService;

public class ListOfAllProposals extends Composite {
	private static final ProjectProposalInterfaceAsync proposalProvider = 	 
		(ProjectProposalInterfaceAsync) GWT.create(ProjectProposalInterface.class);
	
	static{
		ServiceDefTarget endpoint = (ServiceDefTarget) proposalProvider;
		String moduleRealtiveURL = GWT.getModuleBaseURL() + "projectProposal";
		endpoint.setServiceEntryPoint(moduleRealtiveURL);
	}/*
	private static final SetupTestdataInterfaceAsync setup = 	 
		(SetupTestdataInterfaceAsync) GWT.create(SetupTestdataInterface.class);
	
	static{
		ServiceDefTarget endpoint2 = (ServiceDefTarget) setup;
		String moduleRealtiveURL2 = GWT.getModuleBaseURL() + "setupTestdata";
		endpoint2.setServiceEntryPoint(moduleRealtiveURL2);
	}*/
	public ArrayList<ProjectProposal> proposals = new ArrayList<ProjectProposal>();
	public VerticalPanel mainPanel;
	public ProposalService proposalService = new ProposalService();
	//public Object zipFile;
	private ProposalsMain mainPage;
	private FlexTable proposalTable;
	private VerticalPanel oberPanel;
	private String searchterm = "";
	
	public ListOfAllProposals(ProposalsMain main){
		
		mainPage = main;
		
		oberPanel = new VerticalPanel();
		initWidget(oberPanel);
		
		mainPanel = new VerticalPanel();
		oberPanel.add(mainPanel);
		
//		mainPage = main;
//		mainPanel = new VerticalPanel();
//		initWidget(mainPanel);
		mainPanel.setStyleName("mainPanel");
		mainPanel.setWidth("90%");
	}
	
	public void setupPage(){
		mainPanel = new VerticalPanel();
		initWidget(mainPanel);
		mainPanel.setStyleName("mainPanel");
		mainPanel.setWidth("90%");
		HTMLPanel panel = new HTMLPanel("<h3>All Proposals</h3>");
		mainPanel.add(panel);
	}
	
	public void renderProposals(){
		
		oberPanel.remove(mainPanel);
		mainPanel = new VerticalPanel();
		oberPanel.add(mainPanel);

		mainPanel.setStyleName("mainPanel");
		mainPanel.setWidth("90%");
		
		proposalProvider.getProjectProposals( new AsyncCallback<ArrayList<ProjectProposal>>() {
			public void onFailure(Throwable caught) {
				//Window.alert("loading proposals failed.\n\n" + caught.getMessage());
			}
			@Override
			public void onSuccess(ArrayList<ProjectProposal> result) {
				proposals = result;
			}});
		
		HTMLPanel titlePanel = new HTMLPanel("<h3>Alle Projektvorschläge</h3>");
		HorizontalPanel searchPanel = getSearchPanel();
		proposalTable = new FlexTable();
		addTableHead();
		addFilledTable();
		
		String str = GWT.getModuleBaseURL() + "downloadZip";
		Anchor downloadAsZip = new Anchor("alle Projektdateien herunterladen", str);
		downloadAsZip.setStyleName("DownloadLink");
		
		mainPanel.add(titlePanel);
		mainPanel.add(searchPanel);
		mainPanel.add(proposalTable);
		mainPanel.add(downloadAsZip);
	}
	
	private HorizontalPanel getSearchPanel(){
		HorizontalPanel panel = new HorizontalPanel();
		final TextBox searchfield = new TextBox();
		searchfield.setText(searchterm);
		Button searchButton = new Button("Suchen");
		searchButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				searchterm = searchfield.getValue();
				renderProposals();
			}
		});
		panel.add(searchfield);
		panel.add(searchButton);
		return panel;
	}
	
	private void addTableHead(){
		proposalTable.setText(0, 0, "Status");
		proposalTable.setText(0, 1, "Projektname");
		proposalTable.setText(0, 2, "Keywords");
		proposalTable.setText(0, 3, "Partner");
		proposalTable.setText(0, 4, "Betreuer");
		proposalTable.setText(0, 5, "Teamgröße");
		proposalTable.setText(0, 6, "Dateien");
		proposalTable.setText(0, 7, "");
	}
	
	private Boolean containsSearchterm(ProjectProposal proposal){
		if (searchterm == "") return true;
		if (proposal.getProjectName().contains(searchterm) || 
				proposal.getProjectDescription().contains(searchterm) || 
				proposal.getKeywords().contains(searchterm)){
			return true;
		}
		return false;
	}
	
	private void addFilledTable(){
		int counter = 1;
		for (final ProjectProposal proposal : proposals){
			if (!proposal.getIsDeleted() && proposal.getIsPublic() && containsSearchterm(proposal)){
				String votable = "";
				if (proposal.getIsRejected()){
					votable = "Nicht zur Wahl gestellt";
				} else {votable = "Zur Wahl gestellt";}
				proposalTable.setText(counter, 0, votable);
				Label projectLink = new Label(proposal.getProjectName());
				projectLink.addClickHandler(new ClickHandler(){
					public void onClick(ClickEvent event) {
						mainPage.showElement(mainPage.getDetailedViewProposal());
						mainPage.getDetailedViewProposal().renderProposal(proposal.getProjectId());
					}
				});
				proposalTable.setWidget(counter, 1, projectLink);
				proposalTable.setText(counter, 2, "Keywords");
				proposalTable.setText(counter, 3, proposal.getPartnerName());
				proposalTable.setText(counter, 4, "Kontaktpersonen");
				proposalTable.setText(counter, 5, proposal.getMinStud() + "-" + proposal.getMaxStud());
				proposalTable.setText(counter, 6, "Dateien");
				String voteButtonText = "";
				if (proposal.getIsRejected()){
					voteButtonText = "Zur Wahl stellen";
				} else {voteButtonText = "Von der Wahl ausschließen";}
				Button voteButton = new Button(voteButtonText);
				voteButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						proposalProvider.setVotable(proposal, !proposal.getIsRejected(), new AsyncCallback<Void>() {
							public void onFailure(Throwable caught) {
								Window.alert("Zur Wahl stellen ist nicht möglich");
							}
							public void onSuccess(Void result) {
								proposal.setIsRejected(!proposal.getIsRejected());
								if (proposal.getIsRejected()){
									Window.alert("Das Projekt wurde von der Wahl ausgeschlossen");
								} else {
									Window.alert("Das Projekt wurde zur Wahl gestellt");
								}
								renderProposals();
							}
						});
				}});
				proposalTable.setWidget(counter, 7, voteButton);
				counter += 1;
			}
		}
	}
	
//	private Button addDownloadButton(){
//		Button button = new Button("alle Projektdateien herunterladen");
//		
//		ClickHandler handler_downloadFiles = new ClickHandler(){
//			public void onClick(ClickEvent event) {
//				proposalService.getZippedProjectFiles(callbackFiles);
//			}
//		};
//		button.addClickHandler(handler_downloadFiles);
//		
//		return button;
//	}
//	
//	AsyncCallback callbackFiles = new AsyncCallback(){
//		
//		public void onFailure(Throwable caught) {
//			Window.alert("zipping files failed.\n\n" + caught.getMessage());
//			//TODO render failure info
//			//renderFailureInfo();
//		}
//
//		public void onSuccess(Object result) {
//			//zipFile = result;
//			Window.alert("succeeded: "+result);
//			//renderContent();
//			//TODO mehrfaches einfügen verhindern
//		}
//
//		
//	};
}