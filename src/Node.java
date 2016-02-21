
public class Node {
	private Node leftChild;
	private Node rightChild;
	private String name;
	private String leafValue;
	private boolean leafNode;
	private int nodeNumber;
	private static int depth = -1;
	

	public Node() {
		super();
	}

	public Node(String value){
		this.leafValue = value;
		this.setLeafNode(Boolean.TRUE);
	}
	
	public Node(String bestAttribute, Node leftChild, Node rightChild){
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.name = bestAttribute;
		this.setLeafNode(Boolean.FALSE);
	}
	

	public void setLeafNode(boolean leafNode) {
		this.leafNode = leafNode;
	}
	
	public Node getLeft() {
		return leftChild;
	}

	public void setLeft(Node left) {
		this.leftChild = left;
	}

	public Node getRight() {
		return rightChild;
	}

	public void setRight(Node right) {
		this.rightChild = right;
	}
	public String getLeafValue() {
		return leafValue;
	}

	public void setLeafValue(String leafValue) {
		this.leafValue = leafValue;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public boolean isLeafNode() {
		return leafNode;
	}

	
	public int getNodeNumber() {
		return nodeNumber;
	}

	public void setNodeNumber(int nodeNumber) {
		this.nodeNumber = nodeNumber;
	}

	
	public void print(){
		depth++;
		if(this.name == null){
			System.out.print(" : " + leafValue);
		}
		else{
			System.out.println();
			for(int i=0; i<depth;i++){
				System.out.print(" | ");
			}
			System.out.print(name + " = 0");
		}

		if(leftChild != null){
			leftChild.print();
			if(this.name == null){
				System.out.print(" : " + leafValue);
			}
			else{
				System.out.println();
				for(int i=0; i<depth;i++){
					System.out.print(" | ");
				}
				System.out.print(name + " = 1" );
			}
			rightChild.print();
		}
		depth--;
	}

}
