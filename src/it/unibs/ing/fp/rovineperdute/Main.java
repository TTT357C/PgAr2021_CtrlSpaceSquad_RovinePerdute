package it.unibs.ing.fp.rovineperdute;
import it.unibs.ing.fp.altro.Graph;
import it.unibs.ing.fp.altro.ksp.Yen;
import it.unibs.ing.fp.altro.util.Path;
import it.unibs.ing.fp.mylib.InputDati;
import it.unibs.ing.fp.pathfinding.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
    public static final String MENU2 =  "  _____________________________________________\n"+
                                        " |                                             |\n"+
                                        " |  1- Fast Execution (A*)                     |\n"+
                                        " |  2- Slow Execution (A* and Yen)             |\n"+
                                        " |_____________________________________________|\n";
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
        int vertices=2000;

        //New instance of ReadXML
        ReadXML read = new ReadXML();
        //========================================================================================================

        //Menu (File chooser)
        //========================================================================================================
        File folder = new File("test_file");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println(" "+(i+1)+"-" + listOfFiles[i].getName());
            }
        }

        int index_t = InputDati.leggiIntero(" Enter the number for choosing the file: ");
        String file_name = listOfFiles[index_t-1].getName();
        //========================================================================================================


        //Arraylist with the cities (Nodes of the graph)
        //========================================================================================================
        ArrayList<City> cities=new ArrayList<>();
        ArrayList<City> cities_temp=new ArrayList<>();
        //========================================================================================================


        //Read from file
        //========================================================================================================
        read.readCities(cities,"test_file/"+file_name);
        read.readCities(cities_temp,"test_file/"+file_name);
        //========================================================================================================

        //Vehicle
        //========================================================================================================
        Vehicle team=new Vehicle(TEAM_NAME_1,0);
        Vehicle team2=new Vehicle(TEAM_NAME_2,1);
        //========================================================================================================


        //Weight Calculation
        //========================================================================================================
        for (City city:cities) {
            city.calculateLink(team,cities);
        }

        for (City city:cities_temp) {
            city.calculateLink(team2,cities_temp);
        }
        //========================================================================================================


        //Menu
        //========================================================================================================
        System.out.println(MENU);
        System.out.println(MENU2);
        //variabile for time calculation
        long timeStartTot=0;

        ArrayList<Integer> ids=new ArrayList<>();
        ArrayList<Integer> ids2=new ArrayList<>();
        double fuel=0;
        double fuel2=0;

        //========================================================================================================
        int scelta=InputDati.leggiIntero("-->",1,2);
        switch (scelta){
            //========================================================================================================
            case 1:
                timeStartTot = System.currentTimeMillis();
                //Fast Calculation

                fuel=aStarInitializer(vertices,cities,ids);
                fuel2=aStarInitializer(vertices,cities_temp,ids2);
            break;
            //========================================================================================================
            case 2:
                timeStartTot = System.currentTimeMillis();
                //Slow Calculation
                fuel=aStarInitializer(vertices,cities,ids);
                fuel2=aStarInitializer(vertices,cities_temp,ids2);
                yenPreInitializer(vertices, cities);
                yenPreInitializer(vertices, cities_temp);
            break;
            //========================================================================================================
        }

        //File Exporter
        //========================================================================================================
        WriteXML writeXML = new WriteXML();
        ArrayList<City> output=new ArrayList<>();
        ArrayList<City> output2=new ArrayList<>();
        for (int index:ids) {
            output.add(cities.get(index));
        }
        for (int index:ids2) {
            output2.add(cities_temp.get(index));
        }
        team.setTouched_cities(output);
        team2.setTouched_cities(output2);
        team.setFuel(fuel);
        team2.setFuel(fuel2);
        writeXML.writeXML(team,team2);
        System.out.println(" Successfully wrote to the file.");
        //========================================================================================================

        //========================================================================================================
        //End of the execution with time
        System.out.println(LINE);
        long timeFinishTot = System.currentTimeMillis();
        System.out.println(" "+"Total operation took " + (timeFinishTot - timeStartTot) / 1000.0 + " seconds.");

        //================================================================
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
        System.out.print(" "+"Computing the k shortest paths from [" + sourceNode + "] to [" + targetNode + "] ");
        System.out.println("using Yen's algorithm.\n");
        K = InputDati.leggiInteroConMinimo(" Enter the number of routes requested (k): ",1);

        System.out.println(LINE);

        yenInitializer(graphFilename,sourceNode,targetNode,K);

        System.out.println(LINE);
    }

    private static double aStarInitializer(int vertices, ArrayList<City> cities, ArrayList<Integer> ids) {
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

        ids.addAll(pathFinder.outputArrayGen(vertices - 1));
        ProgressBar.value(7);


        ProgressBar.value(10);

        double total_sum = pathFinder.sumFuel(ids);

        //System.out.println(total_sum);
        System.out.print(" "+1 + ") cost: " +total_sum+" - [path] ");
        pathFinder.viewPath2(ids);


        //Numero citta' toccate
        int number = pathFinder.getNumber_city();
        System.out.println(" Number of cities: "+number);
        long timeFinish_a = System.currentTimeMillis();
        System.out.println(" "+"Operation took " + (timeFinish_a - timeStart_a) / 1000.0 + " seconds.");
        System.out.println(LINE);

        return total_sum;
    }

    public static void yenInitializer(String graphFilename, String source, String target, int k) {
        /* Read graph from file */
        Graph graph = new Graph(graphFilename);

        /* Compute the K shortest paths and record the completion time */
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
