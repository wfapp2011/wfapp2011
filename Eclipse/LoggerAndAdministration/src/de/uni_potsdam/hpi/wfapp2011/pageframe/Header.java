package de.uni_potsdam.hpi.wfapp2011.pageframe;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import de.uni_potsdam.hpi.wfapp2011.general.ProcessIdentifier;

public class Header extends Composite{
	private String processType;
	private int year;
	
	private DockLayoutPanel headerMainPanel;	
	private AbsolutePanel doctorHutPanel;
	private DockLayoutPanel logoPanel;
	private DockLayoutPanel titlePanel;
	private HTML greetingTitle;
	public HorizontalPanel menuPanel;
	public HorizontalPanel menuLoginPanel;
	
	
	public Header(ProcessIdentifier pId, String title){
		this.year = pId.getYear();
		if(pId.getType().equals("Ba")){
			this.processType = "Bachelorprojekt";
		}else{
			this.processType = "Masterprojekt";
		}
		
		headerMainPanel = new DockLayoutPanel(Unit.PX);
		doctorHutPanel = new AbsolutePanel();
		logoPanel = new DockLayoutPanel(Unit.PX);
		titlePanel = new DockLayoutPanel(Unit.PX);
		greetingTitle = new HTML("<h1>"+title+"</h1>\r\n\t\t\t\t<h2>"+processType+" "+year+"</h2>");
		menuPanel = new HorizontalPanel();
		menuPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		menuLoginPanel = new HorizontalPanel();
		menuLoginPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		
		menuPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		menuLoginPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		headerMainPanel.setStylePrimaryName("imagesPanel");
		
		HTML hutImage  = new HTML("<img src=\"/images/doktorhut.png\" width=\"191px\" height=\"151px\">");
		HTML hpiLogo  = new HTML("<img src=\"/images/HPI_Logo.png\" width=\"156px\" height=\"93px\">");
		
		doctorHutPanel.add(hutImage);
		logoPanel.addSouth(menuLoginPanel, 39);
		logoPanel.add(hpiLogo);
		titlePanel.addSouth(menuPanel, 39);
		titlePanel.add(greetingTitle);
		
		headerMainPanel.addWest(doctorHutPanel, 200);
		headerMainPanel.addEast(logoPanel, 156);
		headerMainPanel.add(titlePanel);
		
		headerMainPanel.setSize("100%", "151px"); 
		doctorHutPanel.setSize("191px","151px");
		titlePanel.setHeight("151px");
		logoPanel.setSize("156px", "151px");
		
		initWidget(headerMainPanel);		
	}
}
