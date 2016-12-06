import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class GraphTest {
	
	static ArrayList<String[]> inputArray;
	Graph testGraph;
	
	@BeforeClass
	public static void beforeTestSuite(){
		inputArray = new ArrayList<String[]>();
		inputArray.add(new String[]{"A","B","5"});
		inputArray.add(new String[]{"B","C","4"});
		inputArray.add(new String[]{"C","D","8"});
		inputArray.add(new String[]{"D","C","8"});
		inputArray.add(new String[]{"D","E","6"});
		inputArray.add(new String[]{"A","D","5"});
		inputArray.add(new String[]{"C","E","2"});
		inputArray.add(new String[]{"E","B","3"});
		inputArray.add(new String[]{"A","E","7"});
	}
	
	@Before
	public void beforeEachTest(){
		testGraph = new Graph();
	}

	@Test
	public void testAddNodes() {
		for(int i = 0; i < inputArray.size(); i++){
			String startNodeName = inputArray.get(i)[0];
			String endNodeName = inputArray.get(i)[1];
			int distance = Integer.parseInt(inputArray.get(i)[2]);
			testGraph.addNodes(startNodeName, endNodeName, distance);
		}
		assertEquals("Graph should have 5 nodes stored", 5, testGraph.graphNodes.size());
		assertEquals("A should have 3 routes", 3, testGraph.graphNodes.get("A").getRouteList().size());
		assertEquals("B should have 1 routes", 1, testGraph.graphNodes.get("B").getRouteList().size());
		assertEquals("C should have 2 routes", 2, testGraph.graphNodes.get("C").getRouteList().size());
		assertEquals("D should have 2 routes", 2, testGraph.graphNodes.get("D").getRouteList().size());
		assertEquals("E should have 3 routes", 1, testGraph.graphNodes.get("E").getRouteList().size());
	}
	
	@Test
	public void testDistanceOfRoute(){
		ArrayList<String> route;
		for(int i = 0; i < inputArray.size(); i++){
			String startNodeName = inputArray.get(i)[0];
			String endNodeName = inputArray.get(i)[1];
			int distance = Integer.parseInt(inputArray.get(i)[2]);
			testGraph.addNodes(startNodeName, endNodeName, distance);
		}
		route = new ArrayList<String>(Arrays.asList("A","B","C"));
		assertEquals("Distance of Route A-B-C is 9", 9, testGraph.distanceOfRoute(route));
		
		route = new ArrayList<String>(Arrays.asList("A","D"));
		assertEquals("Distance of Route A-D is 5", 5,testGraph.distanceOfRoute(route));
		
		route = new ArrayList<String>(Arrays.asList("A","D","C"));
		assertEquals("Distance of Route A-D-C is 13", 13,testGraph.distanceOfRoute(route));
		
		route = new ArrayList<String>(Arrays.asList("A","E","B","C","D"));
		assertEquals("Distance of Route A-E-B-C-D is 22", 22,testGraph.distanceOfRoute(route));
		
		route = new ArrayList<String>(Arrays.asList("A","E","D"));
		assertEquals("Route A-E-D deos not exist, returns -1", -1, testGraph.distanceOfRoute(route));
	}
	
	@Test
	public void testGetShortestDistance(){
		for(int i = 0; i < inputArray.size(); i++){
			String startNodeName = inputArray.get(i)[0];
			String endNodeName = inputArray.get(i)[1];
			int distance = Integer.parseInt(inputArray.get(i)[2]);
			testGraph.addNodes(startNodeName, endNodeName, distance);
		}
		assertEquals("The shortest route from A to C is 9", 9, testGraph.getShortestDistance("A","C"));
		assertEquals("The shortest route from B to B is 9", 9, testGraph.getShortestDistance("B","B"));
	}
	
	@Test
	public void testGetNumRoutesWithNumStops(){
		for(int i = 0; i < inputArray.size(); i++){
			String startNodeName = inputArray.get(i)[0];
			String endNodeName = inputArray.get(i)[1];
			int distance = Integer.parseInt(inputArray.get(i)[2]);
			testGraph.addNodes(startNodeName, endNodeName, distance);
		}
		assertEquals("Number of 4 stop routes between A and C = 3", 3, testGraph.getNumRoutesWithNumStops("A", "C", 4, false));
		assertEquals("Number of 3 stop routes between C and C = 2", 2, testGraph.getNumRoutesWithNumStops("C", "C", 3, true));
	}
	
	@Test
	public void testGetNumRoutesWithDistThresh(){
		for(int i = 0; i < inputArray.size(); i++){
			String startNodeName = inputArray.get(i)[0];
			String endNodeName = inputArray.get(i)[1];
			int distance = Integer.parseInt(inputArray.get(i)[2]);
			testGraph.addNodes(startNodeName, endNodeName, distance);
		}
		assertEquals("Number of stops between C to C with distance < 30 is 7", 7, testGraph.getNumRoutesWithDistThresh("C", "C", 30));
	}

}
