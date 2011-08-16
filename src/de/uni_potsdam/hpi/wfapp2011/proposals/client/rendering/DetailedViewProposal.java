package de.uni_potsdam.hpi.wfapp2011.proposals.client.rendering;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.uni_potsdam.hpi.wfapp2011.proposals.client.data.Comment;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.data.Person;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.data.ProjectProposal;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.serviceproxies.CommentService;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.serviceproxies.ProposalService;
/**
 * DetailedViewProposal is a subpage of ProposalsMain.
 * Use renderProposal(int proposalId) to render a 
 * detailed view for a specific proposal.
 * 	 
 * @author Katrin Honauer, Josefine Harzmann
 */
public class DetailedViewProposal extends Composite {
	
	// services
	public ProjectProposal proposal = new ProjectProposal();
	public ProposalService proposalService = new ProposalService();
	public CommentService commentService = new CommentService();
	
	// main panels
	private ProposalsMain mainPage;
	private VerticalPanel containerPanel; 
	public VerticalPanel mainPanel;	
	private VerticalPanel vp_title;
	private VerticalPanel vp_proposal;
	private VerticalPanel vp_edit;
	private VerticalPanel vp_lastModified;
	private VerticalPanel vp_comments;

	private TextArea txtarea_comment;	
	private int proposalId;
	
	// string constants
	private String ERR_LOAD_PROPOSAL = "Beim Laden des Projektvorschlags ist ein Fehler aufgetreten. <br> Bitte versuchen Sie es erneut.";
	private String TRY_AGAIN = "Erneut versuchen";
	private String PROJECT_DESCRIPTION = "Projektbeschreibung";
	private String PARTNER = "Projektpartner";
	private String BASICINFO = "Eckdaten";
	private String CONTACTPERSONS = "Ansprechpartner";
	private String EDIT_PROJECT = "Projekt editieren";
	private String CREATE_PDF = "PDF erzeugen";
	private String COMMENTS = "Kommentare";
	private String SUBMIT = "abschicken";
	
	/**
	 * Creates the outer structure.
	 * @param main reference to main page
	 */
	public DetailedViewProposal(ProposalsMain main){	
		mainPage = main;			
		containerPanel = new VerticalPanel();
		mainPanel = new VerticalPanel();
		mainPanel.setStyleName("mainPanel");
		
		initWidget(containerPanel);
		containerPanel.add(mainPanel);
	}
	
	/**
	 * This is the entry method for rendering 
	 * the page with a specific project proposal.
	 *
	 * @param propId proposalId
	 */
	public void renderProposal(int propId) {
		proposalId = propId;
		
		//remove present proposal and render new one 
		containerPanel.remove(mainPanel);
		mainPanel = new VerticalPanel();
		mainPanel.setStyleName("mainPanel");
		containerPanel.add(mainPanel);

		// try to get proposal from server
		proposalService.getProjectProposal(proposalId, callbackGetProposal);
	}
	
	/**
	 * Tries to get the project proposal 
	 * from the server and triggers rendering.
	 */
	AsyncCallback<ProjectProposal> callbackGetProposal = new AsyncCallback<ProjectProposal>(){		
		public void onFailure(Throwable caught) {
			renderContentOnFailure();
		}
		public void onSuccess(ProjectProposal result) {
			proposal = result;
			renderContentOnSuccess();
		}
	};

	private void renderContentOnFailure(){
		VerticalPanel failureInfo = new VerticalPanel();
		HTMLPanel txt_info  = new HTMLPanel(ERR_LOAD_PROPOSAL);		
		Button btn_tryAgain = new Button(TRY_AGAIN);	
		btn_tryAgain.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {				
				renderProposal(proposalId);
			}
		});	
		failureInfo.add(txt_info);
		failureInfo.add(btn_tryAgain);
		mainPanel.add(failureInfo);
	}
		
	private void renderContentOnSuccess() {	
		// set up vertical panels of the sub page
		vp_title = createTitlePanel();
		vp_proposal = createProposalPanel();
		vp_edit = createEditPanel();
		vp_lastModified = createLastModifiedPanel();
		vp_comments = createCommentsPanel();
		
		// set css styles for the panels
		vp_title.setStyleName("detailedViewTitle");		
		vp_proposal.setStyleName("detailedViewText");
		vp_edit.setStyleName("detailedViewEditButtons");
		vp_comments.setStyleName("detailedViewComments");

		// add panels to mainPanel	
		mainPanel.add(vp_title);
		mainPanel.add(vp_proposal);
		mainPanel.add(vp_edit);
		mainPanel.add(vp_lastModified);
		mainPanel.add(vp_comments);
	}
	
	// create and fill the content panels
	private VerticalPanel createTitlePanel(){
		VerticalPanel vp_title = new VerticalPanel();
		HTMLPanel titlePanel = new HTMLPanel("<h3>"+proposal.getProjectName()+"</h3>");
		vp_title.add(titlePanel);
		return vp_title;
	}
	
	private VerticalPanel createProposalPanel(){		
		VerticalPanel vp_proposal = new VerticalPanel();
		
		// HEADLINES
		HTMLPanel headline_projectDescription = new HTMLPanel(PROJECT_DESCRIPTION);
		HTMLPanel headline_partner =  new HTMLPanel("<br> "+PARTNER+": "+proposal.getPartnerNameOr(""));
		HTMLPanel headline_basicInfo  = new HTMLPanel("<br> "+BASICINFO);
		HTMLPanel headline_contact = new HTMLPanel("<br> "+CONTACTPERSONS);
		
		headline_projectDescription.setStyleName("detailedViewHeadline");
		headline_partner.setStyleName("detailedViewHeadline");
		headline_basicInfo.setStyleName("detailedViewHeadline");
		headline_contact.setStyleName("detailedViewHeadline");	
		
		// CONTENT		
		// set project description
		HTMLPanel projectDescription = new HTMLPanel(proposal.getProjectDescriptionOr("Es wurde noch keine Projektbeschreibung angegeben."));
	
		// set project partner
		HTMLPanel partnerDescription = new HTMLPanel(proposal.getPartnerDescriptionOr("Es wurde noch keine Partnerbeschreibung angegeben."));
		
		// set basic information
		HTMLPanel numberOfStudents = new HTMLPanel(proposal.getMinMaxStudOr("Die Anzahl der Studenten wurde noch nicht festgelegt."));
		HTMLPanel estimatedBegin = new HTMLPanel(proposal.getInfoEstimatedBeginOr("Der voraussichtliche Projektbeginn wurde noch nicht festgelegt."));
		
		// set contact information
		HTMLPanel department = new HTMLPanel("Fachbereich: "+proposal.getDepartmentNameOr("Es konnte kein Fachbereich zugeordnet werden."));			
		HTMLPanel prof = new HTMLPanel(proposal.getProfNameAndMailOrDefault());		
		String str_contactPersons = "";
		str_contactPersons += "<li>"+prof+"</li>";
		List<Person> wimis = proposal.getContactPersons();
		if (wimis != null){
			for (Person contactPerson: wimis) {
				String wimiEntry = contactPerson.getNameAndMailOrDefault(); 
				str_contactPersons += "<li>"+wimiEntry+"</li>";
			}
		}
		HTMLPanel contactPersons = new HTMLPanel("<ul>"+str_contactPersons+"</ul>");
		
		// add items to proposalPanel
		vp_proposal.add(headline_projectDescription);
		vp_proposal.add(projectDescription);		
		vp_proposal.add(headline_partner);
		vp_proposal.add(partnerDescription);	
		vp_proposal.add(headline_basicInfo);
		vp_proposal.add(numberOfStudents);
		vp_proposal.add(estimatedBegin);		
		vp_proposal.add(headline_contact);
		vp_proposal.add(department);
		vp_proposal.add(contactPersons);

		return vp_proposal;
	}

	private VerticalPanel createEditPanel() {
		VerticalPanel vp_edit = new VerticalPanel();
		HorizontalPanel hp_buttons = new HorizontalPanel();
		Button btn_edit = new Button(EDIT_PROJECT);
		Button btn_createPDF = new Button(CREATE_PDF);

		btn_edit.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {				
				setVisible(false);
				mainPage.getCreateOrEditProposal().setVisible(true);
				mainPage.getCreateOrEditProposal().editProposal(proposal.getProjectId());
			}
		});
		
		btn_createPDF.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					// TODO connect button to pdf generator
					//PdfGeneratorWorker generator = new PdfGeneratorWorker(proposal);
				}
		});
		
		hp_buttons.add(btn_edit);
		hp_buttons.add(btn_createPDF);
		vp_edit.add(hp_buttons);
		vp_edit.setCellHorizontalAlignment(hp_buttons, HasHorizontalAlignment.ALIGN_RIGHT);		
		return vp_edit;
	}

	private VerticalPanel createLastModifiedPanel() {
		VerticalPanel vp_lastModified = new VerticalPanel();	
		HTMLPanel lastModified = new HTMLPanel(proposal.getLastModifiedOrDefault());
		vp_lastModified.add(lastModified);
		lastModified.setStyleName("detailedViewLastModified");
		return vp_lastModified;
	}

	private VerticalPanel createCommentsPanel() {
		vp_comments = new VerticalPanel();
		HTMLPanel line = new HTMLPanel("<hr>");
		HTMLPanel commentsInfo = new HTMLPanel("<b>"+COMMENTS+"</b>");
		commentService.getComments(proposalId, callbackGetComments);
				
		FlexTable newComment = new FlexTable();
		txtarea_comment = new TextArea();
		txtarea_comment.setWidth("90%");
		
		Button btn_submitComment = new Button(SUBMIT);
		ClickHandler handler_submitComment = new ClickHandler(){
			public void onClick(ClickEvent event) {
				submitComment();
			}
		};
		btn_submitComment.addClickHandler(handler_submitComment);
		
		newComment.setWidget(0, 0, txtarea_comment);
		newComment.setWidget(0, 1, btn_submitComment);
		newComment.setWidth("100%");
		newComment.getFlexCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		newComment.getFlexCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
				
		vp_comments.add(line);
		vp_comments.add(commentsInfo);
		vp_comments.add(newComment);
		
		return vp_comments;
	}
	
	/**
	 * Tries to get a list of comments
	 * from the server and triggers rendering.
	 */
	AsyncCallback<List<Comment>> callbackGetComments = new AsyncCallback<List<Comment>>(){		
		public void onFailure(Throwable caught) {
		}
		public void onSuccess(List<Comment> result) {
			for(Comment comment: result){
				addCommentToPanel(comment);
			}
		}
	};

	/**
	 * Triggers a callback to save the comment in the DB
	 * and display the saved comment afterwards.
	 */
	private void submitComment(){
		commentService.submitComment(txtarea_comment.getText(), proposal.getProjectId(), callbackSubmitComment);
	}
	
	AsyncCallback<Comment> callbackSubmitComment = new AsyncCallback<Comment>() {
		public void onFailure(Throwable caught) {	
		}
		public void onSuccess(Comment newComment) {
			addCommentToPanel(newComment);
		}
	};
	
	/**
	 * Adds the given comment at the top of the list of comments.
	 * @param newComment
	 */
	private void addCommentToPanel(Comment newComment){
		String commentString = "<br>" + newComment.getAuthorOr("") 
								+ newComment.getDateAndTimeStringOr("") 
								+ "<br>" +newComment.getMessage();
		HTMLPanel nextComment = new HTMLPanel(commentString);
		vp_comments.insert(nextComment, 2);
	}
}
	