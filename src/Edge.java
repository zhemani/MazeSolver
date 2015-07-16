//Class to for Edge with various attributes 
public class Edge {
//Declaring private variables which will be used in the class
	private String type, label = "";
	private Node u, v;
	//Constructor for Edge object
	public Edge ( Node u, Node v, String type) {
		this.u = u;
		this.v = v;
		this.type = type;
	}
	//Getter method for the first endpoint of Edge
	public Node firstEndpoint () {
		return u;
	}
	//Getter method for the second endpoint of Edge
	public Node secondEndpoint () {
		return v;
	}
	//Getter method for the type of Edge
	public String getType () {
		return type;
	}
	//Setter method for the label of Edge
	public void setLabel (String label) {
		this.label = label;
	}
	//Getter method for the label of Edge
	public String getLabel () {
		return label;
	}
}//End of Edge class 
