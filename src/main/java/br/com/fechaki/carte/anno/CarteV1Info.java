package br.com.fechaki.carte.anno;

import br.com.fechaki.carte.exception.model.ErrorMessage;
import br.com.fechaki.carte.v1.data.model.Carte;
import br.com.fechaki.carte.v1.data.param.CarteParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@RouterOperations({
        @RouterOperation(
                method = RequestMethod.POST,
                path = "/",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                operation = @Operation(
                        security = @SecurityRequirement(name = "bearer-key"),
                        description = "Create new Carte",
                        operationId = "createCarte",
                        tags = "Carte",
                        requestBody = @RequestBody(
                                description = "Carte Detail",
                                required = true,
                                content = @Content(
                                        schema = @Schema(implementation = CarteParam.class)
                                )
                        ),
                        responses = {@ApiResponse(
                                responseCode = "201",
                                description = "Created",
                                headers = @Header(name = "Location", description = "Path to get the created Carte"),
                                content = @Content(schema = @Schema(hidden = true))
                        ), @ApiResponse(
                                responseCode = "400",
                                description = "Bad Request response",
                                content = {@Content(
                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = ErrorMessage.class)
                                )}
                        ), @ApiResponse(
                                responseCode = "401",
                                description = "Authentication Failure (Unauthorized)",
                                content = @Content(schema = @Schema(hidden = true))
                        )}
                )
        ),
        @RouterOperation(
                method = RequestMethod.GET,
                path = "/{idPlace}/page/{page}",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE,
                operation = @Operation(
                        security = @SecurityRequirement(name = "bearer-key"),
                        description = "Read all Carte ",
                        operationId = "createCarte",
                        tags = "Carte",
                        parameters = {@Parameter(
                                in = ParameterIn.PATH,
                                name = "idPlace",
                                description = "Your Place ID",
                                example = "650af0e13737733c72a88262",
                                required = true
                        ), @Parameter(
                                in = ParameterIn.PATH,
                                name = "page",
                                description = "Page number. Starts at Zero",
                                example = "0",
                                required = true
                        )},
                        responses = {@ApiResponse(
                                responseCode = "200",
                                description = "List of Place Carte",
                                content = @Content(
                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                        array = @ArraySchema(schema = @Schema(implementation = Carte.class))
                                )
                        ), @ApiResponse(
                                responseCode = "404",
                                description = "Not Found",
                                content = {@Content(
                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = ErrorMessage.class)
                                )}
                        ), @ApiResponse(
                                responseCode = "401",
                                description = "Authentication Failure (Unauthorized)",
                                content = @Content(schema = @Schema(hidden = true))
                        )}
                )
        )
//        @RouterOperation(
//                method = RequestMethod.DELETE,
//                path = "/players/{id}",
//                operation =
//                @Operation(
//                        description = "Delete player by id common router",
//                        operationId = "deletePlayerById",
//                        tags = "player",
//                        responses = {
//                                @ApiResponse(
//                                        responseCode = "200",
//                                        description = "Delete player by id response",
//                                        content = {
//                                                @Content(
//                                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
//                                                        schema = @Schema(implementation = Player.class))
//                                        }),
//                                @ApiResponse(
//                                        responseCode = "404",
//                                        description = "Not found response",
//                                        content = {
//                                                @Content(
//                                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
//                                                        schema = @Schema(implementation = ErrorResponse.class))
//                                        })
//                        })),
//        @RouterOperation(
//                method = RequestMethod.GET,
//                path = "/players",
//                operation =
//                @Operation(
//                        description = "Get all players common router",
//                        operationId = "getAllPlayers",
//                        tags = "player",
//                        responses = {
//                                @ApiResponse(
//                                        responseCode = "200",
//                                        description = "Get all players endpoint",
//                                        content = {
//                                                @Content(
//                                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
//                                                        array = @ArraySchema(schema = @Schema(implementation = Player.class)))
//                                        }),
//                                @ApiResponse(
//                                        responseCode = "400",
//                                        description = "Not found response",
//                                        content = {
//                                                @Content(
//                                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
//                                                        schema = @Schema(implementation = ErrorResponse.class))
//                                        })
//                        })),
//        @RouterOperation(
//                method = RequestMethod.GET,
//                path = "/players/{id}",
//                operation =
//                @Operation(
//                        description = "Get player by id common router",
//                        operationId = "getPlayerById",
//                        tags = "player",
//                        responses = {
//                                @ApiResponse(
//                                        responseCode = "200",
//                                        description = "Get player by id response",
//                                        content = {
//                                                @Content(
//                                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
//                                                        schema = @Schema(implementation = Player.class))
//                                        }),
//                                @ApiResponse(
//                                        responseCode = "400",
//                                        description = "Bad Request response",
//                                        content = {
//                                                @Content(
//                                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
//                                                        schema = @Schema(implementation = ErrorResponse.class))
//                                        }),
//                                @ApiResponse(
//                                        responseCode = "404",
//                                        description = "Not found response",
//                                        content = {
//                                                @Content(
//                                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
//                                                        schema = @Schema(implementation = ErrorResponse.class))
//                                        })
//                        }))
})
public @interface CarteV1Info {
}
