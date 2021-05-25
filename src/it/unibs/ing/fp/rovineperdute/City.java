package it.unibs.ing.fp.rovineperdute;

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


}
