import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class RouteTest {
	Route testRoute;
	Node connectingNode;
	int distance;
	
	@Before
	public void beforeEach(){
		testRoute = new Route();
		connectingNode = new Node("A");
		distance = 10;
	}

	@Test
	public void testSetAndGetConnectingNode() {
		testRoute.setConnectingNode(connectingNode);
		assertEquals("testRoute has connectingNode (determined by set and gets)", connectingNode, testRoute.getConnectingNode());
	}

	@Test
	public void testSetAndGetDistance() {
		testRoute.setDistance(distance);
		assertEquals("testRoute has assigned distance (determined by set and gets)", 10, testRoute.getDistance());
	}
}
