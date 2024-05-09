package hw5;
import hw4.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;
import org.junit.Test;

import hw4.Graph;


/**
 * This class contains a set of test cases to test
 * the implementation of ProfessorPaths
 * <p>
 * ProfessorPaths uses the implemented Graph class from Hw4
 * to store data relating to courses and professors who have taught that course.
 * This test suite should validate the correctness of ProfessorPaths along with
 * evaluating the performance of ProfessorPaths
 * <p>
 */
public final class ProfessorPathsTest{
    // helper functions to help with testing
    
    // Takes in a iterator of sorted nodes and creates a arraylist of nodes
    private ArrayList<String> createNodeArray(Iterator<String> it){
        ArrayList<String> nodes = new ArrayList<String>();
        while(it.hasNext()){
            nodes.add(it.next());
        }
        return nodes;
    }
    // start of test suite

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  basic constructor
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testConstructor(){
        // test the basic constructor that takes in no arguments
        // should create a empty ProfessorPaths object since no data file
        ProfessorPaths dataGraph = new ProfessorPaths();
        assertTrue(createNodeArray(dataGraph.getGraph().listNodes()).isEmpty());
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  createNewGraph
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testCreateNewGraph(){
        // test that createNewGraph succesfully creates a Graph that stores
        // the course and professor data from input file
        ProfessorPaths dataGraph = new ProfessorPaths();
        // intialize graph and fill with data from small test case
        dataGraph.createNewGraph("data/smallTest.csv");
        // check if nodes have been successfully added
        assertTrue(dataGraph.getGraph().containsNode("A Bruce Carlson"));
        assertTrue(dataGraph.getGraph().containsNode("A. Michael DelPrete"));
        assertTrue(dataGraph.getGraph().containsNode("Aalok Khandekar"));
        assertTrue(dataGraph.getGraph().containsNode("Abby J. Kinchy"));
        // check if edges have been successfully added
        assertTrue(dataGraph.getGraph().containsEdge("A Bruce Carlson", 
        new Edge<String, String>("Aalok Khandekar", "STSO-2400")));
        assertTrue(dataGraph.getGraph().containsEdge("Aalok Khandekar", 
        new Edge<String, String>("A Bruce Carlson", "STSO-2400")));
        assertTrue(dataGraph.getGraph().containsEdge("Aalok Khandekar", 
        new Edge<String, String>("Abby J. Kinchy", "STSO-6100")));
        assertTrue(dataGraph.getGraph().containsEdge("Abby J. Kinchy", 
        new Edge<String, String>("Aalok Khandekar", "STSO-6100")));

    }

    @Test
    public void testExceptionCreateNewGraph(){
        // ensures empty graph if invalid file
        ProfessorPaths testGraph = new ProfessorPaths();
        String invalidFileName = "data/notFound.csv";
        // empty graph if invalid file or nonexistent
        testGraph.createNewGraph(invalidFileName);
        assertTrue(testGraph.getGraph().equals(Graph.emptyGraph));
    }


    ///////////////////////////////////////////////////////////////////////////////////////
    ////  getGraph
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testGetGraph(){
        // test getGraph, ensure get Graph returns the graph
        ProfessorPaths testGraph = new ProfessorPaths();
        // getGraph should return a empty graph since nothing has been added to graph
        assertTrue(testGraph.getGraph().equals(Graph.emptyGraph));
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  findPath
    ///////////////////////////////////////////////////////////////////////////////////////
    
    @Test
    public void testFindPath(){
        // ensure testPath is returning the shortest Path between 2 nodes
        ProfessorPaths testGraph = new ProfessorPaths();
        testGraph.createNewGraph("data/bfsTest.csv");
        // assert shortestpath given is the shortest path between 2 nodes
        assertTrue(testGraph.findPath("1", "7").contains("1 to 3 via B\n3 to 7 via H"));
        assertTrue(testGraph.findPath("3", "5").contains("3 to 1 via B\n1 to 2 via A\n2 to 5 via D"));
        // ensure if no path exist returns no path
        assertTrue(testGraph.findPath("1", "8").contains("no path found"));
    }

    @Test
    public void testFindPath2(){
        // try more test cases with testPath to ensure correctness
        ProfessorPaths testGraph = new ProfessorPaths();
        testGraph.createNewGraph("data/smallTest.csv");
        // assert shortest path
        String path = testGraph.findPath("A Bruce Carlson", "A. Michael DelPrete");
        String path2 = testGraph.findPath("Aalok Khandekar", "Abby J. Kinchy");
        assertTrue(path.contains("A Bruce Carlson to Aalok Khandekar via STSO-2400"));
        assertTrue(path.contains("Aalok Khandekar to A. Michael DelPrete via STSO-6100"));
        // this test case ensure if there are multiple paths, the lexographical smallest path is chosen
        assertTrue(path2.contains("Aalok Khandekar to Abby J. Kinchy via STSO-2400")); 
        // STSO-2400 could have been STSO-6100, however 2400 is smaller

    }

    @Test
    public void testFindPath3(){
        // try more test cases with testPath
        ProfessorPaths testGraph = new ProfessorPaths();
        testGraph.createNewGraph("data/smallTest2.csv");
        String path = testGraph.findPath("Brian Wang-chen", "John Zolyniak");
        assertTrue(path.contains("Brian Wang-chen to Christopher Kim via 2"));
        assertTrue(path.contains("Christopher Kim to John Zolyniak via 1"));
    }

    @Test
    public void testFindPath4(){
        // try with courses test file we were given
        ProfessorPaths testGraph = new ProfessorPaths();
        testGraph.createNewGraph("data/courses.csv");
        String path = testGraph.findPath("Mohammed J. Zaki", "Wilfredo Colon");
        // expected output that was given, should be true
        assertTrue(path.contains("Mohammed J. Zaki to David Eric Goldschmidt via CSCI-2300"));
        assertTrue(path.contains("David Eric Goldschmidt to Michael Joseph Conroy via CSCI-4430"));
        assertTrue(path.contains("Michael Joseph Conroy to Alan R Cutler via CHEM-1200"));
        assertTrue(path.contains("Alan R Cutler to Wilfredo Colon via CHEM-1100"));
    }

    @Test
    public void testFindPathNullProf(){
        // test if null professors are added
        ProfessorPaths testGraph = new ProfessorPaths();
        testGraph.createNewGraph("data/bfsTest.csv");
        // should throw illegal argument exception
        assertThrows(IllegalArgumentException.class, ()->testGraph.findPath(null, null));
    }

    @Test
    public void testFindPathUnknown(){
        // test if unknown professor is inputed, return unknown professor
        ProfessorPaths testGraph = new ProfessorPaths();
        testGraph.createNewGraph("data/bfsTest.csv");
        // should all return unknown professors
        assertTrue(testGraph.findPath("haha", "hehe").contains("unknown professor"));
        assertTrue(testGraph.findPath("1", "hehe").contains("unknown professor"));
        assertTrue(testGraph.findPath("haha", "1").contains("unknown professor"));
    }

    @Test
    public void testFindPathAlready(){
        // if destination already reached before BFS algorithm
        ProfessorPaths testGraph = new ProfessorPaths();
        testGraph.createNewGraph("data/bfsTest.csv");
        assertTrue(testGraph.findPath("1", "1").contains("path from 1 to 1"));
    }

    // Corrections Tests done, move forward to test for performance
    // test findPath with extremely large files

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  findPath performance test
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void findPathPerformance(){
        ProfessorPaths testGraph = new ProfessorPaths();
        // massive data set, around twice the size of courses.csv
        testGraph.createNewGraph("data/largeData2.csv");
        System.out.println(testGraph.findPath("Crystal Townsend", "Zachary Smith"));
    }
}