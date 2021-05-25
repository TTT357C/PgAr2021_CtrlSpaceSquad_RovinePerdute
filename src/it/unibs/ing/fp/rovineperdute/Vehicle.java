package it.unibs.ing.fp.rovineperdute;

public class Vehicle {

    private String team_name;
    private int vehicle_type;

    /**
     * Constructor method of a vehicle
     * @param team_name Vehicle team name
     * @param vehicle_type Integer which indicates the type of vehicle and how it consumes fuel
     *                     - 0 for Team Tonatiuh, zero fuel on difference in altitude
     *                     - 1 for Team Metztli, zero fuel between city on the same altitude
     */
    public Vehicle(String team_name, int vehicle_type) {
        this.team_name = team_name;
        this.vehicle_type = vehicle_type;
    }

    /**
     * Method that return the team name
     * @return Return team name
     */
    public String getTeam_name() {
        return team_name;
    }

    public double calculateFuel(){
        //calculation fuel for team tonatiuh
        if(vehicle_type == 0){

        }

        //calculation fuel for team Metztli
        if(vehicle_type == 1){

        }
    }

}
