import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainClass {
	

	public static void main(String[] args) {
		
		BufferedReader colFile = null;
		BufferedReader dateFile = null;
		BufferedWriter resultFile = null;
		BufferedWriter IgnoreME = null;
		
		try {
			colFile = new BufferedReader(new FileReader("/home/tudor/Documents/eclipseWorkspace/CheckDateAndQCols/Qcol.txt"));
			dateFile = new BufferedReader(new FileReader("/home/tudor/Documents/eclipseWorkspace/CheckDateAndQCols/DateQ.txt"));
			resultFile = new BufferedWriter(new FileWriter("/home/tudor/Documents/eclipseWorkspace/CheckDateAndQCols/formatedQcol.txt"));
			IgnoreME = new BufferedWriter(new FileWriter("/home/tudor/Documents/eclipseWorkspace/CheckDateAndQCols/formatedDateQ.txt"));
		} catch (IOException e) {
			System.out.println("File creation exception found");
			e.printStackTrace();
		}
		
		///// File date processing 
		try {
			//Column name and stuff
			String inputColString = colFile.readLine();
			String DateString = dateFile.readLine();
			//ColumnName
			resultFile.write(inputColString + "\n");
			IgnoreME.write(DateString + "\n");
			
			inputColString = colFile.readLine();
			DateString = dateFile.readLine();   /// first data line
			while(inputColString != null || DateString != null) {
				
				//Daca nicio coloana nu are date, missing peste tot
				if(inputColString.contains("Missing") && !DateString.contains("Missing")) {
					resultFile.write("True\n");
										
				//Daca discharge nu are date, dar deathq are date, discharge = !death	
				}else {
					
					resultFile.write(inputColString + "\n");
				}

				inputColString = colFile.readLine();
				DateString = dateFile.readLine(); 
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			colFile.close();
			dateFile.close();
			resultFile.close();
			IgnoreME.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Am terminat");
	}

}
