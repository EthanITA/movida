
// https://gist.github.com/imamhidayat92/dff60e5554020bd58b64

package movida.bassolidong;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

// la versione che si trova in questo progetto è stato modificato

/**
 * A simple undirected and unweighted graph implementation.
 * 
 * @param <T> The type that would be used as vertex.
 */
public class Graph {
    private HashMap<String, Set<String>> adjacencyList;
    private HashMap<Integer, Set<String>> disconnectedNodesList;

    /**
     * Create new Graph object.
     */
    public Graph() {
        this.adjacencyList = new HashMap<>();
        this.disconnectedNodesList = new HashMap<>();
        this.disconnectedNodesList.put(0, new HashSet<>());
    }

    /**
     * Add new vertex to the graph.
     * 
     * @param v The vertex object.
     */
    public void addVertex(String v) {
        if (!this.adjacencyList.containsKey(v)) {
            this.adjacencyList.put(v, new HashSet<>());
            this.disconnectedNodesList.get(0).add(v);
        }
    }

    /**
     * Add new edge between vertex. Adding new edge from u to v will automatically
     * add new edge from v to u since the graph is undirected.
     * 
     * @param v Start vertex.
     * @param u Destination vertex.
     */
    public void addEdge(String v, String u) {
        if (this.adjacencyList.containsKey(v) && this.adjacencyList.containsKey(u)) {
            this.adjacencyList.get(v).add(u);
            this.adjacencyList.get(u).add(v);
        }
        updateAddDisconnectedNodesList(v, u);

    }

    /**
     * Check adjacency between 2 vertices in the graph.
     * 
     * @param v Start vertex.
     * @param u Destination vertex.
     * @return <tt>true</tt> if the vertex v and u are connected.
     */
    public boolean isAdjacent(String v, String u) {
        return this.adjacencyList.get(v).contains(u);
    }

    /**
     * Get connected vertices of a vertex.
     * 
     * @param v The vertex.
     * @return An iterable for connected vertices.
     */
    public Set<String> getNeighbors(String v) {
        return this.adjacencyList.get(v) == null ? new HashSet<>() : this.adjacencyList.get(v);
    }

    /**
     * conta quanti sottografi disconnessi sono presenti
     * 
     * @return
     */
    public Integer getNOfConnectedComponents() {
        return getDisconnectedSubgraphs().size();
    }

    /**
     * Get all vertices in the graph.
     * 
     * @return An Iterable for all vertices in the graph.
     */
    public Set<String> getAllVertices() {
        return this.adjacencyList.keySet();
    }

    /**
     * determina se esiste un cammino da u a v
     * 
     * @param u
     * @param v
     * @return
     */
    public boolean reachable(String u, String v) {
        if (adjacencyList.containsKey(u) && adjacencyList.containsKey(v)) {

            return searchKeyFor(u).equals(searchKeyFor(v));
        }
        return false;
    }

    /**
     * Restituisce tutti i sottografo come List<Set>
     * 
     * @return
     */
    public List<Set<String>> getDisconnectedSubgraphs() {
        List<Set<String>> result = new ArrayList<>();
        for (Entry<Integer, Set<String>> entry : disconnectedNodesList.entrySet()) {
            if (entry.getKey() == 0) {
                for (String set : entry.getValue()) {
                    Set<String> temp = new HashSet<>();
                    temp.add(set);
                    result.add(temp);
                }
            } else {
                result.add(entry.getValue());
            }
        }
        return result;
    }

    /**
     * Restituisce il sottografo che contiene il nodo u
     * 
     * @param u nodo
     * @return sottografo
     */
    public Set<String> getSubgraph(String u) {
        return disconnectedNodesList.get(searchKeyFor(u));
    }

    // pulisce il grafo
    public void makeEmpty() {
        this.adjacencyList = new HashMap<>();
        this.disconnectedNodesList = new HashMap<>();
    }

    // Funzioni ausiliarie

    public void updateAddDisconnectedNodesList(String v, String u) {
        if (disconnectedNodesList.get(0).contains(v) && disconnectedNodesList.get(0).contains(u)) {
            disconnectedNodesList.get(0).remove(v);
            disconnectedNodesList.get(0).remove(u);

            Integer newKey = createKey(u, v);
            disconnectedNodesList.put(newKey, new HashSet<>());
            disconnectedNodesList.get(newKey).add(u);
            disconnectedNodesList.get(newKey).add(v);

        } else if (disconnectedNodesList.get(0).contains(v)) {
            transferNode(v, u);
        } else if (disconnectedNodesList.get(0).contains(u)) {
            transferNode(u, v);
        } else {
            Integer uKey = searchKeyFor(u);
            Integer vKey = searchKeyFor(v);
            if (!uKey.equals(vKey)) {
                disconnectedNodesList.get(uKey).addAll(disconnectedNodesList.get(vKey));
                disconnectedNodesList.remove(vKey);
            }
        }

    }

    Integer createKey(String v) {
        return v.hashCode();
    }

    boolean collision(Integer k) {
        return disconnectedNodesList.get(k) != null;

    }

    Integer createKey(String u, String v) {
        Integer key = createKey(u) + createKey(v);
        Integer i = 0;
        // per prevenire collisioni
        while (collision(key)) {
            key = createKey(Integer.toString(key)) + i;
            i += 1;
        }
        return key;
    }

    void transferNode(String toMove, String destination) {

        disconnectedNodesList.get(0).remove(toMove);
        disconnectedNodesList.get(searchKeyFor(destination)).add(toMove);

    }

    Integer searchKeyFor(String u) {

        for (Entry<Integer, Set<String>> entry : disconnectedNodesList.entrySet()) {
            if (entry.getValue().contains(u)) {
                return entry.getKey();
            }
        }
        // so per forza che la chiave esiste
        return 0;
    }
}