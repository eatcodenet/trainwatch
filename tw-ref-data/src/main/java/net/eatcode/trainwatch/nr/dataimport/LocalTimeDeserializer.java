package net.eatcode.trainwatch.nr.dataimport;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class LocalTimeDeserializer implements JsonDeserializer<LocalTime> {

	private static final DateTimeFormatter NR_DATE = DateTimeFormatter.ofPattern("HHmm");

	@Override
	public LocalTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		return LocalTime.parse(json.getAsString(), NR_DATE);
	}

}
