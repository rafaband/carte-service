package br.com.fechaki.carte.exception.model;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.Map;

@Value
@Builder
public class ErrorMessage {
    String path;
    String requestId;
    ErrorMessageType errorType;
    Map<String, Object> messages;
    Instant timestamp;
}
