package de.uni_potsdam.hpi.wfapp2011.assignment.client;

import com.google.gwt.user.client.ui.HTML;

/**
 * Calculation of assignment statistics.
 * Includes a success measure in percent, plus the number of first, second and third wishes.
 * 
 * @return the ready-to-display Statistics page
 */
public class StatisticsCalculator {
	/**
	 * Calculates how good the current assignment is.
	 * 
	 * @return the fully designed statistics page as an HTML widget.
	 */
	public static HTML calculateStatistics(){

		int successsum=0;
		int [] wishes = new int[5];
	    int permittedVotes = AP5_main.DBStudents[0].votes.length;

	    for (Student student : AP5_main.DBStudents) {
	    	int wish = student.findVote(student.placement.projectID);
	    	if (wish != 0) successsum+=(permittedVotes - wish) + 1;
	    	for (int i=0; i<permittedVotes; i++){
	    		if (wish == i+1) {
	    			wishes[i]++;
	    			break;
	    		}
	    	}
	    }
		
	    int successrate = (int) (successsum*100/(5*AP5_main.DBStudents.length));

		return statisticsPage(wishes, successrate);
	}

	/**
	 * Generates the HTML widget that is shown as the statistics page.
	 * 
	 * @param wishes 		an Array of integers representing the number of first, second and third wishes fulfilled
	 * @param successrate	the success measure (in percent) to be displayed as a bar chart		
	 * @return an HTML widget that displays all relevant statistics data
	 */
	private static HTML statisticsPage(int[] wishes, int successrate) {
		int students = AP5_main.DBStudents.length;
		return new HTML("" +
				"<html>" +
				"<body>" +
				"<h1 align=\"center\"> Statistiken </h1>" +
				"<table border=\"0\" align=\"center\">" +
				"<tr>" +
					"<td>"+
				"<style type=\"text/css\">" +
				"dl.progress," +
				"dl.progress *" +
				"    { margin: 0; padding: 0; }" +
				
				"dl.progress {" +
				"    padding: 1px; border: 1px solid #ddd;" +
				"    height: 20px; width: 300px; }" +
				
				".progress dt" +
				"    { width: 0; height: 0; overflow: hidden; }" +
				
				".progress .done," +
				".progress .left" +
				"    { height: 100%; float: left; }" +
				
				".progress .done" +
				"   { background-color: orange; }" +
				
				".progress .done" +
				"   { width: "+ successrate +"%; }" + 	
				".progress .left" +
				"    { width: "+ (100-successrate) +"%; }" + 
				
				".progress a {" +
				"    display: block; width: 100%;" +
				"    height: 100%; text-indent: -9000px; }" +
				"</style>"+
				"Erfolgsrate:  " +
				"</td>" +
				"<td>" +
					"<dl class=\"progress\">" +
					"<dt>Success Rate:</dt>" +
					"<dd class=\"done\">"+ successrate +"% </dd>" +
					"</dl>" +
					"</td>" +
				"</tr>" +
				"<tr>" +
					"<td>" +
						"Erf&uuml;llte Erstw&uuml;nsche: " +
					"</td>" +
					"<td>" +
						wishes[0] +" von "+ students +
					"</td>" +
				"</tr>" +
				"<tr>" +
					"<td>" +
						"Erf&uuml;llte Zweitw&uuml;nsche: " +
					"</td>" +
					"<td>" +
						wishes[1] +" von "+ students +
					"</td>" +
				"</tr>" +
				"<tr>" +
					"<td>" +
						"Erf&uuml;llte Drittw&uuml;nsche: " +
					"</td>" +
					"<td>" +
						wishes[2] +" von "+ students +
					"</td>" +
				"</tr>" +
				"<tr>" +
					"<td>" +
						"Erf&uuml;llte Viertw&uuml;nsche: " +
					"</td>" +
					"<td>" +
						wishes[3] +" von "+ students +
					"</td>" +
				"</tr>" +
				"<tr>" +
					"<td>" +
						"Erf&uuml;llte F&uuml;nftw&uuml;nsche: " +
					"</td>" +
					"<td>" +
						wishes[4] +" von "+ students +
					"</td>" +
				"</tr>" +
				"</table>" +
				"</body>" +
				"</html>");
	}
}
