package it.unibs.ing.fp.pathfinding;

import java.util.ArrayList;

public class Link implements Comparable<Link> {
    // Id for readability of result purposes
    private static int idCounter = 0;
    public int id;

    // Parent in the path
    public Link parent = null;

    public ArrayList<Edge> neighbors;

    // Evaluation functions
    public double f = Double.MAX_VALUE;
    public double g = Double.MAX_VALUE;
    // Hardcoded heuristic
    public double h;

    Link(double h){
        this.h = h;
        this.id = idCounter++;
        this.neighbors = new ArrayList<Edge>();
    }

    @Override
    public int compareTo(Link n) {
        return Double.compare(this.f, n.f);
    }

    public static class Edge {
        Edge(int weight, Link node){
            this.weight = weight;
            this.node = node;
        }

        public int weight;
        public Link node;
    }

    public void addBranch(int weight, Link node){
        Edge newEdge = new Edge(weight, node);
        neighbors.add(newEdge);
    }

    public double calculateHeuristic(Link target){
        return this.h;
    }
}