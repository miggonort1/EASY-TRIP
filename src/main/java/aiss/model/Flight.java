package aiss.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Flight implements Comparable<Flight>{

	//Attributes (Private)
	private String id;
	private String origin;
	private String destination;
	private String airline;
	private String model;
	private List<Passenger> passengers;
	
	//Builder C1: Does not receive parameters and create a Flight object
	public Flight() {
	}
	
	//Builder C2: Receive some parameters excluding id and passengers list and create a Flight object
	public Flight(String origin, String destination, String airline, String model) {
		this.origin = origin;
		this.destination = destination;
		this.airline = airline;
		this.model = model;
	}
	
	//Getters - Setters: Methods for all the attributes
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	//Getters - Setters: Methods for the list attribute
	public List<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}
	
	public Passenger getPassenger(String id) {
		if (this.passengers == null) {
			return null;
		}

		Passenger passenger = null;
		for(Passenger p: this.passengers) {
			if (p.getId().equals(id)) {
				passenger = p;
				break;
			}
		}
		return passenger;
	}
	
	public void addPassenger(Passenger p) {
		if (this.passengers == null) {
			this.passengers = new ArrayList<Passenger>();
		}
		this.passengers.add(p);
	}
	
	public void deletePassenger(Passenger p) {
		this.passengers.remove(p);
	}

	//Equality criteria: All the flights are different from each others depending on the id
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Flight other = (Flight) obj;
		return Objects.equals(id, other.id);
	}

	//Sorting criteria: All the flights are sorted depending on the id
	@Override
	public int compareTo(Flight f) {
		return this.id.compareTo(f.id);
	}

	//String format: Each flight is represented with all the values
	@Override
	public String toString() {
		String list = this.passengers.stream()
				.map(Passenger::toString)
				.collect(Collectors.joining("\n - "));
		return String.format("%s, %s, %s, %s, %s :\n%s", id, origin, destination, airline, model, list);
	}
	
}
