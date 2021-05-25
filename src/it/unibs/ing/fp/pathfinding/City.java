package it.unibs.ing.fp.pathfinding;

import it.unibs.ing.fp.rovineperdute.Coordinates;
import it.unibs.ing.fp.rovineperdute.Main;

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

    private String name;
    private Coordinates coordinate;
    private ArrayList <Link> cities;

    public City(double h){
        this.h = h;
        this.id = idCounter++;
        this.neighbors = new ArrayList<Link>();
    }

    /**
     * City Constructor method, for the creation of the city
     * @param id City id
     * @param name name of the city
     * @param coordinate Coordinate of the city
     */
    public City(double h, int id, String name, Coordinates coordinate) {
        this.h = h;
        this.id = id;
        this.name = name;
        this.coordinate = coordinate;
        this.neighbors = new ArrayList<Link>();
    }

    public City(int id, String name, Coordinates coordinate, ArrayList<Link> link) {
        this.h = 0;
        this.id = id;
        this.name = name;
        this.coordinate = coordinate;
        this.neighbors = link;
    }

    @Override
    public int compareTo(City n) {
        return Double.compare(this.f, n.f);
    }

    public double calculateHeuristic(City target){
        return this.h;
    }

    public void calculateLink(){
        for (int i = 0; i < neighbors.size(); i++) {
            neighbors.get(i).weight = Main.getCities().get(neighbors.get(i).city_id).getCoordinate().distanceMethod(this.coordinate);
        }

    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", parent=" + parent +
                ", neighbors=" + neighbors +
                ", f=" + f +
                ", g=" + g +
                ", h=" + h +
                ", name='" + name + '\'' +
                ", coordinate=" + coordinate +
                ", cities=" + cities +
                "}\n";
    }

    public Coordinates getCoordinate() {
        return coordinate;
    }
}