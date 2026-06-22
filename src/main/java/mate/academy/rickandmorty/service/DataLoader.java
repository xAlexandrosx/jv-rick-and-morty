package mate.academy.rickandmorty.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import lombok.AllArgsConstructor;
import mate.academy.rickandmorty.dto.CreateCharacterRequestDto;
import mate.academy.rickandmorty.dto.RickAndMortyResponseDto;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataLoader {

    private static final String API_URL = "https://rickandmortyapi.com/api/character";

    private final CharacterService service;
    private final ObjectMapper objectMapper;

    @EventListener(ApplicationReadyEvent.class)
    public void loadContent() {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest getRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(API_URL))
                    .build();

            HttpResponse<String> response =
                    httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

            RickAndMortyResponseDto apiResponse = objectMapper.readValue(
                    response.body(),
                    RickAndMortyResponseDto.class
            );

            if (apiResponse != null && apiResponse.getResults() != null) {
                for (CreateCharacterRequestDto requestDto : apiResponse.getResults()) {
                    service.save(requestDto);
                }
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Failed to load and parse character data", e);
        }
    }
}
