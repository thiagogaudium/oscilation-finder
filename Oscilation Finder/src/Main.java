import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {
	private static double SPEED_THRESHOLD_KM_H = 100.0;
	
	public static void main(String[] args) {
		List<List<String>> records = new ArrayList<List<String>>();
		try (BufferedReader br = new BufferedReader(new FileReader("/Users/thiagofsr/Development/OscilationData/os.txt"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		        String[] values = line.split(",");
		        records.add(Arrays.asList(values));
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean found = false;
		if(records.size() > 1) {
			 try {
				GPSPoint previous = GPSPoint.getGPSPointFromList(records.get(0));
				for(List<String> point: records.subList(1, records.size() - 1)) {
					 GPSPoint current = GPSPoint.getGPSPointFromList(point);
					 double speed = current.calculateSpeedFromPrevious(previous);
					 if(speed > SPEED_THRESHOLD_KM_H) {
						 found = true;
						 System.out.println(previous.lat + "," + previous.lng + "," + previous.date);
						 System.out.println(current.lat + "," + current.lng + "," + current.date);
						 System.out.println(speed + "km/h");
						 System.out.println("------------------------------------------------------");
					 }
					 
					 previous = current;
					 
				 }
				if(!found) {
					System.out.println("No inconsistencies found.");
					
				}
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 
			
		}

	}

}
