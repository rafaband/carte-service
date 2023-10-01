package br.com.fechaki.carte.v1.data.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.LinkedHashSet;

@Data
@Builder
@EqualsAndHashCode(of = {"title", "activated"})
public class CategoryEntity {
    private String title;
    private boolean activated;
    private boolean deleted;
    @CreatedDate
    private Instant created;
    @LastModifiedDate
    private Instant updated;
    @Builder.Default()
    private LinkedHashSet<ProductEntity> products = new LinkedHashSet<>();
}
