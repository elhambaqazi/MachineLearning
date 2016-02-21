import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class FindBestAtt {
	public String BestAttribute(ArrayList<ArrayList<String>> dataSet,ArrayList<String> attributeList){
		
		String bestAttribute = null;
		HashMap<String,Double> gainMap = new HashMap<String,Double>();
		HashMap<String,ArrayList<String>> map = new HashMap<String,ArrayList<String>>();
		double posClassCount = 0;
		double negClassCount = 0;
		
		for(int i=1; i < dataSet.size();i++){
			if(dataSet.get(i).get(dataSet.get(i).size()-1).equalsIgnoreCase("1")){
				posClassCount++;
			}
			else{
				negClassCount++;
			}
		}
		map = getDataMap(dataSet);
		
			
		for(String attribute : attributeList.subList(0,attributeList.size()-1)){
		
			ArrayList<String> values = map.get(attribute);
		
			double posLeftNode = 0;
			double posRightNode = 0;
			double negLeftNode = 0;
			double negRightNode = 0;
		
			for(int i=0; i < values.size();i++){
				if(values.get(i).equalsIgnoreCase("1")){
					if(map.get("Class").get(i).equalsIgnoreCase("1"))
						posRightNode++;
					else
						negRightNode++;
				}else{
					if(map.get("Class").get(i).equalsIgnoreCase("1"))
						posLeftNode++;
					else
						negLeftNode++;
				}
			}
		
		
		double gain = infoGain(posClassCount,negClassCount,posLeftNode,posRightNode,negLeftNode,negRightNode);
		gainMap.put(attribute, gain);
		}
		
		ArrayList<Double> valueList = new ArrayList<Double>(gainMap.values());
		Collections.sort(valueList);
		Collections.reverse(valueList);
		for(String key: gainMap.keySet()){
			if (valueList.get(0).equals(gainMap.get(key))){
				bestAttribute = key;
				break;
			}
		}
		return bestAttribute;		
	}
	
	private double infoGain(double posRootNode,double negRootNode,double posLeftNode,double posRightNode,double negLeftNode,double negRightNode){
		double totalRoot = posRootNode + negRootNode;
		double rootEntropy = entropy(posRootNode,negRootNode);
		double leftEntropy = entropy(posLeftNode,negLeftNode);
		double rightEntropy = entropy(posRightNode,negRightNode);
		double totalLeft = posLeftNode + negLeftNode;
		double totalRight = posRightNode + negRightNode;		
		double gain = rootEntropy - (((totalLeft/totalRoot) * leftEntropy) + ((totalRight/totalRoot)* rightEntropy)); 
		//System.out.println(gain);
		return gain;
		
	}
	
	private double entropy(double pos,double neg){
		
		double total = pos + neg;
		double posProb = pos/total;
		double negProb = neg/total;
		if(pos == neg){
			return 1;
		}else if(neg == 0 || pos == 0){
			return 0;
		}else{
			double entropy = ((-posProb)*(logOfBase(posProb,2))) + ((-negProb)*(logOfBase(negProb,2)));
			return entropy;
		}
		
	}
	
	private double logOfBase(double num, double base){
		return Math.log(num)/Math.log(base);
	}

	private HashMap<String,ArrayList<String>> getDataMap(ArrayList<ArrayList<String>> dataSet){
		
		HashMap<String,ArrayList<String>> map = new HashMap<String,ArrayList<String>>();	
		ArrayList<String> attList = dataSet.get(0);
		
		for(int i=0;i<attList.size();i++){
			for(int j=1;j<dataSet.size();j++){
				if (map.containsKey(attList.get(i))){
					map.get(attList.get(i)).add(dataSet.get(j).get(i));
				}
				else{
					ArrayList<String> values = new ArrayList<String>();
					values.add(dataSet.get(j).get(i));
					map.put(attList.get(i), values);
				}
			}
		}
		return map;
				
	}

	public String bestAttributeVariance(ArrayList<ArrayList<String>> dataSet, ArrayList<String> attributeList) {
		
		
		String bestAttribute = null;
		HashMap<String,Double> varianceGainMap = new HashMap<String,Double>();
		HashMap<String,ArrayList<String>> varianceMap = new HashMap<String,ArrayList<String>>();
		double posClassCount = 0;
		double negClassCount = 0;
		
		varianceMap = getDataMap(dataSet);
		for(String value : varianceMap.get("Class")){
			if(value.equalsIgnoreCase("1")){
				posClassCount++;
			}
			else{
				negClassCount++;
			}
		}
		
		
		
			
		for(String attribute : attributeList.subList(0,attributeList.size()-1)){
		
			ArrayList<String> values = varianceMap.get(attribute);
		
			double posLeftNode = 0;
			double posRightNode = 0;
			double negLeftNode = 0;
			double negRightNode = 0;
		
			for(int i=0; i < values.size();i++){
				if(values.get(i).equalsIgnoreCase("1")){
					if(varianceMap.get("Class").get(i).equalsIgnoreCase("1"))
						posRightNode++;
					else
						negRightNode++;
				}else{
					if(varianceMap.get("Class").get(i).equalsIgnoreCase("1"))
						posLeftNode++;
					else
						negLeftNode++;
				}
			}
		
		
		double gain = varianceGain(posClassCount,negClassCount,posLeftNode,negLeftNode,posRightNode,negRightNode);
		varianceGainMap.put(attribute, gain);
		}
		
		ArrayList<Double> valueList = new ArrayList<Double>(varianceGainMap.values());
		Collections.sort(valueList);
		Collections.reverse(valueList);
		for(String key: varianceGainMap.keySet()){
			if (valueList.get(0).equals(varianceGainMap.get(key))){
				bestAttribute = key;
				break;
			}
		}
		return bestAttribute;		
	}
	

	public double varianceGain(double rootPositive, double rootNegative, double positiveLeft, double negativeLeft, double positiveRight, double negativeRight){
		double totalRoot = rootPositive + rootNegative;
		double varianceRoot = ((rootPositive * rootNegative)/(totalRoot*totalRoot));
		double totalLeft = positiveLeft + negativeLeft;
		double totalRight = positiveRight + negativeRight;

		double leftVariance = ((totalLeft/totalRoot)*((positiveLeft * negativeLeft)/(totalLeft * totalLeft)));
		double rightVariance = ((totalRight/totalRoot) * ((positiveRight * negativeRight) / (totalRight * totalRight)));

		double gain = varianceRoot - (leftVariance + rightVariance);
		
		return gain;
	}

		
}