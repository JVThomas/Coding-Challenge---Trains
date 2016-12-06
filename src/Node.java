import java.util.HashMap;

public class Node {
	private String nodeName;
	//connectingNodes is a HashMap with a string key and a value of a Route object
	//Route consists of the connecting node and the distance to that node
	HashMap<String,Route> routeList;
	
	//constructor with no argument
	public Node(){
		nodeName = "";
		routeList = new HashMap<String,Route>();
	}
	
	//overloaded constructor with string Name argument
	public Node(String name){
		nodeName = name;
		routeList = new HashMap<String,Route>();
	}
	
	//getter for nodeName
	public String getNodeName(){
		return nodeName;
	}
	
	//getter for conenctingNodes
	public Route getTargetRoute(String targetNodeName){
		return routeList.get(targetNodeName);
	}
	
	public HashMap<String,Route> getRouteList(){
		return routeList;
	}
	
	//adds a Route object to the connectingNode HashMap
	public void addNewRoute(Node targetNode, int distance){
		//creates local instance of data to be inserted into HashMap
		Route newRoute = new Route(targetNode, distance);
		routeList.put(targetNode.getNodeName(), newRoute);
	}
}
