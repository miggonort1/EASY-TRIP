package aiss.api.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

import org.jboss.resteasy.spi.NotFoundException;

import aiss.model.Passenger;
import aiss.model.repository.FlightRepository;
import aiss.model.repository.MapFlightRepository;

@Path("/passengers")
public class PassengerResource {

	public static PassengerResource _instance = null;
	FlightRepository repository;
	
	private PassengerResource() {
		repository = MapFlightRepository.getInstance();
	}
	
	public static PassengerResource getInstance() {
		if(_instance == null) {
			_instance = new PassengerResource();
		}
		return _instance; 
	}
	
	@GET
	@Produces("application/json")
	public Collection<Passenger> getAllPassengers(@QueryParam("q") String q, @QueryParam("order") String order, @QueryParam("limit") Integer limit, @QueryParam("offset") Integer offset) {
		
		List<Passenger> result = new ArrayList<>(), passengers = repository.getAllPassengers().stream().collect(Collectors.toList());
		int start = offset == null ? 0: offset-1;
		int end = limit == null ? passengers.size(): start  + limit;
		
		for (int i = start; i < end; i++) {
			if (passengers.get(i).getName().contains(q) 
					|| passengers.get(i).getSurname().contains(q) 
					|| passengers.get(i).getAge().contains(q)) {
				result.add(passengers.get(i));
			}	
		}
		
		if (order != null) {
			if (order.equals("name")) {
				Collections.sort(result, Comparator.comparing(Passenger::getName));
			} else if (order.equals("-name")) {
				Collections.sort(result, Comparator.comparing(Passenger::getName).reversed());
			} else if (order.equals("surname")) {
				Collections.sort(result, Comparator.comparing(Passenger::getSurname));
			} else if (order.equals("-surname")) {
				Collections.sort(result, Comparator.comparing(Passenger::getSurname).reversed());
			} else if (order.equals("age")) {
				Collections.sort(result, Comparator.comparing(Passenger::getAge));
			} else if (order.equals("-age")) {
				Collections.sort(result, Comparator.comparing(Passenger::getAge).reversed());
			}
		}
		return result;
	}
	
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Passenger getPassenger(@PathParam("id") String passengerId) {
		
		Passenger passenger = repository.getPassenger(passengerId);
		
		// If the passenger does not exist it throws a 404 Error
		if (passenger == null) {
			throw new NotFoundException("The passenger with id = " + passengerId + " was not found");
		}
		
		return passenger;
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addPassenger(@Context UriInfo uriInfo, Passenger passenger) {
		
		// If the passenger contains a name null or empty it throws a 404 Error
		if (passenger.getName() == null || "".equals(passenger.getName())) {
			throw new NotFoundException("The name of the passenger must not be null");
		} else {
			repository.addPassenger(passenger);
		}
		
		// Builds the response. Returns the flight the has just been added.
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(passenger.getId());
		ResponseBuilder resp = Response.created(uri);
		resp.entity(passenger);			
		return resp.build();
	}
	
	
	@PUT
	@Consumes("application/json")
	public Response updatePassenger(Passenger passenger) {
		
		Passenger oldpassenger = repository.getPassenger(passenger.getId());
		
		// If the passenger does not exist it throws a 404 Error
		if (oldpassenger == null) {
			throw new NotFoundException("The passenger with id="+ passenger.getId() +" was not found");			
		} else {
			// Update name
			if (passenger.getName() != null) {
				oldpassenger.setName(passenger.getName());
			}
			// Update surname
			if (passenger.getSurname() != null) {
				oldpassenger.setSurname(passenger.getSurname());
			}
			// Update age
			if (passenger.getAge() != null) {
				oldpassenger.setAge(passenger.getAge());
			}
		}
		
		// Finally, it creates a 204 Success status
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response removePassenger(@PathParam("id") String passengerId) {
		
		Passenger passenger = repository.getPassenger(passengerId);
		
		// If the passenger does not exist it throws a 404 Error
		if (passenger == null) {
			throw new NotFoundException("The passenger with id = " + passengerId + " was not found");
		} else {
			repository.deletePassenger(passengerId);
		}
		
		// Finally, it creates a 204 Success status
		return Response.noContent().build();
	}
	
}
