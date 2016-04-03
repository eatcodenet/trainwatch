package net.eatcode.trainwatch.nr.dataimport;

import net.eatcode.trainwatch.nr.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class GeoLocationPopulator {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final GeoLocationRepo repo;

    public GeoLocationPopulator(GeoLocationRepo repo) {
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
                new TiplocFileParser(tiplocFile).parse(tiploc -> makeStanoxFrom(tiploc, crsMap));
                result.complete(null);
            } catch (Exception e) {
                result.completeExceptionally(e);
            }
        });
    }

    private void makeStanoxFrom(Tiploc tiploc, Map<String, Crs> crsMap) {
        if (tiploc.hasStanox()) {
            Crs crs = crsMap.get(tiploc.crsCode);
            if (crs != null) {
                log.debug("tiploc stanox: {}", tiploc.stanox);
                GeoLocation stanox = newStanoxFrom(tiploc, crs);
                log.debug("{}", stanox);
                repo.put(stanox);
            }
        }
    }

    private GeoLocation newStanoxFrom(Tiploc tiploc, Crs crs) {
        return new GeoLocation(tiploc.stanox, tiploc.description, tiploc.crsCode, new LatLon(crs.lat, crs.lon));
    }

}
