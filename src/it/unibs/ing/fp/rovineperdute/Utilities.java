package it.unibs.ing.fp.rovineperdute;

import it.unibs.ing.fp.altro.Graph;
import it.unibs.ing.fp.altro.ksp.Yen;
import it.unibs.ing.fp.altro.util.Path;
import it.unibs.ing.fp.mylib.InputDati;
import it.unibs.ing.fp.pathfinding.City;
import it.unibs.ing.fp.pathfinding.PathFinder;
import it.unibs.ing.fp.pathfinding.ProgressBar;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas Causetti
 */
public class Utilities {


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
                                        " |  2- Slow Execution (Yen)                    |\n"+
                                        " |_____________________________________________|\n";
    public static final String TEAM_NAME_1 = "Tonathiu";
    public static final String TEAM_NAME_2 = "Metztli";
    //========================================================================================================


    /**
     * Yen algorithm pre initializer (it calls yenInitializer)
     * @param vertices number of vertices
     * @param cities ArrayList<City>
     * @param ids ArrayList<Integer> (Cities ids)
     * @return total fuel
     */
    protected static double yenPreInitializer(int vertices, ArrayList<City> cities, ArrayList<Integer> ids) {

        //read form file
        try {
            FileWriter myWriter = new FileWriter("Graph.txt");
            for (int i = 0; i < cities.size(); i++) {
                for (int j = 0; j < cities.get(i).getNeighbors().size(); j++) {
                    myWriter.write(cities.get(i).getId()+" "+ cities.get(i).getNeighbors().get(j).getCity_id()+" "+ cities.get(i).getNeighbors().get(j).getWeight());
                    myWriter.write("\n");
                }

            }
            myWriter.close();

        } catch (IOException e) {
            System.out.println(" "+"An error occurred.");
            e.printStackTrace();
        }

        String graphFilename, sourceNode, targetNode;
        int K;

        graphFilename = "Graph.txt";
        sourceNode = "0";
        targetNode = (vertices -1)+"";
        System.out.print(" "+"Computing the k shortest paths from [" + sourceNode + "] to [" + targetNode + "] ");
        System.out.println("using Yen's algorithm.\n");
        K = InputDati.leggiInteroConMinimo(" Enter the number of routes requested (k): ",1);

        System.out.println(Utilities.LINE);

        double fuel=yenInitializer(graphFilename,sourceNode,targetNode,K,ids);

        System.out.println(Utilities.LINE);

        return fuel;
    }

    /**
     * Yen algorithm initializer
     * @param graphFilename where to read the graph
     * @param source first node
     * @param target last node
     * @param k number of routes the algorithm has to find
     * @param output ArrayList<Integer> with all cities ids
     * @return total fuel needed for the best route
     */
    protected static double yenInitializer(String graphFilename, String source, String target, int k, ArrayList<Integer> output) {
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

        //TODO controlla
        int sum=0;
        int sum_t=0;
        int index_max=0;
        for (int i = 0; i < ksp.size(); i++) {
            if(ksp.get(0).getTotalCost()==ksp.get(i).getTotalCost()) {
                if(ksp.get(0).size()==ksp.get(i).size()) {
                    sum = 0;
                    for (int j = 0; j < ksp.get(i).getEdges().size(); j++) {
                        sum += Integer.parseInt(ksp.get(i).getEdges().get(j).getFromNode().toString());
                    }
                    sum += Integer.parseInt(ksp.get(i).getEdges().getLast().getToNode().toString());
                    if (sum > sum_t) {
                        index_max = i;
                    }
                    sum_t = sum;
                }
            }
        }

        for (int i = 0; i < ksp.get(index_max).getEdges().size(); i++) {
            output.add(Integer.parseInt(ksp.get(index_max).getEdges().get(i).getFromNode().toString()));
        }
        output.add(Integer.parseInt(ksp.get(index_max).getEdges().getLast().getToNode().toString()));

        return ksp.get(0).getTotalCost();
    }

    /**
     * A Star algorithm initializer
     * @param vertices number of vertices
     * @param cities ArrayList<City>
     * @param ids ArrayList<Integer> (Cities ids)
     * @return total fuel
     */
    protected static double aStarInitializer(int vertices, ArrayList<City> cities, ArrayList<Integer> ids) {
        for (int i = cities.size()-1; i >=0 ; i--) {
            cities.get(i).setH(i);
        }
        System.out.println(Utilities.LINE);

        System.out.print(" "+"Computing the " + 1 + " shortest paths from [" + 0 + "] to [" + (vertices -1) + "] ");
        System.out.println(" "+"using A* algorithm.\n");

        //Progress Bar initialization
        ProgressBar.value(0);
        long timeStart_a = System.currentTimeMillis();
        PathFinder pathFinder=new PathFinder();
        ProgressBar.value(3);
        pathFinder.aStar(0, vertices - 1,cities);

        ids.addAll(pathFinder.outputArrayGen(vertices - 1));
        ProgressBar.value(7);


        ProgressBar.value(10);

        double total_sum = pathFinder.sumFuel(ids);

        //System.out.println(total_sum);
        System.out.print(" "+1 + ") cost: " +total_sum+" - [path] ");
        pathFinder.viewPath2(ids);


        //Number of cities the algorithm pass thought
        int number = pathFinder.getNumber_city();
        System.out.println(" Number of cities: "+number);
        long timeFinish_a = System.currentTimeMillis();
        System.out.println(" "+"Operation took " + (timeFinish_a - timeStart_a) / 1000.0 + " seconds.");
        System.out.println(Utilities.LINE);

        return total_sum;
    }
}
