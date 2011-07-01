package de.uni_potsdam.hpi.wfapp2011.assignment.client;

import com.google.gwt.user.client.ui.HTML;

public class StatisticsCalculator {

	public static boolean changed;

	public static HTML calculateStatistics(int students){

		int successsum=0;
		int [] wishes = new int[3];
	    //int [][] assignment = HungarianAlgorithm.getAssignment();
	    int permittedVotes = HungarianAlgorithm.StudentList[0].votes.length;

	    for (int i = 0; i < students; i++) {
	    	int wish = HungarianAlgorithm.StudentList[i].findVote(HungarianAlgorithm.StudentList[i].placement.ProjectID);
	    	successsum+=(permittedVotes - wish) + 1;
			if (wish == 1) wishes[0]++;
	    	if (wish == 2) wishes[1]++;
	    	if (wish == 3) wishes[2]++;
		}
		
		int successrate = (int) successsum*100/(5*students);

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
				"</body>" +
				"</html>");
	}

	public static void setChanged() {
		changed = true;
		
	}
}
