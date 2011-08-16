package de.uni_potsdam.hpi.wfapp2011.proposals.client.rendering;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.uni_potsdam.hpi.wfapp2011.proposals.client.data.ProjectProposal;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.interfaces.ProjectProposalInterface;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.interfaces.ProjectProposalInterfaceAsync;
/**
 * ListOfOwnProposals is a subpage of ProposalsMain.
 * It renders all project proposals from one specific department.
 * 	 
 * @author Katrin Honauer, Josefine Harzmann
 */

public class ListOfOwnProposals extends Composite {
	private ProposalsMain mainPage;
	private static final ProjectProposalInterfaceAsync proposalProvider = 	 
		(ProjectProposalInterfaceAsync) GWT.create(ProjectProposalInterface.class);
	
	static{
		ServiceDefTarget endpoint = (ServiceDefTarget) proposalProvider;
		String moduleRealtiveURL = GWT.getModuleBaseURL() + "projectProposal";
		endpoint.setServiceEntryPoint(moduleRealtiveURL);
	}
	public ArrayList<ProjectProposal> proposals = new ArrayList<ProjectProposal>();
	private FlexTable proposalTable;
	public VerticalPanel mainPanel;
	public VerticalPanel oberPanel;
	
	public ListOfOwnProposals(ProposalsMain main){
			
		mainPage = main;		
		oberPanel = new VerticalPanel();
		initWidget(oberPanel);	
		mainPanel = new VerticalPanel();
		oberPanel.add(mainPanel);
		mainPanel.setStyleName("mainPanel");
		mainPanel.setWidth("90%");
		
	}
	
	private void addTableHead(){
		proposalTable.setText(0, 0, "öffentlich");
		proposalTable.setText(0, 1, "Projektname");
		proposalTable.setText(0, 2, "Keywords");
		proposalTable.setText(0, 3, "Partner");
		proposalTable.setText(0, 4, "Betreuer");
		proposalTable.setText(0, 5, "Teamgröße");
		proposalTable.setText(0, 6, "Dateien");
	}
	
	public void addTable(){
		int counter = 1;
		for (final ProjectProposal proposal : proposals){
			if (!proposal.getIsDeleted()/* && proposal.getDeparment()==user.getDepartment()*/ ){
				CheckBox checkbox = new CheckBox();
				checkbox.setValue(proposal.getIsPublic());
				System.out.println("checked: " + checkbox.getValue());
				checkbox.addClickHandler(new ClickHandler() {
				      public void onClick(ClickEvent event) {
				        final boolean checked = ((CheckBox) event.getSource()).getValue();
				        System.out.println("newly checked: " + checked);
				        proposalProvider.setPublicness(proposal.getProjectId(), checked, new AsyncCallback<Void>() {
							public void onFailure(Throwable caught) {
								//Window.alert("Fehler: " + caught.getMessage());
							}
							public void onSuccess(Void result) {
								proposal.setIsPublic(checked);
								if (proposal.getIsPublic()){
									Window.alert("Das Projekt ist nun auch für andere Fachbereiche sichtbar");
								} else {
									Window.alert("Das Projekt ist nun nicht mehr für andere Fachbereiche sichtbar");
								}
								renderProposals();
							}
						});
				      }
				    });
				proposalTable.setWidget(counter, 0, checkbox);
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
				counter += 1;
			}
		}
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
			
			HTMLPanel titlePanel = new HTMLPanel("<h3>Vorschläge des eigenen Fachbereichs</h3>");
			proposalTable = new FlexTable();
			addTableHead();
			addTable();
			
			mainPanel.add(titlePanel);
			mainPanel.add(proposalTable);
		}
}