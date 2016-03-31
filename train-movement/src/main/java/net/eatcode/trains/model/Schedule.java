package net.eatcode.trains.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Schedule implements Serializable {

    public final List<Location> locations = new ArrayList<>();

    public String id;
    public String trainServiceCode;
    public String startDate;
    public String endDate;
    public String atocCode;
    public String origin;
    public String destination;
    public String headcode;
    public String publicDeparture;
    public String publicArrival;
    public String runDays;

    @Override
    public String toString() {
        return "Schedule{" +
                "locations=" + locations +
                ", id='" + id + '\'' +
                ", trainServiceCode='" + trainServiceCode + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", atocCode='" + atocCode + '\'' +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", headcode='" + headcode + '\'' +
                ", publicDeparture='" + publicDeparture + '\'' +
                ", publicArrival='" + publicArrival + '\'' +
                ", runDays='" + runDays + '\'' +
                '}';
    }

    public static class Location implements Serializable {
        public String type;
        public String tipLoc;
        public String publicDeparture;
        public String publicArrival;

        @Override
        public String toString() {
            return "Location{" +
                    "type='" + type + '\'' +
                    ", tipLoc='" + tipLoc + '\'' +
                    ", publicDeparture='" + publicDeparture + '\'' +
                    ", publicArrival='" + publicArrival + '\'' +
                    '}';
        }
    }
}
