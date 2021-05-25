package it.unibs.ing.fp.pathfinding;

public class MainPath {
    public static void main(String[] args) {
        Link head = new Link(3);
        head.g = 0;

        Link n1 = new Link(2);
        Link n2 = new Link(2);
        Link n3 = new Link(2);

        head.addBranch(1, n1);
        head.addBranch(5, n2);
        head.addBranch(2, n3);
        n3.addBranch(1, n2);

        Link n4 = new Link(1);
        Link n5 = new Link(1);
        Link target = new Link(0);

        n1.addBranch(7, n4);
        n2.addBranch(4, n5);
        n3.addBranch(6, n4);

        n4.addBranch(3, target);
        n5.addBranch(1, n4);
        n5.addBranch(3, target);

        Link res = PathFinder.aStar(head, target);
        PathFinder.printPath(res);
    }
}
