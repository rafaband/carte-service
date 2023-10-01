package br.com.fechaki.carte.v1.data.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;
import java.util.LinkedHashSet;

@Data
@Builder
@Document(value = "carte")
@EqualsAndHashCode(of = {"idPlace", "title"})
public class CarteEntity {
    @Id
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private String idPlace;
    private String title;
    private Instant startDate;
    private Instant endDate;
    private boolean activated;
    private boolean deleted;
    @CreatedDate
    private Instant created;
    @LastModifiedDate
    private Instant updated;
    @Builder.Default()
    private LinkedHashSet<CategoryEntity> categories = new LinkedHashSet<>();
}