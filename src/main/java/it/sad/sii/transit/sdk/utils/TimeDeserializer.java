package it.sad.sii.transit.sdk.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.io.IOException;

/**
 * Created by mutschlechner on 3/27/15.
 */
public class TimeDeserializer extends JsonDeserializer<LocalTime> {

    @Override
    public LocalTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        String date = jsonParser.getText();
        if (date != null)
            return DateUtils.parseTime(date);
        else
            return null;
    }
}
