package br.com.fechaki.carte.v1.mapper.converter;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class LocalDateTimeConverterMapper {
    public Instant asInstant(LocalDateTime dateTime) {
        if(dateTime != null) {
            return dateTime.toInstant(ZoneOffset.UTC);
        }
        return null;
    }

    public LocalDateTime asLocalDateTime(Instant instant) {
        if(instant != null) {
            return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        }
        return null;
    }
}
