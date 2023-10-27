package br.com.fechaki.carte.v1.data.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {
    String code;
    String title;
    String description;
    boolean activated;
    BigDecimal price;
}
