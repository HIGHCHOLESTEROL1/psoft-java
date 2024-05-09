package hw7;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Iterator;

import hw4.*;

public class CampusPaths{
    // CampusPaths is not a ADT, therefore no rep invarinat and check rep
    /*
     * In this application, your graph models a network of buildings in campus. Each node in the graph
     * represents a building, and an edge <destinationBg, edgeLable>indicates that building has a path
     * to the other building
     */

    private Graph<Building, Path> mapGraph;
    private Map<String, Building> buildingNameMap = new HashMap<>();
    private HashMap<Building, Coordinate> buildingCoords = new HashMap<>();
    /**
     * Constructor, constructs a empty campusPaths object where graph is empty
     * @param none
     * @requires none
     * @modifies none
     * @effects Creates a new CampusPaths object, where all instance variables are empty
     * @returns none
     * @throws none
     */
    public CampusPaths(){
        mapGraph = new Graph<>();
    }

    /**
     * Caclulate the degree and determine direction from 2 points
     * @param point1 first point, represented by edge
     * @param point2 second point represented by edge
     * @requires point1 and point2 != null
     * @effects none
     * @modifies none
     * @returns String representing direction
     * @throws none
     */
    public String getDirection(Coordinate point1, Coordinate point2){
        // calculate the angle between the 2 points
        Double A2 = Double.valueOf(point2.getX() - point1.getX());
        Double B2 = Double.valueOf(point2.getY() - point1.getY());
        Double Rangle = Math.toDegrees((Math.atan2(B2, A2)));
        // check if negative, if yes minus it from 360
        if (Rangle < 0){
            Rangle+=360;
        }
        Double angle = Rangle + 90;
        // check if over 360 degrees, if yes rotate and minus 360
        if (angle > 360){
            angle = angle - 360;
        }
        // based off that angle determine direction
        if (angle > 337.5 || angle < 22.5){
            return "North";
        }
        else if(angle >= 22.5 && angle <= 67.5){
            return "NorthEast";
        }
        else if(angle > 67.5 && angle < 112.5){
            return "East";
        }

        else if(angle >= 112.5 && angle <= 157.5){
            return "SouthEast";
        }
        else if(angle > 157.5 && angle < 202.5){
            return "South";
        }
        else if(angle >= 202.5 && angle <= 247.5){
            return "SouthWest";
        }
        else if(angle > 247.5 && angle < 292.5){
            return "West";
        }
        return "NorthWest";
        
    }

    /**
     * Caclulate the distance and between 2 points
     * @param point1 first point, represented by edge
     * @param point2 second point represented by edge
     * @requires point1 and point2 != null
     * @effects none
     * @modifies none
     * @returns double representing distance
     * @throws none
     */
    public Double getDistance(Coordinate point1, Coordinate point2){
        // calculate distance between point1 and point using Euclidean distance Formula
        int A2 = point2.getX() - point1.getX();
        int B2 = point2.getY() - point1.getY();
        Double distance = Math.sqrt(Math.pow(A2, 2.0) + Math.pow(B2, 2.0));
        return distance;
    }

    /**
	 * @param filename The path to a "CSV" file that contains the building, id, x, y coordinates
     * @param filename2 the path to a "CSV" file that contains the paths between buildings
	 * @requires filename != null && filename2 != null
	 * @modifies mapGraph
	 * @effects adds parsed data into mapGraph
	 * @throws none
	 * @returns None
	 */
    public void createNewGraph(String filename, String filename2){
        // empty graph
        mapGraph = new Graph<>();
        buildingNameMap.clear();
        // create data structures to store parsed data
        buildingCoords.clear();
        HashSet<Edge<Building, Building>> intersections = new HashSet<>();
        // grab parsed data and fill data structures with it
        try{
            CampusParser.readData(filename, filename2, buildingCoords, intersections);
        }catch(Exception e){}
        // add all building nodes
        for (Building building : buildingCoords.keySet()) {
            mapGraph.addNode(building);
            buildingNameMap.put(building.getId(), building);
            buildingNameMap.put(building.getName(), building);
        }
        // add all edges to graph (add all pathways between building nodes)
        for (Edge<Building, Building> edge : intersections) {
            // get the 2 building nodes from edge
            Building b1 = edge.getKey();
            Building b2 = edge.getValue();
            // find the coordinates relating to each building
            Coordinate p1 = buildingCoords.get(b1);
            Coordinate p2 = buildingCoords.get(b2);
            // calculate distance and direction
            Double dis = getDistance(p1, p2);
            String dir = getDirection(p1, p2);
            Double dis2 = getDistance(p2, p1);
            String dir2 = getDirection(p2, p1);
            // create the pathway and add to mapGraph
            Path path = new Path(dis, dir);
            Path path2 = new Path(dis2, dir2);
            mapGraph.addEdge(b1, b2, path);
            mapGraph.addEdge(b2, b1, path2);
        }
    }
    
     /**
     * Finds the shortest path between 2 Buildings whilst keeping track
     * of direction and distance
     * @param B1 the first building, the start building
     * @param B2 the second building, the destination building
     * @requires part1 and part2 != null
     * @modifies none
     * @effects none
     * @returns String of shortest path
     * @throws none
     */
    public String findPath(String B1, String B2){
        // convert strings to buildings
        Building start = buildingNameMap.get(B1);
        Building dest = buildingNameMap.get(B2);
        // test all intial conditions
        // double unknown professors
        if(!mapGraph.containsNode(start) && !mapGraph.containsNode(dest) && !start.equals(dest)){
            return "Unknown building: " + "[" + B1 + "]\nUnknown building: " + "[" + B2 + "]\n";
        }
        // check for unknown professors
        else if (!mapGraph.containsNode(start)){
            return "Unknown building: " + "[" + B1 + "]\n";
        }
        else if(!mapGraph.containsNode(dest)){
            return "Unknown building: " + "[" + B2 + "]\n";
        }
        // ensure not intersections
        else if(start.getName().contains("Intersection") && dest.getName().contains("Intersection") 
        && !start.getName().equals(dest.getName())){
            return "Unknown building: " + "[" + B1 + "]\nUnknown building: " + "[" + B2 + "]\n";
        }
        else if(start.getName().contains("Intersection")){
            return "Unknown building: " + "[" + B1 + "]\n";
        }
        else if(dest.getName().contains("Intersection")){
            return "Unknown building: " + "[" + B2 + "]\n";
        }
        // if already at destination return string
        else if(start.equals(dest)){
            return "Path from " + start.getName() + " to " + dest.getName() + ":\nTotal distance: 0.000 pixel units.\n";
        }
        // otherwise run dijkstra and return shortest path if existent
        return Dijkstra.run_Dijkstra(mapGraph, start, dest);
    }

    /**
     * Prints all the buildings along with id in lexicographic order
     * @param none
     * @requires none
     * @modifes none
     * @effects none
     * @returns none
     * @throws none
     */
    public void listBuildings(){
        // get all buildings in order
        Iterator<Building> it = mapGraph.listNodes();
        // iterate over and print all the buildings in order
        while(it.hasNext()){
            Building b = it.next();
            // check if intersection, if yes skip
            if(b.getName().contains("Intersection")){
                continue;
            }
            System.out.println(b.getName() + "," + b.getId());
        }
    }
    
    /**
     * finds the building corresponding to the given coordinate
     * @param x the x coord
     * @param y the y coord
     * @requires x != null and y != null
     * @effects none
     * @modifes none
     * @returns the building corresponding to the given coordinate
     * @throws none
     */
    public String findBuilding(double x, double y){
        // iterate over all building and find the corresponding building to the given coordinate
        for (Map.Entry<Building, Coordinate> entry : buildingCoords.entrySet()){
            Coordinate c = entry.getValue();
            if (Math.abs(x - c.getX()) <= 20 && Math.abs(y - c.getY()) <= 20){
                return entry.getKey().getName();
            }
        }
        return "none";
    }

     public static void main(String[] args){
        // create campus path object and fill with data
        CampusPaths p = new CampusPaths();
        p.createNewGraph("data/RPI_map_data_Nodes.csv", "data/RPI_map_data_Edges.csv");
        // user command
        String prompt = "";
        Scanner myObj = new Scanner(System.in);
        // prompt user for commands
        while(!prompt.equals("q")){
            prompt = myObj.nextLine(); // read user input

            // if user enters b, list all buildings in order
            if (prompt.equals("b")){
                p.listBuildings();
            }
            // if user enters r, find shortest path between 2 buildings
            else if(prompt.equals("r")){
                String b1;
                String b2;
                // scan and determine the 2 buildings user inputed
                System.out.print("First building id/name, followed by Enter: ");
                b1 = myObj.nextLine();
                System.out.print("Second building id/name, followed by Enter: ");
                b2 = myObj.nextLine();
                // print the shortest path now
                System.out.print(p.findPath(b1, b2));
            }
            // if user enters m, show a menu of all commands
            else if(prompt.equals("m")){
                System.out.println("b lists all buildings");
                System.out.println("r prints directions for the shortest route between any two buildings");
                System.out.println("q quits the program");
                System.out.println("m prints a menu of all commands");
            }
            // if user enters q, exit
            else if(prompt.equals("q")){
                myObj.close();
                return;
            }
            else{
                // otherwise a unknown command
                System.out.println("Unknown option");
            }
        }
        myObj.close();
     }
}