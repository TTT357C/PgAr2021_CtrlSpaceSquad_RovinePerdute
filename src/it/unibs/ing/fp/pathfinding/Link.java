package it.unibs.ing.fp.pathfinding;

public class Link {
    public double weight;
    public int city_id;

    public Link(int city_id){
        this.weight = 0;
        this.city_id = city_id;
    }

    @Override
    public String toString() {
        return "Link{" +
                "weight=" + weight +
                ", city_id=" + city_id +
                '}';
    }
}
