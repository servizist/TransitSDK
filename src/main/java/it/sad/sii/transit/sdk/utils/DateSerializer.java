package it.sad.sii.transit.sdk.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.LocalDate;

import java.io.IOException;

/**
 * Created by ldematte on 10/29/15.
 */
public class DateSerializer extends JsonSerializer<LocalDate> {

    @Override
    public void serialize(LocalDate date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException, JsonProcessingException {
        jsonGenerator.writeString(DateUtils.printDateNice(date));
    }
}