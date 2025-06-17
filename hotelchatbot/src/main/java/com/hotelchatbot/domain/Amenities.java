package com.hotelchatbot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="amenities")
public class Amenities {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int a_id;
	private String name;

    public Amenities() {}

    public Amenities(String name) {
        this.name = name;
    }
	
	public int getA_id() {
		return a_id;
	}
	public void setA_id(int a_id) {
		this.a_id = a_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
