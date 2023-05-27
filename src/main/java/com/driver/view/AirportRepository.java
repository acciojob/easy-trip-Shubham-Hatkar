package com.driver.view;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class AirportRepository
{
    private HashMap<String,Airport> airportDB = new HashMap<>(); // airportName : Airport obj.
    private HashMap<Integer,Flight> flightDB = new HashMap<>(); // flightID : flight obj
    private HashMap<Integer,List<Passenger>> flightAndPassengersDB = new HashMap<>(); // flight : passengers
    private HashMap<Integer,Passenger> passengerDB = new HashMap<>(); // passengerID : passenger obj

    private List<Pair> airportAndNoOfTerminals = new ArrayList<>(); // airport : NoOfTerminals
    private HashMap<Integer, Integer> passengerAndNoOfTickets = new HashMap<>(); // pass. : noOfTickets

    private HashMap<Integer, Integer> flightAndRevenueDB = new HashMap<>(); // flightID : revenue





    public void addAirport(Airport airport)
    {
        airportDB.put(airport.getAirportName(), airport);
        airportAndNoOfTerminals.add(new Pair(airport.getAirportName(),airport.getNoOfTerminals()));
    }

    public void addPassenger(Passenger passenger)
    {
        passengerDB.put(passenger.getPassengerId(), passenger);
    }

    public void addFlight(Flight flight)
    {
        flightDB.put(flight.getFlightId(), flight);
    }

    public List<Pair> getAllPairsOfAirportAndNoOfTerminals()
    {
        return airportAndNoOfTerminals;
    }

    public String getAirportNameFromFlightId(Integer flightId)
    {
        if(flightDB.containsKey(flightId))
            return flightDB.get(flightId).getFromCity().toString();
        return null;
    }

    public String bookATicket(Integer flightId, Integer passengerId)
    {
        Flight flightObj = flightDB.get(flightId);
        if(flightObj == null) return "FAILURE";

        // check No. of passengers are greater than maxcapacity or what ->
        if(flightAndPassengersDB.containsKey(flightId) && flightObj.getMaxCapacity() <= flightAndPassengersDB.get(flightId).size())
        {
            return "FAILURE";
        }

        List<Passenger> passengerList = new ArrayList<>();
        if(flightAndPassengersDB.containsKey(flightId))
        {
            passengerList = flightAndPassengersDB.get(flightId);

            // check if already booked by passenger or what
            for(Passenger p : passengerList)
            {
                if(p.getPassengerId() == passengerId) return "FAILURE";
            }
        }

        passengerList.add(passengerDB.get(passengerId)); // getting and adding passenger obj
        flightAndPassengersDB.put(flightId,passengerList);
        return "SUCCESS";
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity)
    {
        double currDuration = (double) Integer.MAX_VALUE;
        for(Integer key : flightDB.keySet())
        {
            Flight flight = flightDB.get(key);
            if(flight.getFromCity().equals(fromCity) && flight.getToCity().equals(toCity))
            {
                currDuration = Math.min(currDuration, flight.getDuration());
            }
        }
        if(currDuration == (double) Integer.MAX_VALUE) return -1.0;
        return currDuration;
    }

    public String cancelATicket(Integer flightId, Integer passengerId)
    {
        List<Passenger> passengerList = flightAndPassengersDB.get(passengerId);
        if(passengerList.size() == 0) return "FAILURE";

        for(Passenger passenger : passengerList)
        {
            if(passenger.getPassengerId() == passengerId)
            {
                passengerList.remove(passenger);
                return "SUCCESS";
            }
        }
        return "FAILURE";
    }
}
