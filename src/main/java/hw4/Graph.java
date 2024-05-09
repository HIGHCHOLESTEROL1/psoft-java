package hw4;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * <b>Graph<b> Represents a mutable, directed graph
 * <b>Graph<b> representation in a hashmap where each key element represents a node
 * and each value of that node represents it outgoing edges or child nodes and the label for that edge
 * 
 * Specified Node: the node or vertices of the graph, represented by a string
 * specified Edges: the edges of the graph, represented by a AbstractMap containing 2 strings: childnode, edgelabel
 *                  
 */
public class Graph<T extends Comparable<T>, V extends Comparable<V>> {
    // Rep invariant:
    // graph != null
    // Every node and edge != null
    // handshake theorem applies to graph to ensure proper graph

    // Abstract Function:
    // this == directed graph g such that if g is empty then {}
    // if node a in nodes of graph g, {a = [outgoing edges]}
    // outgoing edges include the destination nodes node a reaches
    // each outgoing edge will have a weight value too

    // T is the generic data, could be type int, string...
    // storing graph in adjacency list
    // Map where each key element is a T representing each node in graph, V represents the edge label
    // Each value of each key is a set of edges
    // edges represented in a edge class, key element is the child node, value is edgelabel
    private HashMap<T, ArrayList<Edge<T, V>>> graph;
    // sum of degree of all vertices
    private int sum_degree = 0;
    // number of edges in graph
    private int num_edges = 0;

    // representing a empty graph
    public final static Graph<?, ?> emptyGraph = new Graph<>();

    /**
     * Creates a empty directed graph
     * @param none
     * @effects Construct a new empty directed raph with no nodes or edges
     * @requires none
     * @modifies none
     * @returns none
     * @throws none
     */
    public Graph(){
        // construct empty graph
        graph = new HashMap<T, ArrayList<Edge<T, V>>>();
        checkRep();
    }

    /**
     * Creates a graph based of a inputed graph, g.
     * @param g graph to be cloned
     * @effects Construct a new empty based of g
     * @requires none
     * @modifies none
     * @returns
     * @throws none
     */
    public Graph(Graph<T, V> g){
        // construct empty graph
        graph = g.getGraph();
        checkRep();
    }

    /**
     * Accessor method that returns a copy of the graph
     * @param none
     * @effects none
     * @modifes none
     * @requires none
     * @returns copy of graph
     * @throws none
     */
    public HashMap<T, ArrayList<Edge<T, V>>> getGraph(){
        return new HashMap<T, ArrayList<Edge<T, V>>>(graph);
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
    public void addNode(T nodeData){
        // ensures that nodeData != null
        if (nodeData == null){
            throw new IllegalArgumentException("NodeData is null");
        }
        ArrayList<Edge<T, V>> emptyEdges = new ArrayList<>();
        // insert node with no edges into graph
        graph.put(nodeData, emptyEdges);
        checkRep();
    }

    /**
     * Creates a edge from parent to child with edge label in graph
     * @param parentNode the node in graph which new Edge is being made
     * @param childNode the child the parent node is being connected to, to create edge
     * @param edgeLabel label of the edge, or the weight
     * @requires all parameters != null and parent, child node both exist in graph
     * @modifies the edges of this graph, add a new edge to graph.get(parentNode).add(edge)
     * @modifies the outgoing edges of parent node
     * @effects add edge with label to graph if the edge does not already exist
     * @throws IllegalArgumentException if any param is null
     * @returns none
     */
    public void addEdge(T parentNode, T childNode, V edgeLabel){
        if (parentNode == null || childNode == null || edgeLabel == null){
            throw new IllegalArgumentException("NodeData is null");
        }
        // Create new edge with child node and edge label
        graph.get(parentNode).add(new Edge<>(childNode, edgeLabel));
        // Update number of edges in graph and degree sum
        num_edges++;
        sum_degree += 2;
        
        checkRep();
    }
    


    /**
     * returns a iterator which represent nodes in lexicographical order
     * @param none
     * @returns a iterator of nodes in lexicographical order
     * @requires none
     * @modifes none
     * @effects none
     * @throws none
     */
    public Iterator<T> listNodes(){
        // sort nodes in lexicographical order using tree set, 
        Set<T> nodes = new TreeSet<>(graph.keySet());
        Iterator<T> it = nodes.iterator();
        return it;
    }

    /**
     * returns a iterator which represent the list of childNode and edgelabel in lexicographical order
     * @param parentNode a node in graph
     * @returns a iterator of childNodes(edgeLabel) of parentNode in lexicographical order 
     * @returns a empty iterator if parentnode is not in graph or if parentNode is null
     * @requires parentNode != null 
     * @modifies none
     * @effects none
     * @throws none
     */
    public Iterator<String> listChildren(T parentNode){
        // if null parentNode return empty iterator
        if(parentNode == null || !graph.containsKey(parentNode)){
            ArrayList<String> empty = new ArrayList<String>();
            Iterator<String> it = empty.iterator();
            return it;
        }
        else{
            // sort lexicographical order, if same alphabet, consider edgeLabel
            TreeSet<Edge<T,V>> sortedChildren = new TreeSet<>(compareEdge);
            sortedChildren.addAll(graph.get(parentNode));
            // iterate over sorted children and create set of strings representing edge
            ArrayList<String> formatted_edges = new ArrayList<>(sortedChildren.size());
            for (Edge<T, V> edge: sortedChildren){
                formatted_edges.add(edge.getKey() + "(" + edge.getValue() + ")");
            }
            Iterator<String> it = formatted_edges.iterator();
            return it;
            }
        }

    /**
     * returns a sorted set of edges which represent the list of childNode and edgelabel in lexicographical order
     * @param parentNode a node in graph
     * @returns a iterator of childNodes(edgeLabel) of parentNode in lexicographical order 
     * @returns a empty iterator if parentnode is not in graph or if parentNode is null
     * @requires parentNode != null 
     * @modifies none
     * @effects none
     * @throws none
     */
    public TreeSet<Edge<T, V>> listChildren2(T parentNode){
        // if null parentNode return empty iterator
        if(parentNode == null || !graph.containsKey(parentNode)){
            TreeSet<Edge<T, V>> empty = new TreeSet<Edge<T, V>>();
            return empty;
        }
        else{
            // sort lexicographical order, if same alphabet, consider edgeLabel
            TreeSet<Edge<T,V>> sortedChildren = new TreeSet<>(compareEdge);
            sortedChildren.addAll(graph.get(parentNode));
            return sortedChildren;
        }
    }

    /**
     * Comparator to properly sort the children nodes or edges of a parentndoe
     * lexicographical order
     * @Override TreeSet<AbstractMap.SimpleEntry<String,String>> comparator
     */
    Comparator<Edge<T, V>> compareEdge = 
    new Comparator<Edge<T, V>>(){
        @Override
        // compare 2 pairs in set of edges
        public int compare(Edge<T, V> p1, Edge<T, V> p2){
            // if edges have same node, compare edge label
            if (p1.getKey().equals(p2.getKey())){
                return p1.getValue().compareTo(p2.getValue());
            }
            else{
                // otherwise compare through node lexicographical order
                return p1.getKey().compareTo(p2.getKey());
            }
        }
    };

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
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Graph<?, ?> myClass = (Graph<?, ?>) obj;
        return this.graph.equals(myClass.graph);
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
        final int prime = 37;
        int result = 1;
        // Calculate hash code based on graph, num_edges, and sum_degree fields
        result = prime * result + ((graph == null) ? 0 : graph.hashCode());
        result = prime * result + num_edges;
        result = prime * result + sum_degree;
        return result; // Return the final hash code
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
    public boolean containsNode(T node){
        if (graph.containsKey(node)){
            return true;
        }
        return false;
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
    public boolean containsEdge(T parent, Edge<T, V> edge){
        if (graph.get(parent).contains(edge)){
            return true;
        }
        return false;
    }
    /**
     * checks that the representation invariant holds (if any)
     * @param none
     * @throws RuntimeException if the rep invariant is violated
     * @requires none
     * @modifies nones
     * @effects none
     * @returns none
     */
    private void checkRep() throws RuntimeException{
    //     // ensure graph is not null
    //     if (graph == null){
    //         throw new RuntimeException("graph is null");
    //     }
    //     // Every node and edge != null is ensured through methods
    //     // ensure handshake theorem applies
    //     if ((num_edges * 2) != sum_degree){
    //         // the sum of the degrees of all vertices is twice the number of edges
    //         throw new RuntimeException("Handshake theorem violated, not valid graph");
    //     }
        
    }
}