import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PrunedTree {
	private int numberNonLeafNodes = 0;

	Node treeBest;
	Node treePrime;
	
	public Node prunedTree(Node root,int l, int k,ArrayList<ArrayList<String>> dataSet){
		
		BuildTree bt = new BuildTree();
		treeBest = new Node();
		copyTree(root, treeBest);
		double bestAccuracyOfTree = bt.accuracyOfTree(treeBest, dataSet);
		treePrime = new Node();
		for(int i=1; i<=l;i++){
			copyTree(root, treePrime);
			Random random = new Random();

			int M = 1 + random.nextInt(k);
			for(int j=0; j<=M; j++){
				calculateNumLeafNodes(treePrime);			//sets the number of leafnodes in the class variable
				int N = getNumberNonLeafNodes();
				if(N>1){
					int P = random.nextInt(N) + 1;
					replaceNode(treePrime, P);
				}
				else{
					break;
				}
			}
			double accuracyOfPrimeTree = bt.accuracyOfTree(treePrime, dataSet);
			if (accuracyOfPrimeTree > bestAccuracyOfTree){
				bestAccuracyOfTree = accuracyOfPrimeTree;
				copyTree(treePrime, treeBest);
				//	treeBest = treePrime;
			}
		}
		return treeBest;
	}
	
	public void copyTree(Node first, Node second){
		second.setLeafNode(first.isLeafNode());
		second.setName(first.getName());
		second.setLeafValue(first.getLeafValue());

		if(!first.isLeafNode()){
			second.setLeft(new Node());
			second.setRight(new Node());

			copyTree(first.getLeft(), second.getLeft());
			copyTree(first.getRight(), second.getRight());

		}
	}
	
	public int getNumberNonLeafNodes() {		//help taken from stackoverflow.com on how to preserve the static values
		int number = numberNonLeafNodes;
		setNumberNonLeafNodes(0);
		return number;
	}


	public void setNumberNonLeafNodes(int numberNonLeafNodes) {
		this.numberNonLeafNodes = numberNonLeafNodes;
	}


	public void calculateNumLeafNodes(Node root){		//could not think of the recursion base case, so made a continous function
		if(!root.isLeafNode()){								//help from stackoverflow.com
			numberNonLeafNodes++;
			root.setNodeNumber(numberNonLeafNodes);
			calculateNumLeafNodes(root.getLeft());
			calculateNumLeafNodes(root.getRight());
		}
	}

	public List<Node> retrieveLeafNodeList(Node root){
		List<Node> leafNodeList = new ArrayList<>();
		if(root.isLeafNode()){ 
			leafNodeList.add(root);
		}
		else{
			if(!root.getLeft().isLeafNode()){
				retrieveLeafNodeList(root.getLeft());
			}
			if(!root.getRight().isLeafNode()){
				retrieveLeafNodeList(root.getRight());
			}
		}
		return leafNodeList;
	}

	public String calculateMajorityClass(Node root){
		int countZero = 0;
		int countOne = 0;
		String majority = "0";
		List<Node> leafNodes = retrieveLeafNodeList(root);
		for(Node node : leafNodes){
			if(node.getLeafValue().equalsIgnoreCase("1")){
				countOne++;
			}
			else{
				countZero++;
			}
		}
		if(countOne>countZero){
			majority = "1";
		}

		return majority;
	}

	public void replaceNode(Node root, int P){
		if(!root.isLeafNode()){
			if(root.getNodeNumber() == P){
				String leafValueToBeChanged = calculateMajorityClass(root);
				root.setLeafNode(Boolean.TRUE);
				root.setLeft(null);
				root.setRight(null);
				root.setLeafValue(leafValueToBeChanged);
			}
			else{
				replaceNode(root.getLeft(), P);
				replaceNode(root.getRight(), P);
			}

		}
	}

}
