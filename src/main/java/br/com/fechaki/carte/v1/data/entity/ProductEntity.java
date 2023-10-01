package br.com.fechaki.carte.v1.data.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Builder
@EqualsAndHashCode(of = {"code", "title", "activated"})
public class ProductEntity {
    private String code;
    private String title;
    private String description;
    private boolean activated;
    private boolean deleted;
    private BigDecimal price;
    @CreatedDate
    private Instant created;
    @LastModifiedDate
    private Instant updated;
}