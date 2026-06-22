package mate.academy.rickandmorty.config;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@org.mapstruct.MapperConfig(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "mate.academy.rickandmorty.mapper.impl",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public class MapperConfig {
}
