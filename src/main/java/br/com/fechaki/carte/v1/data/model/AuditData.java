package br.com.fechaki.carte.v1.data.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.Date;
import java.util.Map;

@Value
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuditData {
    String applicationName;
    String userId;
    String userName;
    String action;
    Map<String, Object> input;
    Date created;

    @Builder
    public AuditData(String applicationName, String userId, String userName, String action, Map<String, Object> input) {
        this.applicationName = applicationName;
        this.userId = userId;
        this.userName = userName;
        this.action = action;
        this.input = input;
        this.created = new Date();
    }
}
