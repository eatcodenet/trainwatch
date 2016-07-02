package net.eatcode.trainwatch.nr.dataimport;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.eatcode.trainwatch.nr.Crs;
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

    public CompletableFuture<Void> populateFromFiles(String crsFile, String tiplocFile) {
        CompletableFuture<Void> result = new CompletableFuture<>();
        populateAsync(crsFile, tiplocFile, result);
        return result;
    }

    private void populateAsync(String crsFile, String tiplocFile, CompletableFuture<Void> result) {
        CompletableFuture.runAsync(() -> {
            try {
                log.info("Starting populating repo");
                Map<String, Crs> crsMap = new HashMap<>();
                new CrsFileParser(crsFile).parse(crs -> crsMap.put(crs.crs, crs));
                log.info("Crs count: {}", crsMap.size());
                new TiplocFileParser(tiplocFile).parse(tiploc -> locationFrom(tiploc, crsMap));
                result.complete(null);
            } catch (Exception e) {
                result.completeExceptionally(e);
            }
        });
    }

    private void locationFrom(Tiploc tiploc, Map<String, Crs> crsMap) {
        if (tiploc.hasStanox()) {
            Crs crs = crsFrom(tiploc, crsMap);
            Location location = newLocation(tiploc, crs);
            log.debug("{}", location);
            repo.put(location);
        }
    }

    private Crs crsFrom(Tiploc tiploc, Map<String, Crs> crsMap) {
        Crs crs = crsMap.get(tiploc.crsCode);
        return (crs == null) ? Crs.empty : crs;
    }

    private Location newLocation(Tiploc tiploc, Crs crs) {
        return new Location(tiploc.stanox, tiploc.description, tiploc.tiploc, tiploc.crsCode,
                new LatLon(crs.lat, crs.lon));
    }

}
