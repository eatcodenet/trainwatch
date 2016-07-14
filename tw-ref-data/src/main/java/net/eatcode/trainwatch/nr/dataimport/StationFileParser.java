package net.eatcode.trainwatch.nr.dataimport;

import com.google.gson.Gson;
import net.eatcode.trainwatch.nr.Station;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class StationFileParser {

	private final Logger log = LoggerFactory.getLogger(StationFileParser.class);
	private final String sourceFile;

	public StationFileParser(String sourceFile) {
		this.sourceFile = sourceFile;
	}

	public void parse(ParsedItemProcessor<Station> stationProcessor) {
		log.info("sourceFile: {}", sourceFile);
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(sourceFile))) {
			StationWrapper wrapper = new Gson().fromJson(reader, StationWrapper.class);
			wrapper.locations.forEach(crs -> stationProcessor.process(crs));
		} catch (IOException e) {
			log.error("Station file parse error", e);
		}
	}

	private static class StationWrapper {
		List<Station> locations;
	}

}
