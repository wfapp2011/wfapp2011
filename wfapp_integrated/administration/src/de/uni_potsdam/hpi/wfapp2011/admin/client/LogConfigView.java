package de.uni_potsdam.hpi.wfapp2011.admin.client;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

public class LogConfigView extends MySimplePopUp {
	
	private DateBox von = new DateBox();
	private DateBox bis = new DateBox();
	private Label error;
	private IntegerBox year;
	private RadioButton bachelor;
	private RadioButton sose; 
	
	public LogConfigView() {
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		
		error = new Label("Bitte treffen Sie eine korrekte Eingabe.");
		error.setStyleName("gwt-Error");
		verticalPanel.add(error);
		showError(false);
		
		HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
		verticalPanel.add(horizontalPanel_2);
		
		Label lblTyp = new Label("Typ");
		lblTyp.setStyleName("form-left");
		horizontalPanel_2.add(lblTyp);
		lblTyp.setWidth("65");
		
		bachelor = new RadioButton("type", "Bachelor");
		bachelor.setValue(true);
		
		RadioButton master = new RadioButton("type", "Master");
		horizontalPanel_2.add(bachelor);
		horizontalPanel_2.add(master);
		
		HorizontalPanel horizontalPanel_3 = new HorizontalPanel();
		verticalPanel.add(horizontalPanel_3);
		
		Label lblSemester = new Label("Semester");
		lblSemester.setStyleName("form-left");
		horizontalPanel_3.add(lblSemester);
		lblSemester.setWidth("65");
		
		sose = new RadioButton("semester", "Sommersemester");
		sose.setValue(true);
		
		RadioButton wise = new RadioButton("semester", "Wintersemester");
		horizontalPanel_3.add(sose);
		horizontalPanel_3.add(wise);
		
		HorizontalPanel horizontalPanel_4 = new HorizontalPanel();
		verticalPanel.add(horizontalPanel_4);
		
		Label lblJahr = new Label("Jahr");
		lblJahr.setStyleName("form-left");
		horizontalPanel_4.add(lblJahr);
		lblJahr.setWidth("65");
		
		year = new IntegerBox();
		horizontalPanel_4.add(year);
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel.add(horizontalPanel);
		
		Label lblVon = new Label("von");
		lblVon.setStyleName("form-left");
		horizontalPanel.add(lblVon);
		lblVon.setWidth("65");

		von.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("dd.MM.yyyy")));
		horizontalPanel.add(von);
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		verticalPanel.add(horizontalPanel_1);
		
		Label lblBis = new Label("bis");
		lblBis.setStyleName("form-left");
		horizontalPanel_1.add(lblBis);
		lblBis.setWidth("65");

		bis.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("dd.MM.yyyy")));
		horizontalPanel_1.add(bis);
		
		buttonbar = new HorizontalPanel();
		verticalPanel.add(buttonbar);
	}

	/**
	 * readout userinput
	 * @return Date[2]
	 */
	public Date[] getDate(){
		Date[] result = new Date[2];
		// readout dates from boxes
		result[0] = von.getValue();
		result[1] = bis.getValue();
		return result;
	}
	
	public String[] getConfig(){
		String[] result = new String[3];
		//readout input
		result[0] = bachelor.getValue()?"Ba":"Ma";
		result[1] = sose.getValue()?"SS":"WS";
		result[2] = (year.getValue() == null)?null:year.getValue().toString();
		return result;
	}
	
	/**
	 * toggles visibility of errorline
	 * @param bool : defines the visibility
	 */
	public void showError(boolean bool){
		error.setVisible(bool);
	}
	
	
}
