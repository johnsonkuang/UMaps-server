package campuspaths;

import com.google.gson.Gson;
import pathfinder.CampusMap;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.textInterface.CoordinateProperties;
import pathfinder.textInterface.Direction;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import campuspaths.utils.CORSFilter;

public class SparkServer {

    public static void main(String[] args) {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();
        // The above two lines help set up some settings that allow the
        // React application to make requests to the Spark server, even though it
        // comes from a different server.
        // You should leave these two lines at the very beginning of main().

        //Initialize one CampusMap for the server
        CampusMap UW = new CampusMap();

        //gets the building names of every building on the map
        Spark.get("/buildings", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                Gson gson = new Gson();
                return gson.toJson(UW.buildingNames());
            }
        });

        //gets the shortest path between start and destination building
        //route: "/path?start=BUILDING-ABR&dest=BUILDING-ABR"
        Spark.get("/path", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String startString = request.queryParams("start");
                String destString = request.queryParams("dest");
                if(startString == null || destString == null){
                    Spark.halt(400, "Must have a start and destination building");
                }

                Path<Point> shortestPath = null;
                try{
                    shortestPath = UW.findShortestPath(startString, destString);
                } catch (Exception e){
                    Spark.halt(400, "Building is not part of campus map");
                }
                Gson gson = new Gson();
                return gson.toJson(shortestPath);
            }
        });

        Spark.get("/email-directions", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String startString = request.queryParams("start");
                String destString = request.queryParams("dest");
                if(startString == null || destString == null){
                    Spark.halt(400, "Must have a start and destination building");
                }

                Path<Point> shortestPath = null;
                try{
                    shortestPath = UW.findShortestPath(startString, destString);
                } catch (Exception e){
                    Spark.halt(400, "Building is not part of campus map");
                }
                Gson gson = new Gson();
                return gson.toJson(showPath(UW.longNameForShort(startString),
                                            UW.longNameForShort(destString),
                                            shortestPath));
            }
        });
    }

    private static String showPath(String start, String end, Path<Point> path) {
        String out = "Path from " + start + " to " + end + ": ";
        StringBuilder str = new StringBuilder(out);
        for(Path<Point>.Segment pathSegment : path) {
            Direction dir = Direction.resolveDirection(pathSegment.getStart().getX(),
                    pathSegment.getStart().getY(),
                    pathSegment.getEnd().getX(),
                    pathSegment.getEnd().getY(),
                    CoordinateProperties.INCREASING_DOWN_RIGHT);
            str.append(String.format("     Walk %.0f feet %s to (%.0f, %.0f) ->  ",
                    pathSegment.getCost(),
                    dir.name(),
                    pathSegment.getEnd().getX(),
                    pathSegment.getEnd().getY()));
        }
        str.append(String.format("Total distance: %.0f feet", path.getCost()));
        return str.toString();
    }

}
