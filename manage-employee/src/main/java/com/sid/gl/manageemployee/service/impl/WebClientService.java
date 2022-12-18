package com.sid.gl.manageemployee.service.impl;

import com.sid.gl.manageemployee.dto.ApiResponse;
import com.sid.gl.manageemployee.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class WebClientService {
    private final WebClient webClient;

    public WebClientService(WebClient.Builder builder) {
        this.webClient =
                builder
                        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .baseUrl("http://localhost:9090/api").build();
    }

    public Flux<ApiResponse> findDepartments(){
        return this.webClient.get()
                .uri("/department")
                .retrieve()
                .bodyToFlux(ApiResponse.class);
    }

    public Mono<?> findDepById(Long id){
        return this.webClient.get()
                .uri("/department/{id}",id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> {
                    if (response.statusCode().is4xxClientError()) {
                        log.error("Response from service is 4xx");
                    } else {
                        log.error("Response from service is 5xx");
                    }
                    return response.bodyToMono(ApiResponse.class)
                            .flatMap(errorBody -> Mono.error(new ResourceNotFoundException(errorBody.getMessage())));
                })
                .bodyToMono(ApiResponse.class);
    }

}
