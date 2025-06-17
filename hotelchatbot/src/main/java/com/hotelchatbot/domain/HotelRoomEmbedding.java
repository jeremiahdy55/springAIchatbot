package com.hotelchatbot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

// A separate entity to store embeddings because Hibernate cannot map the pgvector datatype well
@Entity
@Table(name = "hotel_room_embeddings")
public class HotelRoomEmbedding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // pgvector field
    @Column(columnDefinition = "vector(1536)", nullable = false)
    private float[] embedding;

    @OneToOne
    @JoinColumn(name = "hotel_room_id", nullable = false, unique = true)
    private HotelRoom hotelRoom;

    public HotelRoomEmbedding() {}

    public HotelRoomEmbedding(float[] embedding, HotelRoom hotelRoom) {
        this.embedding = embedding;
        this.hotelRoom = hotelRoom;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float[] getEmbedding() {
        return embedding;
    }

    public void setEmbedding(float[] embedding) {
        this.embedding = embedding;
    }

    public HotelRoom getHotelRoom() {
        return hotelRoom;
    }

    public void setHotelRoom(HotelRoom hotelRoom) {
        this.hotelRoom = hotelRoom;
    }
}