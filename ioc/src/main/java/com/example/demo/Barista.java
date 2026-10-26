package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Barista {
    @Autowired
    private List<CoffeeMachine> coffeeMachines;

    //@Async

    public void makeCoffee() {
        for (CoffeeMachine coffeeMachine : coffeeMachines) {
            coffeeMachine.brew();
        }
    }

}
