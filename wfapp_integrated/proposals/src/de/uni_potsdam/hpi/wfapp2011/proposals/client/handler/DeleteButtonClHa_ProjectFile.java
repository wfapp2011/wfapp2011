package de.uni_potsdam.hpi.wfapp2011.proposals.client.handler;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;

import de.uni_potsdam.hpi.wfapp2011.proposals.client.FlexibleFiles;

public class DeleteButtonClHa_ProjectFile implements ClickHandler {

	private FlexTable table;
	private int row;
	private int column;
	private FlexibleFiles caller;

	public void onClick(ClickEvent event) {
		//table.removeCell(row, column+2); //remove hidden field
		table.removeCell(row,column);   //remove anchor		
		caller.addEmptyProjectFile(table);
	}	
		
	public DeleteButtonClHa_ProjectFile(FlexibleFiles caller, FlexTable table, int rowIndex, int columnIndex){
		this.caller = caller;
		this.table = table;
		this.row = rowIndex;
		this.column = columnIndex;		
	}
}
