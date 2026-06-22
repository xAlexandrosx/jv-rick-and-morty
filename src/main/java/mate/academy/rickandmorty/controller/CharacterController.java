package mate.academy.rickandmorty.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.AllArgsConstructor;
import mate.academy.rickandmorty.dto.CharacterDto;
import mate.academy.rickandmorty.service.CharacterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/characters")
@Tag(name = "Rick and Morty characters endpoints.")
public class CharacterController {

    private final CharacterService service;

    @GetMapping
    @Operation(summary = "get random character wiki.")
    public CharacterDto getRandomCharacter() {
        return service.getRandomCharacter();
    }

    @GetMapping("/{string}")
    @Operation(summary = "get characters who's names contain substring.")
    public List<CharacterDto> getCharactersWhereNameContainSubstring(
            @RequestParam String substring) {
        return service.findByNameContainingSubstring(substring);
    }
}
