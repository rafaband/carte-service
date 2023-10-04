package br.com.fechaki.carte.exception.model;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.Map;

@Getter
@Builder
public class ErrorMessage {
    private final String path;
    private final String requestId;
    private final ErrorMessageType errorType;
    private final Map<String, Object> messages;
    private final Instant timestamp;
}
