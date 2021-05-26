package it.unibs.ing.fp.rovineperdute;
import it.unibs.ing.fp.altro.Graph;
import it.unibs.ing.fp.altro.ksp.Yen;
import it.unibs.ing.fp.altro.util.Path;
import it.unibs.ing.fp.pathfinding.City;
import it.unibs.ing.fp.pathfinding.PathFinder;
import it.unibs.ing.fp.pathfinding.ProgressBar;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {

    private static ArrayList<City> cities;
    private static ArrayList<City> cities_temp;

    public static void main(String[] args) {
        int vertices=10000;
        ReadXML read= new ReadXML();
        cities=new ArrayList<>();
        cities_temp=new ArrayList<>();

        long timeStartTot = System.currentTimeMillis();

        cities_temp=cities;

        read.readCities(cities,"test_file/PgAr_Map_"+vertices+".xml");

        for (City city:cities) {
            city.calculateLink(new Vehicle("Tonathiu",1));
        }

        for (int i = cities.size()-1; i >=0 ; i--) {
            cities.get(i).setH(i);
        }
        System.out.println(" "+"____________________________________________________________________________"+"\n");

        System.out.print(" "+"Computing the " + 1 + " shortest paths from [" + 0 + "] to [" + (vertices-1) + "] ");
        System.out.println(" "+"using A* algorithm.\n");

        ProgressBar.value(0);
        long timeStart_a = System.currentTimeMillis();
        PathFinder pathFinder=new PathFinder();
        ProgressBar.value(3);
        pathFinder.aStar(0, vertices - 1);
        //System.out.print(".");
        //System.out.println("complete.");
        ArrayList<Integer> ids = pathFinder.printPath(vertices - 1);
        ProgressBar.value(7);


        ProgressBar.value(10);

        double total_sum = pathFinder.sumFuel(ids);

        //System.out.println(total_sum);
        System.out.println(" "+"n) cost: [path]");
        System.out.print(" "+1 + ") " +total_sum+": ");
        pathFinder.viewPath2(ids);


        //Numero citta' toccate
        int number = pathFinder.getNumber_city();
        System.out.println(" "+number);
        long timeFinish_a = System.currentTimeMillis();
        System.out.println(" "+"Operation took " + (timeFinish_a - timeStart_a) / 1000.0 + " seconds.");
        System.out.println(" "+"____________________________________________________________________________"+"\n");






        //matrix
        /*
        if(vertices<250) {
            double mat[][] = new double[vertices][vertices];

            for (int i = 0; i < cities.size(); i++) {
                for (int j = 0; j < cities.get(i).getNeighbors().size(); j++) {
                    mat[i][cities.get(i).getNeighbors().get(j).city_id] = cities.get(i).getNeighbors().get(j).weight;
                }
            }



            try {
                FileWriter myWriter = new FileWriter("Matrice.txt");
                for (int i = 0; i < vertices; i++) {
                    for (int j = 0; j < vertices; j++) {
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
        */
        //=============================================================
        /*
        System.out.println("Tutti i percorsi");
        AllPathfinder allpath = new AllPathfinder(vertices);
        for (int i = 0; i < cities.size(); i++) {
            for (int j = 0; j < cities.get(i).getNeighbors().size(); j++) {
                allpath.addEdge(cities.get(i).getId(),cities.get(i).getNeighbors().get(j).city_id);
            }
        }
        allpath.printAllPaths(0, vertices-1);
        */
        //================================================================
        //Test Yen

        try {
            FileWriter myWriter = new FileWriter("Matrice2.txt");
            for (int i = 0; i < cities.size(); i++) {
                for (int j = 0; j < cities.get(i).getNeighbors().size(); j++) {
                    myWriter.write(cities.get(i).getId()+" "+cities.get(i).getNeighbors().get(j).city_id+" "+cities.get(i).getNeighbors().get(j).weight);
                    myWriter.write("\n");
                }

            }
            myWriter.close();
            System.out.println(" "+"Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println(" "+"An error occurred.");
            e.printStackTrace();
        }

        /* Uncomment any of these example tests */
        String graphFilename, sourceNode, targetNode;
        int K;

        graphFilename = "Matrice2.txt";
        sourceNode = "0";
        targetNode = (vertices-1)+"";
        K = 2;

        System.out.println(" "+"____________________________________________________________________________"+"\n");

        yenInizializer(graphFilename,sourceNode,targetNode,K);

        System.out.println(" "+"____________________________________________________________________________"+"\n");

        long timeFinishTot = System.currentTimeMillis();
        System.out.println(" "+"Total operation took " + (timeFinishTot - timeStartTot) / 1000.0 + " seconds.");
        //System.out.println(cities);
    }

    public static ArrayList<City> getCities() {
        return cities;
    }

    public static void yenInizializer(String graphFilename, String source, String target, int k) {
        /* Read graph from file */
        System.out.print(" "+"Reading data from file... ");
        Graph graph = new Graph(graphFilename);
        System.out.println("complete.");

        /* Compute the K shortest paths and record the completion time */
        System.out.print(" "+"Computing the " + k + " shortest paths from [" + source + "] to [" + target + "] ");
        System.out.println("using Yen's algorithm.\n");
        List<Path> ksp;
        long timeStart = System.currentTimeMillis();
        Yen yenAlgorithm = new Yen();
        ksp = yenAlgorithm.ksp(graph, source, target, k);
        long timeFinish = System.currentTimeMillis();
        System.out.println(" Complete");

        System.out.println(" "+"Operation took " + (timeFinish - timeStart) / 1000.0 + " seconds.");

        /* Output the K shortest paths */
        System.out.println(" "+"n) cost: [path]");
        int n = 0;
        for (Path p : ksp) {
            System.out.println(" "+ (++n) + ") " + p.toString());
        }
    }
}
