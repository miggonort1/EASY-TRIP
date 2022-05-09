package aiss.model;

import java.util.Objects;

public class Passenger implements Comparable<Passenger>{

	//Attributes (Private)
	private String id;
	private String name;
	private String surname;
	private String age;
	
	//Builder C1: Does not receive parameters and create a Passenger object
	public Passenger() {
	}
	
	//Builder C2: Receive some parameters excluding id and create a Passenger object
	public Passenger(String name, String surnames, String age) {
		this.name = name;
		this.surname = surnames;
		this.age = age;
	}

	//Getters - Setters: Methods for all the attributes
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	
	//Equality criteria: All the passengers are different from each others depending on the id
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
		Passenger other = (Passenger) obj;
		return Objects.equals(id, other.id);
	}
	
	//Sorting criteria: All the passengers are sorted depending on the id
	@Override
	public int compareTo(Passenger p) {
		return this.id.compareTo(p.id);
	}

	//String format: Each passenger is represented with all the values
	@Override
	public String toString() {
		return String.format("(%s, %s, %s, %s)", id, name, surname, age);
	}
	
}
