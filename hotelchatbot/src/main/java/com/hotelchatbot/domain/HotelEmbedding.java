package com.hotelchatbot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

// A separate entity to store embeddings because Hibernate cannot map the pgvector datatype without extra configurations
@Entity
@Table(name = "hotel_embeddings")
public class HotelEmbedding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // pgvector field
    @Column(columnDefinition = "vector(1536)", nullable = false)
    private float[] embedding;

    @OneToOne
    @JoinColumn(name = "hotel_id", nullable = false, unique = true)
    private Hotel hotel;

    public HotelEmbedding(){}

    public HotelEmbedding(float[] embedding, Hotel hotel) {
        this.embedding = embedding;
        this.hotel = hotel;
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

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}