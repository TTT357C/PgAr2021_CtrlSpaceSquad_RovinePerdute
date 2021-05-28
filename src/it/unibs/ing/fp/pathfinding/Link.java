package it.unibs.ing.fp.pathfinding;

public class Link {
    private double weight;
    private int city_id;

    /**
     * Generate a Link, weight has to be initialized from outside
     * @param city_id other city
     */
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }
}
