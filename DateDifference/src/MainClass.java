import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class MainClass {
	

	public static void main(String[] args) {
		
		BufferedReader dateStart = null, dateStop = null;
		BufferedWriter resultFile = null;
		
		try {
			dateStart = new BufferedReader(new FileReader("/home/tudor/Documents/eclipseWorkspace/DateDifference/dateStart.txt"));
			dateStop = new BufferedReader(new FileReader("/home/tudor/Documents/eclipseWorkspace/DateDifference/dateStop.txt"));
			resultFile = new BufferedWriter(new FileWriter("/home/tudor/Documents/eclipseWorkspace/DateDifference/formatedDateDiff.txt"));
		} catch (IOException e) {
			System.out.println("File creation exception found");
			e.printStackTrace();
		}
		
		///// File date processing 
		try {
			//Column name and stuff
			dateStart.readLine();
			dateStop.readLine();
			Date startDate = null, stopDate = null;
			String startString = dateStart.readLine();
			String stopString = dateStop.readLine();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			
			//ColumnName
			resultFile.write("DaysSymptomsAdmission\n");
			while(startString != null && stopString != null) {
				if(startString.equals("missing")|| stopString.equals("missing")) {
					resultFile.write("missing\n");
				}else {
					startDate = dateFormat.parse(startString);
					stopDate = dateFormat.parse(stopString);
					
					Long difference = stopDate.getTime() - startDate.getTime();
					Long days = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
					resultFile.write(days.toString() + '\n');
					
				}
				startString = dateStart.readLine();
				stopString = dateStop.readLine();
				
			}
			
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				dateStart.close();
				dateStop.close();
				resultFile.close();
			} catch (IOException e) {
				System.out.println("Finally block exception");
			}
			
		}
		
		
		
	}

}
