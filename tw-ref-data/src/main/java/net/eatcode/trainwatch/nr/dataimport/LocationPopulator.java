package net.eatcode.trainwatch.nr.dataimport;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.eatcode.trainwatch.nr.Location;
import net.eatcode.trainwatch.nr.LocationRepo;

public class LocationPopulator {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private final Gson gson = new GsonBuilder().create();

	private final LocationRepo repo;

	public LocationPopulator(LocationRepo repo) {
		this.repo = repo;
	}

	public CompletableFuture<Void> populateFromFiles(String locationsFile) {
		CompletableFuture<Void> result = new CompletableFuture<>();
		populateAsync(locationsFile, result);
		return result;
	}

	private void populateAsync(String locationsFile, CompletableFuture<Void> result) {
		CompletableFuture.runAsync(() -> {
			try (Stream<String> lines = Files.lines(Paths.get(locationsFile))) {
				lines.map(this::toLocation).forEach(this::addToRepo);
				result.complete(null);
			} catch (Exception e) {
				log.error("location file parse error", e);
				result.completeExceptionally(e);
			}
		});
	}

	private Location toLocation(String json) {
		return gson.fromJson(json, Location.class);

	}

	private void addToRepo(Location location) {
		repo.put(location);
	}

}
