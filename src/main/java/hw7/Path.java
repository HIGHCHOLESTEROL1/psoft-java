package hw7;

/**
 * Custom class to represent a pathway between 2 pathways.
 * @param distance the distance of the path
 * @param dir the direction of the path
 */
public class Path implements Comparable<Path>{
    private Double distance;
    private String dir;

    /**
     * Constructor to create the pathway.
     * @param d distance of path.
     * @param a direction of path.
     * @requires none
     * @modifes distance, dir
     * @effects new Path object
     * @returns none
     * @throws none
     */
    public Path(Double d, String a) {
        distance = d;
        dir = a;
    }

    /**
     * Accessor method to get the distance of pathway.
     * @requires none
     * @modifes none
     * @effects none
     * @return The distance of the pathway.
     * @throws none
     */
    public Double getdistance() {
        return distance;
    }
    
    /**
     * Accessor method to get the direction of the pathway.
     * @requires none
     * @modifes none
     * @effects none
     * @return direction of the pathway
     */
    public String getDirection() {
        return dir;
    }

    /**
     * comparator method to compare pathway objects
     * @param B2 pathway two
     * @requires none
     * @modifies none
     * @effects none
     * @returns int based on comparison between pathway one and pathway two
     */
    @Override
    public int compareTo(Path B2) {
        // compare 2 pathway objects
        return getdistance().compareTo(B2.getdistance());
    }
}