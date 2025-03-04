import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainClass {
	

	public static void main(String[] args) {
		
		BufferedReader inputDischarge = null;
		BufferedReader inputDeath = null;
		BufferedWriter resultFileDischarge = null;
		BufferedWriter resultFileDeath = null;
		
		try {
			inputDischarge = new BufferedReader(new FileReader("/home/tudor/Documents/eclipseWorkspace/FormatDischargeDeath/Discharge.txt"));
			inputDeath = new BufferedReader(new FileReader("/home/tudor/Documents/eclipseWorkspace/FormatDischargeDeath/Death.txt"));
			resultFileDischarge = new BufferedWriter(new FileWriter("/home/tudor/Documents/eclipseWorkspace/FormatDischargeDeath/formatedDischarge.txt"));
			resultFileDeath = new BufferedWriter(new FileWriter("/home/tudor/Documents/eclipseWorkspace/FormatDischargeDeath/formatedDeath.txt"));
		} catch (IOException e) {
			System.out.println("File creation exception found");
			e.printStackTrace();
		}
		
		///// File date processing 
		try {
			//Column name and stuff
			String dischargeString = inputDischarge.readLine();
			String deathString = inputDeath.readLine();
			//ColumnName
			resultFileDischarge.write(dischargeString + "\n");
			resultFileDeath.write(deathString + "\n");
			
			dischargeString = inputDischarge.readLine();
			deathString = inputDeath.readLine();   /// first data line
			while(dischargeString != null || deathString != null) {
				
				//Daca nicio coloana nu are date, missing peste tot
				if(dischargeString.contains("Missing") && deathString.contains("Missing")) {
					resultFileDischarge.write("missing\n");
					resultFileDeath.write("missing\n");
					
				//Daca discharge nu are date, dar deathq are date, discharge = !death	
				}else if(dischargeString.contains("Missing")){
					
					if(deathString.contains("True")) {
						resultFileDischarge.write("False\n");
						resultFileDeath.write("True\n");
					}else {
						resultFileDischarge.write("True\n");
						resultFileDeath.write("False\n");
					}
				
				//Daca death nu are date, dar discharge are date, death = !discharge	
				}else if (deathString.contains("Missing")){
					
					if(dischargeString.contains("True")) {
						resultFileDischarge.write("True\n");
						resultFileDeath.write("False\n");
					}else {
						resultFileDischarge.write("False\n");
						resultFileDeath.write("True\n");
					}
					
				}else {
					//Daca exista in ambele date se vor pastra
					if(dischargeString.contains("True")) {
						resultFileDischarge.write("True\n");
					}else {
						resultFileDischarge.write("False\n");
					}
					
					//Daca exista in ambele date, se vor  pastra
					if(deathString.contains("True")) {
						resultFileDeath.write("True\n");
					}else {
						resultFileDeath.write("False\n");
					}
					
				}

				dischargeString = inputDischarge.readLine();
				deathString = inputDeath.readLine(); 
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			inputDischarge.close();
			inputDeath.close();
			resultFileDischarge.close();
			resultFileDeath.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Am terminat");
	}

}
