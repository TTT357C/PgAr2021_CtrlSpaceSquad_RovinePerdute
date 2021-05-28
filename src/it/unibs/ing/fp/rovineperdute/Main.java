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


    /**
     * Main Method, it uses the Utilities class for constant and algorithm initialization
     * @author Thomas Causetti
     * @param args
     */
    public static void main(String[] args) {

        //========================================================================================================
        //Number of vertices
        int vertices;

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

        int index_t = InputDati.leggiIntero(" Enter the index number for choosing the file ("+1+"-"+listOfFiles.length+"): ",1,listOfFiles.length);
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
        vertices=cities.size();
        //========================================================================================================

        //Vehicle
        //========================================================================================================
        Vehicle team=new Vehicle(Utilities.TEAM_NAME_1,0);
        Vehicle team2=new Vehicle(Utilities.TEAM_NAME_2,1);
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
        System.out.println(Utilities.MENU);
        System.out.println(Utilities.MENU2);
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
                fuel = Utilities.aStarInitializer(vertices,cities,ids);
                fuel2 = Utilities.aStarInitializer(vertices,cities_temp,ids2);
            break;
            //========================================================================================================
            case 2:
                timeStartTot = System.currentTimeMillis();
                //Slow Calculation
                fuel = Utilities.yenPreInitializer(vertices, cities,ids);
                fuel2 = Utilities.yenPreInitializer(vertices, cities_temp,ids2);
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

        //Set Touched_cities
        team.setTouched_cities(output);
        team2.setTouched_cities(output2);

        //Set used fuel
        team.setFuel(fuel);
        team2.setFuel(fuel2);

        //Write to file
        writeXML.writeXML(team,team2);
        System.out.println(" Successfully wrote to the file.");
        //========================================================================================================

        //========================================================================================================
        //End of the execution with time
        System.out.println(Utilities.LINE);
        long timeFinishTot = System.currentTimeMillis();
        System.out.println(" "+"Total operation took " + (timeFinishTot - timeStartTot) / 1000.0 + " seconds.");

        //================================================================
    }




}
