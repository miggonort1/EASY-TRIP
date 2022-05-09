package aiss.model.repository;

import java.util.Collection;

import aiss.model.Flight;
import aiss.model.Passenger;

public interface FlightRepository {

	//Passengers: All the operations applied for Passenger type
	public Collection<Passenger> getAllPassengers();
	public Passenger getPassenger(String passengerId);
	public void addPassenger(Passenger p);
	public void updatePassenger(Passenger p);
	public void deletePassenger(String passengerId);
	
	//Flights: All the operations applied for Flight type
	public Collection<Flight> getAllFlights();
	public Flight getFlight(String flightId);
	public void addFlight(Flight f);
	public void updateFlight(Flight f);
	public void deleteFlight(String flightId);
	
	public Collection<Passenger> getAll(String flightId);
	public void addPassenger(String flightId, String passengerId);
	public void removePassenger(String flightId, String passengerId); 

}
