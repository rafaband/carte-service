package br.com.fechaki.carte.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SpringDocConfig {
    private final BuildProperties buildProperties;

    private Contact getContact() {
        return new Contact()
                .name("Support API")
                .email("support.api@fechaki.com.br")
                .url("https://support.fechaki.com.br");
    }
    private List<Server> getServers() {
        Server local = new Server()
                .url("http://localhost:3780/api/v1/carte")
                .description("Local Environment");
        Server uat = new Server()
                .url("https://uat.api.fechaki.com.br/v1/carte")
                .description("UAT Environment");
        Server prd = new Server()
                .url("https://api.fechaki.com.br/v1/carte")
                .description("Production Environment");
        return List.of(local, uat, prd);
    }
    private ExternalDocumentation getExternalDocumentation() {
        return new ExternalDocumentation()
                .description("Fechaki Carte Wiki Documentation")
                .url("https://wiki.fechaki.com.br/docs");
    }
    private Info getInfo() {
        return new Info()
                .title("Fechaki Carte API")
                .description("Carte Module API Definitions")
                .summary("Carte API for Fechaki App")
                .version(buildProperties.getVersion())
                .termsOfService("https://fechaki.com.br/terms")
                .license(new License().name("Apache 2.0").url("http://springdoc.org"));
    }

    private Components getSecurityComponent() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
        return new Components().addSecuritySchemes("bearer-key", securityScheme);
    }
    @Bean
    public OpenAPI fechakiCarteOpenAPI() {
        Info info = getInfo();
        info.contact(getContact());
        return new OpenAPI()
                .info(info)
                .servers(getServers())
                .externalDocs(getExternalDocumentation())
                .components(getSecurityComponent());
    }

//    @Bean
//    public GroupedOpenApi carteOpenApi() {
//        String[] paths = { "/api/v1/carte/**" };
//        return GroupedOpenApi.builder().group("Carte")
//                .pathsToMatch(paths)
//                .addOpenApiCustomizer(openApi -> openApi.info(new Info().title("Carte API").version("v1")))
//                .build();
//    }
}
