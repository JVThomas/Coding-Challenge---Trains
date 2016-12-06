import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
	//HashMap to contain all nodes held within graph
	HashMap<String, Node> graphNodes;
	
	public Graph(){
		//initializes a new HashMap
		graphNodes = new HashMap<String, Node>();
	}
	
	//method to add nodes to graph (or update pre-existing nodes)
	public void addNodes(String startNodeName, String endNodeName, int distance){
		//used internal findOrCreate method to check for pre-existing node data
		Node startNode = findOrCreateNode(startNodeName);
		Node endNode = findOrCreateNode(endNodeName);
		
		//update connections on starting node only (due to directed nature of graph)
		startNode.addNewRoute(endNode, distance);
		
		//put statements will either add a new node or overwrite old node data depending 
		//on whether key already exists
		graphNodes.put(startNode.getNodeName(), startNode);
		graphNodes.put(endNode.getNodeName(), endNode);
	}
	
	//method that calculates route distance between nodes as named in given ArrayList
	//if only one element in array, function returns 0
	//if route does not exist, returns -1
	//returns travelDistance if route exists
	public int distanceOfRoute(ArrayList<String> routeNodeNames){
		if(routeNodeNames.size() == 1){
			return 0;
		}
		else{
			int travelDistance = 0;
			for(int i = 0; i < routeNodeNames.size()-1; i++){
				Node currentNode = graphNodes.get(routeNodeNames.get(i));
				Node nextNode = graphNodes.get(routeNodeNames.get(i+1));
				Route routeDetails = currentNode.getTargetRoute(nextNode.getNodeName());
				if(routeDetails == null){
					return -1;
				}
				else{
					travelDistance += routeDetails.getDistance();
				}
			}
			return travelDistance;
		}	
	}
	
	//public method to determine shortest distance
	public int getShortestDistance(String startNodeName, String endNodeName){
		int shortestDistance = 0;
		Node startNode = graphNodes.get(startNodeName);
		ArrayList<String> currentTrip = new ArrayList<String>();
		shortestDistance = this.shortestDistanceAnalysis(startNode, endNodeName, currentTrip, shortestDistance);
		return shortestDistance;
	}
	
	//helper method for shortest distance analysis
	private int shortestDistanceAnalysis (Node currentNode, String endNodeName, ArrayList<String> currentTrip, int minDistance){
		String currentNodeName = currentNode.getNodeName();
		
		//checks currentNode value to see if trip is at end
		//if condition that filters out loops, since its impossible for them to have shortest distance
		if(!currentTrip.contains(currentNodeName) || currentNodeName.equals(endNodeName)){
			
			//create new array and add new node
			//new array needed to avoid shared ref. during recursion
			ArrayList<String> modifiedTrip = new ArrayList<String>(currentTrip);
			modifiedTrip.add(currentNodeName);
			
			//checks to see if iteration is not initial call
			//needed for trips since its possible for startNodeName == endNodeName
			if (currentNodeName.equals(endNodeName) && modifiedTrip.size() > 1){
				int currentDistance = this.distanceOfRoute(modifiedTrip);
				
				//setMinDistance if no value ever assigned or if new value is lower
				if(minDistance == 0 || currentDistance < minDistance){
					minDistance = currentDistance;
				}
			}
			
			//statement if currentNode != endNode (basically if trip is not over)
			else{
				
				//get list of routes, iterate through, and make a recursive call to connecting nodes
				HashMap<String,Route> routeList = currentNode.getRouteList();
				for(String key : routeList.keySet()){
					Node nextNode = routeList.get(key).getConnectingNode();
					minDistance = shortestDistanceAnalysis(nextNode, endNodeName, modifiedTrip, minDistance);
				}
			}
		}
		return minDistance;
	}
	
	//public method to determine number of routes between two nodes within dist. threshold
	public int getNumRoutesWithDistThresh(String startNodeName, String endNodeName, int distanceThreshold){
		ArrayList<String> currentTrip = new ArrayList<String>();
		Node startNode = graphNodes.get(startNodeName);
		return distanceThresholdAnalysis(startNode, endNodeName, 0, distanceThreshold, 0, currentTrip);
		
	}
	
	//helper method for distance threshold analysis
	private int distanceThresholdAnalysis (Node currentNode, String endNodeName, int currentDistance, int distanceThreshold, int numRoutes, ArrayList<String> currentTrip){	
		//gets list of routes from current node
		HashMap<String, Route> routeList = currentNode.getRouteList();
		
		//iterate through routeList
		for(String key : routeList.keySet()){
			
			//obtain new leg distance and analyze
			int legDistance = routeList.get(key).getDistance();
			if (currentDistance + legDistance < distanceThreshold){
				int newCurrentDistance = currentDistance + legDistance;
				
				//initialize new array for each iteration
				//needed for recursion, cannot pass same array ref. as you delve into graph
				ArrayList<String> modifiedTrip = new ArrayList<String>(currentTrip);
				Node nextNode = routeList.get(key).getConnectingNode();
				modifiedTrip.add(nextNode.getNodeName());
				
				//increment numStops if next node is terminating node
				if(nextNode.getNodeName().equals(endNodeName)){
					numRoutes++;
				}
				//recursive call with nextNode as new currentNode value
				numRoutes = this.distanceThresholdAnalysis(nextNode, endNodeName, newCurrentDistance, distanceThreshold, numRoutes, modifiedTrip);
			}
		}
		return numRoutes;
	}
	
	//public method to analyze routes between two nodes with X num of stops
	public int getNumRoutesWithNumStops (String startNodeName, String endNodeName, int stopThresh, boolean isMaxThresh){
		Node startNode = graphNodes.get(startNodeName);
		ArrayList<String> currentTrip = new ArrayList<String>();
		currentTrip.add(startNode.getNodeName());
		int result = stopsThresholdAnalysis(startNode, endNodeName, 0, stopThresh, 0, currentTrip, isMaxThresh);
		return result;
	}
	
	//private helper method for stop threshold analysis
	private int stopsThresholdAnalysis(Node currentNode, String endNodeName, int stopCount, int stopThresh, int numRoutes, ArrayList<String> currentTrip, boolean isMaxThresh){
		
		//execute if stopCount is below threshold
		if(stopCount < stopThresh){
			//increment stopCount by 1
			stopCount++;
			//obtain routeList of currentNode and iterate through it
			HashMap<String,Route> routeList = currentNode.getRouteList();
			for(String key: routeList.keySet()){
				
				//initialize new ArrayList for each recursive call to avoid modifying shared ALists
				ArrayList<String> modifiedTrip = new ArrayList<String>(currentTrip);
				Node nextNode = routeList.get(key).getConnectingNode();
				//increment if threshold represents maximum number (not exact number) of stops
				if(isMaxThresh && nextNode.getNodeName().equals(endNodeName)){
					numRoutes++;
				}
				modifiedTrip.add(nextNode.getNodeName());
				numRoutes = stopsThresholdAnalysis(nextNode, endNodeName, stopCount, stopThresh, numRoutes, modifiedTrip, isMaxThresh);
			}
		}
		//since iteration only occurs below threshold..
		//else statement defines scenario at threshold
		else{
			//increment numRoutes if last node is terminating and threshold == exact num of stops
			String lastNodeName = currentTrip.get(currentTrip.size()-1);
			if(lastNodeName.equals(endNodeName) && !isMaxThresh){
				numRoutes++;
			}
		}
		return numRoutes;
	}
	
	//private method that searches for pre-existing nodes
	//creates a new node if null value is returned
	private Node findOrCreateNode (String targetName){
		
		Node targetNode = graphNodes.get(targetName);
		
		//if no value exists, new node is created
		if (targetNode == null){
			targetNode = createNewNode(targetName);
		}
		
		//node (whether found or created) is returned
		return targetNode;
	}
	
	//private method to create a new node for graph, uses string argument for name
	private Node createNewNode(String targetName){
		Node newNode = new Node(targetName);
		return newNode;
	}
}
