package hw6;
import hw4.*;
import static org.junit.jupiter.api.Assertions.*;


import org.junit.Test;


/**
 * This class contains a set of test cases to test
 * the implementation of LegoPaths
 * <p>
 * LegoPaths uses the implemented Graph class from Hw4
 * to store data relating to courses and professors who have taught that course.
 * This test suite should validate the correctness of LegoPaths along with
 * evaluating the performance of LegoPaths
 * <p>
 */

 public class LegoPathsTest{    
    // start of test suite

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  basic constructor
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testConstrutcor(){
        // test Constructor creating empty legoGraph
        LegoPaths testGraph = new LegoPaths();
        // should return false since empty
        assertFalse(testGraph.getGraph().listNodes().hasNext());
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  test CreateNewGraph
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void UnknownCreateNewGraph(){
        // test if unknown file entered, should be empty graph
        LegoPaths testGraph = new LegoPaths();
        testGraph.createNewGraph("I do not exist");
        assertFalse(testGraph.getGraph().listNodes().hasNext());
        // since empty graph, should be nodes so hasNext should return false
    }

    @Test
    public void validCreateGraph(){
        // should create valid legoGraph if valid file inputed
        LegoPaths testGraph = new LegoPaths();
        testGraph.createNewGraph("data/selfSmall.csv");

        // ensure all lego part nodes are added
        assertTrue(testGraph.getGraph().containsNode("lego1"));
        assertTrue(testGraph.getGraph().containsNode("lego2"));
        assertTrue(testGraph.getGraph().containsNode("lego3"));

        // ensure all the lego part edges are added
        assertTrue(testGraph.getGraph().containsEdge("lego1", new Edge<String, Double>("lego2", 1.0/3)));
        assertTrue(testGraph.getGraph().containsEdge("lego2", new Edge<String, Double>("lego1", 1.0/3)));
        assertTrue(testGraph.getGraph().containsEdge("lego1", new Edge<String, Double>("lego3", 1.0)));
        assertTrue(testGraph.getGraph().containsEdge("lego3", new Edge<String, Double>("lego1", 1.0)));
        assertTrue(testGraph.getGraph().containsEdge("lego2", new Edge<String, Double>("lego3", 1.0)));
        assertTrue(testGraph.getGraph().containsEdge("lego3", new Edge<String, Double>("lego2", 1.0)));

    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  test findPath
    ///////////////////////////////////////////////////////////////////////////////////////
    
    @Test
    public void testFindPathNullProf(){
        // test if null professors are added
        LegoPaths testGraph = new LegoPaths();
        testGraph.createNewGraph("data/selfSmall.csv");
        // should throw illegal argument exception
        assertThrows(IllegalArgumentException.class, ()->testGraph.findPath(null, null));
    }

    @Test
    public void testFindPathUnknown(){
        // test if unknown professor is inputed, return unknown professor
        LegoPaths testGraph = new LegoPaths();
        testGraph.createNewGraph("data/selfSmall.csv");
        // should all return unknown professors
        assertTrue(testGraph.findPath("haha", "hehe").contains("unknown part"));
        assertTrue(testGraph.findPath("1", "hehe").contains("unknown part"));
        assertTrue(testGraph.findPath("haha", "1").contains("unknown part"));
    }

    @Test
    public void testFindPathAlready(){
        // if destination already reached before BFS algorithm
        LegoPaths testGraph = new LegoPaths();
        testGraph.createNewGraph("data/selfSmall.csv");
        assertEquals("path from lego1 to lego1:\ntotal cost: 0.000\n", testGraph.findPath("lego1", "lego1"));
    }

    @Test
    public void testFindPath(){
        LegoPaths testGraph = new LegoPaths();
        testGraph.createNewGraph("data/selfSmall.csv");
        // test if shortest weighted path is found
        String p1 = "lego1";
        String p2 = "lego2";
        String p3 = "lego3";
        // shortest path from lego1 to lego2 should be edge with weight 1/3
        assertEquals("path from lego1 to lego2:\nlego1 to lego2 with weight 0.333\ntotal cost: 0.333\n", testGraph.findPath(p1, p2));
        // shortest path from lego1 to lego3 should be edge with weight 1
        assertEquals("path from lego1 to lego3:\nlego1 to lego3 with weight 1.000\ntotal cost: 1.000\n", testGraph.findPath(p1, p3));
    }

    @Test
    public void testFindPath2(){
        LegoPaths testGraph = new LegoPaths();
        testGraph.createNewGraph("data/selfMedium.csv");
        // test if shortest weighted path is found
        String p1 = "Harry";
        String p2 = "Hermione";
        String p3 = "Goyle";
        String p4 = "Dumbledore";
        // shortest path from each harrypotter character to each other character, weight corresponds to inverse of set they are in
        assertEquals("path from Harry to Goyle:\nHarry to Goyle with weight 1.000\ntotal cost: 1.000\n", testGraph.findPath(p1, p3));
        assertEquals("path from Harry to Hermione:\nHarry to Hermione with weight 0.333\ntotal cost: 0.333\n", testGraph.findPath(p1, p2));
        // this should return no path since there is no edge between harry and thumbledore
        assertEquals("path from Harry to Dumbledore:\nno path found\n", testGraph.findPath(p1, p4));
    }
 }
