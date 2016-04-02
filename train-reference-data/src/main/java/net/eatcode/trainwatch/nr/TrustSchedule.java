package net.eatcode.trainwatch.nr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TrustSchedule implements Serializable {

    private static final long serialVersionUID = 2L;

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
        return "Schedule [id=" + id + ", trainServiceCode=" + trainServiceCode + ", startDate=" + startDate
                + ", endDate=" + endDate + ", atocCode=" + atocCode + ", origin=" + origin + ", destination="
                + destination + ", headcode=" + headcode + ", publicDeparture=" + publicDeparture + ", publicArrival="
                + publicArrival + ", runDays=" + runDays + ", locations=" + locations + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        return Objects.equals(this.id, ((TrustSchedule) obj).id);
    }

    public static class Location implements Serializable {
        private static final long serialVersionUID = 1L;
        public String type;
        public String tipLoc;
        public String publicDeparture;
        public String publicArrival;

        @Override
        public String toString() {
            return "Location{" + "type='" + type + '\'' + ", tipLoc='" + tipLoc + '\'' + ", publicDeparture='"
                    + publicDeparture + '\'' + ", publicArrival='" + publicArrival + '\'' + '}';
        }
    }
}
