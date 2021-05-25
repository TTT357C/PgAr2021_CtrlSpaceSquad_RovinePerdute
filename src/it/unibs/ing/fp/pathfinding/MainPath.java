package it.unibs.ing.fp.pathfinding;

public class MainPath {
    public static void main(String[] args) {
        City head = new City(3);
        head.g = 0;

        City n1 = new City(2);
        City n2 = new City(2);
        City n3 = new City(2);

        head.addBranch(1, n1);
        head.addBranch(5, n2);
        head.addBranch(2, n3);
        n3.addBranch(1, n2);

        City n4 = new City(1);
        City n5 = new City(1);
        City target = new City(0);

        n1.addBranch(7, n4);
        n2.addBranch(4, n5);
        n3.addBranch(6, n4);

        n4.addBranch(3, target);
        n5.addBranch(1, n4);
        n5.addBranch(3, target);

        City res = PathFinder.aStar(head, target);
        PathFinder.printPath(res);
    }
}
