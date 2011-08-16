
package de.uni_potsdam.hpi.wfapp2011.proposals.client.handler;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;

public class DeleteButtonClHa implements ClickHandler {

	private FlexTable list;
	private int rowIndex;
	
	//TODO fix delete bug last item
	@Override
	public void onClick(ClickEvent event) {
		list.removeRow(rowIndex);		
	}
	
	public DeleteButtonClHa(FlexTable list, int rowIndex){
		this.list = list;
		this.rowIndex = rowIndex;
	}
}
