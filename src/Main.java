import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

	public static void main(String args[]) {

	if (args.length < 5) {
			System.out.println("Please pass arguments and try again.");

		} else {
			boolean toCount;
			int L = Integer.parseInt(args[0]);
			int K = Integer.parseInt(args[1]);
			String fileNameTrainingData = args[2];
			String fileNameValidationData = args[3];
			String fileNameTest = args[4];
			String print = args[5];
		
			if (print.equalsIgnoreCase("Yes")) {
				toCount = Boolean.TRUE;
			} else {
				toCount = Boolean.FALSE;
			}
		
			ArrayList<ArrayList<String>> trainingDataSet = new ArrayList<ArrayList<String>>();
			ArrayList<ArrayList<String>> testDataSet = new ArrayList<ArrayList<String>>();
			ArrayList<ArrayList<String>> validationDataSet = new ArrayList<ArrayList<String>>();
			ArrayList<String> attributeList = new ArrayList<String>();


			ReadData rd = new ReadData();
			try{
			trainingDataSet = rd.readData(fileNameTrainingData);
			testDataSet = rd.readData(fileNameValidationData);
			validationDataSet = rd.readData(fileNameTest);

			attributeList = trainingDataSet.get(0);

			BuildTree bt = new BuildTree();
			VarianceTree vt = new VarianceTree();
			PrunedTree pt = new PrunedTree();

			Node treeNode = bt.buildTree(trainingDataSet, attributeList);
			Node varianceTreeNode = vt.buildVarianceTree(trainingDataSet, attributeList);

			System.out.println(
					"ACCURACY OF TREE WITH first HEURISTIC ON TEST DATA: " + bt.accuracyOfTree(treeNode, testDataSet));
			System.out.println("ACCURACY OF TREE WITH second HEURISTIC ON TEST DATA: "
					+ bt.accuracyOfTree(varianceTreeNode, testDataSet));
			System.out.println();
		
			Node prunedTreeNode = pt.prunedTree(treeNode, L, K, validationDataSet);
			Node prunedTreeVarNode = pt.prunedTree(varianceTreeNode, L, K, validationDataSet);

			System.out.println("ACCURACY OF Pruned TREE WITH first HEURISTIC ON TEST DATA: "
					+ bt.accuracyOfTree(prunedTreeNode, testDataSet));
			System.out.println("ACCURACY OF Pruned TREE WITH second HEURISTIC ON TEST DATA: "
					+ bt.accuracyOfTree(prunedTreeVarNode, testDataSet));
			System.out.println();
			
			if(toCount){
				System.out.println("------------Tree Using First Heuristic--------------");
				System.out.println();
				treeNode.print();
				System.out.println();
				System.out.println("------------Tree Using Second Heuristic--------------");
				System.out.println();
				varianceTreeNode.print();
				System.out.println();
			}
			}catch(FileNotFoundException e){
				System.out.println("File Not found");
			}catch(IOException e){
				System.out.println("Error");
			}
		}
	}
}
