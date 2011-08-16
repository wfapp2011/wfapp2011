package de.uni_potsdam.hpi.wfapp2011.clientcore.pageframe;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;

/**
 * This class build a footer panel. An instance of this class is a composite panel, which can be handle like ordinary GWT panel. <br/>
 * It is used from every sub-package, so it allows to take changes just at this class.
 * 
 * @author Jannik Marten, Yanina Yurchenko
 *
 */

public class Footer extends Composite{
	private HTML footer;
	
	/**
	 * Constructor of Footer
	 */
	public Footer(){
		footer = new HTML(
				"<html>\r\n\t" +
				"<head>\r\n\t<link type=\"text/css\" rel=\"stylesheet\" href=\"wfapp.css\">\r\n\t" +
				"</head>\r\n\t\r\n\t" +
				"<body>\r\n\t\t<hr>\r\n\t\t" +
				"<table border=\"0\" width=\"100%\" margin=\"0\">\r\n\t\t\t" +
					"<colgroup>\r\n    \t\t\t<col width=\"*\">\r\n    \t\t\t<col width=\"100\">\r\n    \t\t\t<col width=\"100\">\r\n  \t\t\t</colgroup>\r\n  \t\t\t" +
					"<td></td>\r\n\t\t\t" +
					"<td>\r\n\t\t\t\t<a href=\"https://www.hpi.uni-potsdam.de/support/impressum.html\">Impressum</a>\r\n\t\t\t</td>\r\n\t\t\t" +
					"<td>\r\n\t\t\t\t<a href=\"https://github.com/wfapp2011/wfapp2011/issues\"><nobr>Report a Bug</nobr></a>\r\n\t\t\t</td>\r\n\t\t" +
				"</table>\r\n\t</body>\r\n</html>");
		footer.setStyleName("hr");
		initWidget(footer);
	}
}
