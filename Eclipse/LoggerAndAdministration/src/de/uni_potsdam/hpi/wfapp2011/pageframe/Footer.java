package de.uni_potsdam.hpi.wfapp2011.pageframe;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;

public class Footer extends Composite{
	private HTML footer;
	
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
