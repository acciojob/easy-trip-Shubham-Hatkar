package com.driver.view;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AirportService
{
    @Autowired
    AirportRepository airportRepository;

    public void addAirport(Airport airport)
    {
        airportRepository.addAirport(airport);
    }

    public void addPassenger(Passenger passenger)
    {
        airportRepository.addPassenger(passenger);
    }

    public void addFlight(Flight flight)
    {
        airportRepository.addFlight(flight);
    }

    public String getLargestAirportName()
    {
        List<Pair> list = airportRepository.getAllPairsOfAirportAndNoOfTerminals();
        Collections.sort(list, (a, b) -> (a.noOfTerminals > b.noOfTerminals) ? 1 : -1);
        int size = list.size();
        if(size >= 2 && list.get(size - 1).noOfTerminals == list.get(size - 2).noOfTerminals)
        {
            List<String> airportNamesList = new ArrayList<>();
            int prev = list.get(size - 1).noOfTerminals;
            int i = size - 1;
            while(i >= 0 && list.get(i).noOfTerminals == prev)
            {
                airportNamesList.add(list.get(i).airportName);
                i--;
            }
            if(airportNamesList.size() == 1) return airportNamesList.get(0);

            Collections.sort(airportNamesList);
            return airportNamesList.get(0);
        }
        return list.get(list.size() - 1).airportName;
    }

    public String getAirportNameFromFlightId(Integer flightId)
    {
        return airportRepository.getAirportNameFromFlightId(flightId);
    }

    public String bookATicket(Integer flightId, Integer passengerId)
    {
        return airportRepository.bookATicket(flightId, passengerId);
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity)
    {
        return airportRepository.getShortestDurationOfPossibleBetweenTwoCities(fromCity,toCity);
    }

    public String cancelATicket(Integer flightId, Integer passengerId)
    {
        return airportRepository.cancelATicket(flightId,passengerId);
    }
}
