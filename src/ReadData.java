import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ReadData {

	private ArrayList<ArrayList<String>> dataSet;
	

	public ArrayList<ArrayList<String>> readData(String fileName) throws IOException {
		dataSet = new ArrayList<ArrayList<String>>();

		File file = new File(fileName);
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				ArrayList<String> arrList = new ArrayList<String>(Arrays.asList(line.split(",")));
				dataSet.add(arrList);
			}
		} catch (FileNotFoundException e) {
			System.out.println("File Not found " + file.toString());
		}
		
		return dataSet;
	}
	

}
