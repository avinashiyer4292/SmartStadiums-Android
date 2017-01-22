package com.avinashiyer.bhaukaal.utils;

/**
 * Created by avinashiyer on 1/21/17.
 */

public class Seat {
    private String stand;
    private int seatNo;

    public Seat(){}
    public Seat(String stand, int seatNo) {
        this.stand = stand;
        this.seatNo = seatNo;
    }

    public String getStand() {
        return stand;
    }

    public void setStand(String stand) {
        this.stand = stand;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }
}
