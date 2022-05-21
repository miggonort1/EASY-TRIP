package aiss.api.resources2;

import java.util.Arrays;
import java.util.Collection;

import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

public class Fqas {
	
	
	private String uri = "https://city-events-api.ew.r.appspot.com/api/fqas";

	
	public Collection<Fqas> getAll() {
		ClientResource cr = null;
		Fqas [] fqas = null;
		try {
			cr = new ClientResource(uri);
			fqas = cr.get(Fqas[].class);
			
		} catch (ResourceException re) {
			System.err.println("Error when retrieving all Fqas: " + cr.getResponse().getStatus());
			throw re;
		}
		
		return Arrays.asList(fqas);
	}
	

	public Fqas getFqa(String fqaId) {
		
		ClientResource cr = null;
		Fqas list = null;
		try {
			cr = new ClientResource(uri + "/" + fqaId);
			list = cr.get(Fqas.class);
			
		} catch (ResourceException re) {
			System.err.println("Error when retrieving the fqa: " + cr.getResponse().getStatus());
		}
		
		return list;
	}
	

	public Fqas addFqa(Fqas f) {
		
		ClientResource cr = null;
		Fqas resultFqa = null;
		try {
			cr = new ClientResource(uri);
			cr.setEntityBuffering(true);		
			resultFqa = cr.post(f,Fqas.class);
			
		} catch (ResourceException re) {
			System.err.println("Error when adding the fqa: " + cr.getResponse().getStatus());
		}
		
		return resultFqa;
		

	}
	
	public boolean updateFqa(Fqas fqa) {
		
		ClientResource cr = null;
		boolean success = true;
		try {
			cr = new ClientResource(uri);
			cr.setEntityBuffering(true);		
			cr.put(fqa);
			
			
		} catch (ResourceException re) {
			System.err.println("Error when updating the fqa: " + cr.getResponse().getStatus());
			success = false;
		}
		
		return success;
	}
	

	public boolean deleteFqa (String fqaId) {
		
		ClientResource cr = null;
		boolean success = true;
		try {
			cr = new ClientResource(uri + "/" + fqaId);
			cr.setEntityBuffering(true);	
			cr.delete();
			
		} catch (ResourceException re) {
			System.err.println("Error when deleting the fqa: " + cr.getResponse().getStatus());
			success = false;
			throw re;
		}
		
		return success;
		
	}

}
