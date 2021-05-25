package it.unibs.ing.fp.rovineperdute;

public class Vehicle {

    private String team_name;
    private int vehicle_type;

    /**
     * Constructor method of a vehicle
     * @param team_name Vehicle team name
     * @param vehicle_type Integer which indicates the type of vehicle and how it consumes fuel
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

}
