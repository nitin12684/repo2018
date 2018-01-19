package com.jaba.eight.examples;

import com.jaba.eight.examples.Dream.FREQUENCY;

public interface DreamMaker {

    Dream getInstance(String title, Integer cost, Integer sip, FREQUENCY frequency);
}
