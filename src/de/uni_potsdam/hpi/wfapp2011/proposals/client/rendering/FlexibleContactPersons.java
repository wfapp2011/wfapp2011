package de.uni_potsdam.hpi.wfapp2011.proposals.client.rendering;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBox;

import de.uni_potsdam.hpi.wfapp2011.proposals.client.data.Person;
import de.uni_potsdam.hpi.wfapp2011.proposals.client.handler.DeleteButtonClHa;
/**
 * FlexibleContactPersons is a helper class for CreateOrEditProposal.
 * It manages the flexible addition and removal of contact persons.
 * 	 
 * @author Katrin Honauer, Josefine Harzmann
 */
public class FlexibleContactPersons extends Composite {

	private static String URL_TRASH = "/images/proposals/trash.gif";	
	private FlexTable contactPersons;
	private FlexTable personList;
	private Button btn_addContactPerson;	
	private CreateOrEditProposal parentPage;
	
	public FlexibleContactPersons(CreateOrEditProposal parent) {
		parentPage = parent;
	}

	/**
	 * Creates a new table for flexible contact persons.
	 * @param ft_formFields where to add the new table
	 */
	public void addEmptyContactPersons(FlexTable ft_formFields){		
		contactPersons = createEmptyContactPersonsTable();
		
		// initialize with empty contact person
		addNewContactPerson();
		
		contactPersons.setWidth("100%");
		ft_formFields.setWidget(6, 1, contactPersons);
		ft_formFields.getFlexCellFormatter().setColSpan(6, 1, 3);
	}
	
	/**
	 * Creates a new table and fills it with the given contact persons.
	 * @param ft_formFields where to add the new table
	 * @param persons 
	 */
	public void addFilledContactPersons(FlexTable ft_formFields, List<Person> persons){		
		contactPersons = createFilledContactPersonsTable(persons);
		contactPersons.setWidth("100%");
		ft_formFields.setWidget(6, 1, contactPersons);
		ft_formFields.getFlexCellFormatter().setColSpan(6, 1, 3);
	}

	/**
	 * Creates an empty table for contact persons
	 * and initializes it with one empty person.
	 */
	private FlexTable createEmptyContactPersonsTable(){		
		personList = new FlexTable();
		
		// add info
		HTMLPanel contactPersons_info = new HTMLPanel("Name, E-Mail-Adresse*");
		contactPersons_info.setStyleName("italic");
		personList.setWidget(0, 0, contactPersons_info);

		// add button
		btn_addContactPerson = new Button("weitere Person hinzufügen"); 
		personList.setWidget(1, 0, btn_addContactPerson);
		personList.getFlexCellFormatter().setColSpan(1, 0, 3);
		personList.getFlexCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);		
		ClickHandler handler_addContactPerson = new ClickHandler(){
			public void onClick(ClickEvent event) {
				addNewContactPerson();
			}
		};
		btn_addContactPerson.addClickHandler(handler_addContactPerson);
		return personList;
	}
	
	/**
	 * Creates filled table with given contact persons.
	 */
	private FlexTable createFilledContactPersonsTable(List<Person> persons){			
		personList = createEmptyContactPersonsTable();	
		for (Person person: persons){
			addFilledContactPerson(person);
		}
		return personList;
	}

	private void addNewContactPerson(){		
		int i = parentPage.getHighestContactPerson() + 1;
		int rowCount = personList.getRowCount();
			
		// add empty fields for name and email
		createEmptyFieldName(rowCount, i);
		createEmptyFieldEmail(rowCount, i);
		
		// add trash
		addTrashButton(rowCount);

		// move add-button downwards
		updatePositionButton(rowCount);
		
		// update counter
		updatePersonCounter(i);
	}
	
	private void addFilledContactPerson(Person person){				
		int i = parentPage.getHighestContactPerson() + 1;
		int rowCount = personList.getRowCount();

		// add empty fields for name and email
		TextBox contactPerson_name = createEmptyFieldName(rowCount, i);
		TextBox contactPerson_email = createEmptyFieldEmail(rowCount, i);
		
		// fill fields with values
		contactPerson_name.setText(person.getName());	
		contactPerson_email.setText(person.getEmail());

		// add trash
		addTrashButton(rowCount);

		// move add-button downwards
		updatePositionButton(rowCount);
		
		// update counter
		updatePersonCounter(i);
	}
	
	/**
	 * Checks, if every contact person has a mail-address.
	 * This address is needed to identify the contact person.
	 * @return boolean (true, if every contact person has mail-address)
	 */
	public boolean checkEMailAddresses(){
		//TODO check mail addresses
		return true;
	}

	//HELPER FUNCTIONS
	private void addTrashButton(int rowCount){
		Image img_deleteContactPerson = new Image(URL_TRASH);
		personList.setWidget(rowCount, 2, img_deleteContactPerson);				
		DeleteButtonClHa handler_deleteContactPerson = new DeleteButtonClHa(personList, rowCount);		
		img_deleteContactPerson.addClickHandler(handler_deleteContactPerson);
	}
	
	private void updatePersonCounter(int i){
		parentPage.setHighestContactPerson(i);
		parentPage.hf_highestContactPerson.setValue(Integer.toString(parentPage.getHighestContactPerson()));
	}
	
	private void updatePositionButton(int rowCount){	
		personList.remove(btn_addContactPerson);
		personList.setWidget(rowCount+1, 0, btn_addContactPerson);
		personList.getFlexCellFormatter().setColSpan(rowCount+1, 0, 3);
		personList.getFlexCellFormatter().setHorizontalAlignment(rowCount+1, 0, HasHorizontalAlignment.ALIGN_RIGHT);		
	}
	
	private TextBox createEmptyFieldName(int rowCount, int i){
		TextBox contactPerson_name = new TextBox();
		contactPerson_name.setName("contactPerson_name_"+i);
		contactPerson_name.setWidth("100%");
		personList.setWidget(rowCount, 0, contactPerson_name);
		return contactPerson_name;
	}
	
	private TextBox createEmptyFieldEmail(int rowCount, int i){
		TextBox contactPerson_email = new TextBox();
		contactPerson_email.setName("contactPerson_email_"+i);
		contactPerson_email.setWidth("100%");
		personList.setWidget(rowCount, 1, contactPerson_email);
		return contactPerson_email;
	}
	
}