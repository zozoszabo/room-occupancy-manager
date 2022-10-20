package com.interview.roomoccupancymanager.application._bean.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.javamoney.moneta.Money;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class MoneyDeserializer extends JsonDeserializer<Money> {

    @Override
    public Money deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        return Money.parse(jsonParser.getValueAsString());
    }

}
