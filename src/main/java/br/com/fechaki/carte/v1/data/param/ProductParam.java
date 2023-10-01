package br.com.fechaki.carte.v1.data.param;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Product", description = "Product Object Definition")
public class ProductParam {

    @Size(min = 3, max = 20)
    @Schema(description = "Your Product Code", example = "PRDCT001")
    private String code;

    @NotBlank
    @Schema(description = "Your Product Name", example = "Coke 290ml")
    private String title;

    @Schema(description = "Product Description", example = "Coke original flavor bottle 290ml")
    private String description;

    @Schema(description = "Product activation for sell. Default is false", example = "true")
    private boolean activated;

    @NotNull
    @DecimalMax(value = "99.99")
    @Digits(integer=3, fraction=2)
    @Schema(description = "Product Price")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

}
