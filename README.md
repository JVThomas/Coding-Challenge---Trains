# Coding Challenge Trains
A coding challenge to analyze a train network saved within a .txt file. Criteria of the coding challenge are:

* Program should analyze distance of a route, the number of different routes between two towns, and the shortest route between two towns.
* The input file consists of comma seperated values with each value represented in the format Letter-Letter-Number
  - The letters represent the towns (nodes) while the number represents the distance between them
* The graph is directed, thus no two way travel exists within a single route (though it is possible for two way travel with two routes, each moving in the opposite direction)
* No external libraries are allowd save for testing libraries like JUnit, Ant, etc.

My solution involves parsing the input.txt file and creating a grapth with nodes and edges that represent the train network. From there, I created a series of methods within the Graph class for internal analysis of its contents taht can be called upon within the main function.

I've also created a series of JUnit tests to help check for proper functionality of methods.

All in all this was a good coding puzzle. Most of my struggles were centered around learning Java and it's nuances (some examples being == for strings in Java vs. Ruby, ArrayList implementations, method declarations), but that's essentially the story for any new language.




