package de.uni_potsdam.hpi.wfapp2011.assignment.client;
/*
 * Created on Apr 25, 2005
 * 
 * Munkres-Kuhn (Hungarian) Algorithm Clean Version: 0.11
 * 
 * Konstantinos A. Nedas                     
 * Department of Spatial Information Science & Engineering
 * University of Maine, Orono, ME 04469-5711, USA
 * kostas@spatial.maine.edu
 * http://www.spatial.maine.edu/~kostas       
 *
 * This Java class implements the Hungarian algorithm [a.k.a Munkres' algorithm,
 * a.k.a. Kuhn algorithm, a.k.a. Assignment problem, a.k.a. Marriage problem,
 * a.k.a. Maximum Weighted Maximum Cardinality Bipartite Matching].
 *
 * [It can be used as a method call from within any main (or other function).]
 * It takes 2 arguments:
 * a. A 2-D array (could be rectangular or square).
 * b. A string ("min" or "max") specifying whether you want the min or max assignment.
 * [It returns an assignment matrix[array.length][2] that contains the row and col of
 * the elements (in the original inputted array) that make up the optimum assignment.]
 *  
 * [This version contains only scarce comments. If you want to understand the 
 * inner workings of the algorithm, get the tutorial version of the algorithm
 * from the same website you got this one (http://www.spatial.maine.edu/~kostas/dev/soft/munkres.htm)]
 * 
 * Any comments, corrections, or additions would be much appreciated. 
 * Credit due to professor Bob Pilgrim for providing an online copy of the
 * pseudocode for this algorithm (http://216.249.163.93/bob.pilgrim/445/munkres.html)
 * 
 * Feel free to redistribute this source code, as long as this header--with
 * the exception of sections in brackets--remains as part of the file.
 * 
 * Requirements: JDK 1.5.0_01 or better.
 * [Created in Eclipse 3.1M6 (www.eclipse.org).]
 * 
 */

import java.util.*;

public class HungarianAlgorithm {
	
	static HashMap<String, int[]> ProjectTable;
	static HashMap<Integer, Student> StudentTable;
	static Project[] ProjectList;
	static Student[] StudentList;
	static int[][] Assignment;
	static double[][] VotesMatrix;
	
	public static void initHg(Project[] Projects, Student[] Students){
		ProjectList = Projects;
		StudentList = Students;
		
		if (checkVotingData() > 0){
			int[][] ass = {{-1, checkVotingData()}};
			Assignment = ass;
		}
		else{
			while(checkVotingData() < 0){
				ignoreWorstProject();
				System.out.println("Ignoring a project");
			}
			System.out.println("voting data checked");
			Assignment = hgAlgorithm(generateMatrix());
			System.out.println("assignment generated");
			fixErrors(Assignment);
			System.out.println("errors fixed");
		}
	}

	private static int maxStudents(){
		int maxStudents = 0;
		for(int i = 0; i < ProjectList.length; i++){
			maxStudents = maxStudents + ProjectList[i].maxStudents;
		}
		return maxStudents;
	}
	
	private static int minStudents(){
		int minStudents = 0;
		for(int i = 0; i < ProjectList.length; i++){
			minStudents += ProjectList[i].minStudents;
		}
		return minStudents;
	}
	
	public static int checkVotingData(){
		int max = maxStudents(), min = minStudents(), NoSt = StudentList.length;
		if (NoSt > max){
			return (NoSt - max); 	//will be positive
		}
		else if (NoSt < min) {
			return (NoSt - min);	//will be negative
		}
		return 0;
	}
	
	public static Project[] getProjectRanking() {
		int prIndex = 0;
		double[][] matrix = generateMatrix();
		for(int i = 0; i<ProjectList.length; i++){
			int rankPoints = 0;
			prIndex = ProjectTable.get(ProjectList[i].ProjectID)[0];
			for(int j = 0; j<StudentList.length; j++){
				rankPoints += matrix[j][prIndex];
			}
			ProjectList[i].rankPoints = rankPoints;
		}
		return SortByRank(ProjectList);
	}

	private static Project[] SortByRank(Project[] Array) {
	//least voted project first
		for(int i = 0; i<Array.length; i++){
				int min = i;
				for (int j = i+1; j < Array.length; j++){
					if (Array[j].rankPoints < Array[min].rankPoints) min = j;
				}
				Project temp = Array[i];
				Array[i]=Array[min];
				Array[min]=temp;
		}
		return Array;
	}
	
	private static void ignoreWorstProject(){
		Project[] SortedProjectList = getProjectRanking();
		ProjectList = new Project[ProjectList.length -1];
		for (int i=1; i<SortedProjectList.length; i++){
			ProjectList[i-1] = SortedProjectList[i];
		}
	}

	//should be removed soon!
	public static double[][] generateMatrix(Project[] testProjects, Student[] testStudents) {
		initHg(testProjects, testStudents);
		return generateMatrix();
	}

	public static double[][] generateMatrix() {
		double[][] matrix = new double[StudentList.length][maxStudents()];
		int PrNo = 0, i = 0;
		ProjectTable = new HashMap<String,int[]>();
		StudentTable = new HashMap<Integer, Student>();
		
		while (PrNo < ProjectList.length){
			int[] x = {i, i + ProjectList[PrNo].maxStudents - 1};
			ProjectTable.put(ProjectList[PrNo].ProjectID, x);
			i = i + ProjectList[PrNo].maxStudents;
			PrNo++;
		}
		
		int StNo = 0;
		while (StNo < StudentList.length){
			Student student = StudentList[StNo];
			StudentTable.put(StNo, student);
			for (int j=0; j < 5; j++){
				int[] inputPlaces = ProjectTable.get(student.votes[j]);
				for (int k = inputPlaces[0]; k <= inputPlaces[1]; k++){
					matrix[StNo][k] = 5-j;	//replace 5 by number of permitted votes
				}
			}
			StNo++;
		}
		VotesMatrix = matrix;
		return matrix;
	}
	
	public static Student lookUpStudent(int i) {
		return StudentTable.get(i);
	}

	public static Project lookUpProject(int PlacementNo){
		for (int i = 0; i<ProjectTable.size(); i++){
			int[] indizes = ProjectTable.get(ProjectList[i].ProjectID);
			if (indizes[0]<=PlacementNo & indizes[1]>=PlacementNo) return ProjectList[i];
		}
		return null;
	}
	
	public static int[][] getAssignment(){
		return Assignment;
	}

	public static int[] getSizeErrors(int[][] assignment) {
		int prIndex = 0;
		int[] errors = new int[ProjectList.length];
		while(prIndex < ProjectList.length){
			Project pr = ProjectList[prIndex];
			if (pr.count(assignment) < pr.minStudents){
					System.out.println("Projekt "+ pr.ProjectID +
						" hat nicht genug Studenten!" );
					errors[prIndex]=pr.checkCorrectSize(assignment);
			}
			prIndex++;
		}
		return errors;
	}
	

	public static void fixErrors(int[][] ass) {
		int[] errors = getSizeErrors(ass);
		for(int i=0; i<errors.length; i++){
			while (errors[i]>0){
				int s = findStudentToMove(ProjectList[i], ass, 1);
				if (s >= 0){
					ass[s][1] = ProjectTable.get(ProjectList[i].ProjectID)[0] 
					           + ProjectList[i].count(ass);
					errors[i]--;
				}
				else {
					int[] p = findStudentPairToMove(ProjectList[i], ass, 1);
					if(p[0] >= 0){
						ass[p[1]][1] = ass[p[0]][1];
						ass[p[0]][1] = ProjectTable.get(ProjectList[i].ProjectID)[0] 
						            + ProjectList[i].count(ass);
						errors[i]--;
					}
					else{
						s = findStudentToMove(ProjectList[i], ass, 2);
						if(s >= 0){
							ass[s][1] = ProjectTable.get(ProjectList[i].ProjectID)[0] 
						            + ProjectList[i].count(ass);
							errors[i]--;
						}
						else {
							p = findStudentPairToMove(ProjectList[i], ass, 2);
							if(p[0] >= 0){
								ass[p[1]][1] = ass[p[0]][1];
								ass[p[0]][1] = ProjectTable.get(ProjectList[i].ProjectID)[0] 
								            + ProjectList[i].count(ass);
								errors[i]--;
							}							
							else{
								System.out.println("another phase needed");
								errors[i]=0;
							}
						}
					}
				}
			}
		}
	}

	private static int findStudentToMove(Project pr, int[][] ass, int voteIndex) {
	//Suche Student der pr als xt-Wunsch hat und nicht in minimalem Projekt ist
		for(int j=0; j<StudentList.length; j++){
			Student st = lookUpStudent(j);
			Project lookedUpPr = lookUpProject(ass[j][1]);
			if(st.votes[voteIndex] == pr.ProjectID & lookedUpPr.count(ass) > lookedUpPr.minStudents){
				return j;
			}
		}
		return -1;
	}
	
	private static int[] findStudentPairToMove(Project pr, int[][] ass, int voteIndex){
	//find Student who has pr as xth wish and another to replace him in his minimal project
		for(int n=0; n<=voteIndex; n++){	
			for(int j=0; j<StudentList.length; j++){
				Student st = lookUpStudent(j);
				Project lookedUpPr = lookUpProject(ass[j][1]);
				if(st.votes[voteIndex] == pr.ProjectID & lookedUpPr.count(ass) >= lookedUpPr.minStudents){
					int k = findStudentToMove(lookedUpPr, ass, n);
					if(k>0){
						int[] re = {j, k};
						return re;
					}
				}
			}
		}
		int[] re = {-1, -1};
		return re;	
	}

	//********************************//
	//METHODS FOR CONSOLE INPUT-OUTPUT//
	//********************************//
	
	/*public static int readInput(String prompt)	//Reads input,returns double.
	{
		Scanner in = new Scanner(System.in);
		System.out.print(prompt);
		int input = in.nextInt();
		return input;
	}*/
	
	//*******************************************//
	//METHODS THAT PERFORM ARRAY-PROCESSING TASKS//
	//*******************************************//
	
	public static double findLargest		//Finds the largest element in a positive array.
	(double[][] array)
	//works for arrays where all values are >= 0.
	{
		double largest = 0;
		for (int i=0; i<array.length; i++)
		{
			for (int j=0; j<array[i].length; j++)
			{
				if (array[i][j] > largest)
				{
					largest = array[i][j];
				}
			}
		}
			
		return largest;
	}
	public static double[][] transpose		//Transposes a double[][] array.
	(double[][] array)	
	{
		double[][] transposedArray = new double[array[0].length][array.length];
		for (int i=0; i<transposedArray.length; i++)
		{
			for (int j=0; j<transposedArray[i].length; j++)
			{transposedArray[i][j] = array[j][i];}
		}
		return transposedArray;
	}
	public static double[][] copyOf			//Copies all elements of an array to a new array.
	(double[][] original)	
	{
		double[][] copy = new double[original.length][original[0].length];
		for (int i=0; i<original.length; i++)
		{
			//Need to do it this way, otherwise it copies only memory location
			System.arraycopy(original[i], 0, copy[i], 0, original[i].length);
		}
		
		return copy;
	}
	
	//**********************************//
	//METHODS OF THE HUNGARIAN ALGORITHM//
	//**********************************//
	
	public static int[][] hgAlgorithm (double[][] array)
	{
		double[][] cost = copyOf(array);	//Create the cost matrix
		
		double maxWeight = findLargest(cost);
		for (int i=0; i<cost.length; i++)		//Generate cost by subtracting.
		{
			for (int j=0; j<cost[i].length; j++)
			{
				cost [i][j] = (maxWeight - cost [i][j]);
			}
		}
		
		double maxCost = findLargest(cost);		//Find largest cost matrix element (needed for step 6).
		
		int[][] mask = new int[cost.length][cost[0].length];	//The mask array.
		int[] rowCover = new int[cost.length];					//The row covering vector.
		int[] colCover = new int[cost[0].length];				//The column covering vector.
		int[] zero_RC = new int[2];								//Position of last zero from Step 4.
		int step = 1;											
		boolean done = false;
		while (done == false)	//main execution loop
		{ 
			switch (step)
		    {
				case 1:
					step = hg_step1(step, cost);     
		    	    break;
		    	case 2:
		    	    step = hg_step2(step, cost, mask, rowCover, colCover);
					break;
		    	case 3:
		    	    step = hg_step3(step, mask, colCover);
					break;
		    	case 4:
		    	    step = hg_step4(step, cost, mask, rowCover, colCover, zero_RC);
					break;
		    	case 5:
					step = hg_step5(step, mask, rowCover, colCover, zero_RC);
					break;
		    	case 6:
		    	   	step = hg_step6(step, cost, rowCover, colCover, maxCost);
					break;
		  	    case 7:
		    	    done=true;
		    	    break;
		    }
		}//end while
		
		int[][] assignment = new int[array.length][2];	//Create the returned array.
		for (int i=0; i<mask.length; i++)
		{
			for (int j=0; j<mask[i].length; j++)
			{
				if (mask[i][j] == 1)
				{
					assignment[i][0] = i;
					assignment[i][1] = j;
				}
			}
		}	
		return assignment;
	}
	
	
	public static int hg_step1(int step, double[][] cost)
	{
		//What STEP 1 does:
		//For each row of the cost matrix, find the smallest element
		//and subtract it from from every other element in its row. 
	    
	   	double minval;
	   	
		for (int i=0; i<cost.length; i++)	
	   	{									
	   	    minval=cost[i][0];
	   	    for (int j=0; j<cost[i].length; j++)	//1st inner loop finds min val in row.
	   	    {
	   	        if (minval>cost[i][j])
	   	        {
	   	            minval=cost[i][j];
	   	        }
			}
			for (int j=0; j<cost[i].length; j++)	//2nd inner loop subtracts it.
	   	    {
	   	        cost[i][j]=cost[i][j]-minval;
	   	    }
		}
	   			    
		step=2;
		return step;
	}
	public static int hg_step2(int step, double[][] cost, int[][] mask, int[]rowCover, int[] colCover)
	{
		//What STEP 2 does:
		//Marks uncovered zeros as starred and covers their row and column.
		
		for (int i=0; i<cost.length; i++)
	    {
	        for (int j=0; j<cost[i].length; j++)
	        {
	            if ((cost[i][j]==0) && (colCover[j]==0) && (rowCover[i]==0))
	            {
	                mask[i][j]=1;
					colCover[j]=1;
	                rowCover[i]=1;
				}
	        }
	    }
							
		clearCovers(rowCover, colCover);	//Reset cover vectors.
			    
		step=3;
		return step;
	}
	public static int hg_step3(int step, int[][] mask, int[] colCover)
	{
		//What STEP 3 does:
		//Cover columns of starred zeros. Check if all columns are covered.
		
		for (int i=0; i<mask.length; i++)	//Cover columns of starred zeros.
	    {
	        for (int j=0; j<mask[i].length; j++)
	        {
	            if (mask[i][j] == 1)
	            {
	                colCover[j]=1;
				}
	        }
	    }
	    
		int count=0;						
		for (int j=0; j<colCover.length; j++)	//Check if all columns are covered.
	    {
	        count=count+colCover[j];
	    }
		
		if (count>=mask.length)	//Should be cost.length but ok, because mask has same dimensions.	
	    {
			step=7;
		}
	    else
		{
			step=4;
		}
	    	
		return step;
	}
	public static int hg_step4(int step, double[][] cost, int[][] mask, int[] rowCover, int[] colCover, int[] zero_RC)
	{
		//What STEP 4 does:
		//Find an uncovered zero in cost and prime it (if none go to step 6). Check for star in same row:
		//if yes, cover the row and uncover the star's column. Repeat until no uncovered zeros are left
		//and go to step 6. If not, save location of primed zero and go to step 5.
		
		int[] row_col = new int[2];	//Holds row and col of uncovered zero.
		boolean done = false;
		while (done == false)
		{
			row_col = findUncoveredZero(row_col, cost, rowCover, colCover);
			if (row_col[0] == -1)
			{
				done = true;
				step = 6;
			}
			else
			{
				mask[row_col[0]][row_col[1]] = 2;	//Prime the found uncovered zero.
				
				boolean starInRow = false;
				for (int j=0; j<mask[row_col[0]].length; j++)
				{
					if (mask[row_col[0]][j]==1)		//If there is a star in the same row...
					{
						starInRow = true;
						row_col[1] = j;		//remember its column.
					}
				}
							
				if (starInRow==true)	
				{
					rowCover[row_col[0]] = 1;	//Cover the star's row.
					colCover[row_col[1]] = 0;	//Uncover its column.
				}
				else
				{
					zero_RC[0] = row_col[0];	//Save row of primed zero.
					zero_RC[1] = row_col[1];	//Save column of primed zero.
					done = true;
					step = 5;
				}
			}
		}
		
		return step;
	}
	public static int[] findUncoveredZero	//Aux 1 for hg_step4.
	(int[] row_col, double[][] cost, int[] rowCover, int[] colCover)
	{
		row_col[0] = -1;	//Just a check value. Not a real index.
		row_col[1] = 0;
		
		int i = 0; boolean done = false;
		while (done == false)
		{
			int j = 0;
			while (j < cost[i].length)
			{
				if (cost[i][j]==0 && rowCover[i]==0 && colCover[j]==0)
				{
					row_col[0] = i;
					row_col[1] = j;
					done = true;
				}
				j = j+1;
			}//end inner while
			i=i+1;
			if (i >= cost.length)
			{
				done = true;
			}
		}//end outer while
		
		return row_col;
	}
	public static int hg_step5(int step, int[][] mask, int[] rowCover, int[] colCover, int[] zero_RC)
	{
		//What STEP 5 does:	
		//Construct series of alternating primes and stars. Start with prime from step 4.
		//Take star in the same column. Next take prime in the same row as the star. Finish
		//at a prime with no star in its column. Unstar all stars and star the primes of the
		//series. Erasy any other primes. Reset covers. Go to step 3.
		
		int count = 0;												//Counts rows of the path matrix.
		int[][] path = new int[(mask[0].length*mask.length)][2];	//Path matrix (stores row and col).
		path[count][0] = zero_RC[0];								//Row of last prime.
		path[count][1] = zero_RC[1];								//Column of last prime.
		
		boolean done = false;
		while (done == false)
		{ 
			int r = findStarInCol(mask, path[count][1]);
			if (r>=0)
			{
				count = count+1;
				path[count][0] = r;					//Row of starred zero.
				path[count][1] = path[count-1][1];	//Column of starred zero.
			}
			else
			{
				done = true;
			}
			
			if (done == false)
			{
				int c = findPrimeInRow(mask, path[count][0]);
				count = count+1;
				path[count][0] = path [count-1][0];	//Row of primed zero.
				path[count][1] = c;					//Col of primed zero.
			}
		}//end while
		
		convertPath(mask, path, count);
		clearCovers(rowCover, colCover);
		erasePrimes(mask);
		
		step = 3;
		return step;
		
	}
	private static int findStarInCol			//Aux 1 for hg_step5.
	(int[][] mask, int col)
	{
		int r=-1;	//Again this is a check value.
		for (int i=0; i<mask.length; i++)
		{
			if (mask[i][col]==1)
			{
				r = i;
			}
		}
				
		return r;
	}
	private static int findPrimeInRow		//Aux 2 for hg_step5.
	(int[][] mask, int row)
	{
		int c = -1;
		for (int j=0; j<mask[row].length; j++)
		{
			if (mask[row][j]==2)
			{
				c = j;
			}
		}
		
		return c;
	}
	private static void convertPath			//Aux 3 for hg_step5.
	(int[][] mask, int[][] path, int count)
	{
		for (int i=0; i<=count; i++)
		{
			if (mask[(path[i][0])][(path[i][1])]==1)
			{
				mask[(path[i][0])][(path[i][1])] = 0;
			}
			else
			{
				mask[(path[i][0])][(path[i][1])] = 1;
			}
		}
	}
	private static void erasePrimes			//Aux 4 for hg_step5.
	(int[][] mask)
	{
		for (int i=0; i<mask.length; i++){
			for (int j=0; j<mask[i].length; j++){
				if (mask[i][j]==2){
					mask[i][j] = 0;
				}
			}
		}
	}
	private static void clearCovers			//Aux 5 for hg_step5 (and not only).
	(int[] rowCover, int[] colCover)
	{
		for (int i=0; i<rowCover.length; i++){
			rowCover[i] = 0;
		}
		for (int j=0; j<colCover.length; j++){
			colCover[j] = 0;
		}
	}
	public static int hg_step6(int step, double[][] cost, int[] rowCover, int[] colCover, double maxCost)
	{
		//What STEP 6 does:
		//Find smallest uncovered value in cost: a. Add it to every element of covered rows
		//b. Subtract it from every element of uncovered columns. Go to step 4.
		
		double minval = findSmallest(cost, rowCover, colCover, maxCost);
		
		for (int i=0; i<rowCover.length; i++){
			for (int j=0; j<colCover.length; j++){
				if (rowCover[i]==1)
				{
					cost[i][j] = cost[i][j] + minval;
				}
				if (colCover[j]==0)
				{
					cost[i][j] = cost[i][j] - minval;
				}
			}
		}
			
		step = 4;
		return step;
	}
	private static double findSmallest		//Aux 1 for hg_step6.
	(double[][] cost, int[] rowCover, int[] colCover, double maxCost)
	{
		double minval = maxCost;				//There cannot be a larger cost than this.
		for (int i=0; i<cost.length; i++)		//Now find the smallest uncovered value.
		{
			for (int j=0; j<cost[i].length; j++)
			{
				if (rowCover[i]==0 && colCover[j]==0 && (minval > cost[i][j]))
				{
					minval = cost[i][j];
				}
			}
		}
		return minval;
	}
}
