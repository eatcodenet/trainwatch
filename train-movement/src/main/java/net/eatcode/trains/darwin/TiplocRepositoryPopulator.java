package net.eatcode.trains.darwin;

import net.eatcode.trains.model.TiplocRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class TiplocRepositoryPopulator {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final TiplocRepo repo;

    public TiplocRepositoryPopulator(TiplocRepo repo) {
        this.repo = repo;
    }

    public CompletableFuture<Integer> populateFromFile(String fileName) {
        log.info("Starting populating repo from: {}", fileName);
        return new TiplocFileParser(fileName).parse(tiploc -> repo.putByStanox(tiploc));
    }

}
