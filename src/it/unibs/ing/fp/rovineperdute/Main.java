package it.unibs.ing.fp.rovineperdute;
import it.unibs.ing.fp.pathfinding.City;
import it.unibs.ing.fp.pathfinding.Link;
import it.unibs.ing.fp.pathfinding.PathFinder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Main {

    private static ArrayList<City> cities;
    private static ArrayList<City> cities_temp;

    public static void main(String[] args) {
        int numero=10000;
        ReadXML read= new ReadXML();
        cities=new ArrayList<>();
        cities_temp=new ArrayList<>();

        read.readCities(cities,"test_file/PgAr_Map_"+numero+".xml");

        for (City city:cities) {
            city.calculateLink(new Vehicle("Tonathiu",-1));
        }

        for (int i = cities.size()-1; i >=0 ; i--) {
            cities.get(i).h=i;
        }

        PathFinder pathFinder=new PathFinder();

        ArrayList<Integer> index_al_path=pathFinder.aStar(0, numero - 1);
        ArrayList<Integer> ids = pathFinder.printPath(numero - 1);
        index_al_path.retainAll(ids);
        System.out.println(index_al_path);
        cities_temp=cities;



        pathFinder.viewPath(ids);
        double total_sum = pathFinder.sumFuel(ids);
        System.out.println(total_sum);

        //Numero citta' toccate
        int number = pathFinder.getNumber_city();
        System.out.println(number);

        int cont=1;
        int cont2=0;



        System.out.println("Caricamento...");
        System.out.println("[");

        for (int i = 0; i < index_al_path.size()-1; i++) {
            int index=index_al_path.get(i);
            for (int j = 0; j < cities.get(index).getNeighbors().size(); j++) {
                if (cities.get(index).getNeighbors().get(j).city_id == ids.get(cont)) {
                    Link temp = cities.get(index).getNeighbors().get(j);
                    cities.get(index).getNeighbors().remove(j);
                    cont++;
                    PathFinder pathFinder2 = new PathFinder();
                    pathFinder2.aStar(0, numero - 1);
                    cities.get(index).getNeighbors().add(j, temp);

                    ArrayList<Integer> ids2;
                    ids2=pathFinder2.printPath(numero - 1);
                    double sum = pathFinder2.sumFuel(ids2);
                    pathFinder2.viewPath(ids2);
                    System.out.println(sum);
                    //System.out.print("=");
                    if(sum == total_sum){
                        System.out.println("Uguale");
                    }
                    break;
                }
            }
        }

        System.out.print("]");
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
