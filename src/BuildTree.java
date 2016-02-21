import java.util.ArrayList;
import java.util.HashMap;

public class BuildTree {


	public Node buildTree(ArrayList<ArrayList<String>> dataSet, ArrayList<String> attributeList) {
		
		Utilities uti = new Utilities();	
		HashMap<String,ArrayList<ArrayList<String>>> reduceMap = new HashMap<String,ArrayList<ArrayList<String>>>();
		
	
		double positiveCount = 0;
		double negativeCount = 0;
		
		for(int i=1; i < dataSet.size();i++){
			if(dataSet.get(i).get(dataSet.get(i).size()-1).equalsIgnoreCase("1")){
				positiveCount++;
			}
			else{
				negativeCount++;
			}
		}
		
		
		if ((attributeList.size() < 1) || (positiveCount == (dataSet.size() - 1))) {
			return new Node("1");
		}else if((negativeCount == (dataSet.size() - 1))){
			return new Node("0");
		}else{
			FindBestAtt fb = new FindBestAtt();
			
			String bestAttribute = fb.BestAttribute(dataSet,attributeList);
			reduceMap = uti.reducedMap(dataSet,bestAttribute);
			
			ArrayList<String> newAttList = new ArrayList<String>();
			for(String val: attributeList){
				if(!val.equalsIgnoreCase(bestAttribute)){
					newAttList.add(val);
				}
			}


			if (reduceMap.size() < 2){
				String value = "0";
				if( positiveCount > negativeCount){
					value = "1";
				}

				return new Node(value);
			}

			
			return new Node(bestAttribute,buildTree(reduceMap.get("0"),newAttList),buildTree(reduceMap.get("1"),newAttList));
		}

	}

	public double accuracyOfTree(Node treeNode, ArrayList<ArrayList<String>> list) {
		
		double accuracy = 0;
		int positiveExamples = 0;

		ArrayList<String> attributes = list.get(0);
		for(ArrayList<String> row : list.subList(1, list.size())){	//looping over each row
			boolean exampleCheck = treeOutputCheck(treeNode, row, attributes);					//passing each row to check if the output of each data instance matches tree traversing
			if(exampleCheck){
				positiveExamples++;
			}
		}
		accuracy = (((double) positiveExamples / (double) (list.size()-1)) * 100.00);

		return accuracy;
	}
	
	public boolean treeOutputCheck(Node root, ArrayList<String> row, ArrayList<String> attributeList){
		Node nodeCopy = root;
		while(true){
			if(nodeCopy.isLeafNode()){
				if(nodeCopy.getLeafValue().equalsIgnoreCase(row.get(row.size()-1))){
					return true;
				}
				else{
					return false;
				}
			}

			int index = attributeList.indexOf(nodeCopy.getName());
			String value = row.get(index);
			if(value.equalsIgnoreCase("0")){
				nodeCopy = nodeCopy.getLeft();
			}
			else{
				nodeCopy = nodeCopy.getRight();
			}
		}
	}

}
