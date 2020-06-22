package com.tibiacalcs.tibiadata.api.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class EmbededJsonDateDeserializer extends StdDeserializer<Date> {

  public EmbededJsonDateDeserializer(Class t) {
    super(t);
  }

  @SuppressWarnings("unused")
  public EmbededJsonDateDeserializer() {
    this(null);
  }

  @Override
  public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    SimpleDateFormat formatter
        = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
    formatter.setTimeZone(TimeZone.getTimeZone(ZoneId.of("CET")));

    jsonParser.nextValue();
    String date = jsonParser.getText();

    // Read remaining tokens for date embeded document
    jsonParser.nextValue();
    jsonParser.nextValue();
    jsonParser.nextValue();

    try {
      return formatter.parse(date);
    } catch (ParseException e) {
      log.error("Error parsing date {}", date);

      return null;
    }
  }

}