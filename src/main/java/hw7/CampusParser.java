package hw7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.HashMap;
import hw4.*;

public class CampusParser {
    /**
	 * @param filename The path to a "CSV" files that contains the
	 *                 buildins, id, coordinates
	 * @param filename2 the path to the "CSV" files that contain all the
     *                  paths between buildings by id
     * @param buildingCoords a hashmap to store the buildings or intersections, id: x-coordinate, y-coordinate
     * @param intersections hashSet of Edges data structure to store pairs of building or intersections that have pathways between
     * 
     * @requires fileName != null && fileName2 != null
     * @modifies buildingCoords, intersections, buildings
     * @effects adds parsed data into buildingCoords, intersections, buildings
     * @throws IOException if file cannnot be read
     * @returns none
	 */
    
     public static void readData(String filename, String filename2, HashMap<Building, Coordinate> buildingCorrds, 
     HashSet<Edge<Building, Building>> intersections) throws IOException {
        // keep track of buildings
        HashMap<String, String> temp = new HashMap<String, String>();
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
            String line = null;
            // read data from first file
            while((line = reader.readLine()) != null){
                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }
                String[] data  = line.split(",");
                if (data.length != 4){
                    throw new IOException("INVALID FILE");
                }
                // intialize the data retrieved from line
                String buildName = data[0];
                String id = data[1];
                // if no building name, is a intersection
                if (buildName.equals("")){
                    buildName = "Intersection " + id;
                }
                int x = Integer.valueOf(data[2]);
                int y = Integer.valueOf(data[3]);
                // add corresponding data into data set storing building id and coordinates
                buildingCorrds.put(new Building(id, buildName), new Coordinate(x, y));
                temp.put(id, buildName);
            }
        }catch(IOException e){}
        // read data from second file
        try(BufferedReader reader = new BufferedReader(new FileReader(filename2))){
            String line = null;

            // read data from second file
            while((line = reader.readLine()) != null){
                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }
                String[] data  = line.split(",");
                if (data.length != 2){
                    throw new IOException("INVALID FILE");
                }
                // intialize data and add to intersections data
                Building p1 = new Building(data[0], temp.get(data[0]));
                Building p2 = new Building(data[1], temp.get(data[1]));
                intersections.add(new Edge<Building, Building>(p1, p2));
            }
        }catch(IOException e){}
    }
}
