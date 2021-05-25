package it.unibs.ing.fp.rovineperdute;

import it.unibs.ing.fp.pathfinding.City;

import java.util.ArrayList;

public class Vehicle {

    private String team_name;
    private int vehicle_type;
    private ArrayList<City> touched_cities;
    private double fuel;


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
        touched_cities = new ArrayList<City>();
    }

    /**
     * Method that return the team name
     * @return Return team name
     */
    public String getTeam_name() {
        return team_name;
    }

    /**
     * @return Return the vehicle type
     */
    public int getVehicle_type() {
        return vehicle_type;
    }

    /**
     * @return Return arraylist of touched cities
     */
    public ArrayList<City> getTouched_cities() {
        return touched_cities;
    }

    /**
     * Setter for arraylist touched cities
     * @param touched_cities Arraylist of touched cities
     */
    public void setTouched_cities(ArrayList<City> touched_cities) {
        this.touched_cities = touched_cities;
    }

    /**
     * @return Return a double that rappresent used fuel
     */
    public double getFuel() {
        return fuel;
    }

    /**
     * @param fuel Double used fuel
     */
    public void setFuel(double fuel) {
        this.fuel = fuel;
    }
}
