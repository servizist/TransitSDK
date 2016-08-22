package it.sad.sii.transit.sdk.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.io.IOException;

/**
 * Created by ldematte on 10/29/15.
 */
public class DateDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        String date = jsonParser.getText();
        return DateUtils.parseDateNice(date);
    }
}