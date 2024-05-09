package hw7;

/**
 * Custom class to represent a building.
 * @param id the building id
 * @param name the name of the building
 */
public class Building implements Comparable<Building>{
    private String id ;
    private String name;

    /**
     * Constructor to create the building with a id and name.
     * @param i The id of the buidling.
     * @param n The name of the building.
     * @requires none
     * @modifes id, name
     * @effects new building
     * @returns none
     * @throws none
     */
    public Building(String i, String n) {
        id = i;
        name = n;
    }

    /**
     * Accessor method to get the id of building.
     * @requires none
     * @modifes none
     * @effects none
     * @return The id of the building.
     * @throws none
     */
    public String getId() {
        return id;
    }
    
    /**
     * Accessor method to get the name of the building.
     * @requires none
     * @modifes none
     * @effects none
     * @return name of the building
     */
    public String getName() {
        return name;
    }

    /**
     * comparator method to compare building objects
     * @param B2 building two
     * @requires none
     * @modifies none
     * @effects none
     * @returns int based on comparison between building one and building two
     */
    @Override
    public int compareTo(Building B2) {
        // compare 2 building objects
        return getName().compareTo(B2.getName());
    }
    
    /**
     * override equals method
     * @param Object obj The object to compare
     * @requires none
     * @modifes none
     * @effects none
     * @returns the boolean based on if obj is equal
     * @throws none
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Building building = (Building) obj;
        return id.equals(building.getId());
    }

    /**
     * override hash method
     * @param none
     * @requires none
     * @modifes none
     * @effects none
     * @returns the hash number
     * @throws none
     */
    @Override
    public int hashCode() {
        final int prime = 37;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }
}
