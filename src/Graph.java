import java.util.Iterator;
import java.util.Stack;
//Class to implement a Graph 
public class Graph implements GraphADT {
//Declaring private variables to be used throughout the class
	private Node graph [];
	private Edge edge [][];
	private int n;
//Constructor for the graph object	
	public Graph (int n) {
		this.n = n;
		graph = new Node [n]; //Graph is a single dimension array to store nodes 
		edge = new Edge [n][n]; //Edge is double dimension array
		for (int i = 0; i < n; i++) { //Setting all values of graph to increase by 1; Positive integers
			graph[i] = new Node(i);
		}	
	}
	//Method to insert an edge into the graph
	public void insertEdge(Node nodeu, Node nodev, String type) throws GraphException {
		int nameU = nodeu.getName(); //Get the integer value of nodeu
		int nameV = nodev.getName(); //Get the integer value of nodev
		if (nameU < 0 || nameV < 0 || nameU > n-1 || nameV > n-1) {
			throw new GraphException ("Node u or v does not exist"); //Throw a graph exception if the node is out of bounds
		}
		if (edge[nameU][nameV] != null) { //If the edge is not null, it means it already exists within the graph
			throw new GraphException ("This Edge already exists");
		}
		edge[nameU][nameV] = new Edge(nodeu, nodev, type); // Insert edge object in the position going one way
		edge[nameV][nameU] = new Edge(nodeu, nodev, type); // Insert edge object in the position going the other way
	}
//Method to get a node from the graph
	public Node getNode(int name) throws GraphException {
		if (name < 0 || name > n-1) { //If the node is out of bound then it does not exist
			throw new GraphException("This node does not exist");
		}
		return graph[name]; //Return the node 
	}
//Method to return edges incident on a specified node 
	public Iterator<Edge> incidentEdges(Node u) throws GraphException {
		int nameU = u.getName(); //Get the integer value of the node
		if (nameU < 0 || nameU > n-1) { //If it is out of bounds then the node does not exist
			throw new GraphException("This node does not exist");
		}
		Stack<Edge> edgeList = new Stack<Edge>(); //Create a stack object of Edges
		for (int i = 0; i < n; i++) { 
			if (edge[nameU][i] != null) { //If the node has edges to it
				edgeList.push(edge[nameU][i]); //Push the edge onto the stack
			}
		}
		if (!edgeList.isEmpty()) { //If the stack is not empty then return an iterator 
			Iterator <Edge> x = edgeList.iterator();
			return x;
		}
		return null; //Only return null, if there are no edges 
	}
//Method to get a specified edge from the graph
	public Edge getEdge(Node u, Node v) throws GraphException {
		int nameU = u.getName(); //Get integer value of node u
		int nameV = v.getName(); //Get integer value of node v
		if (nameU < 0 || nameV < 0 || nameU > n-1 || nameV > n-1) { //If the node is out of bounds then it does not exist
			throw new GraphException ("Node u or v does not exist");
		}
		if (areAdjacent(u,v) == false) { //If these nodes are not adjacent then the particular edge does not exist
			throw new GraphException ("There is no edge that exists between these nodes");
		}
		return edge[nameU][nameV]; //Return the edge connected to the two points
	}
//Method which returns true or false dependent on whether nodes are adjacent to each other 
	public boolean areAdjacent(Node u, Node v) throws GraphException {
		int nameU = u.getName(); //Get integer value of node u
		int nameV = v.getName(); //Get integer value of node v
		if (nameU < 0 || nameV < 0 || nameU > n-1 || nameV > n-1) { //If the node is out bounds then it does not exist
			throw new GraphException ("Node u or v does not exist");
		}
		if (edge[nameU][nameV] == null) { //If the edge does not exist between the two nodes then they are not adjacent 
			return false;
		}
		return true; //Otherwise return true as they are adjacent
	}
} //End of graph class
