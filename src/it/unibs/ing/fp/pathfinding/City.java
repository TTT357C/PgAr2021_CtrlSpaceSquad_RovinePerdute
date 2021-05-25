package it.unibs.ing.fp.pathfinding;

import java.util.ArrayList;

public class City implements Comparable<City> {
    // Id for readability of result purposes
    private static int idCounter = 0;
    public int id;

    // Parent in the path
    public City parent = null;

    public ArrayList<Link> neighbors;

    // Evaluation functions
    public double f = Double.MAX_VALUE;
    public double g = Double.MAX_VALUE;
    // Hardcoded heuristic
    public double h;

    public City(double h){
        this.h = h;
        this.id = idCounter++;
        this.neighbors = new ArrayList<Link>();
    }

    @Override
    public int compareTo(City n) {
        return Double.compare(this.f, n.f);
    }



    public void addBranch(int weight, City node){
        Link newEdge = new Link(weight, node);
        neighbors.add(newEdge);
    }

    public double calculateHeuristic(City target){
        return this.h;
    }

    @Override
    public String toString() {
        return "Link{" +
                "id=" + id +
                ", parent=" + parent +
                ", neighbors=" + neighbors +
                ", f=" + f +
                ", g=" + g +
                ", h=" + h +
                '}';
    }
}