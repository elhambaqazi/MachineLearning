import java.util.ArrayList;
import java.util.HashMap;

public class Utilities {
	
	public HashMap<String,ArrayList<ArrayList<String>>> reducedMap(ArrayList<ArrayList<String>> data, String bestAttribute){
		
		HashMap<String, ArrayList<ArrayList<String>>> reducedMap = new HashMap<String, ArrayList<ArrayList<String>>>();
		int index = data.get(0).indexOf(bestAttribute);
		for(int i=1;i<data.size();i++){
			if(data.get(i).get(index).equalsIgnoreCase("0")){
				if(reducedMap.containsKey("0")){
					reducedMap.get("0").add(data.get(i));
				}
				else{
					ArrayList<ArrayList<String>> dataAdd = new ArrayList<ArrayList<String>>();
					dataAdd.add(data.get(0));
					dataAdd.add(data.get(i));
					reducedMap.put("0",dataAdd);
				}

			}
			else{
				if(reducedMap.containsKey("1")){
					reducedMap.get("1").add(data.get(i));
				}
				else{
					ArrayList<ArrayList<String>> dataAdd = new ArrayList<ArrayList<String>>();
					dataAdd.add(data.get(0));
					dataAdd.add(data.get(i));
					reducedMap.put("1",dataAdd);
				}
			}
		}

		return reducedMap;
		
	}
}
