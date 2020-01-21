package Tran_Tzul_CSC_401_501_HW4;
/**
 * @author Alvaro Tzul, Vuong Tran
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Dijkstra {

    public static void main(String[] args) {
        Graph g = new Graph();

        g.addVertex(0, Arrays.asList(new Vertex(1, 7.), new Vertex(2, 14.), new Vertex(3, 15.)));
        g.addVertex(1, Arrays.asList(new Vertex(0, 9.), new Vertex(4, 23.)));
        g.addVertex(2, Arrays.asList(new Vertex(0, 14.), new Vertex(3, 5.), new Vertex(4, 18.), new Vertex(5, 30.)));
        g.addVertex(3, Arrays.asList(new Vertex(0, 15.), new Vertex(2, 5.), new Vertex(5, 20.), new Vertex(7, 44.)));
        g.addVertex(4, Arrays.asList(new Vertex(1, 23.), new Vertex(2, 18.), new Vertex(5, 2.), new Vertex(6, 6.), new Vertex(7, 19.)));
        g.addVertex(5, Arrays.asList(new Vertex(2, 30.), new Vertex(3, 20.), new Vertex(4, 2.), new Vertex(6, 11.), new Vertex(7, 16.)));
        g.addVertex(6, Arrays.asList(new Vertex(4, 6.), new Vertex(5, 11.), new Vertex(7, 6.)));
        g.addVertex(7, Arrays.asList(new Vertex(3, 44.), new Vertex(4, 19.), new Vertex(5, 16.), new Vertex(6, 6.)));
        System.out.println("0 -> 7    " + g.getShortestPath(0, 7));// s -> t
        System.out.println("0 -> 5    " + g.getShortestPath(0, 5));// 
        System.out.println("0 -> 6    " + g.getShortestPath(0, 6));
    }

}

class Vertex implements Comparable<Vertex> {

    private Integer id;   // id of the node
    private Double distance;   // distance from the node id to another node

    public Vertex(Integer id, Double distance) {
        super();
        this.id = id;
        this.distance = distance;
    }

    public Integer getId() {
        return id;
    }

    public Double getDistance() {
        return distance;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    /**
     *
     * @return the hashcode value for the object
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((distance == null) ? 0 : distance.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Vertex other = (Vertex) obj;
        if (distance == null) {
            if (other.distance != null) {
                return false;
            }
        } else if (!distance.equals(other.distance)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Vertex [id=" + id + ", distance=" + distance + "]";
    }

    @Override
    public int compareTo(Vertex o) {
        if (this.distance < o.distance) {
            return -1;
        } else if (this.distance > o.distance) {
            return 1;
        } else {
            return this.getId().compareTo(o.getId());
        }
    }
}

class Graph {

    private final Map<Integer, List<Vertex>> vertices;

    public Graph() {
        this.vertices = new HashMap<>();
    }

    public void addVertex(Integer character, List<Vertex> vertex) {
        this.vertices.put(character, vertex);
    }

    public List<Integer> getShortestPath(Integer start, Integer finish) {
        final Map<Integer, Double> distances = new HashMap<>();
        final Map<Integer, Vertex> previous = new HashMap<>();
        PriorityQueue<Vertex> nodes = new PriorityQueue<>();

        for (Integer vertex : vertices.keySet()) {
            if (vertex == start) {
                distances.put(vertex, 0.);
                nodes.add(new Vertex(vertex, 0.));
            } else {
                distances.put(vertex, Double.MAX_VALUE);
                nodes.add(new Vertex(vertex, Double.MAX_VALUE));
            }
            previous.put(vertex, null);
        }

        while (!nodes.isEmpty()) {
            Vertex smallest = nodes.poll();
            if (smallest.getId() == finish) {
                final List<Integer> path = new ArrayList<>();
                while (previous.get(smallest.getId()) != null) {
                    path.add(smallest.getId());
                    smallest = previous.get(smallest.getId());
                }
                return path;
            }

            if (distances.get(smallest.getId()) == Double.MAX_VALUE) {
                break;
            }

            for (Vertex neighbor : vertices.get(smallest.getId())) {
                Double alt = distances.get(smallest.getId()) + neighbor.getDistance();
                if (alt < distances.get(neighbor.getId())) {
                    distances.put(neighbor.getId(), alt);
                    previous.put(neighbor.getId(), smallest);
                    forloop:
                    for (Vertex n : nodes) {
                        if (n.getId() == neighbor.getId()) {
                            nodes.remove(n);
                            n.setDistance(alt);
                            nodes.add(n);
                            break;
                        }
                    }
                }
            }
        }
        System.out.print(start);
        return new ArrayList<Integer>(distances.keySet());
    }

}
