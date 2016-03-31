package net.eatcode.trainwatch.nr.dataimport;

import net.eatcode.trainwatch.nr.CrsRepo;
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
