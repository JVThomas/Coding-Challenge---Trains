import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class NodeTest {
	
	private Node testNode;
	
	@Before 
	public void executedBeforeEach(){
		testNode = new Node("A");
	}
	
	@Test
	public void testGetNodeName() {
		assertEquals("Node name must be 'A'","A", testNode.getNodeName());
	}
	
	
	@Test
	public void testAddNewRoute() {
		assertEquals("Node has nothing in routeList", 0, testNode.getRouteList().size());
		Node newNode = new Node("B");
		testNode.addNewRoute(newNode, 10);
		assertEquals("Node will now have newNode in routeList",1, testNode.getRouteList().size());
	}

	@Test
	public void testGetTargetRoute() {
		Node newNode  = new Node("B");
		testNode.addNewRoute(newNode, 10);
		Route targetRoute = testNode.getTargetRoute("B");
		assertEquals("Node will return newNode upon request", "B", targetRoute.getConnectingNode().getNodeName());
		assertEquals("Node will return distance to conencting route", 10, targetRoute.getDistance());
	}
}
