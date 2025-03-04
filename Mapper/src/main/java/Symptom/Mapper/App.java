package Symptom.Mapper;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import com.opencsv.CSVWriter;
import com.oracle.svm.core.annotate.Hybrid.Array;


public class App {
	

	public static void main(String[] args) throws IOException {
		
		BufferedReader input = null;
		BufferedWriter resultFile = null;
		TreeSet<String> allSymptoms = new TreeSet<String>();
		
		try {
			input = new BufferedReader(new FileReader("/home/tudor/Documents/eclipseWorkspace/Mapper/ChronicDiseases.txt"));
			resultFile = new BufferedWriter(new FileWriter("/home/tudor/Documents/eclipseWorkspace/Mapper/formatedChronicDiseases.txt"));
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
		
		//System.out.println(allSymptoms.size());
		//Max 20k lini si 100 de coloane
		mapDiseasesWithHelper(allSymptoms);
		
		
		
	}

	private static void mapDiseasesWithHelper(TreeSet<String> allSymptoms) throws IOException {
		
		ArrayList<String> symptomsArray = new ArrayList<String>(allSymptoms);
		BufferedReader input = new BufferedReader(new FileReader("/home/tudor/Documents/eclipseWorkspace/Mapper/formatedChronicDiseases.txt"));
		BufferedReader inputHelper = new BufferedReader(new FileReader("/home/tudor/Documents/eclipseWorkspace/Mapper/formatedDiseasesQ.txt"));
		
		CSVWriter csvWriter = new CSVWriter(new FileWriter("/home/tudor/Documents/eclipseWorkspace/Mapper/mappedChronicDiseases.csv"), '\t', 
                CSVWriter.NO_QUOTE_CHARACTER, 
                CSVWriter.DEFAULT_ESCAPE_CHARACTER, 
                CSVWriter.DEFAULT_LINE_END);
		//String[][] mappedSymptoms = new String[20000][100];
		List<String[]> mappedSymptoms = new ArrayList<String[]>();
		
		//Column Names
		int lineIdx = 0;
		mappedSymptoms.add(new String[100]);
		for(int i = 0;i<symptomsArray.size();i++) {
			mappedSymptoms.get(lineIdx)[i] = symptomsArray.get(i);
		}
		lineIdx++;
		
		//Column name is ignorede
		String inputHelpString = inputHelper.readLine();
		String inputString = input.readLine();
		inputString = input.readLine();   /// first data line
		inputHelpString = inputHelper.readLine();
		
		while(inputString != null ) {
			mappedSymptoms.add(new String[100]); //// Add new line in file
			
			//System.out.println(inputString);
			//Daca linia contine missing
			//Daca DiseaseQ e true si avem missing pt Disease, atunci nu stim
			//Dar daca DiseaseQ e false si avem missing pt Diseasse, atunci valoarea reala e False pt rand
			if(inputString.equals("missing") && inputHelpString.equals("True")) {
				//Pun peste tot missing, mai putin la coloana missing care se seteaza la True
				for(int i = 0;i<symptomsArray.size();i++) {
					mappedSymptoms.get(lineIdx)[i] = "missing";
				}
				mappedSymptoms.get(lineIdx)[symptomsArray.indexOf("missing")] = "True";
			}else {
				
				//Initializez toata linia cu false
				for(int i = 0;i<symptomsArray.size();i++) {
					mappedSymptoms.get(lineIdx)[i] = "False";
				}
				
				//Voi pune true-uri doar daca doar coloana de diseases NU e missing
				//Aici nu mai trebuie sa se tina cont de DisseaseQ
				if(inputString.equals("missing") == false ) {
					//Pt simptomele care apar suprascriu cu True
					for(String s : inputString.split(", ")) {
						mappedSymptoms.get(lineIdx)[symptomsArray.indexOf(s)] = "True";
					}
				}
				
				
			}
			inputString = input.readLine();
			inputHelpString = inputHelper.readLine();
			lineIdx++;
		}
		
		csvWriter.writeAll(mappedSymptoms);
		System.out.println("Am terminat");
		
		input.close();
		inputHelper.close();
		csvWriter.close();
		
	}

}
