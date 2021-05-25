package it.unibs.ing.fp.rovineperdute;

import java.util.ArrayList;

public class City {

    private int id;
    private String name;
    private Coordinates coordinate;
    private ArrayList <it.unibs.ing.fp.pathfinding.City> cities;

    /**
     * City Constructor method, for the creation of the city
     * @param id City id
     * @param name name of the city
     * @param coordinate Coordinate of the city
     */
    public City(int id, String name, Coordinates coordinate) {
        this.id = id;
        this.name = name;
        this.coordinate = coordinate;
        cities = new ArrayList<it.unibs.ing.fp.pathfinding.City>();
    }

    public City(int id, String name, Coordinates coordinate, ArrayList<it.unibs.ing.fp.pathfinding.City> cities) {
        this.id = id;
        this.name = name;
        this.coordinate = coordinate;
        this.cities = cities;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinate=" + coordinate +
                ", links=" + cities +
                '}';
    }
}
