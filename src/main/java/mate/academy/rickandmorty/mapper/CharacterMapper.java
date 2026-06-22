package mate.academy.rickandmorty.mapper;

import mate.academy.rickandmorty.config.MapperConfig;
import mate.academy.rickandmorty.dto.CharacterDto;
import mate.academy.rickandmorty.dto.CreateCharacterRequestDto;
import mate.academy.rickandmorty.model.CharacterModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CharacterMapper {

    CharacterDto toDto(CharacterModel character);

    @Mapping(target = "externalId", source = "id")
    CharacterModel toEntity(CreateCharacterRequestDto requestDto);
}
