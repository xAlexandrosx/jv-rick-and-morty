package mate.academy.rickandmorty.service;

import java.util.List;
import mate.academy.rickandmorty.dto.CharacterDto;
import mate.academy.rickandmorty.dto.CreateCharacterRequestDto;

public interface CharacterService {

    void save(CreateCharacterRequestDto requestDto);

    CharacterDto getRandomCharacter();

    List<CharacterDto> findByNameContainingSubstring(String substring);
}
