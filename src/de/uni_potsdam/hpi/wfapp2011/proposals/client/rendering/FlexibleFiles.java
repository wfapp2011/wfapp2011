package de.uni_potsdam.hpi.wfapp2011.proposals.client.rendering;
/**
 * FlexibleFiles is a helper class for CreateOrEditProposal.
 * It manages the flexible addition and removal of additional files.
 * 	 
 * @author Katrin Honauer, Josefine Harzmann
 */
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Image;

import de.uni_potsdam.hpi.wfapp2011.proposals.client.data.FileInfo;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.handler.DeleteButtonClHa;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.handler.DeleteButtonClHa_ProjectFile;

public class FlexibleFiles extends Composite {

	private static String URL_TRASH = "/images/proposals/trash.gif";	
	private FlexTable additionalFiles;	
	private FlexTable additionalFilesList;	
	private Button btn_addAdditionalFile;	
	private CreateOrEditProposal parentPage;
	
	public FlexibleFiles(CreateOrEditProposal parent) {
		parentPage = parent;
	}
	
	/*
	 * There is one specific project file for each proposal.
	 * This file explains the project and will be published.
	 * 
	 * Additional files can be added during proposal collection.
	 * They will not be published. 
	*/
	
	// ADDITIONAL FILES
	/**
	 * Creates a new table for flexible additional files.
	 * @param ft_formFields Where to add the new table
	 */
	public void addEmptyAdditionalFiles(FlexTable ft_formFields){	
		additionalFiles = createEmptyAdditionalFilesTable();
		// initialize with empty file upload
		addNewAdditionalFile();
		ft_formFields.setWidget(9, 1, additionalFiles);		
	}
	
	/**
	 * Creates a new table and fills it with the given additional files.
	 * @param ft_formFields Where to add the new table
	 * @param listAdditionalFiles Files to be added
	 */
	public void addFilledAdditionalFiles(FlexTable ft_formFields, List<FileInfo> listAdditionalFiles){		
		additionalFiles = createFilledAdditionalFilesTable(listAdditionalFiles);
		ft_formFields.setWidget(9, 1, additionalFiles);
	}
	
	/**
	 * Creates an empty table for additional files
	 * and initializes it with one empty file upload.
	 */
	private FlexTable createEmptyAdditionalFilesTable(){		
		additionalFilesList = new FlexTable();
		
		// add button
		btn_addAdditionalFile = new Button("weitere Datei hinzufügen"); 
		additionalFilesList.setWidget(1, 0, btn_addAdditionalFile);
		additionalFilesList.getFlexCellFormatter().setColSpan(1, 0, 3);
		additionalFilesList.getFlexCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);		
		ClickHandler handler_addAdditionalFile = new ClickHandler(){
			public void onClick(ClickEvent event) {
				addNewAdditionalFile();
			}
		};
		btn_addAdditionalFile.addClickHandler(handler_addAdditionalFile);
		return additionalFilesList;
	}
	
	/**
	 * Creates filled table with given additional files
	 * @param listAdditionalFiles Files to be displayed
	 */
	private FlexTable createFilledAdditionalFilesTable(List<FileInfo> listAdditionalFiles){	
		additionalFilesList = new FlexTable();
		
		for (int i=0; i<listAdditionalFiles.size(); i++){
			Anchor a_additionalFile = createFileAnchor(listAdditionalFiles.get(i));
			Hidden h_additionalFile = createHiddenField(listAdditionalFiles.get(i), i);				
			additionalFilesList.setWidget(i, 0, a_additionalFile);
			additionalFilesList.setWidget(i, 2, h_additionalFile);
			addTrashButton(i, 1);
		}
		
		btn_addAdditionalFile = new Button("weitere Datei hinzuf�gen"); 
		additionalFilesList.setWidget(3, listAdditionalFiles.size()+1, btn_addAdditionalFile);		
		ClickHandler handler_addAdditionalFile = new ClickHandler(){
			public void onClick(ClickEvent event) {
				addNewAdditionalFile();
			}
		};
		btn_addAdditionalFile.addClickHandler(handler_addAdditionalFile);
		
		updateFilesCounter(listAdditionalFiles.size());
		return additionalFilesList;
	}
	
	/**
	 * Add an empty file upload for additional files.
	 */	
	private void addNewAdditionalFile(){	
		int i = parentPage.getHighestAdditionalFile() + 1;	
		int rowCount = additionalFilesList.getRowCount();
		
		// add empty fileupload
		FileUpload fu_additionalFile = new FileUpload();
		fu_additionalFile.setName("additionalFile_"+i);
		additionalFilesList.setWidget(rowCount, 0, fu_additionalFile);
		
		// add trash
		addTrashButton(rowCount, 1);
		
		// move add-button downwards
		updatePositionButton(rowCount);

		// update counter
		updateFilesCounter(i);
	}

	
	/**
	 * Parse files and set up project file as well as additional files.
	 * @param ft_formFields Where to render
	 * @param files FileInfos to parse
	 */
	public void initializeFileFields(FlexTable ft_formFields, List<FileInfo> files){
		
		boolean noProjectFile = true;
		boolean noAdditionalFiles = true;
		FileInfo projectFile = null;
		List<FileInfo> additionalFiles = new ArrayList<FileInfo>();
	
		for (FileInfo fileinfo: files){
			if (fileinfo.isProjectFile()){
				noProjectFile = false;
				projectFile = fileinfo;
			}
			else {
				noAdditionalFiles = false;
				additionalFiles.add(fileinfo);
			}
		}
		
		if (noProjectFile){
			addEmptyProjectFile(ft_formFields);
		}
		else {
			addFilledProjectFile(ft_formFields, projectFile);
		}
		
		if (noAdditionalFiles){
			addEmptyAdditionalFiles(ft_formFields);			
		}
		else {
			addFilledAdditionalFiles(ft_formFields, additionalFiles);
		}
	}
	
	// SPECIFIC PROJECTFILE	
	public void addEmptyProjectFile(FlexTable ft_formFields){			
		FileUpload fu_projectFile = new FileUpload();
		fu_projectFile.setName("projectFile");
		ft_formFields.setWidget(8, 1, fu_projectFile);
	}
		
	private void addFilledProjectFile(FlexTable ft_formFields, FileInfo fileinfo){
		FlexTable projectFileTable = new FlexTable();
		
		// anchor to open file
		Anchor a_projectFile = createFileAnchor(fileinfo);
		
		// trash
		Image img_deleteProjectFile = new Image(URL_TRASH);
		DeleteButtonClHa_ProjectFile handler_deleteProjectFile = new DeleteButtonClHa_ProjectFile(this, ft_formFields, 8, 1);		
		img_deleteProjectFile.addClickHandler(handler_deleteProjectFile);
		
		// hidden field
		Hidden hf_projectFile = new Hidden();
		hf_projectFile.setName("givenPrFileFilenameFtp");
		hf_projectFile.setValue(fileinfo.getFilenameFtp());
		
		// placement
		projectFileTable.setWidget(0, 0, a_projectFile);
		projectFileTable.setWidget(0, 1, img_deleteProjectFile);
		projectFileTable.setWidget(0, 2, hf_projectFile);
		ft_formFields.setWidget(8, 1, projectFileTable);
	}

	//HELPER FUNCTIONS
	private Anchor createFileAnchor(FileInfo fileinfo){
		String str = GWT.getModuleBaseURL() + "downloadFile"
						+"?destFolderFtp="+fileinfo.getDestFolderFtp()
						+"&fileNameFtp="+fileinfo.getFilenameFtp()
						+"&fileName="+fileinfo.getFilename();	
		Anchor anchor = new Anchor(fileinfo.getFilename(), str);
		return anchor;
	}
	
	private Hidden createHiddenField(FileInfo fileinfo, int i){	
		Hidden hf_additionalFile = new Hidden();
		hf_additionalFile.setName("givenAddFileFilenameFtp_"+i);
		hf_additionalFile.setValue(fileinfo.getFilenameFtp());
		return hf_additionalFile;
	}
	
	private void addTrashButton(int row, int column){
		Image img_deleteAdditionalFile = new Image(URL_TRASH);
		additionalFilesList.setWidget(row, column, img_deleteAdditionalFile);				
		DeleteButtonClHa handler_deleteAdditionalFile = new DeleteButtonClHa(additionalFilesList, row);		
		img_deleteAdditionalFile.addClickHandler(handler_deleteAdditionalFile);
	}
	
	private void updateFilesCounter(int i){
		parentPage.setHighestAdditionalFile(i);
		parentPage.hf_highestAdditionalFile.setValue(Integer.toString(parentPage.getHighestAdditionalFile()));
	}

	private void updatePositionButton(int rowCount){	
		additionalFilesList.remove(btn_addAdditionalFile);
		additionalFilesList.setWidget(rowCount+1, 0, btn_addAdditionalFile);
		additionalFilesList.getFlexCellFormatter().setColSpan(rowCount+1, 0, 3);
		additionalFilesList.getFlexCellFormatter().setHorizontalAlignment(rowCount+1, 0, HasHorizontalAlignment.ALIGN_RIGHT);	
	}
	
}