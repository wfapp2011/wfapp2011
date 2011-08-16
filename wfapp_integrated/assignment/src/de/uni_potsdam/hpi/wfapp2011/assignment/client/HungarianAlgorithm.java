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

/**
 * Hungarian algorithm implementation with necessary adaptions to WfApp Application
 */

public class HungarianAlgorithm {
	/**
	 * HashMap that allows to look up which index range in the matrix from 
	 * <code>generateMatrxi()</code>belongs to it.
	 */
	private static HashMap<String, int[]> ProjectTable;
	private static HashMap<Integer, Student> StudentTable;
	private static Project[] ProjectList;
	private static Student[] StudentList;
	private static int[][] Assignment;
	
	/**
	 * Initializes the necessary data for the algorithm
	 * 
	 * @param Projects	an Array of Project objects that are supposed to be filled with Students
	 * @param Students	an Array of Student objects that are supposed to be assigned to a Project
	 * @return whether the algorithms could produce a valid result with the given data
	 */
	public static boolean initHg(Project[] Projects, Student[] Students){
		ProjectList = Projects;
		StudentList = Students;
		
		if (checkVotingData() > 0){
			int[][] ass = {{-1, checkVotingData()}};
			Assignment = ass;
			return false;
		}
		else{
			while(checkVotingData() < 0){
				ignoreWorstProject();
			}
			Assignment = hgAlgorithm(generateMatrix(StudentList, ProjectList));
			fixErrors(Assignment);
			setPlacements();
			return true;
		}
	}

	/**
	 * finds out to which Project each student has been assigned and sets his/her placement variable accordingly
	 */
	private static void setPlacements() {
		for(int i=0; i<StudentList.length; i++){
			StudentList[i].placement = lookUpProject(Assignment[i][1]);
		}
	}

	/**
	 * calculates the sum of all maxStudents attributes from given projects
	 * 
	 * @param projList	the Array of Projects for which maximum number of students should be calculated
	 * @return the maximum number of Project places available
	 */
	private static int maxStudents(Project[] projList){
		int maxStudents = 0;
		for(Project p : projList){
			maxStudents += p.maxStudents;
		}
		return maxStudents;
	}
	
	/**
	 * calculates the sum of all minStudents attributes from given projects
	 * 
	 * @param projList	the Array of Projects for which minimum number of students should be calculated
	 * @return the minimum number of Project places available
	 */
	private static int minStudents(Project[] projList){
		int minStudents = 0;
		for(Project p : projList){
			minStudents += p.minStudents;
		}
		return minStudents;
	}
	
	/**
	 * checks whether a valid assignment might be impossible based on project places / student ratio
	 * 
	 * @return 	0 if there is no problem, 
	 * 			a positive number if there are too many students - saying how many places are missing
	 * 			a negative number if there are too little students - saying how many more are needed
	 */
	public static int checkVotingData(){
		int max = maxStudents(ProjectList), min = minStudents(ProjectList), NoSt = StudentList.length;
		if (NoSt > max){
			return (NoSt - max); 	//will be positive
		}
		else if (NoSt < min) {
			return (NoSt - min);	//will be negative
		}
		return 0;
	}
	
	/**
	 * Shortens <code>ProjectList</code> by one, deleting the least liked project.
	 */
	private static void ignoreWorstProject(){
		Project[] SortedProjectList = getProjectRanking();
		ProjectList = new Project[ProjectList.length - 1];
		for (int i=1; i<SortedProjectList.length; i++){
			ProjectList[i-1] = SortedProjectList[i];
		}
	}
	
	/**
	 * Calculates for each Project in the <code>ProjectList</code> how popular they are.
	 * Each Project receives a score, calculated based on who has voted it with which preference.
	 * E.g. if three votes are permitted, one points are assigned to a project for each student who voted 
	 * it on third rank, two for each who voted it on second rank etc.
	 * 
	 * @return a Project Array sorted from most often to least often voted
	 */
	private static Project[] getProjectRanking() {
		int prIndex = 0;
		double[][] matrix = generateMatrix(StudentList, ProjectList);
		HashMap<Project, Integer> rankPoints = new HashMap<Project, Integer>();
		for(int i = 0; i<ProjectList.length; i++){
			int points = 0;
			prIndex = ProjectTable.get(ProjectList[i].projectID)[0];
			for(int j = 0; j<StudentList.length; j++){
				points += matrix[j][prIndex];
			}
			rankPoints.put(ProjectList[i], points);
		}
		return sort(ProjectList, rankPoints);
	}

	/**
	 * Sorts the Projects by rank points previously calculated.
	 * 
	 * @param Array			the Array of Projects to be sorted
	 * @param rankPoints 	a HashMap that assigns a point value to each Project
	 * @return the sorted Project Array, starting with the Project with the least points
	 */
	private static Project[] sort(Project[] Array, HashMap<Project, Integer> rankPoints) {
		for(int i = 0; i<Array.length; i++){
				int min = i;
				for (int j = i+1; j < Array.length; j++){
					if (rankPoints.get(Array[j]) < rankPoints.get(Array[min])) min = j;
				}
				Project temp = Array[i];
				Array[i]=Array[min];
				Array[min]=temp;
		}
		return Array;
	}
	
	/**
	 * Generates the matrix that is taken as input by the Hungarian Algorithm.
	 * Each entry specifies how much it will be worth if the corresponding Student (columns/first index) is assigned
	 * to the corresponding Project (rows/second index). Since the Hungarian Algorithm only does one-to-one
	 * assignments, there are as many rows for each project as students are maximally allowed in this project.
	 * 
	 * @param studList	the Array of Students that have to be assigned to Projects
	 * @param projList	the Array of Projects that the Students should be assigned to
	 * @return the matrix (integers) to be used as input for Hungarian Algorithm
	 */
	public static double[][] generateMatrix(Student[] studList, Project[] projList) {
		double[][] matrix = new double[studList.length][maxStudents(AP5_main.DBProjects)];
		int PrNo = 0;
		int MaxProjectTableIndex = 0;
		ProjectTable = new HashMap<String,int[]>();
		StudentTable = new HashMap<Integer, Student>();
		
		while (PrNo < projList.length){
			int[] x = {MaxProjectTableIndex, MaxProjectTableIndex + projList[PrNo].maxStudents - 1};
			ProjectTable.put(projList[PrNo].projectID, x);
			MaxProjectTableIndex = MaxProjectTableIndex + projList[PrNo].maxStudents;
			PrNo++;
		}
		
		int StNo = 0;
		while (StNo < studList.length){
			Student student = studList[StNo];
			StudentTable.put(StNo, student);
			for (int j=0; j < 5; j++){
				if (ProjectTable.containsKey(student.votes[j])){
					int[] inputPlaces = ProjectTable.get(student.votes[j]);
					for (int k = inputPlaces[0]; k <= inputPlaces[1]; k++){
						matrix[StNo][k] = studList[0].votes.length-j;	//number of votes permitted (5 for Ba)
																		//minus priority (--> for Ba - first wish: 5, second wish: 4 etc.
					}
				}
			}
			StNo++;
		}
		return matrix;
	}
	
	/**
	 * Looks up which Student belongs to an index.
	 * 
	 * Useful for Hungarian Algorithm. This way we can relate the resulting assignment back to
	 * the Students since all Students are initially stored in the HashMap <code>StudentTable</code>
	 * with their index.
	 * 
	 * @param i	the index that will be looked up
	 * @return	the <code>Student</code> that corresponds to this index
	 */
	private static Student lookUpStudent(int i) {
		return StudentTable.get(i);
	}

	/**
	 * Given a placement number a student was assigned to, it finds the project that this number represents.
	 * 
	 * @see ProjectTable
	 * 
	 * @param placementNo the number in the second "column" of the assignment matrix
	 * @return the <code>Project</code> that the placement number belongs to
	 */
	private static Project lookUpProject(int placementNo){
		for (int i = 0; i<ProjectTable.size(); i++){
			int[] indizes = ProjectTable.get(ProjectList[i].projectID);
			if (indizes[0]<=placementNo & indizes[1]>=placementNo) 
				return ProjectList[i];
		}
		return null;
	}
	
	/**
	 * Getter for the assignment matrix.
	 * This matrix is the output of the Hungarian Algorithm. It has two columns(second index)
	 * and as many rows (first index) as Students as have been the input. So each row represents 
	 * a student which can be accessed via <code>lookUpStudent(int i)</code>. 
	 * For each student, the first column (Assignment[i][1]) represents his/her "placement number" where 
	 * each placement is unique and can be related to the Project through <code>lookUpProject()</code>.
	 * The second column (Assignment[i][0]) says how much this placement is worth for that student (relating
	 * to his/her votes)
	 * 
	 * @return the Assignment class variable
	 */
	public static int[][] getAssignment(){
		return Assignment;
	}
	
	/**
	 * Counts the students assigned to the given project in the current Assignment matrix.
	 * 
	 * @param p	the Project for which student number shall be counted
	 * @return the number of students assigned to p in <code>Assignment</code>
	 */
	private static int countStudents(Project p) {
		int count = 0;
		for(int i = 0; i<Assignment.length; i++){
			if (lookUpProject(Assignment[i][1]) == p) count++;
		}
		return count;
	}
	
	/**
	 * Takes errors found by <code>getSizeErrors()</code> and tries to fix them by step by step
	 * allowing more loss in quality.
	 * @param ass the Integer-Matrix representing the current Assignment
	 */
	private static void fixErrors(int[][] ass) {
		int[] errors = getSizeErrors(ass);
		//iterates over Projects
		for(int i=0; i<errors.length; i++){
			//executes once for each student missing in the project
			while (errors[i]>0){
				for(int step=0; step <= 4 /*TODO:replace*/; step++){
					if(moveSingleStudent(ProjectList[i], ass, step)){
						errors[i]--;
						break;
					}
					else if(moveStudentPair(ProjectList[i], ass, step)){
						errors[i]--;
						break;
					}
				}
			}
		}
	}
	
	/**
	 * Checks which projects do not have enough students.
	 * It is guaranteed by the implementation that maximum student number will not be exceeded
	 * @see generateMatrix
	 * 
	 * @param assignment the Integer-Matrix representing the current Assignment
	 * @return an Array with the same indexes as <code>ProjectList</code> and values that say
	 * 		how many students are missing in the project to fulfill its minimum student number requirement
	 */
	private static int[] getSizeErrors(int[][] assignment) {
		int[] errors = new int[ProjectList.length];
		for(int prIndex = 0; prIndex < ProjectList.length; prIndex++){
			Project pr = ProjectList[prIndex];
			int noSt = countStudents(pr);
			if (noSt < pr.minStudents)
					errors[prIndex]=pr.minStudents - noSt;
		}
		return errors;
	}
	
	/**
	 * Finds and moves a student to a certain Project with a certain decrease of quality of the assignment.
	 * 
	 * @param project	the Project that a student should be moved to
	 * @param ass		the Integer-Matrix representing the current assignment
	 * @param step		specifies how much loss in the assignment value is allowed.
	 * @return	true if a student was moved
	 * 			false if no student was found able to be moved with the specified decrease in quality
	 */
	private static boolean moveSingleStudent(Project project, int[][] ass, int step){
		int s = findStudentToMove(project, ass, step);
		if (s >= 0){
			ass[s][1] = ProjectTable.get(project.projectID)[0] 
			           + countStudents(project);
			return true;
		}
		return false;
	}
	
	/**
	 * Finds and moves a student to a Project out of a minimally filled other Project, whose place
	 * is taken by another student from a different Project. 
	 * 
	 * @param project	the Project that a student should be moved to
	 * @param ass		the Integer-Matrix representing the current assignment
	 * @param step		specifies how much loss in the assignment value is allowed for each move.
	 * @return	true if a student pair was moved
	 * 			false if no student pair was found able to be moved with the specified decrease in quality
	 */
	private static boolean moveStudentPair(Project project, int[][] ass, int step){
		int[] p = findStudentPairToMove(project, ass, 1);
		if(p[0] >= 0){
			ass[p[1]][1] = ass[p[0]][1];
			ass[p[0]][1] = ProjectTable.get(project.projectID)[0] 
			            + countStudents(project);
			return true;
		}
		return false;
	}
	
	/**
	 * Finds a student that has pr as his voteIndex-th wish and is not in a minimally filled project
	 * 
	 * @param pr		the Project that a student should be moved to
	 * @param ass		the Integer-Matrix representing the current assignment
	 * @param voteIndex	specifies how much loss in the assignment value is allowed.
	 * @return	the index of the student that can be moved if such was found
	 * 			-1 if no such student has been found
	 */
	private static int findStudentToMove(Project pr, int[][] ass, int voteIndex) {
		for(int j=0; j<StudentList.length; j++){
			Student st = lookUpStudent(j);
			Project lookedUpPr = lookUpProject(ass[j][1]);
			if(st.votes[voteIndex].equals(pr.projectID) & countStudents(lookedUpPr) > lookedUpPr.minStudents){
				return j;
			}
		}
		return -1;
	}
	
	/**
	 * Finds a student that has pr as his voteIndex-th wish and is in a minimally filled project,
	 * find another student to replace him there
	 * 
	 * @param pr		the Project that a student should be moved to
	 * @param ass		the Integer-Matrix representing the current assignment
	 * @param voteIndex	specifies how much loss in the assignment value is allowed with each move
	 * @return	the indexes of the students that can be moved if such were found in form of a two-value array
	 * 			{-1,-1} if no such students have been found
	 */
	private static int[] findStudentPairToMove(Project pr, int[][] ass, int voteIndex){
		for(int n=0; n<=voteIndex; n++){	
			for(int j=0; j<StudentList.length; j++){
				Student st = lookUpStudent(j);
				Project lookedUpPr = lookUpProject(ass[j][1]);
				if(st.votes[voteIndex].equals(pr.projectID) & countStudents(lookedUpPr) >= lookedUpPr.minStudents){
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

	//******************************************************************//
	//MR. NEDA'S IMPLEMENTATION OF THE HUNGARIAN ALGORITHM STARTING HERE//
	//******************************************************************//
	
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
