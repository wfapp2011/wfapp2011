package de.uni_potsdam.hpi.wfapp2011.admin.client;

//# Imports #
import java.util.ArrayList;
import java.util.Date;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * popup providing the configurator different abilities, like choosing a year and the type of a project
 */
public class NewProjectPopUp extends MySimplePopUp {
	
	private ListBox comboBox;
	private RadioButton bachelor;

	public NewProjectPopUp() {
		
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		
		Label lblSelectProjectType = new Label("Select project type:");
		verticalPanel.add(lblSelectProjectType);
		
		bachelor = new RadioButton("type", "Bachelor");
		bachelor.setValue(true);
		verticalPanel.add(bachelor);
		
		RadioButton master = new RadioButton("type", "Master");
		verticalPanel.add(master);
		
		Label lblSelectSemester = new Label("Select semester:");
		verticalPanel.add(lblSelectSemester);
		
		comboBox = new ListBox();
		for (int i=0; i<5; i++){
			Date date = new Date(); // today
			@SuppressWarnings("deprecation")
			int year = date.getYear()+1900+i;
			for (int j=0; j<2; j++){
				comboBox.addItem(((j ==0)?"SS":"WS")+" "+String.valueOf(year));
			}
		}
		verticalPanel.add(comboBox);
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel.add(horizontalPanel);
		
		buttonbar = new HorizontalPanel();
		verticalPanel.add(buttonbar);
	}
	
	public ArrayList<String> getContent(){
		ArrayList<String> returnValues = new ArrayList<String>();
		
		returnValues.add(bachelor.getValue()?"Ba":"Ma");
		returnValues.add(comboBox.getValue(comboBox.getSelectedIndex()));
		
		return returnValues;
	}

}
