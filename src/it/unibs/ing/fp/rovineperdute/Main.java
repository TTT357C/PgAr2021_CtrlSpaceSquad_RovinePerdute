package it.unibs.ing.fp.rovineperdute;
import it.unibs.ing.fp.altro.Graph;
import it.unibs.ing.fp.altro.ksp.Yen;
import it.unibs.ing.fp.altro.util.Path;
import it.unibs.ing.fp.mylib.InputDati;
import it.unibs.ing.fp.pathfinding.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {

    //Constant
    //========================================================================================================
    public static final String LINE = " " + "____________________________________________________________________________" + "\n";
    public static final String MENU = "\n" +
            "__________            .__                __________                 .___      __          \n" +
            "\\______   \\ _______  _|__| ____   ____   \\______   \\ ___________  __| _/_ ___/  |_  ____  \n" +
            " |       _//  _ \\  \\/ /  |/    \\_/ __ \\   |     ___// __ \\_  __ \\/ __ |  |  \\   __\\/ __ \\ \n" +
            " |    |   (  <_> )   /|  |   |  \\  ___/   |    |   \\  ___/|  | \\/ /_/ |  |  /|  | \\  ___/ \n" +
            " |____|_  /\\____/ \\_/ |__|___|  /\\___  >  |____|    \\___  >__|  \\____ |____/ |__|  \\___  >\n" +
            "        \\/                    \\/     \\/                 \\/           \\/                \\/ \n";
    public static final String TEAM_NAME_1 = "Tonathiu";
    public static final String TEAM_NAME_2 = "Metztli";
    //========================================================================================================

    /**
     * Main Method
     * @author Thomas Causetti
     * @param args
     */
    public static void main(String[] args) {

        //========================================================================================================
        //Number of vertices
        int vertices=200;

        //New instance of ReadXML
        ReadXML read = new ReadXML();
        //========================================================================================================


        //Arraylist with the cities (Nodes of the graph)
        //========================================================================================================
        ArrayList<City> cities=new ArrayList<>();
        ArrayList<City> cities_temp=new ArrayList<>();
        //========================================================================================================


        //Read from file
        //========================================================================================================
        read.readCities(cities,"test_file/PgAr_Map_"+vertices+".xml");
        read.readCities(cities_temp,"test_file/PgAr_Map_"+vertices+".xml");
        //========================================================================================================


        //Weight Calculation
        //========================================================================================================
        for (City city:cities) {
            city.calculateLink(new Vehicle(TEAM_NAME_1,0),cities);
        }

        for (City city:cities_temp) {
            city.calculateLink(new Vehicle(TEAM_NAME_2,1),cities_temp);
        }
        //========================================================================================================


        //Menu
        //========================================================================================================
        System.out.println(MENU);
        //variabile for time calculation
        long timeStartTot=0;


        //========================================================================================================
        int scelta=InputDati.leggiIntero("-->",0,2);
        switch (scelta){
            //========================================================================================================
            case 0:
                //Exit
            break;
            //========================================================================================================
            case 1:
                timeStartTot = System.currentTimeMillis();
                //Fast Calculation
                aStarInitializer(vertices,cities);
                aStarInitializer(vertices,cities_temp);
            break;
            //========================================================================================================
            case 2:
                timeStartTot = System.currentTimeMillis();
                //Slow Calculation
                aStarInitializer(vertices,cities);
                aStarInitializer(vertices,cities_temp);
                yenPreInitializer(vertices, cities);
                yenPreInitializer(vertices, cities_temp);
            break;
            //========================================================================================================
        }


        //========================================================================================================
        //End of the execution with time
        System.out.println(LINE);
        long timeFinishTot = System.currentTimeMillis();
        System.out.println(" "+"Total operation took " + (timeFinishTot - timeStartTot) / 1000.0 + " seconds.");
        //========================================================================================================


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

        //================================================================

        //System.out.println(cities);
    }

    private static void yenPreInitializer(int vertices, ArrayList<City> cities) {
        //Test Yen

        try {
            FileWriter myWriter = new FileWriter("Matrice2.txt");
            for (int i = 0; i < cities.size(); i++) {
                for (int j = 0; j < cities.get(i).getNeighbors().size(); j++) {
                    myWriter.write(cities.get(i).getId()+" "+ cities.get(i).getNeighbors().get(j).city_id+" "+ cities.get(i).getNeighbors().get(j).weight);
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
        targetNode = (vertices -1)+"";
        K = 2;

        System.out.println(LINE);

        yenInitializer(graphFilename,sourceNode,targetNode,K);

        System.out.println(LINE);
    }

    private static void aStarInitializer(int vertices, ArrayList<City> cities) {
        for (int i = cities.size()-1; i >=0 ; i--) {
            cities.get(i).setH(i);
        }
        System.out.println(LINE);

        System.out.print(" "+"Computing the " + 1 + " shortest paths from [" + 0 + "] to [" + (vertices -1) + "] ");
        System.out.println(" "+"using A* algorithm.\n");

        ProgressBar.value(0);
        long timeStart_a = System.currentTimeMillis();
        PathFinder pathFinder=new PathFinder();
        ProgressBar.value(3);
        pathFinder.aStar(0, vertices - 1,cities);
        //System.out.print(".");
        //System.out.println("complete.");
        ArrayList<Integer> ids = pathFinder.outputArrayGen(vertices - 1);
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
        System.out.println(LINE);
    }

    public static void yenInitializer(String graphFilename, String source, String target, int k) {
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
