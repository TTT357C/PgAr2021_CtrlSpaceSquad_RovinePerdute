package it.unibs.ing.fp.pathfinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class PathFinder {
    public static City aStar(City start, City target){

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
                City m = edge.node;
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

    public static void printPath(City target){
        City n = target;

        if(n==null)
            return;

        ArrayList<Integer> ids = new ArrayList<>();

        while(n.parent != null){
            ids.add(n.id);
            n = n.parent;
        }
        ids.add(n.id);
        Collections.reverse(ids);

        for(int id : ids){
            System.out.print(id + " ");
        }
        System.out.println("");
    }
}

