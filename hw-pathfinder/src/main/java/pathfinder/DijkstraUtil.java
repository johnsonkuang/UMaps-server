package pathfinder;

import graph.Graph;
import graph.Node;
import pathfinder.datastructures.Path;

import java.util.*;

public final class DijkstraUtil {
    /**
     * This is a static class
     */


    /**
     * Finds the minimum cost path between two nodes in a graph
     * @param graph the graph to be searched
     * @param startLabel the label of the starting node
     * @param endLabel the label of the ending node
     * @return the shortest path between start and end, no path exists if returns null
     * @spec.requires graph != null, start != null, end != null, graph.contains(start), graph.contains(end)
     * @spec.throws IllegalArgumentException if any of the above statements are violated
     */
    public static <E> Path<E> dijkstraSearch(Graph<E, Double> graph, E startLabel,
                                      E endLabel){
        if(graph == null || startLabel == null || endLabel == null){
            throw new IllegalArgumentException();
        }

        Node<E, Double> start = graph.getNode(startLabel);
        Node<E, Double> dest = graph.getNode(endLabel);

        if(!graph.containsNode(start) || !graph.containsNode(dest)){
            throw new IllegalArgumentException();
        }

        Queue<Path<E>> active = new PriorityQueue<>(new SortByCost<>());
        Set<E> finished = new HashSet<>();
        Path<E> self = new Path<>(start.getLabel());
        active.add(self);

        while(!active.isEmpty()){
            // minPath is the lowest-cost path in active and,
            // if minDest isn't already 'finished,' is the
            // minimum-cost path to the node minDest
            Path<E> minPath = active.remove();
            E minDest = minPath.getEnd();

            if(minDest.equals(endLabel)){
                return minPath;
            }

            if(finished.contains(minDest)){
                continue;
            }

            for(Node<E, Double>.DirectedEdge e : graph.getNode(minDest).getEdges()){
                // If we don't know the minimum-cost path from start to child,
                // examine the path we've just found
                if(!finished.contains(e.getEnd())){
                    Path<E> newPath = minPath.extend(e.getEnd().getLabel(), e.getLabel());
                    active.add(newPath);
                }
            }
            finished.add(minDest);
        }
        return null;
    }

    private static class SortByCost<E> implements Comparator<Path<E>>{
        @Override
        public int compare(Path<E> o1, Path<E> o2) {
            return Double.compare(o1.getCost(), o2.getCost());
        }
    }
}
