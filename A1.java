import java.io.*;
import java.util.*;

// Check that it works with java 1.8
// Ensure it loads with java A1 input.txt
// Ensure it has correct output

// Main Class
public class A1 {
	// Main Run
	static int dispSize = 0;

	public static void main(String[] args) {
		// !Remember to convert this to an argument
		String fileName = "input.txt";

		List<Process> processes = readInput(fileName);

		FCFS fcfs = new FCFS(dispSize);
		System.out.print(fcfs.simulate(processes));

	}

	// Precondition: There is a file with the correct format
	// Postcondition: An array of Process objects is created.
	public static List<Process> readInput(String fileName) {
		List<Process> ps = new ArrayList<Process>();

		File inFile = new File(fileName);


		try {
			Scanner myReader = new Scanner(inFile);
			
			//Set up the DISP/Check format of file
			if (!myReader.nextLine().equals("BEGIN")){
				System.out.println("Error with format of file");
				myReader.close();
				return ps;
			}
			//Empty Line
			myReader.nextLine();

			//Get the value of the next line, with "DISP: " removed.
			String dispString = myReader.nextLine().replace("DISP: ", "");
			dispSize = Integer.valueOf(dispString);
			System.out.println(dispString);
			
			//END LINE
			myReader.nextLine();

			//Input format:
			// ID: p1
			// Arrive: 0
			// ExecSize: 10
			// Priority: 4
			// END
			while(myReader.hasNextLine()){
				//Blank line at start of each
				myReader.nextLine();

				if (myReader.hasNext("EOF")){
					break;
				}
				String id = myReader.nextLine().replace("ID: ", "");
				int arrive = Integer.valueOf(myReader.nextLine().replace("Arrive: ", ""));
				int execSize = Integer.valueOf(myReader.nextLine().replace("ExecSize: ", ""));
				int priority = -1;
				//If there is no priority
				if (!myReader.hasNext("END")){
					priority = Integer.valueOf(myReader.nextLine().replace("Priority: ", ""));
				}

				//END
				myReader.nextLine();

				ps.add(new Process(id, arrive, execSize, priority));
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Error, cannot find file");
		}
		return ps;
	}

}
