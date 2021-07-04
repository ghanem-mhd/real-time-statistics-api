package com.example.realtimestatisticsapi.statistics;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class StatisticsSerializer extends JsonSerializer<Statistics> {

    @Override
    public void serialize(Statistics value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("sum", String.valueOf(value.getSum()));
        gen.writeStringField("avg", String.valueOf(value.getAvg()));
        gen.writeStringField("max", String.valueOf(value.getMax()));
        gen.writeStringField("min", String.valueOf(value.getMin()));
        gen.writeNumberField("count", value.getCount());
        gen.writeEndObject();
    }
}
