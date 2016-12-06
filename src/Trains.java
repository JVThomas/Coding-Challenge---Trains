import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

//Trains class, contains main function for application
public class Trains {
	
	//main method reads input.txt and parses string for analysis
	//simply run trains to analyze input (included)
	public static void main(String[] args) throws FileNotFoundException {
		//File object for reference to input.txt
		File inputFile = new File("input.txt");
		
		//scanner object to help parse input filw
		Scanner scan = new Scanner(inputFile);
		Graph trainGraph = new Graph();
		while(scan.hasNextLine()){
			
			//obtain input line by line
			String graphInput = scan.nextLine();
			
			//split line by " ," value and store in array
			String [] inputArray = graphInput.split(", ");
			
			//for each element in array, split by character
			// indexes: 0 = startNode, 1 = endNode, 2 = distance(String)
			//pass elements in new array as arguments for addNodes
			for( String nodesInputString: inputArray){
				String [] nodesInputArray = nodesInputString.split("");
				trainGraph.addNodes(nodesInputArray[0], nodesInputArray[1], Integer.parseInt(nodesInputArray[2]));
			}
		}
		//close scanner when parsing is finished
		scan.close();
		//call helper to display output to console
		displayTestOutput(trainGraph);
	}
	
	//helper method to format testInput
	private static void displayTestOutput(Graph trainGraph){
		//initialize array of size 10 to hold results of method calls
		int[] outputArray = new int[10];
		//define inputs for distance of specified route via ArrayList
		ArrayList<ArrayList<String>> definedRoutesArray = new ArrayList<ArrayList<String>>();
		definedRoutesArray.add(new ArrayList<String>(Arrays.asList(new String[]{"A","B","C"})));
		definedRoutesArray.add(new ArrayList<String>(Arrays.asList(new String[]{"A","D"})));
		definedRoutesArray.add(new ArrayList<String>(Arrays.asList(new String[]{"A","D","C"})));
		definedRoutesArray.add(new ArrayList<String>(Arrays.asList(new String[]{"A","E","B", "C", "D"})));
		definedRoutesArray.add(new ArrayList<String>(Arrays.asList(new String[]{"A","E","D"})));
		
		//iterate through array, feed into distanceOfRoute, store in associated output index
		for(int i = 0; i < definedRoutesArray.size(); i++){
			outputArray[i] = trainGraph.distanceOfRoute(definedRoutesArray.get(i));
		}
		//rest of test input can be directly passed to method call
		//store in associated output index
		outputArray[5] = trainGraph.getNumRoutesWithNumStops("C", "C", 3, true);
		outputArray[6] = trainGraph.getNumRoutesWithNumStops("A", "C", 4, false);
		outputArray[7] = trainGraph.getShortestDistance("A", "C");
		outputArray[8] = trainGraph.getShortestDistance("B","B");
		outputArray[9] = trainGraph.getNumRoutesWithDistThresh("C", "C", 30);
		
		//send outputArray to helper method
		outputHandler(outputArray);
		
	}
	
	//helper method to output result
	private static void outputHandler(int[]outputArray){
		//for loop to iterate through array
		for(int i = 0; i < outputArray.length; i++){
			
			//results for all methods are int values
			//however distanceOfRoute can return -1 if no route exists
			//if conditional should output NO SUCH ROUTE if result == -1
			//otherwise output result as is
			if (outputArray[i] == -1){
				System.out.println("Output #" + (i+1) + ": " + "NO SUCH ROUTE");
			}
			else{
				System.out.println("Output #" + (i+1) + ": " + Integer.toString(outputArray[i]));
			}
		}
	}

}
