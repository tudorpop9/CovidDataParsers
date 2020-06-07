import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainClass {
	

	public static void main(String[] args) {
		
		BufferedReader input = null;
		BufferedWriter resultFile = null; 
		
		try {
			input = new BufferedReader(new FileReader("/home/tudor/Documents/eclipseWorkspace/FormatTFMiss/inputData.txt"));
			resultFile = new BufferedWriter(new FileWriter("/home/tudor/Documents/eclipseWorkspace/FormatTFMiss/formatedInputData.txt"));
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
				}else {
				
					inputString = inputString.replaceAll("[{}\"]*", "");
					if(inputString.contains("True")) {
						inputString = "True";
					}else {
						inputString = "False";
					}
					resultFile.write(inputString + '\n');
					
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
		System.out.println("Am terminat");
	}

}
