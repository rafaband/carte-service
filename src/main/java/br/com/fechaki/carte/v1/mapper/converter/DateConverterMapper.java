package br.com.fechaki.carte.v1.mapper.converter;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class DateConverterMapper {
    public Instant asInstant(Date dateTime) {
        if(dateTime != null) {
            return dateTime.toInstant();
        }
        return null;
    }
}
