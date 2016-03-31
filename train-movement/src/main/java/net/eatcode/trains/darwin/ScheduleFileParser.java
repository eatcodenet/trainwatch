package net.eatcode.trains.darwin;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.eatcode.trains.model.Schedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ScheduleFileParser {

    private final Logger log = LoggerFactory.getLogger(ScheduleFileParser.class);

    private final String sourceFile;
    private final JsonParser parser = new JsonParser();

    public ScheduleFileParser(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public CompletableFuture<Void> parse(ParsedItemProcessor<Schedule> scheduleProcessor) {
        log.debug("sourceFile: {}", this.sourceFile);
        CompletableFuture<Void> result = new CompletableFuture<>();
        CompletableFuture.runAsync(() -> {
            try (Stream<String> lines = Files.lines(Paths.get(sourceFile))) {
                lines
                    .filter(onlyLinesWithLocation())
                    .map(this::toSchedule)
                    .filter(s -> s.id != null)
                    .forEach(s -> scheduleProcessor.process(s));
                result.complete(null);
            } catch (Exception e) {
                log.error("schedule file parse error", e);
                result.completeExceptionally(e);
            }
        });
        return result;
    }

    private Predicate<String> onlyLinesWithLocation() {
        return (line) -> line.contains("schedule_location");
    }

    private Schedule toSchedule(String json) {
        JsonObject o = parser.parse(json).getAsJsonObject().getAsJsonObject("JsonScheduleV1");
        Schedule schedule = new Schedule();
        schedule.startDate = o.get("schedule_start_date").getAsString();
        schedule.endDate = o.get("schedule_end_date").getAsString();
        schedule.runDays = getOrBlank(o, "schedule_days_runs");
        schedule.atocCode = getOrBlank(o, "atoc_code");
        JsonObject segment = o.getAsJsonObject("schedule_segment");
        schedule.id = o.get("CIF_train_uid").getAsString();
        schedule.trainServiceCode = segment.get("CIF_train_service_code").getAsString();
        schedule.headcode = getOrBlank(segment, "signalling_id");
        segment.getAsJsonArray("schedule_location").forEach(sl -> {
            JsonObject loc = sl.getAsJsonObject();
            Schedule.Location location = new Schedule.Location();
            location.type = loc.get("location_type").getAsString();
            location.tipLoc = loc.get("tiploc_code").getAsString();
            location.publicDeparture = getOrBlank(loc, "public_departure");
            location.publicArrival = getOrBlank(loc, "public_arrival");
            if (location.type.equals("LO")) {
                schedule.origin = location.tipLoc;
                schedule.publicDeparture = location.publicDeparture;
            }

            if (location.type.equals("LT")) {
                schedule.destination = location.tipLoc;
                schedule.publicArrival = location.publicArrival;
            }
            location.publicDeparture = getOrBlank(loc, "publicDeparture");
            // schedule.locations.add(location);
        });
        return schedule;
    }

    private String getOrBlank(JsonObject o, String field) {
        JsonElement val = o.get(field);
        if (val == null || val.isJsonNull()) {
            return "";
        }
        return val.getAsString();
    }

}
