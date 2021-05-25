package it.unibs.ing.fp.rovineperdute;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ReadXML read= new ReadXML();
        ArrayList<City> cities=new ArrayList<>();
        read.readCities(cities,"test_file/PgAr_Map_5.xml");
        System.out.println(cities);
    }
}
