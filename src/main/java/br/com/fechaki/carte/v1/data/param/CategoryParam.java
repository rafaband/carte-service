package br.com.fechaki.carte.v1.data.param;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Category", description = "Category Object Definition")
public class CategoryParam {
    @NotBlank
    @Size(min = 3, max = 100)
    @Schema(description = "Your Category Title", example = "Soft Drinks")
    private String title;

    @NotNull
    @Size(min = 1, max = 20)
    @ArraySchema(arraySchema = @Schema(description = "List of Products"))
    private LinkedHashSet<ProductParam> products;
}
