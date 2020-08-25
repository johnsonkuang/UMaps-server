package graph.implTest;

import graph.Graph;
import graph.Node;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class GraphTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    //Graphs for convenience
    Graph<String, String> empty;
    Graph<String, String> singleNode;
    Graph<String, String> doubleNode;
    Graph<String, String> connectedGraph;
    Graph<String, String> singlyConnectedNode;

    //Nodes for convenience
    Node[] nodes =  new Node[]{
            new Node<String, String>("node"),
            new Node<String, String>("parent"),
            new Node<String, String>("child"),
            new Node<String, String>("isolated node"),
            new Node<String, String>("singly connected node"),
            new Node<String, String>("A"),
            new Node<String, String>("B")
    };

    List<Node<String, String>> lst_single, lst_double, lst_connected, lst_singlyConnected;

    // SetUp Method depends on Graph constructor and addEdge method
    @Before
    public void setUp(){
        lst_single = new ArrayList<>();
        lst_single.add(new Node<String, String>("node"));

        lst_connected = new ArrayList<>();
        lst_connected.add(new Node<String, String>("parent"));
        lst_connected.add(new Node<String, String>("child"));

        empty = new Graph<String, String>();

        singleNode = new Graph<String, String>();
        singleNode.addNode(new Node<String, String>("node"));

        doubleNode = new Graph<String, String>();
        doubleNode.addNode(new Node<String, String>("A"));
        doubleNode.addNode(new Node<String, String>("B"));

        connectedGraph = new Graph<String, String>();
        connectedGraph.addNode(new Node<String, String>("parent"));
        connectedGraph.addNode(new Node<String, String>("child"));
        connectedGraph.addEdge(new Node<String, String>("parent"), new Node<String, String>("child"), "e1");

        singlyConnectedNode = new Graph<String, String>();
        singlyConnectedNode.addNode(new Node<String, String>("singly connected node"));
        singlyConnectedNode.addEdge(new Node<String, String>("singly connected node"), new Node<String, String>("singly connected node"), "circular connection");
    }

    // Test observers
    @Test
    public void testGetNodes() {
        List<Node> emptyList = new ArrayList<>();
        assertArrayEquals(empty.getNodes().toArray(), emptyList.toArray());
        assertArrayEquals(singleNode.getNodes().toArray(), lst_single.toArray());
        assertArrayEquals(connectedGraph.getNodes().toArray(), lst_connected.toArray());
    }

    @Test
    public void testAddNode() {
        Graph<String, String> singleNodeTest =  new Graph<>();
        assertTrue(singleNodeTest.addNode(new Node<String, String>("node")));

        Graph<String, String> doubleNodeTest = new Graph<>();
        assertTrue(doubleNodeTest.addNode(new Node<String, String>("A")));
        assertTrue(doubleNodeTest.addNode(new Node<String, String>("B")));

        //can't add two of the same nodes
        assertFalse(singleNodeTest.addNode(new Node<String, String>("node")));
        assertFalse(doubleNodeTest.addNode(new Node<String, String>("A")));
    }

    @Test
    public void testAddEdge() {
        Graph<String, String> edgeTest = new Graph<>();
        Node<String, String> parent = new Node<>("parent");
        Node<String, String> child = new Node<>("child");
        Node<String, String> child2 = new Node<>("child2");
        assertFalse("Edge was added when both nodes are not in the graph",edgeTest.addEdge(parent,child,"e1"));
        edgeTest.addNode(parent);
        assertFalse("Edge was added when a node was not in the graph", edgeTest.addEdge(parent, child, "e1"));
        edgeTest.addNode(child);
        edgeTest.addNode(child2);
        assertTrue("Edge could not be added when both nodes were in the graph", edgeTest.addEdge(parent, child, "e1"));
        assertTrue("Edge could not be added between same nodes with different label", edgeTest.addEdge(parent, child, "e2"));
        assertTrue("Edge could not be added between a similar parent node and a different child",
                edgeTest.addEdge(parent, child2, "e3"));
        assertFalse("Edge with the same label already exists in the graph", edgeTest.addEdge(parent, child, "e1"));
        assertFalse("Edge cannot be made with node not in the graph", edgeTest.addEdge(parent, new Node<String, String>("Im so angry"), "e2"));
    }
}
