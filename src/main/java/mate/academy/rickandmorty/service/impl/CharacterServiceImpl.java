package mate.academy.rickandmorty.service.impl;

import java.util.List;
import java.util.Random;
import lombok.AllArgsConstructor;
import mate.academy.rickandmorty.dto.CharacterDto;
import mate.academy.rickandmorty.dto.CreateCharacterRequestDto;
import mate.academy.rickandmorty.mapper.CharacterMapper;
import mate.academy.rickandmorty.model.CharacterModel;
import mate.academy.rickandmorty.repository.CharacterRepository;
import mate.academy.rickandmorty.service.CharacterService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository repository;
    private final CharacterMapper mapper;

    @Override
    public CharacterDto save(CreateCharacterRequestDto requestDto) {
        CharacterModel saved = repository.save(mapper.toEntity(requestDto));
        return mapper.toDto(saved);
    }

    @Override
    public CharacterDto getRandomCharacter() {

        List<CharacterModel> all = repository.findAll();

        if (!all.isEmpty()) {
            Random rand = new Random();
            int randomIndex = rand.nextInt(all.size());
            return mapper.toDto(all.get(randomIndex));
        }

        return null;
    }

    @Override
    public List<CharacterDto> findByNameContainingSubstring(String substring) {
        return repository.findAllByNameContainsIgnoreCase(substring)
                .stream()
                .map(mapper::toDto)
                .toList();
    }
}
