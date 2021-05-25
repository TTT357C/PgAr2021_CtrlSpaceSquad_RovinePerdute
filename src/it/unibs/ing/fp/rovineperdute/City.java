package it.unibs.ing.fp.rovineperdute;

import it.unibs.ing.fp.pathfinding.Link;

import java.util.ArrayList;

public class City {

    private int id;
    private String name;
    private Coordinates coordinate;
    private ArrayList <Link> links;

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
        links = new ArrayList<Link>();
    }

    public City(int id, String name, Coordinates coordinate, ArrayList<Link> links) {
        this.id = id;
        this.name = name;
        this.coordinate = coordinate;
        this.links = links;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinate=" + coordinate +
                ", links=" + links +
                '}';
    }
}
