package de.uni_potsdam.hpi.wfapp2011.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class MainPanel extends Composite {
	
	private PopupPanel popup;
	private ListBox listbox = new ListBox();
	private ProjectEditPopUp content;
	private Button btnEdit;
	private NewProjectPopUp content2;
	private ArrayList<String> existingProjects = new ArrayList<String>();
	private HTML html_error = new HTML("<i>no existing projects</i>");
	private VerticalPanel verticalPanel = new VerticalPanel();
	private ConfigInterfaceDataExchangeAsync confInterface = GWT.create(ConfigInterfaceDataExchange.class);
	private Collection<Map<String,String>> configData;
	private String selectedName;

	public MainPanel() {
		
		initWidget(verticalPanel);
		
		Label lblSelectAnExisting = new Label("Select an existing project or create a new one:");
		verticalPanel.add(lblSelectAnExisting);
		
		// Load existingProjects data from server
		confInterface.getProjectList(new AsyncCallback<ArrayList<String>>() {
			public void onSuccess(ArrayList<String> result) {
				existingProjects = result;
				Collections.sort(existingProjects);
				refreshListbox();
			}
			
			public void onFailure(Throwable caught) {
				
			}
		});
		
		// Create listbox with content
		verticalPanel.add(listbox);
		verticalPanel.add(html_error);
		refreshListbox();

		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel.add(horizontalPanel);
		
		btnEdit = new Button("Edit Project");
		btnEdit.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				selectedName = listbox.getItemText(listbox.getSelectedIndex());				
				
				// Load configuration from DB
				confInterface.getConfig(selectedName.split(" ")[0], selectedName.split(" ")[1], selectedName.split(" ")[3], new AsyncCallback<Collection<Map<String,String>>>() {
					public void onFailure(Throwable caught) {
						
					}

					public void onSuccess(Collection<Map<String, String>> result) {
						configData = result;
						popup = new PopupPanel();
						content = new ProjectEditPopUp(selectedName, configData);
						
						//SaveButton
						Button save = new Button("Save");
					    save.addClickHandler(new ClickHandler(){
					        public void onClick(ClickEvent event) {
						            Map<String,String> temp = content.getContent();
						            confInterface.saveConfig(selectedName.split(" ")[0], selectedName.split(" ")[1], selectedName.split(" ")[3], temp, new AsyncCallback<Void>() {
										public void onFailure(Throwable caught) {
											
										}

										public void onSuccess(Void result) {
											System.out.println("Gespeichert!");
										}
									});
						            popup.hide();
						    }
					    });
						content.getButtonBar().add(save);				
						
						//CloseButton
						Button close = new Button("Close");
					    close.addClickHandler(new ClickHandler(){
					        public void onClick(ClickEvent event) {
						            popup.hide();
						    }
					    });
						content.getButtonBar().add(close);
						
						popup.add(content);
						popup.center();
						
						// System.out.println("geladen!");
					}
				});
				
				// System.out.println("Lade Daten");
			}
		});
		btnEdit.setText("Edit Project");
		btnEdit.setEnabled(false);
		horizontalPanel.add(btnEdit);
		
		Button btnNewProject = new Button("New Project");
		btnNewProject.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				popup = new PopupPanel();
				content2 = new NewProjectPopUp();
				
				//CreateButton
				Button create = new Button("Create");
				create.addClickHandler(new ClickHandler(){
			        public void onClick(ClickEvent event) {
			        		ArrayList<String> temp = content2.getContent();
			        		
			        		confInterface.addProject(temp.get(1), temp.get(0), new AsyncCallback<Void>() {
			        			public void onFailure(Throwable caught) {
			        				System.out.println("Übermittlungsfehler");
			        			}
								public void onSuccess(Void result) {
									System.out.println("Erfolgreich gespeichert");
								}
			        		});
			        		listbox.setVisible(true);
			        		html_error.setVisible(false);
			        		listbox.addItem(temp.get(1).split(" ")[1]+" "+temp.get(1).split(" ")[0]+" - "+temp.get(0));
			        		btnEdit.setEnabled(true);
			        		
				            popup.hide();
				    }
			    });
				content2.getButtonBar().add(create);
				
				//CloseButton
				Button close = new Button("Close");
			    close.addClickHandler(new ClickHandler(){
			        public void onClick(ClickEvent event) {
				            popup.hide();
				    }
			    });
				content2.getButtonBar().add(close);
				
				popup.add(content2);
				popup.center();
			}
		});
		horizontalPanel.add(btnNewProject);
		
	}
	
	private void refreshListbox(){
		if (existingProjects.size()<1){
			html_error.setVisible(true);
			listbox.setVisible(false);
			if (btnEdit != null) btnEdit.setEnabled(false);
			
		}			
		else {
			listbox.clear();
			for (String i : existingProjects){
				listbox.addItem(i);
			listbox.setVisible(true);
			html_error.setVisible(false);
			if (btnEdit != null) btnEdit.setEnabled(true);
			}
		}
	}
}