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

    private static final String START_API_URL = "https://rickandmortyapi.com/api/character";

    private final CharacterService service;
    private final ObjectMapper objectMapper;

    @EventListener(ApplicationReadyEvent.class)
    public void loadContent() {
        HttpClient httpClient = HttpClient.newHttpClient();
        String nextUrl = START_API_URL;

        try {
            while (nextUrl != null && !nextUrl.isEmpty()) {
                HttpRequest getRequest = HttpRequest.newBuilder()
                        .GET()
                        .uri(URI.create(nextUrl))
                        .build();

                HttpResponse<String> response =
                        httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() != 200) {
                    throw new RuntimeException("API returned error status: "
                            + response.statusCode() + " Body: " + response.body());
                }

                RickAndMortyResponseDto apiResponse = objectMapper.readValue(
                        response.body(),
                        RickAndMortyResponseDto.class
                );

                if (apiResponse != null && apiResponse.getResults() != null) {
                    for (CreateCharacterRequestDto requestDto : apiResponse.getResults()) {
                        service.save(requestDto);
                    }

                    nextUrl = apiResponse.getInfo()
                            != null ? apiResponse.getInfo().getNext() : null;

                    if (nextUrl != null) {
                        Thread.sleep(500);
                    }
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load and parse character data", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Data loading was interrupted", e);
        }
    }
}
