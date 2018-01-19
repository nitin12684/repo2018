package com.jaba.eight.examples;

import com.jaba.eight.examples.Dream.FREQUENCY;

public interface GenericMaker<T> {
    T getInstance(String title, Integer cost, Integer sip, FREQUENCY frequency);
}
