package it.unibs.ing.fp.pathfinding;

public class ProgressBar {

    public static void value(int value){
        System.out.print(" [");
        for (int i = 0; i < value; i++) {
            System.out.print("=");
        }
        for (int i = 0; i < Math.abs(value-10); i++) {
            System.out.print(" ");
        }
        System.out.print("] "+value+"0%");
        if(value!=10){
            System.out.print("\r");
        }
        else {
            System.out.println("\n");
        }
    }
}
