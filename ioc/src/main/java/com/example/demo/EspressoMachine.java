package com.example.demo;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class EspressoMachine implements CoffeeMachine {
    public void brew() {
        try {
            Thread.sleep((long) (2000 + Math.random() * 1000));
        } catch (Exception e) {};
        System.out.println("brewing coffee with Espresso Machine");
    }
}
