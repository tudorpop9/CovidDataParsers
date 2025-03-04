import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;


public class MainClass {
	

	public static void main(String[] args) {
		
		BufferedReader input = null;
		BufferedWriter resultFile = null;
		TreeSet<String> allSymptoms = new TreeSet<String>();
		
		try {
			input = new BufferedReader(new FileReader("/home/tudor/Documents/eclipseWorkspace/SymptomsFilter/Symptoms.txt"));
			resultFile = new BufferedWriter(new FileWriter("/home/tudor/Documents/eclipseWorkspace/SymptomsFilter/formatedSymptoms.txt"));
		} catch (IOException e) {
			System.out.println("File creation exception found");
			e.printStackTrace();
		}
		
		///// File date processing 
		try {
			//Column name and stuff
			String inputString = input.readLine();
			//ColumnName
			resultFile.write(inputString + "\n");
			inputString = input.readLine();   /// first data line
			while(inputString != null ) {
				if(inputString.contains("Missing")) {
					resultFile.write("missing\n");
					allSymptoms.add("missing");
				}else {
				
					inputString = inputString.replaceAll("[{}\"]*", "");
					if(inputString.contains("Quantity")) {
						inputString = inputString.replaceAll("]", "");
						inputString = inputString.replaceAll("\\[", "");
						inputString = inputString.replaceAll("Quantity[0-9]+[.][0-9]*, DegreesCelsius", "");
						inputString = inputString.replaceAll("QuantityInterval[0-9]+, [0-9]+, DegreesCelsius", "");
						inputString = inputString.replaceAll(", ,", ",");
					}
					resultFile.write(inputString + '\n');
					
					//Pt simptomele unice
					for(String s : inputString.split(", ")) {
						allSymptoms.add(s);
					}
					
				}
				inputString = input.readLine();
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			input.close();
			resultFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
