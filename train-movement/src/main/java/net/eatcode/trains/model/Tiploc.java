package net.eatcode.trains.model;

import java.io.Serializable;

public class Tiploc implements Serializable {

    public String stanox;
    public String tiploc;
    public String nlc;
    public String crsCode;
    public String description;

    @Override
    public String toString() {
        return "Item{" +
                "stanox='" + stanox + '\'' +
                ", tiploc='" + tiploc + '\'' +
                ", nlc='" + nlc + '\'' +
                ", crsCode='" + crsCode + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
