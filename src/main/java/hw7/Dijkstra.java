package hw7;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.TreeSet;

import hw4.*;
/**
 * Class representing Dijkstra algorithm to find shortest path
 */
public class Dijkstra {

    /**
     * Finds the shortest path between 2 Buildings whilst keeping track
     * of direction and distance
     * @param graph the graph storing all the building directions information represented
     * by campusPaths object
     * @param start the first building, the start building
     * @param dest the second building, the destination building
     * @requires part1 and part2 != null && graph != null
     * @modifies none
     * @effects none
     * @returns String of shortest path
     * @throws none
     */
    public static String run_Dijkstra(Graph<Building, Path> map, Building start, Building dest){
        // intialize queue, keep track of visited nodes, and record paths
        PriorityQueue<Edge<Building, Path>> queue = new PriorityQueue<>(compareWeight);
        HashSet<Building> visited = new HashSet<>();
        HashMap<Building, Double> distances = new HashMap<>();
        HashMap<String, String> paths = new HashMap<>();
        // add intital path of starting node
        Edge<Building, Path> temp = new Edge<Building, Path>(start, new Path(0.0, ""));
        queue.add(temp);
        distances.put(start, 0.0);
        paths.put(start.getName(), "Path from " + start.getName() + " to " + dest.getName() + ":\n");
        // piority que picks the building with the shortest total distance
        while(!queue.isEmpty()){
            Edge<Building, Path> n = queue.remove();
            Building b = n.getKey(); Path p = n.getValue();
            String currNode = b.getName();
            Double currDistance = p.getdistance();
            // if found destination building, return path
            if (b.equals(dest)){
                return paths.get(currNode) + String.format("Total distance: %.3f pixel units.\n", currDistance);
            }
            // ensure node is not visited already
            if (visited.contains(b)){
                continue;
            }
            // otherwise mark node as now visited
            visited.add(b);
            // get all neighboring edges
            TreeSet<Edge<Building, Path>> edges = map.listChildren2(b);
            // iterate through all edges and generate paths towards all nodes
            for (Edge<Building, Path> edge : edges){
                Building currB = edge.getKey(); Path currP = edge.getValue();
                String node = currB.getName();
                Double distance = currP.getdistance();
                // cannot visited visted node
                if (!visited.contains(currB)){
                    Double newDistance = currDistance + distance;
                    Double oldDistance = distances.getOrDefault(currB, Double.MAX_VALUE);
                    // if new distance is less than old distance, add to path
                    if (newDistance < oldDistance){
                        // add path and add to queue
                        distances.put(currB, newDistance);
                        String path = paths.get(currNode) + 
                        String.format("\tWalk %s to (%s)\n", currP.getDirection(), node);
                        paths.put(node, path);
                        queue.add(new Edge<>(currB, new Path(newDistance, currP.getDirection())));
                    }
                }
            }
        }
        return String.format("There is no path from %s to %s.\n", start.getName(), dest.getName());

    }

    /**
     * Comparator to properly sort the children nodes or edges of a parentndoe
     * lexicographical order
     * @Override TreeSet<AbstractMap.SimpleEntry<String,String>> comparator
     */
    static Comparator<Edge<Building, Path>> compareWeight = 
    new Comparator<Edge<Building, Path>>(){
        @Override
        // compare 2 pairs in set of edges
        public int compare(Edge<Building, Path> p1, Edge<Building, Path> p2){
            return p1.getValue().getdistance().compareTo(p2.getValue().getdistance());
        }
    };
}
