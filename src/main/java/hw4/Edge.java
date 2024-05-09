package hw4;
import java.util.Objects;

/**
 * Custom class to represent an edge in a graph.
 * @param <T> Type of the child node.
 * @param <V> Type of the edge label.
 */
public class Edge<T, V> {
    private T childNode;
    private V edgeLabel;

    /**
     * Constructor to initialize the edge with a child node and edge label.
     * @param childNode The child node of the edge.
     * @param edgeLabel The label of the edge.
     * @requires none
     * @modifes childNode, edgeLabel
     * @effects the edge will now be initialized
     * @returns none
     * @throws none
     */
    public Edge(T childNode, V edgeLabel) {
        this.childNode = childNode;
        this.edgeLabel = edgeLabel;
    }

    /**
     * Accessor method to get the child node of the edge.
     * @requires none
     * @modifes none
     * @effects none
     * @return The child node of the edge.
     * @throws none
     */
    public T getKey() {
        return childNode;
    }
    
    /**
     * Accessor method to get the edge label of the edge.
     * @requires none
     * @modifes none
     * @effects none
     * @return The edge label of the edge.
     */
    public V getValue() {
        return edgeLabel;
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
        Edge<?, ?> edge = (Edge<?, ?>) obj;
        return Objects.equals(childNode, edge.childNode) && Objects.equals(edgeLabel, edge.edgeLabel);
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int result = 1;
        result = prime * result + ((childNode == null) ? 0 : childNode.hashCode());
        result = prime * result + ((edgeLabel == null) ? 0 : edgeLabel.hashCode());
        return result;
    }
}
