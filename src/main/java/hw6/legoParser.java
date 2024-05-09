package hw6;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;


public class legoParser {
    /**
	 * @param: filename     The path to a "CSV" file that contains the
	 *                      lego part","set" pairs
	 * @param: legoSets     The Map that stores parsed <part, Set-of set lego part of> pairs; usually an empty Map.
	 * @requires: filename != null && legoSets != null
	 * @modifies: legoSets
	 * @effects: adds parsed <part, Set-of set lego part of> pairs to Map
	 *           legoSets
	 * @throws: IOException if file cannot be read or file not a CSV file following
	 *                      the proper format.
	 * @returns: None
	 */
     
     public static void readData(String filename, HashSet<String> legoSets, HashMap<String, HashSet<String>> SetinLego)
        throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            // read lego data
            while ((line = reader.readLine()) != null) {
                // Check if the line is empty or contains only delimiters
                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }
                
                int i = line.indexOf("\",\"");
                if (i == -1 || line.charAt(0) != '\"' || line.charAt(line.length() - 1) != '\"') {
                    throw new IOException("File " + filename + " not a CSV (\"lego\",\"set\") file.");
                }
                
                String[] parts = line.split("\",\"");
                if (parts.length == 2) {
                    // Extract the part and set information
                    String part = parts[0].substring(1); // Remove leading quote
                    String set = parts[1].substring(0, parts[1].length() - 1); // Remove trailing quote
                    // Add the part to the legoSets HashSet
                    legoSets.add(part);
                    // Add the part to the HashSet associated with the set name
                    HashSet<String> p = SetinLego.getOrDefault(set, new HashSet<>());
                    p.add(part);
                    SetinLego.put(set, p);
                }
            }
        }
    }
}
