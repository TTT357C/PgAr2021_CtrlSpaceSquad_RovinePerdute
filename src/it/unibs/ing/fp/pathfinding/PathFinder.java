package it.unibs.ing.fp.pathfinding;

import it.unibs.ing.fp.rovineperdute.Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class PathFinder {

    private int number_city;

    public int aStar(int start_, int target_){

        City start=Main.getCities().get(start_);
        City target=Main.getCities().get(target_);

        PriorityQueue<City> closedList = new PriorityQueue<>();
        PriorityQueue<City> openList = new PriorityQueue<>();

        //Calcolo Euristico
        start.setF(start.getG() + start.calculateHeuristic(target));
        openList.add(start);

        while(!openList.isEmpty()){
            City n = openList.peek();
            if(n == target){
                return -1;
            }

            for(Link edge : n.getNeighbors()){
                int m_int = edge.city_id;
                City m=Main.getCities().get(m_int);
                double totalWeight = n.getG() + edge.weight;

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
        return 1;
    }

    public ArrayList<Integer> printPath(int target){

        City n=Main.getCities().get(target);

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

    public double sumFuel(ArrayList<Integer> ids) {
        //Sum and number of city calculation
        double sum=0;
        number_city=0;
        for (int i = 0; i < ids.size()-1; i++) {
            for (int j = 0; j < Main.getCities().get(ids.get(i)).getNeighbors().size(); j++) {
                if (Main.getCities().get(ids.get(i)).getNeighbors().get(j).city_id== ids.get(i+1)) {
                    sum+=(Main.getCities().get(ids.get(i)).getNeighbors().get(j).weight);
                    number_city++;
                }
            }
        }
        return sum;
    }



    public int getNumber_city() {
        return number_city;
    }
}

