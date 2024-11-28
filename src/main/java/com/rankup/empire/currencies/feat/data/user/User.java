package com.rankup.empire.currencies.feat.data.user;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class User {

    private final UUID uuid;
    private String username;
    private double coins;


    public void withdraw(double value) {
        this.coins -= value;
    }

    public void deposit(double value) {
        this.coins += value;
    }


    public void set(double value) {
        this.coins = value;
    }

}
