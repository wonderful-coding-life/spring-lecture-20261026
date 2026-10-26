package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class DripCoffeeMachine implements CoffeeMachine {
    public void brew() {
        try {
            Thread.sleep((long) (2000 + Math.random() * 1000));
        } catch (Exception e) {};
        System.out.println("brewing coffee with Drip Coffee Machine");
    }
}
