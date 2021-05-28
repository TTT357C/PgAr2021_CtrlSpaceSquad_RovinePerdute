package it.unibs.ing.fp.pathfinding;

import it.unibs.ing.fp.rovineperdute.Coordinates;
import it.unibs.ing.fp.rovineperdute.Main;
import it.unibs.ing.fp.rovineperdute.Vehicle;

import java.util.ArrayList;
import java.util.Random;

public class City implements Comparable<City> {
    // Id for readability of result purposes
    private int id;
    private ArrayList<Link> neighbors;
    // Parent in the path
    private City parent = null;

    // Evaluation functions
    private double f = 0;
    private double g = 0;
    //public double g = Double.MAX_VALUE;
    // Hardcoded heuristic
    private double h;

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

    private String name;
    private Coordinates coordinate;

    public int getId() {
        return id;
    }

    public City getParent() {
        return parent;
    }

    public double getF() {
        return f;
    }

    public double getG() {
        return g;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public void setF(double f) {
        this.f = f;
    }

    public void setG(double g) {
        this.g = g;
    }

    public void setParent(City parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }


    @Override
    public int compareTo(City n) {
        return Double.compare(this.f, n.f);
    }

    public double calculateHeuristic(City target){
        return this.h;
    }

    public void calculateLink(Vehicle vehicle, ArrayList<City> cities){
            //zero fuel on difference in altitude
            if(vehicle.getVehicle_type() == 0){
                for (int i = 0; i < neighbors.size(); i++) {
                    neighbors.get(i).setWeight(cities.get(neighbors.get(i).getCity_id()).getCoordinate().distanceMethod(this.coordinate));
                }
            }
            //add fuel between city on the different altitude
            if(vehicle.getVehicle_type() == 1) {
                for (int i = 0; i < neighbors.size(); i++) {
                    neighbors.get(i).setWeight(cities.get(neighbors.get(i).getCity_id()).getCoordinate().heightDifference(this.coordinate));

                }
            }
            //for testing
            if(vehicle.getVehicle_type() == -1){
                Random rand= new Random();
                for (int i = 0; i < neighbors.size(); i++) {
                    neighbors.get(i).setWeight(rand.nextInt(1000 - 1 + 1) + 1);
                }
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
                "}\n";
    }

    public Coordinates getCoordinate() {
        return coordinate;
    }

    public ArrayList<Link> getNeighbors() {
        return neighbors;
    }
}