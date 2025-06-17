package com.hotelchatbot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="roomtype")
public class RoomType {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int typeId;
	private String name;

    public RoomType() {}

    public RoomType(String name) {
        this.name = name;
    }

	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
