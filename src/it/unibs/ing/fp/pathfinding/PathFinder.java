package it.unibs.ing.fp.pathfinding;

import it.unibs.ing.fp.rovineperdute.Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * @author Thomas Causetti
 */
public class PathFinder {

    /**
     * Number of cities in the A* Path, if the sumFuel Method is not called this variabile value is -1
     */
    private int number_city=-1;

    /**
     * Array with all the cities (Nodes for A*) and all links (Edge for A*)
     */
    private ArrayList<City> cities_arr;

    /**
     * Pathfinding method, it found the best path between two nodes of a graph.
     * It's really fast compared to other methods
     * @param start_ Start node
     * @param target_ End node
     * @param cities Arraylist of cities
     * @return -1 if there are problems, (in other cases 1)
     */
    public int aStar(int start_, int target_, ArrayList<City> cities){

        cities_arr=new ArrayList<>();
        cities_arr=cities;

        City start=cities_arr.get(start_);
        City target=cities_arr.get(target_);

        PriorityQueue<City> closedList = new PriorityQueue<>();
        PriorityQueue<City> openList = new PriorityQueue<>();

        //Heuristic Calculation
        start.setF(start.getG() + start.calculateHeuristic(target));
        openList.add(start);

        //=====================================
        //A*
        //=====================================
        while(!openList.isEmpty()){
            City n = openList.peek();
            if(n == target){
                return -1;
            }

            for(Link edge : n.getNeighbors()){
                int m_int = edge.getCity_id();
                City m=cities_arr.get(m_int);
                double totalWeight = n.getG() + edge.getWeight();

                if(!openList.contains(m) && !closedList.contains(m)){
                    m.setParent(n);
                    m.setG(totalWeight);
                    m.setF(m.getG() + m.calculateHeuristic(target));
                    openList.add(m);
                } else {
                    if(totalWeight < m.getG()){
                        m.setParent(n);
                        m.setG(totalWeight);
                        m.setF(m.getG() + m.calculateHeuristic(target));

                        if(closedList.contains(m)){
                            closedList.remove(m);
                            openList.add(m);
                        }
                    }
                }
            }

            openList.remove(n);
            closedList.add(n);
        }
        //=====================================

        return 1;
    }

    /**
     * It generate the array of cities ids
     * @param target
     * @return ids ArrayList<Integer>
     */
    public ArrayList<Integer> outputArrayGen(int target){

        City n=cities_arr.get(target);

        if(n==null)
            return (new ArrayList<Integer>());

        ArrayList<Integer> ids = new ArrayList<>();

        while(n.getParent() != null){
            ids.add(n.getId());
            n = n.getParent();
        }
        ids.add(n.getId());
        Collections.reverse(ids);

        return ids;
    }

    /**
     * First kind of visualization of the Arraylist
     * @param ids Arraylist with the ids of all the city of the current path
     */
    public void viewPath(ArrayList<Integer> ids) {
        int cont=0;
        for(int id : ids){
            if(cont!=0) {
                System.out.print(" => ");
            }
            System.out.print(id);
            cont++;
        }
        System.out.println();
    }

    /**
     * Second kind of visualization of the Arraylist
     * @param ids Arraylist with the ids of all the city of the current path
     */
    public void viewPath2(ArrayList<Integer> ids) {
        int cont=0;
        System.out.print("[");
        for(int id : ids){
            if(cont!=0) {
                System.out.print("-");
            }
            System.out.print(id);
            cont++;
        }
        System.out.println("]");
    }

    /**
     * Method for the calculation of the total amount of fuel used and the number of city it pass thought
     * @param ids Arraylist with the ids of all the city of the current path
     * @return sum (total fuel)
     */
    public double sumFuel(ArrayList<Integer> ids) {
        //Sum and number of city calculation
        double sum=0;
        number_city=0;
        for (int i = 0; i < ids.size()-1; i++) {
            for (int j = 0; j < cities_arr.get(ids.get(i)).getNeighbors().size(); j++) {
                if (cities_arr.get(ids.get(i)).getNeighbors().get(j).getCity_id()== ids.get(i+1)) {
                    sum+=(cities_arr.get(ids.get(i)).getNeighbors().get(j).getWeight());
                    number_city++;
                }
            }
        }
        return sum;
    }


    /**
     * Getter for the number of city of the path find with aStar
     * @return
     */
    public int getNumber_city() {
        return number_city;
    }
}

