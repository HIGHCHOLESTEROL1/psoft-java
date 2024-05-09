package hw6;
import hw4.*;

import java.util.PriorityQueue;
import java.util.TreeSet;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Comparator;

public class LegoPaths{
    // legoPaths is not a ADT, therefore no rep invarinat and check rep
    /*
     * In this application, your graph models a network of legos. Each node in the graph
     * represents a lego, and an edge <lego1, lego2>indicates that lego1 is in the same lego set which
     * lego2 is also in.
     */

     private Graph<String, Double> legoGraph;

    /*
     * Constructor, constructs a empty legoPaths object where graph is empty
     * @param none
     * @requires none
     * @modifies none
     * @effects Creates a new legoPaths object, where all instance variables are empty
     * @returns none
     * @throws none
     */
    public LegoPaths(){
        legoGraph = new Graph<>();
    }

    /*
     * Accessor method, returns legoGraph
     * @param none
     * @requires none
     * @modifies none
     * @effects none
     * @returns legoGraph
     * @throws none
     */
    public Graph<String, Double> getGraph(){
        Graph<String, Double> clone = new Graph<>(legoGraph);
        return clone;
    }

    /**
	 * @param: filename The path to a "CSV" file that contains the "lego part","set" pairs
	 * @requires: filename != null
	 * @modifies: legoGraph
	 * @effects: adds parsed <Set, lego parts in same set> to legoGraph
	 * @throws: IOException if file cannot be read or file not a CSV file following
	 *                      the proper format. "part","Set"
	 * @returns: None
	 */
    public void createNewGraph(String fileName){
        // Clear graph first
        legoGraph = new Graph<>();
        HashSet<String> legoinSet = new HashSet<>();
        HashMap<String, HashSet<String>> SetinLego = new HashMap<>();
        try{
            legoParser.readData(fileName, legoinSet, SetinLego);
        } catch (IOException e) { // handle exception
        }
        
        // Add nodes to the graph
        for (String node : legoinSet) {
            legoGraph.addNode(node);
        }

        // Initialize a map to store the count of shared sets between Lego parts
        HashMap<String, HashMap<String, Integer>> sharedSetsCount = new HashMap<>();
        // compute the adjacency maps for each node to avoid redudant object creation later on
        for (HashSet<String> parts : SetinLego.values()) {
            for (String part : parts) {
                sharedSetsCount.putIfAbsent(part, new HashMap<>());
            }
        }
        // keep track of processed parts
        // Iterate through all sets 
        for (HashSet<String> parts : SetinLego.values()) {
            // For each part in the set, update the shared sets count map
            for (String part : parts) {
                // avoid redudant checks
                HashMap<String, Integer> adjMap = sharedSetsCount.get(part);
                for (String otherPart : parts) {
                    if (!part.equals(otherPart) && part.compareTo(otherPart) < 0) {
                        // Add both bidirectional pairs in
                        adjMap.merge(otherPart, 1, Integer::sum);
                    }
                }
            }
        }
        // Add edges to the graph in batches with calculated weights
        sharedSetsCount.forEach((part1, sharedCountMap) -> {
            sharedCountMap.forEach((part2, sharedSets) -> {
                // avoid redundant edge calculation when already caculated
                if(part1.compareTo(part2) < 0) {
                    double weight = 1.0 / sharedSets;
                    // add bidirectional edge
                    legoGraph.addEdge(part1, part2, weight);
                    legoGraph.addEdge(part2, part1, weight);
                }
            });
        });
    }

    /*
     * Finds the shortest path between 2 legoparts whilst keeping track of total weight
     * returns the shortest path if exist along total weight in a formatted string
     * @param part1 the first lego part
     * @param part2 the second lego part
     * @requires part1 and part2 != null and part1 and part2 exist in
     * @modifies none
     * @effects none
     * @returns String of shortest path with total weight or no path
     * @returns unknown lego part if lego is not found
     * @throws IllegalArgumentException if null lego parts
     */
    public String findPath(String part1, String part2){
        String start = part1;
        String dest = part2;
        // test all intial conditions
        if (start == null || dest == null) {
            throw new IllegalArgumentException("Invalid Lego");
        }
        // double unknown professors
        if(!legoGraph.containsNode(start) && !legoGraph.containsNode(dest) && !start.equals(dest)){
            String result = "unknown part " + start + "\nunknown part " + dest + "\n";
            return result;
        }
        // check for unknown professors
        if (!legoGraph.containsNode(start)){
            return "unknown part " + start + "\n";
        }
        if(!legoGraph.containsNode(dest)){
            return "unknown part " + dest + "\n";
        }
        // if already at destination return string
        if(start.equals(dest)){
            return "path from " + start + " to " + dest + ":\ntotal cost: 0.000\n";
        }
        // intialize queue, keep track of visited nodes, and record paths
        PriorityQueue<Edge<String, Double>> queue = new PriorityQueue<>(compareWeight);
        HashSet<String> visited = new HashSet<>();
        HashMap<String, Double> weights = new HashMap<>();
        HashMap<String, String> paths = new HashMap<>();
        // add intitial path of node to itself
        Edge<String, Double> temp = new Edge<String, Double>(start, 0.0);
        queue.add(temp);
        weights.put(start, 0.0);
        paths.put(start, "path from " + start + " to " + dest + ":\n");
        // Piority que picks the node with the shortest total weighted path
        while (!queue.isEmpty()) {
            Edge<String, Double> n = queue.remove();
            String currNode = n.getKey();
            Double currWeight = n.getValue();
            // if found destination node return
            if (currNode.equals(dest)) {
                return paths.get(currNode) + String.format("total cost: %.3f\n", currWeight);
            }
            // ensure node not visited already
            if (visited.contains(currNode)) {
                continue;
            }
            // mark node as visted
            visited.add(currNode);
            TreeSet<Edge<String,Double>> edges = legoGraph.listChildren2(currNode);
            // iterate through all edges and generate paths toward all nodes
            for(Edge<String, Double> edge: edges) {
                String node = edge.getKey();
                Double weight = edge.getValue();
                // cannot visit a node that ahs already been visited
                if (!visited.contains(node)) {
                    Double newWeight = currWeight + weight;
                    Double oldWeight = weights.getOrDefault(node, Double.MAX_VALUE);
                    // if weight is less than oldWeight, add to path
                    if (newWeight < oldWeight) {
                        weights.put(node, newWeight);
                        // add path
                        String path = paths.get(currNode) + 
                        String.format("%s to %s with weight %.3f\n", currNode, node, weight);
                        // add new que to path
                        paths.put(node, path);
                        queue.add(new Edge<>(node, newWeight));
                    }
                }
            }
        }
        return "path from " + start + " to " + dest + ":\nno path found\n";
    }
    
    /**
     * Comparator to properly sort the children nodes or edges of a parentndoe
     * lexicographical order
     * @Override TreeSet<AbstractMap.SimpleEntry<String,String>> comparator
     */
    Comparator<Edge<String, Double>> compareWeight = 
    new Comparator<Edge<String, Double>>(){
        @Override
        // compare 2 pairs in set of edges
        public int compare(Edge<String, Double> p1, Edge<String, Double> p2){
            return p1.getValue().compareTo(p2.getValue());
        }
    };

    
    // public static void main(String[] args){

    //     LegoPaths g = new LegoPaths();
    //     // try{
    //     //     // Map<String, HashSet<String>> legoinSet = new HashMap<String, HashSet<String>>();
    //     //     //legoParser.readData("data/lego2020.csv", legoinSet);
    //     //     // FileWriter f = new FileWriter("data/output.txt");
    //     //     // for (String key : legoinSet.keySet()){
    //     //     //     f.write(key + ": " + legoinSet.get(key));
    //     //     // }
    //     //     // f.close();
    //     // } catch (IOException e) {
    //     // }
    //     try{
    //         long startT = System.currentTimeMillis();
    //         long startMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            
    //         g.createNewGraph("data/lego1960.csv");
    //         System.out.println(g.findPath("3005 Trans-Clear Brick 1 x 1", 
    //         "3065b Green Brick 1 x 2 without Bottom Tube, Slotted (with 1 slot)"));
    //         // System.out.println(g.findPath("880006 Black Stopwatch",
    //         // "3007d White Brick 2 x 8 without Bottom Tubes, 1 End Slot"));

    //         long endT = System.currentTimeMillis();
    //         long endMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

    //         long elapsedTimeMillis = endT - startT;
    //         long elapsedTimeSeconds = elapsedTimeMillis / 1000;
    //         long elapsedTimeMinutes = elapsedTimeSeconds / 60;
    //         elapsedTimeSeconds %= 60;
    //         double memoryUsedBytes = endMem - startMem;
    //         double memoryUsedGB = memoryUsedBytes / (1024 * 1024 * 1024); // convert bytes to gigabytes

    //         System.out.println("Time taken: " + elapsedTimeMinutes + " minutes " + elapsedTimeSeconds + " seconds");
    //         System.out.println("Memory used: " + memoryUsedGB + " GB");
    //     }finally {}
    // }
}