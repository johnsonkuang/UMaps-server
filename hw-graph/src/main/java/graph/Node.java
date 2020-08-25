package graph;

import java.util.*;

/**
 *  <b>Node</b> represents a mutable element in the Graph ADT.
 *  Abstract value: a Node has a collection of edges represented as
 *                  {e_1, e_2, ... , e_n}
 *
 * @param <N> Type of the node label
 * @param <E> Type of the edge label
 */
public final class Node<N, E> {
    /**
     * Abstract Function:
     *      AF(this): a un-ordered collection of edges given by this.edges()
     *                  {e_1, e_2, ... e_n} represents the set of individual edges
     *                  with this as the parent node
     *
     * Rep Invariant:
     *      edges != null &&
     *      every edge in edges != null &&
     *      every edge in edges has a unique label &&
     *      label != null
     */

    /**
     * setting for expensive (runtime O(n) or above checks in Graph ADT
     */
    private static final boolean DEBUG = false;
    private final N label;
    private Set<DirectedEdge> edges;

    /**
     *
     * @param label the label of this node
     * @spec.effects Constructs a new Node labeled 'label'
     */
    public Node(N label){
        this.label = label;
        edges = new HashSet<>();
        checkRep();
    }

    /**
     * Returns an iterator of this node's DirectedEdges
     * @return an iterator of this node's DirectedEdges
     */
    public Set<DirectedEdge> getEdges(){
        return Collections.unmodifiableSet(edges);
    }

    /**
     * Returns the label of this Node
     * @return the label of this node
     */
    public N getLabel(){
        return this.label;
    }

    /**
     * Adds a new DirectedEdge into this.edges setting this as the parent node
     *
     * Multiple edges can connect the same parent-child pair if they have different labels
     *
     * @param end child node of edge
     * @param label label of new edge
     * @return true if added successfully, false otherwise
     * @spec.requires end != null
     * @spec.modifies this.edges
     * @spec.effects this.edges.contains(newEdge)
     */
    public boolean addEdge(Node<N, E> end, E label){
        if(end != null && label != null){
            DirectedEdge newEdge = new DirectedEdge(end, label);
            boolean success = edges.add(newEdge);
            checkRep();
            return success;
        }
        return false;
    }

    /**
     * Standard equality operation.
     *
     * @param obj the object to be compared for equality
     * @return true if and only if 'obj' is an instance of a DirectedEdge and 'this' and 'obj' represent
     * the same edge.
     */
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Node<?, ?>)){
            return false;
        }
        Node<?, ?> tempNode = (Node<?, ?>) obj;
        //Nodes are the same if they have the same label since we enforce an invariant that all nodes in a graph
        //have unique labels
        return this.label.equals(tempNode.label);
    }

    /**
     * Standard hashCode function.
     *
     * @return an int that all objects equal to this will also return
     */
    @Override
    public int hashCode() {
        return this.label.hashCode() * 31;
    }

    private void checkRep(){
        assert edges != null: "edges has not been initialized";
        if(DEBUG) {
            Set<E> uniqueLabels = new HashSet<>();
            for(Node<N, E>.DirectedEdge edge: edges){
                assert edge != null: "edge is null";
                assert edge.label != null;
                assert !uniqueLabels.contains(edge.label) : "label is not unique";
                uniqueLabels.add(edge.label);
            }
        }
    }

    /**
     * <b>DirectedEdge</b> represents an immutable directed connection between two Node objects
     * in the graph
     *
     * Spec Field:
     * this.end = the endpoint Node
     * this.label the label of this edge
     */

    public class DirectedEdge {
        /**
         * Abstraction Function: this.edge represents the endpoint and this.label represents the label of this edge
         * Rep Invariant:
         *      end != null &&
         *      label != null
         */

        private final Node<N, E> end;
        private final E label;

        /**
         * @param end the child node of the directed edge
         * @param label the label of the edge
         * @spec.requires end != null
         * @spec.effects Constructs a new DirectedEdge with a child node 'end' and labeled as 'label'
         */
        public DirectedEdge(Node<N, E> end, E label){
            this.end = end;
            this.label = label;
            checkRep();
        }

        /**
         * Gets the label of this edge
         * @return the label of this edge
         */
        public E getLabel(){
            return this.label;
        }

        /**
         * Gets the end node of this edge
         * @return the end node of this edge
         */
        public Node<N, E> getEnd(){
            return this.end;
        }

        private void checkRep() {
            assert end != null: "null endpoint";
            assert label != null: "null label";
        }

        /**
         * Standard equality operation.
         *
         * @param obj the object to be compared for equality
         * @return true if and only if 'obj' is an instance of a DirectedEdge and 'this' and 'obj' represent
         * the same edge.
         */
        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof Node<?, ?>.DirectedEdge)){
                return false;
            }
            Node<?, ?>.DirectedEdge edge_obj = (Node<?, ?>.DirectedEdge) obj;
            return this.label.equals(edge_obj.label) && this.end.equals(edge_obj.end);
        }

        /**
         * Standard hashCode function.
         *
         * @return an int that all objects equal to this will also return
         */
        @Override
        public int hashCode() {
            return this.label.hashCode() * 31 + this.end.hashCode();
        }
    }
}
