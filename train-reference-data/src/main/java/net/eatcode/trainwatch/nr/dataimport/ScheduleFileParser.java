package net.eatcode.trainwatch.nr.dataimport;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.eatcode.trainwatch.nr.TrustSchedule;

public class ScheduleFileParser {

    private final Logger log = LoggerFactory.getLogger(ScheduleFileParser.class);

    private final String sourceFile;
    private final Gson gson = new GsonBuilder().create();
    private final boolean stopOnError = true;

    public ScheduleFileParser(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public CompletableFuture<Void> parse(ParsedItemProcessor<TrustSchedule> scheduleProcessor) {
        log.debug("sourceFile: {}", this.sourceFile);
        CompletableFuture<Void> result = new CompletableFuture<>();
        CompletableFuture.runAsync(() -> {
            try (Stream<String> lines = Files.lines(Paths.get(sourceFile)).parallel()) {
                lines.map(this::toScheduleGson)
                    .forEach(s -> scheduleProcessor.process(s));
                result.complete(null);
            } catch (Exception e) {
                log.error("schedule file parse error", e);
                if (stopOnError)
                    result.completeExceptionally(e);
            }
        });
        return result;
    }

    private TrustSchedule toScheduleGson(String json) {
        return gson.fromJson(json, TrustSchedule.class);
    }

}
