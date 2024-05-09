package hw5;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;
import hw4.*;

public class ProfessorPaths{

    // ProfessorPaths is not a ADT, therefore no rep invariant and no check rep

    /*
     * In this application, your graph models a network of course instructors. Each node in the graph
     * represents a professor, and an edge <Prof1, Prof2>indicates that Prof1 has taught a course which
     * Prof2 has also taught. The course will be the edgeLabel associated with the edge
     */
    private Graph<String, String> profGraph;

    /*
     * Constructor, constructs a empty professorPaths object where graph is empty
     * @param none
     * @requires none
     * @modifies none
     * @effects Creates a new ProfessorPaths object, where all instance variables are empty
     * @returns none
     * @throws none
     */
    public ProfessorPaths(){
        profGraph = new Graph<>();
    }

    /*
     * Accessor method, returns profGraph
     * @param none
     * @requires none
     * @modifies none
     * @effects none
     * @returns profGraph
     * @throws none
     */
    public Graph<String, String> getGraph(){
        Graph<String, String> clone = new Graph<>(profGraph);
        return clone;
    }

    /*fo
     * Constructor that takes in a file name and creates a graph that stores the data
     * @param fileName the name of the file that stores the data
     * @requires fileName to be a valid file that exists and can be accessed
     * @modifes profsTeacging, profs, profGraph, will fill 2 instance variables with the
     *          data within input file, profTeaching and prof. Then it will fill profGraph
     *          with the data within profTeaching and prof.
     * @effects stores the data within fileName in profsTeacging, profs, profGraph
     * @returns null if fileName is invalid or non
     * @throws none
     */
    public void createNewGraph(String fileName){
        // clear graph first
        profGraph = new Graph<>();
        Map<String, Set<String>> profsTeaching = new HashMap<String, Set<String>>();
        Set<String> profs = new HashSet<String>();
        // fill profsTeaching and profs with file data
        try {
            ProfessorParser.readData(fileName, profsTeaching, profs);
        } catch (IOException e) {// Handle the exception 
        }
        // fill graph with profsTeaaching and profs data
        // add nodes and handle the edges
        for (String key: profsTeaching.keySet()){
            // iterate set and add edges between each professor who have taught same course
            for (String prof: profsTeaching.get(key)){
                // create node
                if (!profGraph.containsNode(prof)){
                    profGraph.addNode(prof);
                }
                // iterate through set of professors and edges between each of them
                for (String matchProf: profsTeaching.get(key)){
                    if (!profGraph.containsNode(matchProf)){
                        profGraph.addNode(matchProf);
                    }
                    // check to see if same professor to parent professor, prevent reflective edge
                    if(!prof.equals(matchProf) && 
                    !profGraph.containsEdge(prof,new Edge<String, String>(matchProf, key))){
                        // if edge does not exist, add edge, otherwise do nothing
                        profGraph.addEdge(prof, matchProf, key);
                    }
                }
            }
        }
    }

    /*
     * Finds lexicographiclaly shortest paths between 2 professors via BFS. 
     * Searches for and returns a path that connects them specifically a course taught by both. 
     * @param node1 First professor
     * @param node2 Second professor
     * @requires node1 and node2 != null
     * @modifies none
     * @effects none
     * @returns Shortest path between prof1 and prof2
     * @returns the professors that are not within graph if not valid
     * @throws IllegalArgumentException if prof1 or prof2 is null
     */
    public String findPath(String node1, String node2) {
        // check if professors are valid professors
        if (node1 == null || node2 == null) {
            throw new IllegalArgumentException("Invalid professors");
        }
        // double unknown professors
        if(!profGraph.containsNode(node1) && !profGraph.containsNode(node2) && !node1.equals(node2)){
            String result = "unknown professor " + node1 + "\nunknown professor " + node2 + "\n";
            return result;
        }
        // check for unknown professors
        if (!profGraph.containsNode(node1)){
            return "unknown professor " + node1 + "\n";
        }
        if(!profGraph.containsNode(node2)){
            return "unknown professor " + node2 + "\n";
        }
        // if already at destination return string
        if(node1.equals(node2)){
            return "path from " + node1 + " to " + node2 + ":\n";
        }
        // Initialization
        Queue<String> que = new LinkedList<>();
        Map<String, ArrayList<String>> path = new HashMap<>();

        que.add(node1);
        ArrayList<String> pt = new ArrayList<>();
        pt.add(node1);
        path.put(node1, pt);
        
        // Main loop via BFS
        while (!que.isEmpty()) {
            String currProf = que.remove();
            // Check if current node is destination
            if (currProf.equals(node2)) {
                String result = "path from " + node1 + " to " + node2 + ":\n";
                return result + formatPath(path, node2);
            }
            // Process neighbors
            Iterator<String> neighbors = profGraph.listChildren(currProf);
            // iterate through neightbors and get the path to each neighbor
            while (neighbors.hasNext()){
                String currChild = neighbors.next();
                int p = currChild.indexOf("(");
                String child = currChild.substring(0, p);
                if (!path.containsKey(child)) { // Check if neighbor has been visited
                    ArrayList<String> pPrime = new ArrayList<>(path.get(currProf));
                    // get the edge including the label
                    String label  = currChild.substring(p+1, currChild.indexOf(")"));
                    pPrime.add(child + "," + label);
                    path.put(child, pPrime); // Update path for neighbor
                    // add new neighbor to que
                    que.add(child);
                }
            }
        }
        // No path found
        String result = "path from " + node1 + " to " + node2 + ":\n";
        result += "no path found\n";
        return result;
    }
    

    /*
     * Formats the the path input given into formatted string
     * @param path the path to be formattted
     * @requires path != null and path.contains(destination), otherwise empty path
     * @modifies none
     * @effects none
     * @returns formatted string representing path
     * @returns empty string for path if invalid inputs
     * @throws none
     */
    private String formatPath(Map<String, ArrayList<String>> path, String destination) {
        String result = "";
        for (int i = 0; i < path.get(destination).size()-1; i++) {
            // string formatting
            result += path.get(destination).get(i).split(",")[0] + " to " + path.get(destination).get(i+1).split(",")[0] 
            + " via " + path.get(destination).get(i+1).split(",")[1] + "\n";
        }
        return result;
    }

}