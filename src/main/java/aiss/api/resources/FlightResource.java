package aiss.api.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.NotFoundException;

import aiss.model.Flight;
import aiss.model.Passenger;
import aiss.model.repository.FlightRepository;
import aiss.model.repository.MapFlightRepository;

@Path("/flights")
public class FlightResource {
	
	private static FlightResource _instance=null;
	FlightRepository repository;
	
	private FlightResource() {
		repository = MapFlightRepository.getInstance();
	}
	
	public static FlightResource getInstance() {
		if(_instance == null) {
			_instance = new FlightResource();
		}
		return _instance;
	}
	
	@GET
	@Produces("application/json")
	public Collection<Flight> getAllFlights(@QueryParam("order") String order, @QueryParam("isEmpty") Boolean isEmpty, @QueryParam("name") String name) 
	{
		List<Flight> result = new ArrayList<>();
		
		for (Flight flight: repository.getAllFlights()) {
			if (name == null || flight.getOrigin().equals(name)) {
				if (isEmpty == null
						|| (isEmpty && (flight.getPassengers() == null || flight.getPassengers().size() == 0))
						|| (!isEmpty && (flight.getPassengers() != null || flight.getPassengers().size() > 0))) {
					result.add(flight);
				}		
			}
		}
		
		if (order != null) {
			if (order.equals("origin")) {
				Collections.sort(result,Comparator.comparing(Flight::getOrigin));
			
			} else if (order.equals("-origin")) {
				Collections.sort(result, Comparator.comparing(Flight::getOrigin).reversed());
			
			} else if (order.equals("destination")) {
				Collections.sort(result, Comparator.comparing(Flight::getDestination));
			
			} else if (order.equals("-destination")) {
				Collections.sort(result, Comparator.comparing(Flight::getDestination).reversed());
			
			} else if (order.equals("airline")) {
				Collections.sort(result, Comparator.comparing(Flight::getAirline));
			
			} else if (order.equals("-airline")) {
				Collections.sort(result, Comparator.comparing(Flight::getAirline).reversed());
			
			} else if (order.equals("model")) {
				Collections.sort(result, Comparator.comparing(Flight::getModel));
			
			} else if (order.equals("-model")) {
				Collections.sort(result, Comparator.comparing(Flight::getModel).reversed());
			
			} else {
				throw new BadRequestException("The order parameter must be 'origin' , '-origin' , 'destination' , '-destination' ,"
						+ " 'airline' , '-airline' , 'model' , '-model'.");
			}
		}
		return result;
	}
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Flight getFlight(@PathParam("id") String id)
	{
		Flight flight = repository.getFlight(id);
		
		// If the flight does not exist it throws a 404 Error
		if (flight == null) {
			throw new NotFoundException("The flight with id = "+ id +" was not found");		
		}
		
		return flight;
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addFlight(@Context UriInfo uriInfo, Flight flight) {
		
		// All the fields of the flight must not be empty or null, in other case it throws a 400 Error
		// The field passengers from the flight must be empty, in other case it throws a 400 Error
		if (flight.getOrigin() == null || "".equals(flight.getOrigin())) {
			throw new BadRequestException("The origin of the flight must not be null");
		
		} else if (flight.getDestination() == null || "".equals(flight.getDestination())) {
			throw new BadRequestException("The destination of the flight must not be null");
		
		} else if (flight.getAirline() == null || "".equals(flight.getAirline())) {
			throw new BadRequestException("The airline of the flight must not be null");
		
		} else if (flight.getModel() == null || "".equals(flight.getModel())) {
			throw new BadRequestException("The model of the flight must not be null");
		
		} else if (flight.getPassengers() != null) {
			throw new BadRequestException("The passengers property is not editable.");	
		} 
		
		repository.addFlight(flight);


		// Builds the response. Returns the flight the has just been added.
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(flight.getId());
		ResponseBuilder resp = Response.created(uri);
		resp.entity(flight);			
		return resp.build();
	}

	
	@PUT
	@Consumes("application/json")
	public Response updateFlight(Flight flight) {
		Flight oldflight = repository.getFlight(flight.getId());
		
		// If that flight does not exist it throws a 404 Error
		if (oldflight == null) {
			throw new NotFoundException("The flight with id = "+ flight.getId() +" was not found");			
		}
		// If the field of the new flight contain any passenger it throws a 400 Error
		if (flight.getPassengers() != null) {
			throw new BadRequestException("The passengers property is not editable.");
		}
			
		// Update origin
		if (flight.getOrigin() != null) {
			oldflight.setOrigin(flight.getOrigin());
		}
		// Update destination
		if (flight.getDestination() != null) {
			oldflight.setDestination(flight.getDestination());
		}
		// Update airline
		if (flight.getAirline() != null) {
			oldflight.setAirline(flight.getAirline());
		}
		// Update model
		if (flight.getModel() != null) {
			oldflight.setModel(flight.getModel());
		}
		
		// Finally, it creates a 204 Success status
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response removeFlight(@PathParam("id") String id) {
		
		Flight flight = repository.getFlight(id);
		
		// If the flight does not exist it throws a 404 Error
		if (flight == null) {
			throw new NotFoundException("The flight with id = "+ id +" was not found");		
		} else {
			repository.deleteFlight(id);
		}
		
		// Finally, it creates a 204 Success status
		return Response.noContent().build();
	}
	
	@GET
	@Path("/{flightId}/{passengerId}")
	@Produces("application/json")
	public Passenger getPassengerFromAFlight(@PathParam("flightId") String flightId, @PathParam("passengerId") String passengerId) {
		Flight flight = repository.getFlight(flightId);
		Passenger passenger = repository.getPassenger(passengerId);
		
		// If the flight or the passenger do not exist it throws a 404 Error
		// If the flight do not contains on the passenger it throws a 400 Error
		if (flight == null) {
			throw new NotFoundException("The flight with id = " + flightId + " was not found");
		
		} else if (passenger == null) {
			throw new NotFoundException("The passenger with id = " + passengerId + " was not found");
		
		} else if (flight.getPassenger(passengerId) == null) {
			throw new BadRequestException("The passenger with id = " + passengerId + " is not included on the flight with id = " + flightId);
		}
		
		return flight.getPassenger(passengerId);
	}
	
	@POST	
	@Path("/{flightId}/{passengerId}")
	//@Consumes("text/plain")
	@Produces("application/json")
	public Response addPassengerToAFlight(@Context UriInfo uriInfo,@PathParam("flightId") String flightId, @PathParam("passengerId") String passengerId) {				
		
		Flight flight = repository.getFlight(flightId);
		Passenger passenger = repository.getPassenger(passengerId);
		
		// If the flight or the passenger do not exist it throws a 404 Error
		// If the flight do not contains on the passenger it throws a 400 Error
		if (flight == null) {
			throw new NotFoundException("The flight with id = " + flightId + " was not found");
		
		} else if (passenger == null) {
			throw new NotFoundException("The passenger with id = " + passengerId + " was not found");
		
		} else if (flight.getPassenger(passengerId) != null) {
			throw new BadRequestException("The passenger with id = " + passengerId + " is already included on the flight with id = " + flightId);
		} 
		repository.addPassenger(flightId, passengerId);		

		// Builds the response
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(flightId);
		ResponseBuilder resp = Response.created(uri);
		resp.entity(flight);			
		return resp.build();
	}
	
	
	@DELETE
	@Path("/{flightId}/{passengerId}")
	public Response removePassengerFromAFlight(@PathParam("flightId") String flightId, @PathParam("passengerId") String passengerId) {
		
		Flight flight = repository.getFlight(flightId);
		Passenger passenger = repository.getPassenger(passengerId);
		
		// If the flight or the passenger do not exist it throws a 404 Error
		// If the flight do not contains on the passenger it throws a 400 Error
		if (flight == null) {
			throw new NotFoundException("The flight with id = " + flightId + " was not found");
		
		} else if (passenger == null) {
			throw new NotFoundException("The passenger with id = " + passengerId + " was not found");
		
		} else if (flight.getPassenger(passengerId) == null) {
			throw new BadRequestException("The passenger with id = " + passengerId + " is not included on the flight with id = " + flightId);
		
		} else {
			repository.removePassenger(flightId, passengerId);	
		}
		
		// Finally, it creates a 204 Success status
		return Response.noContent().build();
	}
}
