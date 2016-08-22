package it.sad.sii.transit.sdk.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.io.IOException;

/**
 * Created by mutschlechner on 3/26/15.
 */
public class TimeSerializer extends JsonSerializer<LocalTime> {

    @Override
    public void serialize(LocalTime dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException, JsonProcessingException {
        if (dateTime != null)
            jsonGenerator.writeString(DateUtils.printTime(dateTime));
        else
            jsonGenerator.writeNull();
    }
}
