import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Stack;
//Class to create and find a solution to a particular map
public class Map {
//Declare private variables to be used throughout the class 
	private Graph graph;
	private int s, l, w, k, start, end, n; //Numerous variables for size, length, width, dollarAmount, startNode, endNode, sizeOfGraph
	private Node startNode, endNode; //Start and end node reference 
//Constructor to create the map inputted 
	public Map (String inputFile) throws MapException, GraphException {
		try { //Try Buffer Reader
			BufferedReader input = new BufferedReader(new FileReader(inputFile));
			//First 4 lines in the file are integers, so convert to integers and assign to variables 
			s = Integer.valueOf(input.readLine());
			l = Integer.valueOf(input.readLine());
			w = Integer.valueOf(input.readLine());
			k = Integer.valueOf(input.readLine());
//Get the size of the graph
			n = l * w;
			graph = new Graph(n);
			int nodeNumber = -1;
//Infinite loop to create the map 
			for (;;) {
				String temp = input.readLine(); 
				char c; //Variable to store temp 
				if (temp == null) {
					input.close();  //Close the buffer if the input is null
					break; 
				}
				nodeNumber++; //Increase the number of nodes
				for (int i = 0; i < temp.length(); i++) { 
					c = temp.charAt(i); //Make c the first character 
					if (c == 's') {//If c is s then it is the starting node  
						startNode = graph.getNode(((nodeNumber / 2) * (w)) + ((i) / 2));
						break;
						
					} 
					else if (c == 'e') { //If c is e then it is the ending node 
						endNode = graph.getNode(((nodeNumber / 2) * (w))
								+ ((i) / 2));
						break;
						} 
					else if (c == 'h') { //If c is h then it is an edge; insert edge that is a toll
						graph.insertEdge(graph.getNode(((nodeNumber / 2) * (w)) + ((i - 1) / 2)), graph.getNode(((nodeNumber / 2) * (w)) + ((i + 1) / 2)), "toll");
						break;
					} 
					else if (c == 'v') { //If c is v then it is an edge; insert edge that is a toll
						graph.insertEdge(graph.getNode((((nodeNumber - 1) / 2) * (w)) + ((i) / 2)), graph.getNode((((nodeNumber + 1) / 2) * (w)) + ((i) / 2)), "toll");
						break;
					}  
					else if (c == '-') { //If c is - then it is an edge; insert edge that is a free road
						graph.insertEdge(graph.getNode(((nodeNumber / 2) * (w)) + ((i - 1) / 2)), graph.getNode(((nodeNumber / 2) * (w)) + ((i + 1) / 2)), "free");
						break;
					} 
					else if (c == '|') { //If c is | then it is an edge; insert edge that is a free rode 
						graph.insertEdge(graph.getNode((((nodeNumber - 1) / 2) * (w)) + ((i) / 2)), graph.getNode((((nodeNumber + 1) / 2) * (w)) + ((i) / 2)), "free");
						break;
					} 
					else if (c == 'o') { //If c is 0, then it is an intersection so break the loop
						break;
					} 
					else if (c == ' ') { //If c is ' ' then it is a block of houses so break the loop
						break;
					}
				} 
			} 
		} 
		catch (IOException e) { //Catch the exception if the input file does not exist 
			throw new MapException("Input file does not exist");
		} 
	} //End of Map constructor 
	//Method to return a graph created in the map constructor 
	public Graph getGraph () throws MapException {
		if (graph == null) { //If the graph is null then throw an exception
			throw new MapException("This graph is empty");
		}
		return graph; //Else return the graph
	}
	//Find path method to return an iterator with correct integer values for the path to end 
	public Iterator findPath() throws GraphException {
		Node st = graph.getNode(start); //Get the starting node
		Node en = graph.getNode(end); //Get the ending node 
		Stack<Integer> S = DFS(st, en); 
		return S.iterator(); //Return an iterator of stack containing the correct nodes 
	}
//Private method to do a depth first traversal and insert correct nodes into a stack while marking and labeling them
	private Stack<Integer> DFS(Node v, Node z) throws GraphException {

		Stack<Integer> s = new Stack<Integer>(); //Create a stack to keep all node names

		s.push(v.getName()); //Insert the starting node into the stack 
		if (v.getName() == z.getName()) { //If the start and end node are the same
			v.setMark(true);
			return s; //return the stack
		}
		  else {
			for (Iterator<Edge> check = graph.incidentEdges(v); check.hasNext();) { //For loop until the node has edges incident on it 
				Edge e1 = check.next(); //Get the next edge 

				if (v.getMark() == false) { //If the mark is false and is not visited 
					if (k > 0 && e1.getType().equals("toll")) {
						k = k - 1; //If the node is toll, then decrease the value of k by $1
					}
					Node n2;
					if (v == e1.firstEndpoint()) { //If v is the first end point
						n2 = e1.secondEndpoint(); //Then n2 can be the secondn end point of the edge
					} else {
						n2 = e1.firstEndpoint();
					}
					if (n2.getMark() == false) { //If the mark of the second endpoint is false 
						e1.setLabel("discovery"); //It is not visited so label it as discovery
						s.push(n2.getName()); //Push the integer onto the stack
						n2.setMark(true); //Set the mark to true as it is now visited 
					}
					if (k > 0) { //If there is still money left then recursively call DFS method 
						DFS(e1.secondEndpoint(), z);
						if (e1.secondEndpoint() != z && e1.getType().equals("toll")) {
							k = k + 1; //Increase the toll by 1 if no path is found and the last edge is a toll 
						}
					}
				} 
				else if (v.getMark() == true) { //If the mark of the node is true 
					// s.pop();
					if (e1.getType().equals("toll") || e1.getType().equals("free")) {
						e1.setLabel("back"); //Set the label as back as it has been visited 
					}
				}
			}
		}
		if (s != null) {
			return s; //Return the stack containing these nodes
		} 
		return null; //If the stack is empty then return null
	}
} //End of map class 
