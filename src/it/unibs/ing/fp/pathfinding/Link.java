package it.unibs.ing.fp.pathfinding;

public class Link {
    Link(int weight, City node){
        this.weight = weight;
        this.node = node;
    }

    public int weight;
    public City node;
}
