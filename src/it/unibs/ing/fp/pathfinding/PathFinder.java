package it.unibs.ing.fp.pathfinding;

import it.unibs.ing.fp.rovineperdute.Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class PathFinder {

    private static int number_city;

    public static City aStar(int start_, int target_){

        City start=Main.getCities().get(start_);
        City target=Main.getCities().get(target_);

        PriorityQueue<City> closedList = new PriorityQueue<>();
        PriorityQueue<City> openList = new PriorityQueue<>();

        //Calcolo Euristico
        start.f = start.g + start.calculateHeuristic(target);
        openList.add(start);

        while(!openList.isEmpty()){
            City n = openList.peek();
            if(n == target){
                return n;
            }

            for(Link edge : n.neighbors){
                int m_int = edge.city_id;
                City m=Main.getCities().get(m_int);
                double totalWeight = n.g + edge.weight;

                if(!openList.contains(m) && !closedList.contains(m)){
                    m.parent = n;
                    m.g = totalWeight;
                    m.f = m.g + m.calculateHeuristic(target);
                    openList.add(m);
                } else {
                    if(totalWeight < m.g){
                        m.parent = n;
                        m.g = totalWeight;
                        m.f = m.g + m.calculateHeuristic(target);

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
        return null;
    }

    public static int printPath(int target){

        City n=Main.getCities().get(target);

        if(n==null)
            return -1;

        ArrayList<Integer> ids = new ArrayList<>();

        while(n.parent != null){
            ids.add(n.id);
            n = n.parent;
        }
        ids.add(n.id);
        Collections.reverse(ids);

        int cont=0;
        for(int id : ids){
            if(cont!=0) {
                System.out.print(" => ");
            }
            System.out.print(id);
            cont++;
        }
        System.out.println();

        //Sum and number of city calculation
        int sum=0;
        number_city=0;
        for (int i = 0; i < ids.size()-1; i++) {
            for (int j = 0; j < Main.getCities().get(ids.get(i)).getNeighbors().size(); j++) {
                if (Main.getCities().get(ids.get(i)).getNeighbors().get(j).city_id==ids.get(i+1)) {
                    sum+=(Main.getCities().get(ids.get(i)).getNeighbors().get(j).weight);
                    number_city++;
                }
            }
        }
        return sum;
    }

    public static int getNumber_city() {
        return number_city;
    }
}

