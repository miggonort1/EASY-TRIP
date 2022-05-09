package aiss.model.repository;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import aiss.model.Flight;
import aiss.model.Passenger;

public class MapFlightRepository implements FlightRepository {

	//Attributes
	public Map<String, Flight> flightMap;
	public Map<String, Passenger> passengerMap;
	private static MapFlightRepository instance = null;
	private int indexFlight = 0;	// Index to create flights' identifiers.	
	private int indexPassenger = 0;	// Index to create passengers' identifiers.	
	
	public static MapFlightRepository getInstance() {
		if (instance == null) {
			instance = new MapFlightRepository();
			instance.init();
		}
		return instance;
	}
	
	public void init() {
		flightMap = new TreeMap<String, Flight>();
		passengerMap = new TreeMap<String, Passenger>();
		
		//Create passengers
		Passenger p1 = new Passenger("Roberto", "García Martínes", "23");
		addPassenger(p1);
		Passenger p2 = new Passenger("Lucía", "López Vladic", "33");
		addPassenger(p2);
		Passenger p3 = new Passenger("Loren", "Hernández Quispe", "40");
		addPassenger(p3);
		Passenger p4 = new Passenger("Pepe", "Sánchez Diaz", "28");
		addPassenger(p4);
		Passenger p5 = new Passenger("Alberto", "Moreno Romero", "33");
		addPassenger(p5);
		Passenger p6 = new Passenger("María", "Giménez Ibáñez", "72");
		addPassenger(p6);
		Passenger p7 = new Passenger("Miguel", "Ortega Castro", "25");
		addPassenger(p7);
		Passenger p8 = new Passenger("Juan", "Rubio Marín", "18");
		addPassenger(p8);
		Passenger p9 = new Passenger("Pablo", "Iglesias Medina", "47");
		addPassenger(p9);
		Passenger p10 = new Passenger("Paula", "Ortiz Zambrano", "52");
		addPassenger(p10);
		Passenger p11 = new Passenger("Lucía", "Molina Blanzo", "36");
		addPassenger(p11);
		Passenger p12 = new Passenger("Elena", "Aguilar Pascual", "66");
		addPassenger(p12);
		Passenger p13 = new Passenger("María", "Duarte Herrera", "50");
		addPassenger(p13);
		Passenger p14 = new Passenger("Paco", "Fuentes Peña", "43");
		addPassenger(p14);
		Passenger p15 = new Passenger("Gloria", "Nieto León", "33");
		addPassenger(p15);
		Passenger p16 = new Passenger("Juan", "Casimiro Caleya", "32");
		addPassenger(p16);
		Passenger p17 = new Passenger("Ana", "Muñoz Alonso", "22");
		addPassenger(p17);
		Passenger p18 = new Passenger("Alicia", "González Álvarez", "27");
		addPassenger(p18);
		Passenger p19 = new Passenger("Enrique", "Gutiérrez Navarro", "38");
		addPassenger(p19);
		Passenger p20 = new Passenger("Fernando", "Torres Díaz", "22");
		addPassenger(p20);
		Passenger p21 = new Passenger("Teresa", "Peña Campos", "19");
		addPassenger(p21);
		Passenger p22 = new Passenger("Sergio", "Caballero Cabañas", "29");
		addPassenger(p22);
		Passenger p23 = new Passenger("Celia", "Reyes Vega", "37");
		addPassenger(p23);
		Passenger p24 = new Passenger("Francisco", "Zambrano Zambrano", "49");
		addPassenger(p24);
		Passenger p25 = new Passenger("María", "Rico Ferrer", "65");
		addPassenger(p25);
		Passenger p26 = new Passenger("José Joaquín", "Conde Ortiz", "22");
		addPassenger(p26);
		Passenger p27 = new Passenger("Luis", "Ramos Vázquez", "31");
		addPassenger(p27);
		Passenger p28 = new Passenger("Sergio", "Serrano Alonso", "25");
		addPassenger(p28);
		Passenger p29 = new Passenger("Daniela", "Suarez Delgado", "27");
		addPassenger(p29);
		Passenger p30 = new Passenger("Jesús", "Ortiz Pecero", "47");
		addPassenger(p30);
		
		//Create flights
		Flight f1 = new Flight("Madrid", "Barcelona", "Iberia", "Airbus A330-300");
		addFlight(f1);
		Flight f2 = new Flight("Sevilla", "Valencia", "Vueling", "Airbus A320-214");
		addFlight(f2);
		Flight f3 = new Flight("Madrid", "Lisboa", "Plus Ultra", "Airbus A340-313");
		addFlight(f3);
		Flight f4 = new Flight("Paris", "Roma", "Air Europa", "Airbus A330-300");
		addFlight(f4);
		Flight f5 = new Flight("Asturias", "Vigo", "Iberia", "Airbus A330-300");
		addFlight(f5);
		
		//Add passengers to flights
		addPassenger(f1.getId(), p1.getId());
		addPassenger(f1.getId(), p2.getId());
		addPassenger(f1.getId(), p3.getId());
		addPassenger(f1.getId(), p4.getId());
		addPassenger(f1.getId(), p5.getId());
		addPassenger(f1.getId(), p6.getId());
		addPassenger(f1.getId(), p7.getId());
		addPassenger(f1.getId(), p8.getId());
		addPassenger(f1.getId(), p9.getId());
		addPassenger(f1.getId(), p10.getId());
		addPassenger(f1.getId(), p11.getId());
		addPassenger(f1.getId(), p12.getId());
		addPassenger(f2.getId(), p13.getId());
		addPassenger(f2.getId(), p14.getId());
		addPassenger(f2.getId(), p15.getId());
		addPassenger(f2.getId(), p16.getId());
		addPassenger(f2.getId(), p17.getId());
		addPassenger(f2.getId(), p18.getId());
		addPassenger(f2.getId(), p19.getId());
		addPassenger(f3.getId(), p20.getId());
		addPassenger(f3.getId(), p21.getId());
		addPassenger(f3.getId(), p22.getId());
		addPassenger(f3.getId(), p23.getId());
		addPassenger(f3.getId(), p24.getId());
		addPassenger(f3.getId(), p25.getId());
		addPassenger(f4.getId(), p26.getId());
		addPassenger(f4.getId(), p27.getId());
		addPassenger(f4.getId(), p28.getId());
		addPassenger(f5.getId(), p29.getId());
		addPassenger(f5.getId(), p30.getId());
	}
	
	//Passengers: All the operations applied for Passenger type
	@Override
	public Collection<Passenger> getAllPassengers() {
		return this.passengerMap.values();
	}

	@Override
	public Passenger getPassenger(String passengerId) {
		return this.passengerMap.get(passengerId);
	}

	@Override
	public void addPassenger(Passenger p) {
		String id = "p" + indexPassenger++;
		p.setId(id);
		passengerMap.put(id, p);
	}

	@Override
	public void updatePassenger(Passenger p) {
		passengerMap.put(p.getId(), p);
	}

	@Override
	public void deletePassenger(String passengerId) {
		passengerMap.remove(passengerId);
	}

	//Flights: All the operations applied for Flight type
	@Override
	public Collection<Flight> getAllFlights() {
		return this.flightMap.values();
	}

	@Override
	public Flight getFlight(String flightId) {
		return this.flightMap.get(flightId);
	}

	@Override
	public void addFlight(Flight f) {
		String id = "f" + indexFlight++;
		f.setId(id);
		flightMap.put(id, f);
	}

	@Override
	public void updateFlight(Flight f) {
		flightMap.put(f.getId(), f);
	}

	@Override
	public void deleteFlight(String flightId) {
		flightMap.remove(flightId);
	}

	@Override
	public Collection<Passenger> getAll(String flightId) {
		return flightMap.get(flightId).getPassengers();
	}

	@Override
	public void addPassenger(String flightId, String passengerId) {
		getFlight(flightId).addPassenger(passengerMap.get(passengerId));
	}

	@Override
	public void removePassenger(String flightId, String passengerId) {
		getFlight(flightId).deletePassenger(passengerMap.get(passengerId));
	}

}
