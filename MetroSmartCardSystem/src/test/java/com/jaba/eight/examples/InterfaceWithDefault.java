package com.jaba.eight.examples;

public interface InterfaceWithDefault {

    default double sqrt(double n) {
        return Math.sqrt(n);
    }
}
