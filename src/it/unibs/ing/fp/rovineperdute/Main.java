package it.unibs.ing.fp.rovineperdute;
import it.unibs.ing.fp.pathfinding.City;
import it.unibs.ing.fp.pathfinding.PathFinder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Main {

    private static ArrayList<City> cities;

    public static void main(String[] args) {
        int numero=10000;
        ReadXML read= new ReadXML();
        cities=new ArrayList<>();
        read.readCities(cities,"test_file/PgAr_Map_"+numero+".xml");

        for (City city:cities) {
            city.calculateLink(new Vehicle("Tonathiu",0));
        }

        for (int i = cities.size()-1; i >=0 ; i--) {
            cities.get(i).h=i;
        }

        PathFinder.aStar(0, numero - 1);
        System.out.println(PathFinder.printPath(numero - 1));

        //Numero citta' toccate
        System.out.println(PathFinder.getNumber_city());

        //matrix
        if(numero<250) {
            double mat[][] = new double[numero][numero];

            for (int i = 0; i < cities.size(); i++) {
                for (int j = 0; j < cities.get(i).getNeighbors().size(); j++) {
                    mat[i][cities.get(i).getNeighbors().get(j).city_id] = cities.get(i).getNeighbors().get(j).weight;
                }
            }



            try {
                FileWriter myWriter = new FileWriter("Matrice.txt");
                for (int i = 0; i < numero; i++) {
                    for (int j = 0; j < numero; j++) {
                        myWriter.write(((int) mat[i][j]) + ",");

                    }
                    //System.out.println("i: "+i+"\n");
                    myWriter.write("\n");
                }
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }



        //System.out.println(cities);
    }

    public static ArrayList<City> getCities() {
        return cities;
    }
}
