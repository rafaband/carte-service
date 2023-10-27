package br.com.fechaki.carte.v1.data.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;

import java.util.LinkedHashSet;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Category {
    String title;
    LinkedHashSet<Product> products;
}
