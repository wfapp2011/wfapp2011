package de.uni_potsdam.hpi.wfapp2011.proposals.client;

import java.util.ArrayList;
import java.util.List;

import org.zenika.widget.client.datePicker.DatePicker;

import com.axeiya.gwtckeditor.client.CKEditor;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormSubmitEvent;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.uni_potsdam.hpi.wfapp2011.proposals.client.data.FileInfo;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.data.ProjectProposal;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.serviceproxies.FileService;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.serviceproxies.ProposalService;

@SuppressWarnings("deprecation")
public class CreateOrEditProposal extends Composite {

	public ProposalService proposalService = new ProposalService();
	public FileService fileService = new FileService();
	public ProjectProposal proposal = new ProjectProposal();
	private int proposalId = 0;
	
	// main panels and tables
	private ProposalsMain mainPage;
	private VerticalPanel containerPanel; 
	public VerticalPanel mainPanel;
	private FormPanel formPanel;
	private FlexTable ft_formFields; 
	private VerticalPanel infoMessagePanel; 
	private Label label_infoMessage;
	
	// form fields
	private TextBox tb_projectName;
	private TextBox tb_keywords;
	private TextBox tb_partnerName;
	private DatePicker dp_estimatedBegin; 
	private HorizontalPanel hp_teamSize;	
	private ListBox lb_minStud;
	private ListBox lb_maxStud;
	private Button btn_submit;
	private Button btn_generatePdf;
	
	// other 
	private static int minMinStud = 3;
	private static int minMaxStud = 4;
	private int highestContactPerson;
	private int highestAdditionalFile; 	
	public List<FileInfo> files = new ArrayList<FileInfo>();
	public Hidden hf_highestContactPerson = new Hidden();
	public Hidden hf_highestAdditionalFile = new Hidden();
	private Hidden hf_projectDescription = new Hidden();
	private Hidden hf_partnerDescription = new Hidden();
	private Hidden hf_currentProposalId = new Hidden();

	// rich text editor
	private CKEditorConfig helper_ck = new CKEditorConfig();
	private CKEditor ck_projectDescription;
	private CKEditor ck_partnerDescription;
	 
	// helper classes
	FlexibleContactPersons helper_cp;
	FlexibleFiles helper_fi;
	
	/**
	 * Creates the outer structure.
	 * @param main reference to main page
	 */
	public CreateOrEditProposal(ProposalsMain main) {	
		mainPage = main;
		containerPanel = new VerticalPanel();
		mainPanel = new VerticalPanel();
		mainPanel.setStyleName("mainPanel");

		initWidget(containerPanel);
		containerPanel.add(mainPanel);
		
		helper_cp = new FlexibleContactPersons(this);
		helper_fi = new FlexibleFiles(this);
		highestContactPerson = 0;
		highestAdditionalFile = 0;
	}
	
	/** 
	 * Removes the present mainPanel and creates a new one. 
	 */
	public void clearPage(){
		containerPanel.remove(mainPanel);
		mainPanel = new VerticalPanel();
		mainPanel.setStyleName("mainPanel");
		containerPanel.add(mainPanel);
	}
		
	/**
	 * This is the entry method for rendering 
	 * an empty form without a proposal.
	 */
	public void createNewProposal(){	
		clearPage();
		// create and add empty form		
		renderBasicForm();
		helper_cp.addEmptyContactPersons(ft_formFields);
		helper_fi.addEmptyProjectFile(ft_formFields);
		helper_fi.addEmptyAdditionalFiles(ft_formFields);
		
		// create and add label for error messages
		renderEmptyInfoMessage();
		
		hf_currentProposalId.setValue("-1");
	}
	
	/**
	 * This is the entry method for rendering 
	 * the form with a specific project proposal.
	 *
	 * @param propId proposalId
	 */
	public void editProposal(int propId){		
		proposalId = propId;
		clearPage();
		
		// create and add basic empty form		
		renderBasicForm();
		
		// create and add label for error messages
		renderEmptyInfoMessage();
	
		// initialize values
		hf_currentProposalId.setValue(Integer.toString(proposalId));
		proposalService.getProjectProposal(proposalId, callbackGetProposal);
		fileService.getAllFiles(proposalId, callbackGetFileInfos);
	}

	/**
	 * Renders a basic empty form with fields and buttons except
	 * contact persons and files.
	 */
	public void renderBasicForm(){	
		formPanel = new FormPanel();
		formPanel.setAction(GWT.getModuleBaseURL() + "saveProposal");
		formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		formPanel.setMethod(FormPanel.METHOD_POST);						
		ft_formFields = new FlexTable();
		formPanel.setWidget(ft_formFields);
			
		addRegularFormFields();
		addButtons();
		
		mainPanel.add(formPanel);
	}

	/**
	 * Creates a placeholder for error messages.
	 */
	public void renderEmptyInfoMessage(){
		infoMessagePanel = new VerticalPanel();
		label_infoMessage = new Label("");		
		label_infoMessage.setStyleName("infoLabel");
		infoMessagePanel.add(label_infoMessage);
		mainPanel.add(infoMessagePanel);
	}
	
	private void addRegularFormFields(){				
		// project name
		ft_formFields.setText(0, 0, "Projektname*");
		tb_projectName = new TextBox();
		tb_projectName.setName("projectName");
		tb_projectName.setWidth("100%");
		ft_formFields.setWidget(0, 1, tb_projectName);
		ft_formFields.getFlexCellFormatter().setColSpan(0, 1, 3);

		// project description (with large ckeditor)
		ft_formFields.setText(1, 0, "Beschreibung");
		ft_formFields.getFlexCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);	
		ck_projectDescription = new CKEditor(true, helper_ck.getCKEditorCustomization(150));
		ft_formFields.setWidget(1, 1, ck_projectDescription);
		hf_projectDescription.setName("projectDescription");
		ft_formFields.setWidget(1, 3, hf_projectDescription);
		ft_formFields.getFlexCellFormatter().setColSpan(1, 1, 3);
				
		// keywords
		ft_formFields.setText(2, 0, "Keywords");
		tb_keywords = new TextBox();
		tb_keywords.setName("keywords");
		tb_keywords.setWidth("100%");
		ft_formFields.setWidget(2, 1, tb_keywords);
		ft_formFields.getFlexCellFormatter().setColSpan(2, 1, 3);

		// partner name
		ft_formFields.setText(3, 0, "Partner");
		tb_partnerName = new TextBox();
		tb_partnerName.setName("partnerName");
		tb_partnerName.setWidth("100%");
		ft_formFields.setWidget(3, 1, tb_partnerName);
		ft_formFields.getFlexCellFormatter().setColSpan(3, 1, 3);
		
		// partner description (with small ckeditor)
		ft_formFields.setText(4, 0, "Partnerbeschreibung");
		ft_formFields.getFlexCellFormatter().setVerticalAlignment(4, 0, HasVerticalAlignment.ALIGN_TOP);
		ck_partnerDescription = new CKEditor(true, helper_ck.getCKEditorCustomization(80));
		ft_formFields.setWidget(4, 1, ck_partnerDescription);
		hf_partnerDescription.setName("partnerDescription");
		ft_formFields.setWidget(4, 3, hf_partnerDescription);
		ft_formFields.getFlexCellFormatter().setColSpan(4, 1, 3);

		// estimated begin
		ft_formFields.setText(5, 0, "vorauss. Projektbeginn");
		dp_estimatedBegin = new DatePicker();
		dp_estimatedBegin.setName("estimatedBegin");
		ft_formFields.setWidget(5, 1, dp_estimatedBegin);
		  	
		// label for contact persons
		ft_formFields.setText(6, 0, "Projektbetreuer");
		ft_formFields.getFlexCellFormatter().setVerticalAlignment(6, 0, HasVerticalAlignment.ALIGN_TOP);
		
		// team size
		ft_formFields.setText(7, 0, "Teamgröße");
		hp_teamSize = new HorizontalPanel();
		initializeTeamSizePanel();
		ft_formFields.setWidget(7, 1, hp_teamSize);

		// labels for project file and additional files
		ft_formFields.setText(8, 0, "Projektdatei");
		ft_formFields.setText(9, 0, "weitere Dateien");
		ft_formFields.getFlexCellFormatter().setVerticalAlignment(9, 0, HasVerticalAlignment.ALIGN_TOP);
		
		// hidden fields for flexible elements
		hf_highestContactPerson.setName("highestContactPerson");
		ft_formFields.setWidget(11, 0, hf_highestContactPerson);
		
		hf_highestAdditionalFile.setName("highestAdditionalFile");
		ft_formFields.setWidget(11, 1, hf_highestAdditionalFile);
		
		hf_currentProposalId.setName("projectIdIfExistent");
		ft_formFields.setWidget(11, 2, hf_currentProposalId);

	}
		
	private void addButtons(){		
		// add submit button
		btn_submit = new Button("Speichern");
		btn_submit.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				 hf_projectDescription.setValue(ck_projectDescription.getData());
				 hf_partnerDescription.setValue(ck_partnerDescription.getData());
				 formPanel.submit();
			}
		});

		FormHandler submitHandler = new FormHandler(){
			public void onSubmit(FormSubmitEvent event) {
			 if (tb_projectName.getText().trim().length() == 0) {
				 Window.alert("Der Projektname darf nicht leer sein.");
				 event.setCancelled(true);
			 	}
			 else if (checkNoDuplicatedFiles() == false) {
				 Window.alert("Die Projektnamen müssen eindeutig sein.");
				 event.setCancelled(true);
			 	}
			 else if (checkValidRangeTeamSize() == false){
				 Window.alert("Die Angaben der Teamgröße sind ungültig.");
				 event.setCancelled(true);
			 	}
			 else if (checkEMailAddresses() == false){
				 Window.alert("Es müssen E-Mail-Adressen der Betreuer angegeben werden.");
				 event.setCancelled(true);
			 	}			 
			}
			public void onSubmitComplete(FormSubmitCompleteEvent event) {							
				String response = event.getResults();
				if (response.contains("ID:")){								
					int startIndex = response.indexOf("ID:")+3;
					int endIndex = response.indexOf(":DI");
					int id = Integer.parseInt(response.substring(startIndex, endIndex));
					setVisible(false);
					mainPage.getDetailedViewProposal().renderProposal(id);
					mainPage.getDetailedViewProposal().setVisible(true);
				}
				else {
					label_infoMessage.setText("Beim Speichern ist ein Fehler aufgetreten. Bitte versuchen Sie es erneut.");
				}			
			}
		};
		
		formPanel.addFormHandler(submitHandler);
		
		// add pdf button
		btn_generatePdf = new Button("PDF erzeugen");
		btn_generatePdf.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				 //TODO generate PDF
			}
		});

		ft_formFields.setWidget(10, 1, btn_submit);
		ft_formFields.setWidget(10, 2, btn_generatePdf);

		ft_formFields.getFlexCellFormatter().setHorizontalAlignment(10, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		ft_formFields.getFlexCellFormatter().setHorizontalAlignment(10, 2, HasHorizontalAlignment.ALIGN_RIGHT);
	}
	
	// CHECK FUNCTIONS
	
	private boolean checkNoDuplicatedFiles() {
		//TODO checkForDuplicatedFiles
		return true;		
	}
	
	private boolean checkEMailAddresses(){
		return helper_cp.checkEMailAddresses();
	}
	
	private boolean checkValidRangeTeamSize(){
		String min = lb_minStud.getItemText(lb_minStud.getSelectedIndex());
		String max = lb_maxStud.getItemText(lb_maxStud.getSelectedIndex());		
		return Integer.parseInt(min) <= Integer.parseInt(max);
	}
	
	private HorizontalPanel initializeTeamSizePanel() {
	
		// list box for min stud
		lb_minStud = new ListBox();
		hp_teamSize.add(lb_minStud);
		lb_minStud.setName("minStud");
		for (int i=minMinStud; i<8; i++){
			lb_minStud.addItem(Integer.toString(i), Integer.toString(i));
		}
		lb_minStud.setItemSelected(1, true);

		Label until = new Label("bis");
		hp_teamSize.add(until);

		// list box for max stud
		lb_maxStud = new ListBox();
		hp_teamSize.add(lb_maxStud);
		lb_maxStud.setName("maxStud");
		for (int i=minMaxStud; i<11; i++){
			lb_maxStud.addItem(Integer.toString(i), Integer.toString(i));
		}
		lb_maxStud.setItemSelected(4, true);

		Label students = new Label("Studenten");
		hp_teamSize.add(students);

		return hp_teamSize;
	}

	
	AsyncCallback<ProjectProposal> callbackGetProposal = new AsyncCallback<ProjectProposal>(){		
		public void onFailure(Throwable caught) {
		}
		public void onSuccess(ProjectProposal result) {
			proposal = result;
			initializeFormFields();
		}
	};
	
	AsyncCallback<ArrayList<FileInfo>> callbackGetFileInfos = new AsyncCallback<ArrayList<FileInfo>>(){		
		public void onFailure(Throwable caught) {
		}
		public void onSuccess(ArrayList<FileInfo> result) {
			files = result;
			helper_fi.initializeFileFields(ft_formFields, files);
		}
	};
	
	/**
	 * Initializes form fields with values of proposal
	 */
	private void initializeFormFields(){	
		// project name and description
		tb_projectName.setValue(proposal.getProjectNameOr(""));
		ck_projectDescription.setData(proposal.getProjectDescriptionOr(""));
		
		//keywords, partner name, partner description
		tb_keywords.setValue(proposal.getKeywordsOr(""));
		tb_partnerName.setValue(proposal.getPartnerNameOr(""));
		ck_partnerDescription.setData(proposal.getPartnerDescriptionOr(""));

		// estimated begin
		dp_estimatedBegin.setValue(proposal.getEstimatedBeginOr(""));

		// contact persons
		if (proposal.getContactPersons() != null) {
			helper_cp.addFilledContactPersons(ft_formFields, proposal.getContactPersons());			
		}
		else {
			helper_cp.addEmptyContactPersons(ft_formFields);
		}
	
		// team size
		int minStud = proposal.getMinStudOr(4);
		int maxStud = proposal.getMaxStudOr(8);	
		lb_minStud.setItemSelected(minStud-minMinStud, true);
		lb_maxStud.setItemSelected(maxStud-minMaxStud, true);
		
		// files are rendered in extra method		
	}

	
	public int getHighestContactPerson() {
		return highestContactPerson;
	}

	public void setHighestContactPerson(int highestContactPerson) {
		this.highestContactPerson = highestContactPerson;
	}
	
	public int getHighestAdditionalFile() {
		return highestAdditionalFile;
	}

	public void setHighestAdditionalFile(int highestAdditionalFile) {
		this.highestAdditionalFile = highestAdditionalFile;
	}
}