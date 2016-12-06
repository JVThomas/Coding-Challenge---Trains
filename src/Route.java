public class Route {
	Node connectingNode;
	int distance;
	
	//basic constructor with no arguments passed
	public Route(){
		distance = 0;
		connectingNode = null;
	}
	
	//overloaded constructor with Node and int arguments
	public Route(Node targetNode, int targetDistance){
		distance = targetDistance;
		connectingNode = targetNode;
	}
	
	//setter for connectingNode, requires Node argument
	public void setConnectingNode(Node targetNode){
		connectingNode = targetNode;
	}
	
	//getter for connectingNode
	public Node getConnectingNode(){
		return connectingNode;
	}
	
	//setter for distance, requires int argument
	public void setDistance(int nodeDistance){
		distance = nodeDistance;
	}
	
	//getter for distance
	public int getDistance(){
		return distance;
	}

}
