package net.eatcode.trainwatch.nr.dataimport;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.eatcode.trainwatch.nr.Tiploc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

class TiplocFileParser {

    private final Logger log = LoggerFactory.getLogger(TiplocFileParser.class);

    private final String sourceFile;
    private final JsonParser parser = new JsonParser();

    TiplocFileParser(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    CompletableFuture<Integer> parse(ParsedItemProcessor<Tiploc> processor) {
        CompletableFuture<Integer> result = new CompletableFuture<>();
        CompletableFuture.runAsync(() -> {
            try (BufferedReader reader = Files.newBufferedReader(Paths.get(sourceFile))) {
                parseJson(reader, processor, result);
            } catch (IOException e) {
                log.error("tiploc file parse error", e);
                result.completeExceptionally(e);
            }
        });
        return result;
    }

    private void parseJson(BufferedReader reader, ParsedItemProcessor<Tiploc> processor, CompletableFuture<Integer> result) {
        final AtomicInteger count = new AtomicInteger(0);
        JsonArray data = parser.parse(reader).getAsJsonObject().getAsJsonArray("TIPLOCDATA");
        data.forEach(o -> {
            JsonObject tld = o.getAsJsonObject();
            String stanox = tld.get("STANOX").getAsString().trim();
            if (!stanox.isEmpty()) {
                Tiploc item = new Tiploc();
                item.stanox = stanox;
                item.crsCode = tld.get("3ALPHA").getAsString().trim();
                item.nlc = tld.get("NLC").getAsString().trim();
                item.tiploc = tld.get("TIPLOC").getAsString().trim();
                item.description = tld.get("NLCDESC").getAsString().trim();
                processor.process(item);
                count.getAndIncrement();
            }
        });
        result.complete(count.intValue());
    }

}
