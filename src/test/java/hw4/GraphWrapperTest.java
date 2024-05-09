package hw4;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

/**
 * This class contains a set of test cases to test
 * the implementation of Graph class
 * <p>
 * Graph is implemented and should pass all the tests in this suite.
 * This test suite should have the possible test cases and show the
 * usage of Graph class.
 * <p>
 */
public final class GraphWrapperTest{
    // Helper functions with testing

    // Creates a random graph with randome number of nodes
    private GraphWrapper createRandomGraph(int randomNum){
        GraphWrapper testGraph = new GraphWrapper();
        // add nodes to graph
        for (int i = 0; i < randomNum; i++){
            testGraph.addNode(String.valueOf(i));
        }
        return testGraph;
    }

    // add edges to graph
    private void addEdges(GraphWrapper graph, int randomNum){
        for (int i = 0; i < randomNum-1; i++){
            // add a edge to graph
            graph.addEdge(String.valueOf(i), String.valueOf(i+1), 
            String.valueOf(i+1));
        }
    }

    // add randomized edges
    private void addRandomEdges(GraphWrapper graph, int randomNum){
        for (int i = 0; i < randomNum-1; i++){
            // add a random edge to graph
            int randomInt = new Random().nextInt(randomNum);
            graph.addEdge(String.valueOf(i), String.valueOf(randomInt), 
            String.valueOf(randomInt));
        }
    }
    

    // Takes in a iterator of sorted nodes and creates a arraylist of nodes
    private ArrayList<String> createNodeArray(Iterator<String> it){
        ArrayList<String> nodes = new ArrayList<String>();
        while(it.hasNext()){
            nodes.add(it.next());
        }
        return nodes;
    }
    // Start of test suite
    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Constructor
    ///////////////////////////////////////////////////////////////////////////////////////   
  
    @Test
    public void testConstructor() {
        //  Test empty graph creation, testing unctionality of constructor
        new GraphWrapper();
        GraphWrapper testEmptyGraph = new GraphWrapper();
        // should be a empty since no nodes exist and therefore no edges exists
        assertTrue(createNodeArray(testEmptyGraph.listNodes()).isEmpty());
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  addNode
    ///////////////////////////////////////////////////////////////////////////////////////   
    
    @Test
    public void testAddNode(){
        // Test adding one node to the graph
        GraphWrapper newGraph = new GraphWrapper();
        newGraph.addNode("E");
        assertTrue(newGraph.containsNode("E"));

    }

    @Test
    public void testAddMultipleNodes(){
        // Test adding multiple nodes to the graph
        GraphWrapper newGraph = new GraphWrapper();
        String[] alphabetUpper = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", 
        "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        // random number of nodes added to graph
        int randomNum = new Random().nextInt(alphabetUpper.length);
        // add nodes to graph
        for (int i = 0; i < randomNum; i++){
            newGraph.addNode(alphabetUpper[i]);
            // ensure at each instance a node is being added
            assertTrue(newGraph.containsNode(alphabetUpper[i]));
        }
    }

    @Test
    public void testAddNullNode(){
        // test if adding a null node, should not be allowed
        GraphWrapper testGraph = new GraphWrapper();
        assertThrows(IllegalArgumentException.class, () -> testGraph.addNode(null));
    }

    @Test
    public void testAddExistingNode(){
        // test if adding a existing node, will not add since no duplicates
        GraphWrapper newGraph = new GraphWrapper();
        newGraph.addNode("A");
        newGraph.addNode("A");
        // should only be one node
        assertTrue(createNodeArray(newGraph.listNodes()).size() == 1);
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  addEdge
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testAddEdge(){
        // adding a few random nodes
        GraphWrapper testGraph = new GraphWrapper();
        testGraph.addNode("A");
        testGraph.addNode("B");
        // adding a edge
        testGraph.addEdge("A", "B", "3");
        // test if edge has been added
        assertTrue(testGraph.containsEdge("A", new Edge<String, String> ("B", "3")));

    }

    @Test
    public void testAddMultipleEdges(){
        // random number of nodes added to graph
        int randomNum = new Random().nextInt(50);
        GraphWrapper testGraph = createRandomGraph(randomNum);
        // add edges to testGraph
        addEdges(testGraph, randomNum);
        // check if nodes have been properly added to graph
        for (int i = 0; i < randomNum-1; i++){
            assertTrue(testGraph.containsEdge(String.valueOf(i),
            new Edge<String, String> (String.valueOf(i+1), String.valueOf(i+1))));
        }
    }

    @Test
    public void testReflectiveEdge(){
        // test a edge that reflects, parent and child same node
        GraphWrapper testGraph = new GraphWrapper();
        // add a node and a edge that reflects
        testGraph.addNode("A");
        testGraph.addEdge("A", "A", "1");
        assertTrue(testGraph.containsEdge("A", new Edge<String, String> ("A", "1")));
    }

    @Test
    public void testAddNullEdge(){
        // test adding a Null edge, should throw exception
        GraphWrapper testGraph = new GraphWrapper();
        assertThrows(IllegalArgumentException.class, () -> testGraph.addEdge(null, null, null));
    }
    
    @Test
    public void testAddExistentEdge(){
        // test adding a existing edge, should not be added no dupes
        GraphWrapper testGraph = new GraphWrapper();
        testGraph.addNode("A");
        testGraph.addNode("B");
        // same edge
        testGraph.addEdge("A", "B", "1");
        testGraph.addEdge("A", "B", "1");
        // parent should only have one edge since no duplicate edges
        assertTrue(createNodeArray(testGraph.listChildren("A")).size() == 1);
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////
    ////  listNodes
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testListNodes(){
        // test listNodes with a singular node
        GraphWrapper testGraph = new GraphWrapper();
        testGraph.addNode("A");
        // iterator of sorted nodes
        Iterator<String> it = testGraph.listNodes();
        // iteratre through itertaor to create array list of sorted nodes
        ArrayList<String> nodes = createNodeArray(it);
        String expected[] = {"A"};
        // compare recieved listNodes against expected listNodes
        assertArrayEquals(expected, nodes.toArray());
    }

    @Test
    public void testBigListNodes(){
        // test listNodes with a few node
        GraphWrapper testGraph = new GraphWrapper();
        // should sort properly or alphabetically
        testGraph.addNode("A");
        testGraph.addNode("D");
        testGraph.addNode("B");
        testGraph.addNode("F");
        // iterator of sorted nodes
        Iterator<String> it = testGraph.listNodes();
        // iteratre through itertaor to create array list of sorted nodes
        ArrayList<String> nodes = createNodeArray(it);
        String expected[] = {"A", "B", "D", "F"};
        // compare recieved listNodes against expected listNodes
        assertArrayEquals(expected, nodes.toArray());
    }

    @Test
    public void testBiggerListNodes(){
        // test listNodes with mutiple nodes
        int randomInt = new Random().nextInt(100);
        // create a random graph with random nodes
        GraphWrapper testGraph = createRandomGraph(randomInt);
        // create iterator of sorted nodes and put into arrayList
        Iterator<String> it  = testGraph.listNodes();
        ArrayList<String> nodes = createNodeArray(it);
        // check listNodes functionality
        for(int i = 0; i < randomInt-1; i++){
            // compare next node vs previous node lexicographically
            assertTrue(nodes.get(i).compareTo(nodes.get(i+1)) < 0);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  listChildren
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testListChildren(){
        // test listChildren correctly gives a iterator to sorted children nodes(edges)
        // create graph
        GraphWrapper testGraph = new GraphWrapper();
        testGraph.addNode("A");
        testGraph.addNode("B");
        testGraph.addNode("C");
        testGraph.addNode("D");
        testGraph.addEdge("A", "B", "1");
        testGraph.addEdge("A", "C", "1");
        testGraph.addEdge("A", "D", "3");
        // iterator
        Iterator<String> it = testGraph.listChildren("A");
        ArrayList<String> nodes = createNodeArray(it);
        // expected results
        String[] expected = {"B(1)", "C(1)", "D(3)"};
        // compare expected results with recieved results
        assertArrayEquals(expected, nodes.toArray());
    }

    @Test
    public void testFewListChildren(){
        // test listChildren with a few nodes, 
        // simlar to first test but ensures listChildren compares edgelabel when nodes lexicographiclly same
        // create graph
        GraphWrapper testGraph = new GraphWrapper();
        testGraph.addNode("A");
        testGraph.addNode("B");
        testGraph.addNode("C");
        testGraph.addEdge("A", "B", "1");
        testGraph.addEdge("A", "B", "2");
        testGraph.addEdge("A", "C", "3");
        // iterator
        Iterator<String> it = testGraph.listChildren("A");
        ArrayList<String> nodes = createNodeArray(it);
        // expected results
        String[] expected = {"B(1)", "B(2)", "C(3)"};
        // compare expected results with recieved results
        assertArrayEquals(expected, nodes.toArray());
    }

    @Test
    public void testManyListChilderen(){
        // test listChildren with many nodes
        // create a random graph with random nodes, edges
        int randomInt = new Random().nextInt(100);
        GraphWrapper testGraph = createRandomGraph(randomInt);
        addEdges(testGraph, randomInt);
        addRandomEdges(testGraph, randomInt);
        // iterator of listChildren of a random parent node
        int randParent = new Random().nextInt(100);
        Iterator<String> it = testGraph.listChildren(String.valueOf(randParent));
        ArrayList<String> nodes = createNodeArray(it);
        // test if listChildren sorted children nodes correctly
        for (int i = 0; i < nodes.size()-1; i++){
            // each next node should be lexigraphically equal or greater than prev
            assertTrue(nodes.get(i).compareTo(nodes.get(i+1)) < 0);
        }
    }

    @Test
    public void testNoParentChildren(){
        // test if a parent node that does not exist in graph is inputed
        // create a graph
        GraphWrapper testGraph = new GraphWrapper();
        testGraph.addNode("B");
        testGraph.addNode("C");
        testGraph.addEdge("B", "C", "1");
        // iterator with non existent parent
        Iterator<String> nodes = testGraph.listChildren("A");
        // non existent parent node inputed should return a empty iterator
        assertTrue(!nodes.hasNext());
    }

    @Test
    public void testNullListChildren(){
        // test if null parent node is inputed, should return a empty iterator
        // create a graph
        GraphWrapper testGraph = new GraphWrapper();
        testGraph.addNode("A");
        testGraph.addNode("B");
        testGraph.addEdge("A", "B", "1");
        // iterator with null parent
        Iterator<String> nodes = testGraph.listChildren(null);
        // null parent node inputed should return a empty iterator
        assertTrue(!nodes.hasNext());
    }

    
    ///////////////////////////////////////////////////////////////////////////////////////
    ////  getGraph
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testGetGraph(){
        GraphWrapper testGraph = new GraphWrapper();
        // intialized a empty graph, so getGraph should return a empty graph
        assertTrue(testGraph.getGraph().equals(Graph.emptyGraph.getGraph()));
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  containsNode and containsEdge
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testContainsNode(){
        // check if containsNode returns correct boolean based on if a node is in graph
        GraphWrapper testGraph = new GraphWrapper();
        testGraph.addNode("A");
        // should return true since A is added in graph
        assertTrue(testGraph.containsNode("A"));
        // should return false since B does not exist in graph
        assertFalse(testGraph.containsNode("B"));
    }
    @Test
    public void testContainsEdge(){
        // check if containsEdge correctly returns boolean base on if edge is in graph
        GraphWrapper testGraph = new GraphWrapper();
        testGraph.addNode("A");
        testGraph.addNode("B");
        testGraph.addNode("C");
        testGraph.addEdge("A", "B", "AB");
        // should return true since edge is in graph
        assertTrue(testGraph.containsEdge("A", new Edge<String, String>("B", "AB")));
        // should return false since this edge does not exist
        assertFalse(testGraph.containsEdge("A", new Edge<String, String>("C", "AC")));
    }
}
