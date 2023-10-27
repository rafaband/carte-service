package br.com.fechaki.carte.route;

import br.com.fechaki.carte.anno.CarteV1Info;
import br.com.fechaki.carte.v1.handler.CarteHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Slf4j
@Component
public record CarteV1Route() {
    private static final String ENDPOINT_API = "/api/v1/carte";

    @Bean
    @CarteV1Info
    public RouterFunction<ServerResponse> carteRouteV1(CarteHandler carteHandler) {
        log.info("Running Carte Route V1");
        return nest(path(ENDPOINT_API),
                route()
                        .POST("", accept(MediaType.APPLICATION_JSON), carteHandler::create)
                        .POST("/", accept(MediaType.APPLICATION_JSON), carteHandler::create)
                        .GET("/audit", carteHandler::audit)
                        .GET("/{idPlace}/page/{page}", accept(MediaType.APPLICATION_JSON), carteHandler::readAll)
                        .GET("/{idPlace}/current", accept(MediaType.APPLICATION_JSON), carteHandler::readCurrent)
//                        .GET("/{idPlace}/stream", accept(MediaType.TEXT_EVENT_STREAM), carteHandler::readStream)
                        .GET("/{idPlace}/{idCarte}", accept(MediaType.APPLICATION_JSON), carteHandler::read)
//                        .PUT("/{idPlace}/{idCarte}", accept(MediaType.APPLICATION_JSON), carteHandler::update)
                        .DELETE("/{idPlace}/{idCarte}", accept(MediaType.APPLICATION_JSON), carteHandler::delete)
                        .PUT("/{idPlace}/{idCarte}/activate", accept(MediaType.APPLICATION_JSON), carteHandler::activate)
                        .PUT("/{idPlace}/{idCarte}/deactivate", accept(MediaType.APPLICATION_JSON), carteHandler::deactivate)
                        .build()
        );
    }
}
