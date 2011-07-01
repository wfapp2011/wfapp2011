package de.uni_potsdam.hpi.wfapp2011.assignment.client;

public class StudentGenerator {
	String[] ProjectList;
	private static String [] StudentNames = {
		"Adrian Klinger",
		"Alexander Schulze",
		"Angelo Haller",
		"Antonia G\u00f6bel",
		"Astrid Thomschke",
		"Benjamin Rei\u00dfaus",
		"Benjamin Siegmund",
		"Catharina Hahnfeld",
		"Cathleen Ramson",
		"Christoph Matthies",
		"Christoph Oehlke",
		"Christopher Schmidt",
		"Claudia Exeler",
		"Conrad Calmez",
		"Cristian Godde",
		"Daniel Hoffmann",
		"Daniel Sch\u00e4ufele",
		"Dennis Fuhrmann",
		"Dominic Petrick",
		"Eric Seckler",
		"Eric Bustrel Guimatsia Zangue",
		"Fabian Eckert",
		"Felix Jankowski",
		"Felix Kubicek",
		"Georg Kr\u00fcger",
		"Hannes W\u00fcrfel",
		"Hubert Hesse",
		"Ingo Richter",
		"Jakob Zwiener",
		"Jan Ko\u00dfmann",
		"Jan-Peer Rudolph",
		"Janek R\u00e4hl",
		"Janek Ummethum",
		"Jannik Marten",
		"Jeffrey Wichlitzky",
		"Johannes Henning",
		"Johannes Schirrmeister",
		"Jonas Enderlein",
		"Julien Bergner",
		"Kai Rollmann",
		"Katrin Honauer",
		"Leon Berov",
		"Ludwig Kraatz",
		"Ludwig Wilhelm	Wall",
		"Lukas Brand",
		"Lukas Pirl",
		"Lukas Schulze",
		"Magdalena Noffke",
		"Mandy Roick",
		"Marcel Pursche",
		"Maria Graber",
		"Maria Neise",
		"Marius Knaust",
		"Markus Dietsche",
		"Markus Hinsche",
		"Martin Fritzsche",
		"Martin Sch\u00f6nberg",
		"Marvin Keller",
		"Matthias Bastian",
		"Max Bothe",
		"Michael Hopstock",
		"Nicolas Fricke",
		"Patrick L\u00fchne",
		"Patrick Rein",
		"Robert Lehmann",
		"Robert Sch\u00e4fer",
		"Robin J\u00f6rke",
		"Robin Schreiber",
		"Stefan Lehmann",
		"Stefanie Birth",
		"Steffen Grohsschmiedt",
		"Stephan Wunderlich",
		"Tim Spankowski",
		"Tim Sporleder",
		"Tino Junge",
		"Tommy Neubert",
		"Yanina Yurchenko"};
	
	StudentGenerator(String[] PL){
		ProjectList = PL;
	}
	//***********************//
	//Shuffle!				 //
	//***********************//	
    
    // swaps array elements i and j
    public static void exch(String[] a, int i, int j) {
        String swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    // take as input an array of strings and rearrange them in random order
    public static void shuffle(String[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = i + (int) (Math.random() * (N-i));   // between i and N-1
            exch(a, i, r);
        }
    }
    
    //**********************//
    
  //Generate RandomStudents
	public Student[] getList (){
	int numberOfStudents = StudentNames.length;
	Student[] TestStudents = new Student[numberOfStudents];
	for (int i=0; i<numberOfStudents; i++){
		String prename = StudentNames[i].split(" ")[0];
		String name = StudentNames[i].split(" ")[StudentNames[i].split(" ").length-1];
		shuffle(ProjectList);
		String[] votes = {ProjectList[0],ProjectList[1],ProjectList[2],ProjectList[3],ProjectList[4]};
		Student aStudent = new Student(prename, name, votes);
		TestStudents[i]=aStudent;
		}
	return TestStudents;
	}
}
