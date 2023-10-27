package br.com.fechaki.carte.v1.handler;

import br.com.fechaki.carte.v1.data.entity.CarteEntity;
import br.com.fechaki.carte.v1.data.model.AuditData;
import br.com.fechaki.carte.v1.data.model.Carte;
import br.com.fechaki.carte.v1.data.param.CarteParam;
import br.com.fechaki.carte.v1.event.AuditEvent;
import br.com.fechaki.carte.v1.mapper.CarteMapper;
import br.com.fechaki.carte.v1.service.CarteService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@Component
public final class CarteHandler {
    private final CarteMapper mapper = Mappers.getMapper(CarteMapper.class);
    private final CarteService service;
    private final AuditEvent<CarteEntity> auditEvent;
    private final ValidatorHandler validatorHandler;

    public CarteHandler(CarteService service, ValidatorHandler validatorHandler, AuditEvent<CarteEntity> auditEvent) {
        this.service = service;
        this.validatorHandler = validatorHandler;
        this.auditEvent = auditEvent;
    }

    public Mono<ServerResponse> audit(ServerRequest request) {
        log.trace(request.toString());

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(auditEvent.getSink(), AuditData.class);
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        log.trace(request.toString());
        Mono<CarteParam> data = request.bodyToMono(CarteParam.class);
        return data
                .doOnNext(validatorHandler::validate)
                .mapNotNull(mapper::toEntity)
                .flatMap(service::create)
                .doOnNext(auditEvent::notifyCreate)
                .mapNotNull(entity -> URI.create(String.format("%s/%s/%s", request.uri().getPath(), entity.getIdPlace(), entity.getId())))
                .flatMap(uri -> ServerResponse.created(uri).build());
    }

    public Mono<ServerResponse> read(ServerRequest request) {
        log.trace(request.toString());
        String idPlace = request.pathVariable("idPlace");
        String idCarte = request.pathVariable("idCarte");

        return ServerResponse.ok().body(service.read(idPlace, idCarte), Carte.class);
    }

    public Mono<ServerResponse> readCurrent(ServerRequest request) {
        log.trace(request.toString());
        String idPlace = request.pathVariable("idPlace");

        return ServerResponse.ok().body(service.readCurrent(idPlace).map(mapper::toModel), Carte.class);
    }

    public Mono<ServerResponse> readAll(ServerRequest request) {
        log.trace(request.toString());
        String idPlace = request.pathVariable("idPlace");
        int pageNumber = Integer.parseInt(request.queryParam("pageNumber").orElse("0"));
        int pageSize = Integer.parseInt(request.queryParam("pageSize").orElse("10"));
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return ServerResponse.ok().body(service.readAll(idPlace, pageRequest), Carte.class);
    }

/*    public Mono<ServerResponse> readStream(ServerRequest request) {
        log.info(request.toString());
        String idPlace = request.pathVariable("idPlace");

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(event.getSink().filter(item -> idPlace.equals(item.comment())), Carte.class);
    }*/

   /* public Mono<ServerResponse> update(ServerRequest request) {
        log.info(request.toString());
        Mono<CarteParam> data = request.bodyToMono(CarteParam.class);
        String idPlace = request.pathVariable("idPlace");
        String idCarte = request.pathVariable("idCarte");

        return ServerResponse.status(HttpStatus.PARTIAL_CONTENT)
                .body(data.map(mapper::toEntity).flatMap(item -> service.update(idPlace, idCarte, item));
//                        .doOnNext(event::notifyUpdate)
//                        .map(mapper::toModel), Carte.class);
    }*/

    public Mono<ServerResponse> activate(ServerRequest request) {
        log.info(request.toString());
        String idPlace = request.pathVariable("idPlace");
        String idCarte = request.pathVariable("idCarte");

        return service
                .activate(idPlace, idCarte)
                .then(Mono.defer(() -> ServerResponse.accepted().build()));
//                .doOnNext(r -> event.notifyActivate(idPlace, idCarte))
//                .flatMap(item -> ServerResponse.accepted().build());
    }

    public Mono<ServerResponse> deactivate(ServerRequest request) {
        log.info(request.toString());
        String idPlace = request.pathVariable("idPlace");
        String idCarte = request.pathVariable("idCarte");

        return service
                .deactivate(idPlace, idCarte)
                .then(Mono.defer(() -> ServerResponse.accepted().build()));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        log.info(request.toString());
        String idPlace = request.pathVariable("idPlace");
        String idCarte = request.pathVariable("idCarte");

        return service
                .delete(idPlace, idCarte)
                .then(Mono.defer(() -> ServerResponse.accepted().build()));
    }
}
