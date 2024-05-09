package hw7;

/**
 * Custom class to represent a coordinate.
 * @param x x coordinate
 * @param y y coordinate 
 */
public class Coordinate{
    private Integer x ;
    private Integer y;

    /**
     * Constructor to create the building with a id and name.
     * @param xc x coordinate.
     * @param yc y coordinate.
     * @requires none
     * @modifes x, y
     * @effects new coordinate
     * @returns none
     * @throws none
     */
    public Coordinate(int xc, int yc) {
        x = xc;
        y = yc;
    }

    /**
     * Accessor method to get the x coord.
     * @requires none
     * @modifes none
     * @effects none
     * @return The x coord.
     * @throws none
     */
    public Integer getX() {
        return x;
    }
    
    /**
     * Accessor method to get the y coordinate.
     * @requires none
     * @modifes none
     * @effects none
     * @return y coord
     */
    public Integer getY() {
        return y;
    }
}