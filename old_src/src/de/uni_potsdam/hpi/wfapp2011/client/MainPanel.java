package de.uni_potsdam.hpi.wfapp2011.client;

import java.util.*;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class MainPanel extends Composite {
	
	private Boolean debug = true;
	
	private ArrayList<String> existingProjects = new ArrayList<String>();
	private ConfigInterfaceDataExchangeAsync confInterface = GWT.create(ConfigInterfaceDataExchange.class);
	
	private HTML htmlNoExisitingProjects = new HTML("<i> no exisiting projects </i>", true);
	private VerticalPanel verticalProjectPanel;
	
	private PopupPanel popup;
	private NewProjectPopUp content;
	
	private void toggleVisibility(Boolean bool){
		htmlNoExisitingProjects.setVisible(!bool);
		verticalProjectPanel.setVisible(bool);
	}
	
	private void refreshContent(){
		if (existingProjects.size()<1){
			toggleVisibility(false);
		}			
		else {
			verticalProjectPanel.clear();
			
			for (String i : existingProjects){
				if(debug) System.out.println(i);
				verticalProjectPanel.add(createNewProjectEntry(i));
			}
			
			toggleVisibility(true);
		}
	}

	private HorizontalPanel createNewProjectEntry(String project){
	
		HorizontalPanel horizontalPanel = new HorizontalPanel();
			//Name
		Label lblproject = new Label(project);
		horizontalPanel.add(lblproject);
		horizontalPanel.setCellWidth(lblproject, "100");
			// EditButton
		Button edit = createEditButton(project);
		horizontalPanel.add(edit);
		horizontalPanel.setCellWidth(edit, "80");
			// DeleteButton
		Button delete = createDeleteButton(project);
		horizontalPanel.add(delete);
		horizontalPanel.setCellWidth(delete, "65");
			// StartButton
		Button start = createStartButton(project);
		horizontalPanel.add(start);
		
		return horizontalPanel;
	}
	
	private void createNewProjectPopUp(){
		popup = new PopupPanel();
		content = new NewProjectPopUp();
		
		//CreateButton
		Button create = new Button("Create");
		create.addClickHandler(new ClickHandler(){
	        public void onClick(ClickEvent event) {
	        		ArrayList<String> temp = content.getContent();
	        		
	        		confInterface.addProject(temp.get(1), temp.get(0), new AsyncCallback<Void>() {
	        			public void onFailure(Throwable caught) {
	        				if (debug) System.out.println("Übermittlungsfehler");
							if (debug) databaseError(caught);
	        			}
						public void onSuccess(Void result) {
							if (debug) System.out.println("Erfolgreich gespeichert");
							loadDataFromDb();
			        		refreshContent();
						}
	        		});
		            popup.hide();
		    }
	    });
		content.getButtonBar().add(create);
		
		//CloseButton
		content.getButtonBar().add(createCloseButton());
		
		popup.add(content);
		popup.center();
	}
	
	private Button createCloseButton() {
		//CloseButton
		Button close = new Button("Close");
	    close.addClickHandler(new ClickHandler(){
	        public void onClick(ClickEvent event) {
		            popup.hide();
		    }
	    });
		return close;
	}
	
	private Button createSaveButton(final String project, final ProjectEditPopUp content) {
		//SaveButton
		Button save = new Button("Save");
	    save.addClickHandler(new ClickHandler(){
	        public void onClick(ClickEvent event) {
		            Map<String,String> temp = content.getContent();
		            if (temp==null){
		            	Window.alert("Kein Datum darf vorverlegt werden.");
		            }else{
		            	confInterface.saveConfig(project.split(" ")[0], project.split(" ")[1], project.split(" ")[3], temp, new AsyncCallback<Void>() {
							public void onFailure(Throwable caught) {
								databaseError(caught);
							}

							public void onSuccess(Void result) {
								if (debug) System.out.println("Gespeichert!");
							}
						});
			            popup.hide();
		            }
		    }
	    });
		return save;
	}
	
	private Button createEditButton(final String project){
		Button edit = new Button("Editieren");
		edit.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {	
				
				// Load configuration from DB
				confInterface.getConfig(project.split(" ")[0], project.split(" ")[1], project.split(" ")[3], new AsyncCallback<Collection<Map<String,String>>>() {
					public void onFailure(Throwable caught) {
						databaseError(caught);
					}

					public void onSuccess(Collection<Map<String, String>> result) {
						Collection<Map<String, String>> configData = result;
						popup = new PopupPanel();
						final ProjectEditPopUp content = new ProjectEditPopUp(project, configData);
						
						Button save = createSaveButton(project, content);
						content.getButtonBar().add(save);				
						
						Button close = createCloseButton();
						content.getButtonBar().add(close);
						
						popup.add(content);
						popup.center();
						
						// System.out.println("geladen!");
					}
				});
				
				// System.out.println("Lade Daten");
			}
		});
		
		return edit;
	}

	private Button createDeleteButton(final String project){
		Button delete = new Button("L\u00F6schen");
		delete.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {	
				if (Window.confirm("Wollen Sie das Projekt wirklich löschen?"))
					// Deleting project from DB
					confInterface.deleteProject(project.split(" ")[0], project.split(" ")[1], project.split(" ")[3], new AsyncCallback<Void>() {
						public void onFailure(Throwable caught) {
							databaseError(caught);
						}

						public void onSuccess(Void result) {
							loadDataFromDb();
							refreshContent();
							Window.alert("Projekt gelöscht!");
						}
				
					});
			}
		});
		
		return delete;
	}

	private Button createStartButton(final String project){
		Button start = new Button("Starten");
		start.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {	
				if (Window.confirm("Wollen Sie das Projekt wirklich starten?"))
					// Deleting project from DB
					confInterface.startProject(project.split(" ")[0], project.split(" ")[1], project.split(" ")[3], new AsyncCallback<Void>() {
						public void onFailure(Throwable caught) {
							databaseError(caught);
						}

						public void onSuccess(Void result) {
							Window.alert("Projekt gestartet!");
							loadDataFromDb();
							refreshContent();
						}
				
					});
			}
		});
		
		return start;
	}
	
	private void loadDataFromDb(){
		// Load existingProjects data from server
		confInterface.getProjectList(new AsyncCallback<ArrayList<String>>() {
			public void onSuccess(ArrayList<String> result) {
				existingProjects = result;
				Collections.sort(existingProjects);
				refreshContent();
			}
			
			public void onFailure(Throwable caught) {
				databaseError(caught);
			}
		});
	}
	
	private void databaseError(Throwable caught){
		Window.alert("Es konnte keine Verbindung zur Datenbank aufgebaut werden.\n" +
		 "Bitte wenden Sie sich an den Support.\n\n" +
		 "Fehlercode:\n"+caught);
	}
	
	public MainPanel() {
		
		loadDataFromDb();
		
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		
		// headline
		Label lblExistingProjects = new Label("Aktuelle Projekte:");
		verticalPanel.add(lblExistingProjects);
		
		// html-error
		verticalPanel.add(htmlNoExisitingProjects);

		// Projectentries
		verticalProjectPanel = new VerticalPanel();
		verticalPanel.add(verticalProjectPanel);
		
		// Create new project
		Button btnNeuesProjektErstellen = new Button("Neues Projekt erstellen");
		btnNeuesProjektErstellen.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				createNewProjectPopUp();
			}
		});
		verticalPanel.add(btnNeuesProjektErstellen);
	}

}
