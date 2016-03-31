package net.eatcode.trainwatch.nr.dataimport;

import net.eatcode.trainwatch.nr.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class GeoStanoxPopulator {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final GeoStanoxRepo repo;

    public GeoStanoxPopulator(GeoStanoxRepo repo) {
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
                new TiplocFileParser(tiplocFile).parse(tiploc -> processTiploc(tiploc, crsMap));
                result.complete(null);
            } catch (Exception e) {
                result.completeExceptionally(e);
            }
        });
    }

    private void processTiploc(Tiploc tiploc, Map<String, Crs> crsMap) {
        if (tiploc.hasStanox()) {
            Crs crs = crsMap.get(tiploc.crsCode);
            if (crs != null) {
                log.debug("tiploc stanox: {}", tiploc.stanox);
                GeoStanox stanox = newStanoxFrom(tiploc, crs);
                log.debug("{}", stanox);
                repo.put(stanox);
            }
        }
    }

    private GeoStanox newStanoxFrom(Tiploc tiploc, Crs crs) {
        return new GeoStanox(tiploc.stanox, tiploc.description, tiploc.crsCode, new LatLon(crs.lat, crs.lon));
    }

}
