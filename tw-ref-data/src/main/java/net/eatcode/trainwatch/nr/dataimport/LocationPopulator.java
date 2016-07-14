package net.eatcode.trainwatch.nr.dataimport;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.eatcode.trainwatch.nr.Station;
import net.eatcode.trainwatch.nr.LatLon;
import net.eatcode.trainwatch.nr.Location;
import net.eatcode.trainwatch.nr.LocationRepo;
import net.eatcode.trainwatch.nr.Tiploc;

public class LocationPopulator {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final LocationRepo repo;

    public LocationPopulator(LocationRepo repo) {
        this.repo = repo;
    }

    public CompletableFuture<Void> populateFromFiles(String stationsFile, String tiplocFile) {
        CompletableFuture<Void> result = new CompletableFuture<>();
        populateAsync(stationsFile, tiplocFile, result);
        return result;
    }

    private void populateAsync(String stationsFile, String tiplocFile, CompletableFuture<Void> result) {
        CompletableFuture.runAsync(() -> {
            try {
                log.info("Starting populating repo");
                Map<String, Station> crsMap = new HashMap<>();
                new StationFileParser(stationsFile).parse(crs -> crsMap.put(crs.crs, crs));
                log.info("Station count: {}", crsMap.size());
                new TiplocFileParser(tiplocFile).parse(tiploc -> locationFrom(tiploc, crsMap));
                result.complete(null);
            } catch (Exception e) {
                result.completeExceptionally(e);
            }
        });
    }

    private void locationFrom(Tiploc tiploc, Map<String, Station> crsMap) {
        if (tiploc.hasStanox()) {
            Station station = crsFrom(tiploc, crsMap);
            Location location = newLocation(tiploc, station);
            log.debug("{}", location);
            repo.put(location);
        }
    }

    private Station crsFrom(Tiploc tiploc, Map<String, Station> crsMap) {
        Station station = crsMap.get(tiploc.crsCode);
        return (station == null) ? Station.empty : station;
    }

    private Location newLocation(Tiploc tiploc, Station station) {
        return new Location(tiploc.stanox, tiploc.description, tiploc.tiploc, tiploc.crsCode,
                new LatLon(station.lat, station.lon));
    }

}
