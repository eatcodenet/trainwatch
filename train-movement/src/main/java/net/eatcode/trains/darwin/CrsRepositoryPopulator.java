package net.eatcode.trains.darwin;

import net.eatcode.trains.model.CrsRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrsRepositoryPopulator {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final CrsRepo repo;

    public CrsRepositoryPopulator(CrsRepo repo) {
        this.repo = repo;
    }

    public void populateFromFile(String fileName) {
        log.info("Starting populating repo from: {}", fileName);
        new CrsFileParser(fileName).parse(crs -> repo.put(crs));
    }

}
