package de.uni_potsdam.hpi.wfapp2011.clientcore.pageframe;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * This class build a header panel. An instance of this class is a composite panel, which can be handle like ordinary GWT panel. <br/>
 * It is used from every sub-package, so it allows to take changes just at this class.
 * 
 * @author Jannik Marten, Yanina Yurchenko
 *
 */

public class Header extends Composite{
	private String processType;
	private String year;
	private DockLayoutPanel headerMainPanel;	
	private AbsolutePanel doctorHutPanel;
	private DockLayoutPanel logoPanel;
	private DockLayoutPanel titlePanel;
	private HTML greetingTitle;
	public HorizontalPanel menuPanel;
	public HorizontalPanel menuLoginPanel;
	
	/**
	 * Constructor of Header
	 * 
	 * @param type : String (Ba/Ma)
	 * @param semester : String (SS/WS)
	 * @param year : Integer of the year (yyyy) of the project series
	 * @param title : String of the heading of the header 
	 */
	public Header(String type, String semester, int year, String title){
		this.year =  ((year == 0 )? "" : String.valueOf(year));
		if(type.equals("Ba")){
			this.processType = "Bachelorprojekt";
		}	
		else if(type.equals("Ma")){
			this.processType = "Masterprojekt";
		} else {
			this.processType = "";
		}
		
		headerMainPanel = new DockLayoutPanel(Unit.PX);
		headerMainPanel.setStylePrimaryName("imagesPanel");
		
		doctorHutPanel = new AbsolutePanel();
		logoPanel = new DockLayoutPanel(Unit.PX);
		titlePanel = new DockLayoutPanel(Unit.PX);
		greetingTitle = new HTML("<h1>"+title+"</h1>\r\n\t\t\t\t<h2>"+processType+" "+this.year+"</h2>");
		
		menuPanel = new HorizontalPanel();
		menuPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		
		menuLoginPanel = new HorizontalPanel();
		menuLoginPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		
		menuPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		menuLoginPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		
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
