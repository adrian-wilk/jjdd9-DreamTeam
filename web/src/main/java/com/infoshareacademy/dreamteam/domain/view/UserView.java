package com.infoshareacademy.dreamteam.domain.view;

import java.util.List;

public class UserView {
    private Long id;
    private String name;
    private String email;
    private String role;
    private List<ReservationView> reservationViews;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<ReservationView> getReservationViews() {
        return reservationViews;
    }

    public void setReservationViews(List<ReservationView> reservationViews) {
        this.reservationViews = reservationViews;
    }
}