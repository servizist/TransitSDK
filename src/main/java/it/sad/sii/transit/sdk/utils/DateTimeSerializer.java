package it.sad.sii.transit.sdk.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.DateTime;

import java.io.IOException;

/**
 * Created by mutschlechner on 3/26/15.
 */
public class DateTimeSerializer extends JsonSerializer<DateTime> {

    @Override
    public void serialize(DateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException, JsonProcessingException {
        if (dateTime != null)
            jsonGenerator.writeString(DateUtils.printDateTime(dateTime));
        else
            jsonGenerator.writeNull();
    }
}
