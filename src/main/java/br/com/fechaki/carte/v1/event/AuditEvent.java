package br.com.fechaki.carte.v1.event;

import br.com.fechaki.carte.v1.data.model.AuditData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.Map;

@Slf4j
@Component
public final class AuditEvent<T> {
    @Value("${spring.application.name}")
    private String applicationName;
    private final Sinks.Many<ServerSentEvent<AuditData>> sink = Sinks.many().replay().latest();

    public Flux<ServerSentEvent<AuditData>> getSink() {
        return sink.asFlux();
    }
    public void notifyCreate(T entity) {
        sendMessage(createMessage("create", entity));
    }
    public void notifyUpdate(T entity) {
        sendMessage(createMessage("update", entity));
    }
    public void notifyDelete(T entity) {
        sendMessage(createMessage("delete", entity));
    }
    public void notifyActivate(T entity) {
        sendMessage(createMessage("activate", entity));
    }
    public void notifyDeactivate(T entity) {
        sendMessage(createMessage("deactivate", entity));
    }
    private AuditData createMessage(String action, T input) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> inputMap = (input == null) ? null : objectMapper.convertValue(input, new TypeReference<Map<String, Object>>() {});

        return AuditData
                .builder()
                .applicationName(applicationName)
                .input(inputMap)
                .action(action)
                .build();
    }
    private void sendMessage(AuditData data) {
        ServerSentEvent<AuditData> message = ServerSentEvent
                .<AuditData>builder()
                .data(data)
                .event(String.format("%s-%s", applicationName.toUpperCase(), data.getAction().toUpperCase()))
                .build();

        sink.tryEmitNext(message).orThrow();
    }
}
