package com.interview.roomoccupancymanager.model.dto.output.serializer;

import java.io.IOException;
import javax.money.format.MonetaryAmountFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.ToStringMonetaryAmountFormat;
import org.javamoney.moneta.ToStringMonetaryAmountFormat.ToStringMonetaryAmountFormatStyle;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class MoneySerializer extends JsonSerializer<Money> {
    private static final MonetaryAmountFormat MONETARY_AMOUNT_FORMAT = ToStringMonetaryAmountFormat.of(ToStringMonetaryAmountFormatStyle.MONEY);

    @Override
    public void serialize(final Money money, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(MONETARY_AMOUNT_FORMAT.format(money));
    }

}
