package net.eatcode.trainwatch.nr.dataimport;

import com.google.gson.Gson;
import net.eatcode.trainwatch.nr.Crs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CrsFileParser {

    private final Logger log = LoggerFactory.getLogger(CrsFileParser.class);
    private final String sourceFile;

    public CrsFileParser(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public void parse(ParsedItemProcessor<Crs> crsProcessor) {
        log.info("sourceFile: {}", sourceFile);
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(sourceFile))) {
            CrsWrapper wrapper = new Gson().fromJson(reader, CrsWrapper.class);
            wrapper.locations.forEach(crs -> crsProcessor.process(crs));
        } catch (IOException e) {
            log.error("Crs file parse error", e);
        }
    }

    private static class CrsWrapper {
        List<Crs> locations;
    }

}
