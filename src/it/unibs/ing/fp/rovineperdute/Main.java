package it.unibs.ing.fp.rovineperdute;
import it.unibs.ing.fp.pathfinding.City;
import it.unibs.ing.fp.pathfinding.PathFinder;

import java.util.ArrayList;


public class Main {

    private static ArrayList<City> cities;

    public static void main(String[] args) {
        ReadXML read= new ReadXML();
        cities=new ArrayList<>();
        read.readCities(cities,"test_file/PgAr_Map_5.xml");

        for (City city:cities) {
            city.calculateLink();
        }

        PathFinder.aStar(0,4);
        PathFinder.printPath(4);

        System.out.println(cities);
    }

    public static ArrayList<City> getCities() {
        return cities;
    }
}
