package it.unibs.ing.fp.rovineperdute;

public class Coordinates {

    private int coordinate_x;
    private int coordinate_y;
    private int coordinate_z;

    public Coordinates(int x, int y, int z){

        this.coordinate_x=x;
        this.coordinate_y=y;
        this.coordinate_z=z;

    }

    public int getCoordinate_x() {
        return coordinate_x;
    }

    public int getCoordinate_y() {
        return coordinate_y;
    }

    public int getCoordinate_z() {
        return coordinate_z;
    }

    /**
     * Method for getting distance between 2 points in a 2-dimensional Cartesian plane
     * @param coord Coordinates of the second point
     * @return distance between the 2 points
     * @author Rossi Mirko
     */
    public double distanceMethod(Coordinates coord){

        int diff_x = this.coordinate_x-coord.getCoordinate_x();
        int diff_y = this.coordinate_y-coord.getCoordinate_y();

        double result = Math.sqrt(Math.pow(diff_x, 2)+Math.pow(diff_y, 2));

        return result;
    }

    /**
     * Method for getting the height difference between 2 points
     * @param coord Coordinates of the second point
     * @return the height difference between the 2 points
     * @author Rossi Mirko
     */
    public int heightDifference(Coordinates coord){

        int result = Math.abs(this.coordinate_z-coord.getCoordinate_z());

        return result;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "coordinate_x=" + coordinate_x +
                ", coordinate_y=" + coordinate_y +
                ", coordinate_z=" + coordinate_z +
                '}';
    }
}
