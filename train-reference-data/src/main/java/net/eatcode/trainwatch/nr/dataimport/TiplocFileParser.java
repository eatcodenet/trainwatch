package net.eatcode.trainwatch.nr.dataimport;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.eatcode.trainwatch.nr.Tiploc;

class TiplocFileParser {

    private final Logger log = LoggerFactory.getLogger(TiplocFileParser.class);

    private final String sourceFile;
    private final JsonParser parser = new JsonParser();

    TiplocFileParser(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    void parse(ParsedItemProcessor<Tiploc> processor) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(sourceFile))) {
            parseJson(reader, processor);
        } catch (IOException e) {
            log.error("tiploc file parse error", e);
        }
    }

    private void parseJson(BufferedReader reader, ParsedItemProcessor<Tiploc> processor) {
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
                item.description = tld.get("NLCDESC").getAsString().replaceAll("\\(TPS INDIC. ONLY\\)", "") .trim();
                processor.process(item);
            }
        });
    }

}
