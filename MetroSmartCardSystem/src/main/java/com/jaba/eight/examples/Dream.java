package com.jaba.eight.examples;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Dream implements Comparable {
    String title;
    Integer cost;
    Integer sip;
    FREQUENCY frequency;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Dream(String title, Integer cost, Integer sip, FREQUENCY frequency) {
        super();
        this.title = title;
        this.cost = cost;
        this.sip = sip;
        this.frequency = frequency;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Dream(String title, Integer cost) {
        super();
        this.title = title;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Dream [title=" + title + ", cost=" + cost + ", sip=" + sip + ", frequency=" + frequency + "]";
    }

    public static Map<INSTRUMENT, Dream> getMap() {

        Map<INSTRUMENT, Dream> dreamMap = new LinkedHashMap<>();
        dreamMap.put(INSTRUMENT.PPF, new Dream("MtPt", 200000, 20000, FREQUENCY.YEARLY));
        dreamMap.put(INSTRUMENT.SBI, new Dream("Golden Bangle", 100000, 12000, FREQUENCY.YEARLY));
        dreamMap.put(INSTRUMENT.LIC8K, new Dream("Comp", 200000, 8000, FREQUENCY.YEARLY));
        dreamMap.put(INSTRUMENT.HI, new Dream("Health", 400000, 40000, FREQUENCY.YEARLY));
        dreamMap.put(INSTRUMENT.LIC48K, new Dream("Retiaral", 4000000, 48000, FREQUENCY.YEARLY));
        dreamMap.put(INSTRUMENT.PF, new Dream("Pension", 10000000, 7000, FREQUENCY.MONTHLY));
        dreamMap.put(INSTRUMENT.MF_I, new Dream("SY_MARR", 4000000, 30000, FREQUENCY.YEARLY));
        dreamMap.put(INSTRUMENT.SIP, new Dream("SUV_GHAR", 10000000, 10000, FREQUENCY.MONTHLY));
        dreamMap.put(INSTRUMENT.TERM1, new Dream("FIN_SEC", 0, 9000, FREQUENCY.YEARLY));
        dreamMap.put(INSTRUMENT.TERM2, new Dream("MORE_FIN_SEC", 0, 11000, FREQUENCY.MONTHLY));
        dreamMap.put(INSTRUMENT.SIP2, new Dream("TEMPLE", 0, 10000, FREQUENCY.YEARLY));
        dreamMap.put(INSTRUMENT.PROXY, new Dream("MORE_FIN_SEC", 11000));
        return dreamMap;
    }

    public static List<String> getKeyList() {
        return getMap().keySet().stream().map(e -> e.toString()).collect(Collectors.toList());
    }

    public static List<Dream> getDreamList() {
        return getMap().keySet().stream().map(e -> getMap().get(e)).collect(Collectors.toList());
    }

    public static List<Dream> getValueList() {
        return getMap().keySet().stream().map(e -> getMap().get(e)).collect(Collectors.toList());
    }

    public static enum INSTRUMENT {
        PPF, SBI, LIC8K, HI, LIC48K, PF, MF_I, SIP, TERM1, TERM2, PROXY, SIP2;
    }

    public static enum FREQUENCY {
        YEARLY, NONE, MONTHLY
    }

    public Integer getSip() {
        return sip;
    }

    public void setSip(Integer sip) {
        this.sip = sip;
    }

    public FREQUENCY getFrequency() {
        return frequency;
    }

    public void setFrequency(FREQUENCY frequency) {
        this.frequency = frequency;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Dream other = (Dream) obj;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }

    @FunctionalInterface
    public static interface Format {
        String format(String value);

        default String formatSimple(String format) {
            return "Simple formatted " + format;
        }
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Dream) {
            return ((Dream) o).getTitle().compareTo(this.getTitle());
        }
        return 0;
    }

    public static int compareTitle(Dream d1, Dream d2) {
        return d1.getTitle().compareTo(d2.getTitle());
    }
}
