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

package pathfinder;

import graph.Graph;
import graph.Node;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;
import pathfinder.parser.CampusPathsParser;

import java.util.*;

/**
 * This represents an immutable campus map of UW, also cached a version of abbreviated names mapped
 * to the longer names for efficient access.
 */
public class CampusMap implements ModelAPI {

    // AF(this) =
    //      each node in this.campusMap => a walk-able point reachable on the map
    //      each edge in this.campusMap => a segment weighted by physical distance between two points on the map

    // Rep Invariant:
    //      abbrevToLongName != null &&
    //      abbrevToPoint != null &&
    //      abbrevToLongName does not contain null keys &&
    //      abbrevToLongName does not contain null values &&
    //      abbrevToPoint does not contain null keys &&
    //      abbrevToPoint does not contain null values &&

    /**
     * map of short name abbreviations to the long names they represent
     */
    private Map<String, String> abbrevToLongName;

    /**
     * map of short name abbreviations to the points they represent
     */
    private Map<String, Point> abbrevToPoint;

    /**
     * graph representing campus map
     */
    private Graph<Point, Double> campusMap;

    private static final boolean DEBUG = false;

    /**
     * Creates a new CampusMap based on the data in campus_buildings.tsv and campus_paths.tsv.
     * Essentially this represents a map of all the walk-able segments on the UW campus
     */
    public CampusMap(){
        abbrevToLongName = new HashMap<>();
        abbrevToPoint = new HashMap<>();
        campusMap = new Graph<>();

        Iterator<CampusBuilding> itr = CampusPathsParser.parseCampusBuildings("campus_buildings.tsv").iterator();
        List<CampusPath> cpLst = CampusPathsParser.parseCampusPaths("campus_paths.tsv");

        //initialize the map of shortNames to longNames and all the Nodes of the graph
        while(itr.hasNext()){
            CampusBuilding cb = itr.next();
            abbrevToLongName.put(cb.getShortName(), cb.getLongName());

            Point pt = new Point(cb.getX(), cb.getY());
            abbrevToPoint.put(cb.getShortName(), pt);
            campusMap.addNode(new Node<Point, Double>(pt));
        }

        //initialize the edges
        for(CampusPath cp: cpLst){
            Node<Point, Double> start = campusMap.getNode(new Point(cp.getX1(), cp.getY1()));
            //start not in graph
            if(start == null){
                start = new Node<>(new Point(cp.getX1(), cp.getY1()));
                campusMap.addNode(start);
            }
            Node<Point, Double> end = campusMap.getNode(new Point(cp.getX2(), cp.getY2()));
            //end not in graph
            if(end == null){
                end = new Node<>(new Point(cp.getX2(), cp.getY2()));
                campusMap.addNode(end);
            }

            campusMap.addEdge(start, end, cp.getDistance());
            campusMap.addEdge(end, start, cp.getDistance());
        }
        checkRep();
    }

    /**
     * @param shortName The short name of a building to query.
     * @return {@literal true} iff the short name provided exists in this campus map.
     */
    @Override
    public boolean shortNameExists(String shortName) {
        checkRep();
        return abbrevToLongName.containsKey(shortName);
    }

    /**
     * @param shortName The short name of a building to look up.
     * @return The long name of the building corresponding to the provided short name.
     * @throws IllegalArgumentException if the short name provided does not exist.
     */
    @Override
    public String longNameForShort(String shortName) {
        checkRep();
        if(!abbrevToLongName.containsKey(shortName)){
            throw new IllegalArgumentException();
        }
        return abbrevToLongName.get(shortName);
    }

    /**
     * @return A mapping from all the buildings' short names to their long names in this campus map.
     */
    @Override
    public Map<String, String> buildingNames() {
        checkRep();
        return Collections.unmodifiableMap(abbrevToLongName);
    }

    /**
     * Finds the shortest path, by distance, between the two provided buildings.
     *
     * @param startShortName The short name of the building at the beginning of this path.
     * @param endShortName   The short name of the building at the end of this path.
     * @return A path between {@code startBuilding} and {@code endBuilding}, or {@literal null}
     * if none exists.
     * @throws IllegalArgumentException if {@code startBuilding} or {@code endBuilding} are
     *                                  {@literal null}, or not valid short names of buildings in
     *                                  this campus map.
     */
    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        checkRep();
        return DijkstraUtil.dijkstraSearch(campusMap,
                abbrevToPoint.get(startShortName),
                abbrevToPoint.get(endShortName));
    }


    // Rep Invariant:
    //      abbrevToLongName != null &&
    //      abbrevToPoint != null &&
    //      abbrevToLongName does not contain null elements &&
    //      abbrevToPoint does not contain null elements &&
    //      campusMap != null &&
    //      campusMap does not contain null elements

    private void checkRep(){
        assert abbrevToPoint != null : "abbrevToPoint not initialized";
        assert abbrevToLongName != null : "abbrevToLongName not initialized";
        assert campusMap != null : "graph is not initialized";

        if(DEBUG){
            for(String s : abbrevToPoint.keySet()){
                assert s != null : "key is null";
                assert abbrevToPoint.get(s) != null : "value is null";
            }
            for (String s : abbrevToLongName.keySet()){
                assert s != null : "key is null";
                assert abbrevToLongName.get(s) != null : "value is null";
            }
        }
    }

}
