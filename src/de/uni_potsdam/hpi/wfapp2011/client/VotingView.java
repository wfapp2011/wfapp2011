package de.uni_potsdam.hpi.wfapp2011.client;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.SimpleCheckBox;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.CheckBox;

public class VotingView extends Composite {
	private static int mainFrameSize = 400;
	private VerticalPanel mainPanel;
	private HTMLPanel panelTitle;
	private HorizontalPanel horizontalPanel;
	private ScrollPanel scrollTopicOverview;
	private ScrollPanel scrollMyTopics;
	private VerticalPanel verticalTopicOverview;
	private VerticalPanel verticalMyTopics;
	private ArrayList<Topic> Topics;
	private VerticalPanel buttonPanel;
	private Button btnRemove;
	private Button btnAdds;
	private Button saveButton;
	private Label lblStatus;
	private final VotingDatabaseServiceAsync databaseService = GWT.create(VotingDatabaseService.class);
	private FlexTable flexPriority;
	private CheckBox AgbCheckBox;
	private int maxVotes;


	public VotingView(ArrayList<Topic> TopicList) {		
		Topics = TopicList;
		mainPanel = new VerticalPanel();
		initWidget(mainPanel);
		loadMaxVotes();
		
		Window.addResizeHandler(new ResizeHandler(){
			  public void onResize(final ResizeEvent event) {
				  mainPanel.setCellHeight(horizontalPanel, (Window.getClientHeight()-mainFrameSize)+"px");
				  scrollTopicOverview.setSize("100%", (Window.getClientHeight()-mainFrameSize)+"px");
				  scrollMyTopics.setHeight((Window.getClientHeight()-mainFrameSize)+"px");
				  flexPriority.setSize("18px", (Window.getClientHeight()-mainFrameSize)+"px");
			  }
		});
							
		panelTitle = new HTMLPanel("<h1>Projekt-Wahl:</h1>");
		mainPanel.add(panelTitle);
		mainPanel.setCellHeight(panelTitle, "41px");
		
		lblStatus = new Label("");
		mainPanel.add(lblStatus);
		mainPanel.setCellHeight(lblStatus, "18");
		
		horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.setSpacing(10);
		mainPanel.add(horizontalPanel);
		horizontalPanel.setWidth("100%");
		mainPanel.setCellHeight(horizontalPanel, (Window.getClientHeight()-mainFrameSize)+"px");
		
		scrollTopicOverview = new ScrollPanel();
		scrollTopicOverview.setStyleName("topicList");
		horizontalPanel.add(scrollTopicOverview);
		scrollTopicOverview.setSize("95%", (Window.getClientHeight()-mainFrameSize)+"px");
		horizontalPanel.setCellWidth(scrollTopicOverview, "40%");
		
		verticalTopicOverview = new VerticalPanel();
		scrollTopicOverview.setWidget(verticalTopicOverview);
		verticalTopicOverview.setSize("100%", "100%");
		
		buttonPanel = new VerticalPanel();
		horizontalPanel.add(buttonPanel);
		buttonPanel.setHeight("48px");
		
		btnAdds = new Button(">>");
		btnAdds.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addTopic();
			}
		});
		buttonPanel.add(btnAdds);
		buttonPanel.setCellHeight(btnAdds, "24px");
		
		btnRemove = new Button("<<");
		btnRemove.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				removeTopic();
			}
		});
		buttonPanel.add(btnRemove);
		buttonPanel.setCellHeight(btnRemove, "24px");
		
		flexPriority = new FlexTable();
		horizontalPanel.add(flexPriority);
		horizontalPanel.setCellVerticalAlignment(flexPriority, HasVerticalAlignment.ALIGN_TOP);
		horizontalPanel.setCellWidth(flexPriority, "18px");
		flexPriority.setSize("18px", (Window.getClientHeight()-mainFrameSize)+"px");
		
		HTMLTable.CellFormatter formatter = flexPriority.getCellFormatter();
			
		for (int i = 1; i<6; i++) {
			flexPriority.setText(i-1, 0, i+".");
			formatter.setHeight(i-1, 0, "40px");
		}
		
		flexPriority.setVisible(false);
		
		scrollMyTopics = new ScrollPanel();
		horizontalPanel.add(scrollMyTopics);
		scrollMyTopics.setSize("98%",(Window.getClientHeight()-mainFrameSize)+"px");
		scrollMyTopics.setStyleName("topicList");
		horizontalPanel.setCellWidth(scrollMyTopics, "40%");
		
		verticalMyTopics = new VerticalPanel();
		scrollMyTopics.setWidget(verticalMyTopics);
		verticalMyTopics.setSize("100%", "100%");
		
		AgbCheckBox = new CheckBox("New check box");
		mainPanel.add(AgbCheckBox);
		AgbCheckBox.setHTML("Hiermit best\u00E4tige ich, dass ich bereits 90 LP oder mehr erworben habe.");
		
		saveButton = new Button("Speichern");
		saveButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				boolean couldVote = AgbCheckBox.getValue();
				if(couldVote) {
					saveVote();
				} else {
					lblStatus.setText("Um w\u00E4hlen zu k\u00F6nnen, m\u00FCssen Sie die Vorrausetzung best\u00E4tigen.");
				}
			}
		});
		mainPanel.add(saveButton);
				
		createTopicOverview();
	}

	private int loadMaxVotes() {
		databaseService.numberOfVotes("Ba", "SS", 2011, new AsyncCallback<Integer>(){

			@Override
			public void onFailure(Throwable caught) {
				lblStatus.setText("Fehler beim Laden der Configuration");
			}

			@Override
			public void onSuccess(Integer result) {
				maxVotes = result;
			}
			
		});
		return 0;
	}

	private void createTopicOverview() {
		for (Topic i: Topics) {
			VoteItem temp = new VoteItem(i);
			temp.addUpClickHandler(createUpClickHandler(temp));
			temp.addDownClickHandler(createDownClickHandler(temp));
			temp.setOrderable(false);
			verticalTopicOverview.add(temp);
		}
	}
	
	private void addTopic() {
		Iterator<Widget> allTopics = verticalTopicOverview.iterator();
		ArrayList<VoteItem> ItemsToMove = new ArrayList<VoteItem>();
		
		VoteItem i = (VoteItem)allTopics.next();
		while(allTopics.hasNext()) {
			if (i.isChecked())
				ItemsToMove.add(i);
			
			i = (VoteItem) allTopics.next();
		}
		if (i.isChecked())
			ItemsToMove.add(i);
		
		for (VoteItem j: ItemsToMove) {
			verticalMyTopics.add(j);
			j.setOrderable(true);
			j.setChecked(false);
		}

		
	}
	
	private void removeTopic() {
		Iterator<Widget> myTopics = verticalMyTopics.iterator();
		ArrayList<VoteItem> ItemsToMove = new ArrayList<VoteItem>();
		
		VoteItem i = (VoteItem)myTopics.next();
		while (myTopics.hasNext())  {
			if (i.isChecked())
				ItemsToMove.add(i);
			
			i = (VoteItem) myTopics.next();
		}
		if (i.isChecked())
			ItemsToMove.add(i);
		
		for (VoteItem j: ItemsToMove) {
			try {
				verticalTopicOverview.insert(j, Topics.indexOf(j.getTopic()));
			} catch (Exception e) {
				verticalTopicOverview.insert(j, verticalTopicOverview.getWidgetCount());
			}
			j.setOrderable(false);
			j.setChecked(false);
		}

		
	}
	
	private ClickHandler createUpClickHandler(final VoteItem Item)
	{
		return new ClickHandler() {
			public void onClick(ClickEvent event) {
				VerticalPanel parent = ((VerticalPanel)Item.getParent());
				parent.insert(Item, parent.getWidgetIndex(Item)-1);
			}
		};
	}
	
	private ClickHandler createDownClickHandler(final VoteItem Item)
	{
		return new ClickHandler() {
			public void onClick(ClickEvent event) {
				VerticalPanel parent = ((VerticalPanel)Item.getParent());
				parent.insert(Item, parent.getWidgetIndex(Item)+2);
			}
		};
	}
	
	public ArrayList<Topic> getTopics() {
		return Topics;
	}

	public void setTopics(ArrayList<Topic> topics) {
		Topics = topics;
	}
	
	public void refresh() {
		verticalTopicOverview.clear();
		createTopicOverview();
	}
	
	private void saveVote() {
		if(verticalMyTopics.getWidgetCount()<maxVotes){
			lblStatus.setText("Sie haben zu wenige Stimmen abgegeben.");
		} else if(verticalMyTopics.getWidgetCount()>maxVotes) {
			lblStatus.setText("Sie haben zu viele Stimmen abgegeben.");
		} else {
			PasswordConfirmationPopUp pwPopUp = new PasswordConfirmationPopUp(new AsyncCallback<Boolean>() {
				@Override
				public void onFailure(Throwable caught) {
					lblStatus.setText("Fehler bei der \u00DCbertragung.");
					System.out.print(caught.getMessage());
				}

				@Override
				public void onSuccess(Boolean result) {
					if (!result) {
						lblStatus.setText("Passwort falsch.");
					} else {
						lblStatus.setText("Stimmen werden gespeichert...");
						ArrayList<Vote> votes = new ArrayList<Vote>();
						Iterator<Widget> myTopics = verticalMyTopics.iterator();
						VoteItem i = (VoteItem)myTopics.next();
						int prio=1;
						while(myTopics.hasNext()) {
							votes.add(new Vote(42, prio, i.getTopic().getProjectID(), i.getTopic().getName()));
							i = (VoteItem) myTopics.next();
							prio++;
						}
						votes.add(new Vote(42, prio, i.getTopic().getProjectID(),i.getTopic().getName()));
						databaseService.saveVotes("Ba", "SS", 2011, votes, new AsyncCallback<Void>(){
	
							@Override
							public void onFailure(Throwable caught) {
								lblStatus.setText("Fehler bei der \u00DCbertragung.");
								System.out.print(caught.getMessage());
							}
	
							@Override
							public void onSuccess(Void result) {
								lblStatus.setText("Erfolgreich gespeichert.");
							}
							
						});
					}
				}
			});
			pwPopUp.show();
			pwPopUp.center();
		}	
	}
}
