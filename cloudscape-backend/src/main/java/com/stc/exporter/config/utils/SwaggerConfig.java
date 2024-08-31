package com.stc.exporter.config.utils;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.*;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import java.util.Arrays;
import java.util.List;


@Configuration
public class SwaggerConfig {

    private SecurityScheme APIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    // Google OAuth2 인증 설정
    private SecurityScheme kakaoOAuthSecurityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .description("This API uses Kakao OAuth 2 with the authorization code grant flow.")
                .flows(new OAuthFlows()
                        .authorizationCode(new OAuthFlow()
                                .authorizationUrl("https://kauth.kakao.com/oauth/authorize")
                                .tokenUrl("https://kauth.kakao.com/oauth/token")
                                .scopes(new Scopes()
                                        .addString("profile_nickname", "Access to your nickname in Kakao profile"))));
    }

    @Bean
    public OpenAPI OpenAPI() {
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("Bearer Authentication");

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes
                        ("Bearer Authentication", APIKeyScheme()))
//                .components(new Components()
//                        .addSecuritySchemes("kakaoOAuth", kakaoOAuthSecurityScheme()))
                .security(Arrays.asList(securityRequirement))
                .info(new Info().title("API")
                        .description("api")
                        .version("v0.0.1")
                        .license(new License().name("springdoc-openapi v2.3.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("")
                        .url("https://springshop.wiki.github.org/docs"));

    }

    @Bean
    public GroupedOpenApi userApi() {
        String[] paths = {"/**"};
        String[] packagesToScan = {"com.stc.exporter.app.user"};
        return GroupedOpenApi.builder().group("user")
                .pathsToMatch(paths)
                .packagesToScan(packagesToScan)
                .build();
    }
    @Bean
    public GroupedOpenApi diaryApi() {
        String[] paths = {"/**"};
        String[] packagesToScan = {"com.stc.exporter.app.diary"};
        return GroupedOpenApi.builder().group("cbm-diary")
                .pathsToMatch(paths)
                .packagesToScan(packagesToScan)
                .build();
    }

    @Bean
    public GroupedOpenApi emotionApi() {
        String[] paths = {"/**"};
        String[] packagesToScan = {"com.stc.exporter.app.emotion"};
        return GroupedOpenApi.builder().group("emotion")
                .pathsToMatch(paths)
                .packagesToScan(packagesToScan)
                .build();
    }

    @Bean
    public GroupedOpenApi keywordApi() {
        String[] paths = {"/**"};
        String[] packagesToScan = {"com.stc.exporter.app.keyword"};
        return GroupedOpenApi.builder().group("keyword")
                .pathsToMatch(paths)
                .packagesToScan(packagesToScan)
                .build();
    }
}
