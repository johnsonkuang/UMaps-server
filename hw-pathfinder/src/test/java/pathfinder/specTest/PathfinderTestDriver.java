/*
 * Copyright (C) 2020 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2020 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package pathfinder.specTest;

import graph.Graph;
import graph.Node;
import marvel.MarvelPaths;
import pathfinder.DijkstraUtil;
import pathfinder.datastructures.Path;

import java.io.*;
import java.util.*;

/**
 * This class implements a test driver that uses a script file format
 * to test an implementation of Dijkstra's algorithm on a graph.
 */
public class PathfinderTestDriver {

    private final Map<String, Graph<String, Double>> graphs = new HashMap<>();
    private final PrintWriter output;
    private final BufferedReader input;

    // Leave this constructor public
    public PathfinderTestDriver(Reader r, Writer w) {
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    // Leave this method public
    public void runTests() throws IOException {
        String inputLine;
        while ((inputLine = input.readLine()) != null) {
            if ((inputLine.trim().length() == 0) ||
                    (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            } else {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if (st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<String>();
                    while (st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            switch(command) {
                case "FindPath":
                    findPath(arguments);
                    break;
                case "CreateGraph":
                    createGraph(arguments);
                    break;
                case "AddNode":
                    addNode(arguments);
                    break;
                case "AddEdge":

                    addEdge(arguments);
                    break;
                case "ListNodes":
                    listNodes(arguments);
                    break;
                case "ListChildren":
                    listChildren(arguments);
                    break;
                default:
                    output.println("Unrecognized command: " + command);
                    break;
            }
        } catch(Exception e) {
            output.println("Exception: " + e.toString());
        }
    }

    private void findPath(List<String> arguments){
        if(arguments.size() != 3) {
            throw new CommandException("Bad arguments to FindPath: " + arguments);
        }

        String graphName = arguments.get(0);
        String start = arguments.get(1);
        start = start.replaceAll("_", " ");
        String dest = arguments.get(2);
        dest = dest.replaceAll("_", " ");

        findPath(graphName, start, dest);
    }

    private void findPath(String graphName, String start, String dest) {
        Graph<String, Double> g = graphs.get(graphName);
        try{

            Path<String> shortestPath = DijkstraUtil.dijkstraSearch(g, start, dest);
            output.println("path from " + start + " to " + dest + ":");
            if(shortestPath != null){
                for(Path<String>.Segment s: shortestPath) {
                    output.println(s.getStart() + " to " + s.getEnd() + " with weight " + String.format("%.3f", s.getCost()));
                }
            } else {
                output.println("path not found");
            }
            output.println("total cost: " + String.format("%.3f", shortestPath.getCost()));
        } catch (Exception e){
            if(!g.containsNode(new Node<String, Double>(start))){
                output.println("unknown node " + start);
            }
            if(!g.containsNode(new Node<String, Double>(dest))){
                output.println("unknown node " + dest);
            }
        }
    }
    private void createGraph(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        graphs.put(graphName, new Graph<String, Double>());
        output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to AddNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        Graph<String, Double> g = graphs.get(graphName);
        g.addNode(new Node<String, Double>(nodeName));
        output.println("added node " + nodeName + " to " + graphName);
    }

    private void addEdge(List<String> arguments) {
        if(arguments.size() != 4) {
            throw new CommandException("Bad arguments to AddEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        Double edgeLabel = Double.parseDouble(arguments.get(3));

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
                         Double edgeLabel) {
        Graph<String, Double> g = graphs.get(graphName);
        Node<String, Double> parent = g.getNode(parentName);
        Node<String, Double> child = g.getNode(childName);
        g.addEdge(parent, child, edgeLabel);
        output.println("added edge " + String.format("%.3f", edgeLabel) + " from " + parentName +
                " to " + childName + " in " + graphName);
    }

    private void listNodes(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to ListNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {
        Graph<String, Double> g = graphs.get(graphName);
        List<Node<String, Double>> sortedNodes = new ArrayList<>();
        for(Node<String, Double> n: g.getNodes()){
            sortedNodes.add(n);
        }
        Collections.sort(sortedNodes, new SortbyLabel());
        String out = graphName + " contains:";
        StringBuilder str = new StringBuilder(out);
        for(Node<String, Double> n: sortedNodes){
            str.append(" " + n.getLabel());
        }
        output.println(str);
    }

    class SortbyLabel implements Comparator<Node<String, Double>>{
        public int compare(Node<String, Double> n1, Node<String, Double> n2){
            return n1.getLabel().compareTo(n2.getLabel());
        }
    }

    private void listChildren(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to ListChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {
        Graph<String, Double> g = graphs.get(graphName);
        Node<String, Double> n = g.getNode(parentName);

        List<Node<String, Double>.DirectedEdge> lst = new ArrayList<>();
        for(Node<String, Double>.DirectedEdge e: n.getEdges()){
            lst.add(e);
        }
        lst.sort(new Comparator<Node<String, Double>.DirectedEdge>() {
            @Override
            public int compare(Node<String, Double>.DirectedEdge o1, Node<String, Double>.DirectedEdge o2) {
                if(o1.getEnd().equals(o2.getEnd())){
                    return o1.getLabel().compareTo(o2.getLabel());
                }
                return o1.getEnd().getLabel().compareTo(o2.getEnd().getLabel());
            }
        });
        String out = "the children of " + parentName + " in  " + graphName + " are:";
        StringBuilder str = new StringBuilder(out);
        for(Node<String, Double>.DirectedEdge e: lst){
            str.append(" " + e.getEnd().getLabel() + "(" + String.format("%.3f", e.getLabel()) + ")");
        }
        output.println(str);
    }

    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }

        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}
