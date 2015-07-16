//Node class to create a node object with various attributes
public class Node {
//Declaring private variables 
	private int name; 
	private boolean mark;
	//Construtor for Node object
	public Node (int name) {
		this.name = name; 
		mark = false;  //Setting the current mark to false 
	}
	//Setter method for the mark of the node
	public void setMark (boolean mark) {
		this.mark = mark;
	}
	//Getter method for the mark of the node
	boolean getMark () {
		return mark;
	}
	//Getter method of the name of the the node, int value 
	public int getName () {
		return name;
	}
}//End of node class
