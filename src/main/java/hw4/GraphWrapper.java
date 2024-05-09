package hw4;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;

/**
 * <b>Graph<b> Represents a mutable, directed graph
 * <b>Graph<b> representation in a hashmap where each key element represents a node
 * and each value of that node represents it outgoing edges or child nodes and the label for that edge
 * 
 * Specified Node: the node or vertices of the graph, represented by a string
 * specified Edges: the edges of the graph, represented by a AbstractMap containing 2 strings: childnode, edgelabel
 *                  
 */
public class GraphWrapper {
    // instance of my Graph
    private Graph<String, String> Dgraph;
    // representing a empty graph
    public static final Graph<?, ?> emptyGraph = Graph.emptyGraph;

    /**
     * Creates a empty directed graph
     * @effects Construct a new empty directed raph with no nodes or edges
     * @requires none
     * @modifies none
     * @returns none
     * @throws none
     */
    public GraphWrapper(){
        Dgraph = new Graph<>();
    }

    /**
     * Adds a node to the graph
     * @param nodeData the new node being added into graph
     * @effects Adds a new node to the graph if non existent, graph.keySet()
     * @requires nodeData != null
     * @modifies graph by adding a new node to the graph, graph.keySet
     * @returns none
     * @throws IllegalArgumentException if nodeData is null
     */
    public void addNode(String nodeData){
        Dgraph.addNode(nodeData);
    }

    /**
     * Creates a edge from parent to child with edge label in graph
     * @param parentNode the node in graph which new Edge is being made
     * @param childNode the child the parent node is being connected to, to create edge
     * @param edgeLabel label of the edge, or the weight
     * @requires all parameters != null
     * @modifies the edges of this graph, add new edge to node
     * @modifies the outgoing edges of parent node
     * @effects add edge with label to graph if the edge does not already exist
     * @throws IllegalArgumentException if either parentNode or childNode is not
     *                                  in graph, graph.keySet
     * @throws IllegalArgumentException if any param is null
     * @returns none
     */
    public void addEdge(String parentNode, String childNode, String edgeLabel){
        Dgraph.addEdge(parentNode, childNode, edgeLabel);
    }

    /**
     * returns a iterator which represent nodes in lexicographical order
     * @returns a iterator of nodes in lexicographical order
     * @requires none
     * @modifes none
     * @effects none
     * @throws none
     */
    public Iterator<String> listNodes(){
        return Dgraph.listNodes();
    }

    /**
     * returns a iterator which represent the list of childNode(edgeLabel) in lexicographical order
     * @param parentNode a node in graph
     * @returns a iterator of childNodes(edgeLabel) of parentNode in lexicographical order 
     * @returns a empty iterator if parentnode is not in graph or if parentNode is null
     * @requires parentNode != null 
     * @modifies none
     * @effects none
     * @throws none
     */
    public Iterator<String> listChildren(String parentNode){
        return Dgraph.listChildren(parentNode);
    }

    @Override
    /**
     * Overridden hashCode method to generate hash code for Graph objects.
     * @return int representing the hash code of the Graph object
     * @modifies none
     * @effects none
     * @requires none
     * @throws none
     */
    public int hashCode() {
        return Dgraph.hashCode();
    }
    
    @Override
    /**
    * Overidden equals method to compare two Graphs since by default
    * equals method in java compares memory addresses of objects, this
    * method will allow comparison by Graph value
    * @param obj object to compare to
    * @returns boolean indicating the equality of two Graphs
    * @modifies none
    * @effects none
    * @requires none
    * @throws none
    */
    public boolean equals(Object obj) {
        return Dgraph.equals(obj);
    }
    /**
    * Determine if a node is within graph, returns true if within, false otherwise
    * @param node the node to check if within graph
    * @returns boolean indicating if node is within graph
    * @modifes none
    * @effects none
    * @requires none
    * @throws none
    */
    public boolean containsNode(String node){
        return Dgraph.containsNode(node);
    }
    /**
    * Determine if a edge is within graph, returns true if within, false otherwise
    * @param parent the parent node of edge
    * @param edge the AbstractMap.SimpleEntry indicating child node and edge label of edge
    * @returns boolean indicating if edge is within graph
    * @requires none
    * @modifes none
    * @effects none
    * @throws none
    */
    public boolean containsEdge(String parent, Edge<String, String> edge){
        return Dgraph.containsEdge(parent, edge);
    }

    /**
     * Accessor method that returns the graph
     * @param none
     * @effects none
     * @modifes none
     * @requires none
     * @returns Graph
     * @throws none
     */
    public Map<String, ArrayList<Edge<String, String>>> getGraph(){
        return Dgraph.getGraph();
    }    
}
