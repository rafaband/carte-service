package br.com.fechaki.carte.v1.data.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Carte", description = "Carte Object Definition")
public class CarteParam {
    @NotBlank
    @Schema(description = "Your Place ID", example = "650af0e13737733c72a88262")
    private String idPlace;

    @NotBlank
    @Size(min = 3, max = 100)
    @Schema(description = "Your Carte Title", example = "The Example Pub")
    private String title;

    @FutureOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @Schema(description = "Date when the Carte should start working. It must be a Present or Future Date", example = "2023-07-03T02:20:00.000+0000")
    private LocalDateTime startDate;

    @Future
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @Schema(description = "Date when the Carte should be stop working. It must be a Future Date higher then start date", example = "2023-07-23T02:20:00.000+0000")
    private LocalDateTime endDate;

    @NotNull
    @Size(min = 1, max = 10)
    @ArraySchema(arraySchema = @Schema(description = "List of Carte Categories"))
    private LinkedHashSet<CategoryParam> categories;
}
